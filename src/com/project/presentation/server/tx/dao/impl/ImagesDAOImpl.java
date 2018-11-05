package com.project.presentation.server.tx.dao.impl;

import com.project.presentation.server.tx.dao.IEinstellungDAO;
import com.project.presentation.server.tx.dao.IImagesDAO;
import com.project.presentation.server.tx.dom.ImageDOM;
import com.project.presentation.server.tx.dom.TabItemDOM;
import com.project.presentation.shared.dto.ImageDTO;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.ImageView;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImagesDAOImpl implements IImagesDAO {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private IEinstellungDAO einstellungDAO;

	@Override
	public ImageView save(ImageDTO image, long idEinstellung) {
		final ImageView imageView = new ImageView();
		final NotificationBean notificationBean = new NotificationBean();

		if (idEinstellung != 0){
			final ImageDOM imageDOM = new ImageDOM();
			imageDOM.setName(image.getName());
			imageDOM.setImage(image.getImage());
			imageDOM.setEinstellung(einstellungDAO.find(idEinstellung));

			em.persist(imageDOM);
			imageView.setId(imageDOM.getImagesId());
			em.flush();
			notificationBean.setNotify(ENotify.SUCCESS);
			notificationBean.setMessage("Bild wurder erfolgreich gespeichert !");
		}else {
			notificationBean.setNotify(ENotify.FAILURE);
			notificationBean.setMessage("Image darf ohne ID nicht abgespeichert werden!");
		}

		imageView.setNotification(notificationBean);
		return imageView;
	}

	/**
	 * Laden aller Images zu den Einstellungen
	 * @param
	 * @return
	 */
	@Override
	public List<ImageDTO> loadAllImages(long idEinstellung) {
		final TypedQuery<ImageDOM> query = em.createQuery("SELECT a FROM ImageDOM AS a WHERE a.einstellung.einstellungId = :id", ImageDOM.class);
		query.setParameter("id", idEinstellung);
		final List<ImageDOM> resultList = query.getResultList();

		final List<ImageDTO> images = new ArrayList<>();
		for (ImageDOM imageDOM : resultList) {
			final ImageDTO imageDTO = new ImageDTO();
			imageDTO.setImagesID(imageDOM.getImagesId());
			imageDTO.setName(imageDOM.getName());
			imageDTO.setImage(imageDOM.getImage());
			images.add(imageDTO);
		}

		return images;
	}

	@Override
	public NotificationBean deleteImage(long id) {
		final NotificationBean notificationBean = new NotificationBean();
		try {
			final ImageDOM imageDOM = em.find(ImageDOM.class, id);
			if (imageDOM != null){

				/** Image muss in den TabItems weggeloescht werden */
				if (imageDOM.getEinstellung() != null){
					for (TabItemDOM tabItemDOM : imageDOM.getEinstellung().getTabItem()) {
						if (tabItemDOM.getImage() != null){
							if (tabItemDOM.getImage().getImagesId() == imageDOM.getImagesId()){
								tabItemDOM.setImage(null);
								em.merge(tabItemDOM);
							}
						}
					}
				}

				imageDOM.setEinstellung(null);
				imageDOM.setTabItem(null);

				em.merge(imageDOM);
				em.remove(imageDOM);
				em.flush();
				notificationBean.setNotify(ENotify.SUCCESS);
				notificationBean.setMessage("Erfolgreich gel"+ (char) 246 +"scht!");
			}else {
				notificationBean.setNotify(ENotify.FAILURE);
				notificationBean.setMessage("Das Bild konnte nicht gefunden werden!");
			}
		}catch (Exception e){
			notificationBean.setNotify(ENotify.FAILURE);
			notificationBean.setMessage(e.getMessage());
		}
		return notificationBean;
	}

	@Override
	public ImageDOM find(long imagesID) {
		return em.find(ImageDOM.class, imagesID);
	}

	@Override
	public ImageDTO findImage(long imageID) {
		final ImageDOM imageDOM = find(imageID);
		final ImageDTO image = new ImageDTO();
		image.setImagesID(imageDOM.getImagesId());
		image.setImage(imageDOM.getImage());
		image.setName(imageDOM.getName());
		return image;
	}

}