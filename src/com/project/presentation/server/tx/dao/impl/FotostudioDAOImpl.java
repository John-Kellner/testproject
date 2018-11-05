package com.project.presentation.server.tx.dao.impl;

import com.project.presentation.server.tx.dao.IFotoStDAO;
import com.project.presentation.server.tx.dom.AdresseDOM;
import com.project.presentation.server.tx.dom.FotostudioDOM;
import com.project.presentation.shared.dto.AdresseDTO;
import com.project.presentation.shared.dto.FotostudioDTO;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.enumerations.ENotify;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FotostudioDAOImpl implements IFotoStDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * query for database
	 */
	String hql;

	@Override
	public NotificationBean save(FotostudioDTO studio) {
		final NotificationBean notificationBean = new NotificationBean();
		/** Umbauen von DTO nach DOM */

		final FotostudioDOM fotostudioDOM = new FotostudioDOM();
		fotostudioDOM.setName(studio.getName());
		fotostudioDOM.setEmail(studio.getEmail());
		fotostudioDOM.setPassword(studio.getPassword());
		fotostudioDOM.setTelephone_number(studio.getTelephone_number());
		fotostudioDOM.setFax(studio.getFax());
		fotostudioDOM.setLogo(studio.getLogo());
		fotostudioDOM.setOeffnungszeiten(studio.getOeffnungszeiten());
		fotostudioDOM.setPage(studio.getPage());

		final AdresseDTO adresse = studio.getAdresse();
		final AdresseDOM adresseDOM = new AdresseDOM();

		adresseDOM.setStrasse(adresse.getStrasse());
		adresseDOM.setPlz(adresse.getPlz());
		adresseDOM.setOrt(adresse.getOrt());
		adresseDOM.setLand(adresse.getLand());

		final List<AdresseDOM> container = new ArrayList<AdresseDOM>();
		container.add(adresseDOM);
		fotostudioDOM.setAdresses(container);

		em.persist(fotostudioDOM);
		em.flush();

		notificationBean.setNotify(ENotify.SUCCESS);
		return notificationBean;
	}

	@Override
	public FotostudioDTO find(String name) {
		hql = "from FotostudioDOM studio ";
		hql += "where studio.name=:name ";

		final List<FotostudioDOM> resultTopic = em.createQuery(hql, FotostudioDOM.class).setParameter("name",  name).setMaxResults(1).getResultList();

		if (resultTopic.size() == 1) {
			return convert(resultTopic.get(0));
		}
		return null;
	}

	@Override
	public FotostudioDOM findStudioByUserID(long userID){
		hql = "SELECT f FROM FotostudioDOM f INNER JOIN FETCH f.users AS user WHERE user.userId =:userid";
		final FotostudioDOM studio = em.createQuery(hql, FotostudioDOM.class).setParameter("userid", userID).getSingleResult();
		return studio;
	}

	@Override
	public FotostudioDOM findStudioByStudioID(long fotostudioId) {
		hql = "SELECT f FROM FotostudioDOM f WHERE f.fotostudioId =:id";
		final FotostudioDOM studio = em.createQuery(hql, FotostudioDOM.class).setParameter("id", fotostudioId).getSingleResult();
		return studio;
	}

	@Override
	public FotostudioDTO findStudioLogin(String email, String password) {
		hql = "FROM FotostudioDOM fotostudio ";
		hql += "WHERE fotostudio.email=:email ";
		hql += "AND fotostudio.password=:password";

		final List<FotostudioDOM> resultList = em.createQuery(hql, FotostudioDOM.class).setParameter("email", email).setParameter("password", password).getResultList();

		//FIXME DKL Bitte testen
		if (resultList.size() == 1) {
			return convert(resultList.get(0));
			}

		return null;
	}

	@Override
	public FotostudioDTO convert(FotostudioDOM fotostudioDOM) {
		if (fotostudioDOM != null){
			final FotostudioDTO fotostudioDTO = new FotostudioDTO();
			fotostudioDTO.setFotostudioId(fotostudioDOM.getFotostudioId());
			fotostudioDTO.setName(fotostudioDOM.getName());
			fotostudioDTO.setEmail(fotostudioDOM.getEmail());
			fotostudioDTO.setTelephone_number(fotostudioDOM.getTelephone_number());
			fotostudioDTO.setFax(fotostudioDOM.getFax());
			fotostudioDTO.setLogo(fotostudioDOM.getLogo());
			fotostudioDTO.setOeffnungszeiten(fotostudioDOM.getOeffnungszeiten());
			fotostudioDTO.setPage(fotostudioDOM.getPage());
			AdresseDTO adresseDTO = null;
			if (fotostudioDOM.getAdresses() != null){
				final AdresseDOM adresseDOM = fotostudioDOM.getAdresses().get(0);
				adresseDTO = new AdresseDTO();
				adresseDTO.setStrasse(adresseDOM.getStrasse());
				adresseDTO.setOrt(adresseDOM.getOrt());
				adresseDTO.setPLZ(adresseDOM.getPlz());
				adresseDTO.setLand(adresseDOM.getLand());
				adresseDTO.setAdresseID(adresseDOM.getAdresseID());
			}
			fotostudioDTO.setAdresse(adresseDTO);
			return fotostudioDTO;
		}
		return null;
	}
}