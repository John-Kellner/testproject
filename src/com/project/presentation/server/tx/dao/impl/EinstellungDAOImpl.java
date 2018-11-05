package com.project.presentation.server.tx.dao.impl;

import com.project.presentation.server.tx.dao.*;
import com.project.presentation.server.tx.dao.impl.check.IDuplicateEntry;
import com.project.presentation.server.tx.dom.*;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.enumerations.ENotify;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@Repository
public class EinstellungDAOImpl implements IEinstellungDAO {
	@PersistenceContext
	private EntityManager em;

	@Resource
	private IUserDAO userDAO;

	@Resource
	private ITabItemsDAO tabItemDAO;

	@Resource
	private IImagesDAO imagesDAO;

	@Resource
	private  ITemplateDAO templateDAO;

	@Override
	public NotificationBean save(EinstellungDTO topicDTO) {
		final NotificationBean notificationBean = new NotificationBean();
		/** Umbauen von DTO nach DOM */

		/** Einstellung DTO nach DOM */
		final EinstellungDOM einstellungDOM = new EinstellungDOM();
		//einstellungDOM.setEinstellungId(topicDTO.getEinstellungId());

		/** Tempate DTO nach DOM */
		final TemplateDOM templateDOM = new TemplateDOM();
		templateDOM.setName(topicDTO.getTemplate().getName());
		templateDOM.setEinstellung(einstellungDOM);

		einstellungDOM.setTemplate(templateDOM);

		/** Bidirektional 1:1 Beziehung */
		//templateDOM.setEinstellung(einstellungDOM);
		//einstellungDOM.setTemplate(templateDOM);

		/** List<TabItems> DTO nach DOM */
		final List<com.project.presentation.server.tx.dom.TabItemDOM> tabItemDOMs = new ArrayList<>();

		List<TabItemDTO> tabItems = topicDTO.getTabItem();
		final IDuplicateEntry iDuplicateEntry = tabItemDAO.checkDuplicatedEntry(tabItems);
		if (iDuplicateEntry.isDuplicate()){
			tabItems = iDuplicateEntry.getModifiedList();
		}

		if (tabItems != null && tabItems.size() > 0){
			/** Durchlauf von allen DTOs TabItems */
			for (TabItemDTO tabItemDTO : tabItems) {
				final TabItemDOM tabItemDOM = new TabItemDOM();
				tabItemDOM.setTabItemId(tabItemDTO.getTabItemID());
				tabItemDOM.setContent(tabItemDTO.getContent());
				tabItemDOM.setTitle(tabItemDTO.getTitle());
				tabItemDOM.setAnzeigen(tabItemDTO.isVisible());
				tabItemDOM.setPosition(tabItemDTO.getPosition());
				tabItemDOM.setUeberschrift(tabItemDTO.getName());
				tabItemDOM.setEinstellung(einstellungDOM);

				/** Image */
				if (tabItemDTO.getImage() != null){
					tabItemDOM.setImage(imagesDAO.find(tabItemDTO.getImage().getImagesID()));
				}
				tabItemDOMs.add(tabItemDOM);
			}
		}
		einstellungDOM.setTabItem(tabItemDOMs);

		/** Aktivierung */
		final List<AktivierungDOM> aktivierungDOM = new ArrayList<>();
		if (topicDTO.getAktivierung() != null && topicDTO.getAktivierung().size() > 0){
			for (AktivierungDTO aktivierungDTOItem : topicDTO.getAktivierung()) {
				final AktivierungDOM aktiverungItemDom = new AktivierungDOM();
				aktiverungItemDom.setAktivierungID(aktivierungDTOItem.getAktivierungsID());
				aktiverungItemDom.setDatumVon(aktivierungDTOItem.getDatumVon());
				aktiverungItemDom.setDatumBis(aktivierungDTOItem.getDatumBis());
				aktiverungItemDom.setDauer(aktivierungDTOItem.getDauer());
				aktivierungDOM.add(aktiverungItemDom);
			}
		}

		//einstellungDOM.setAktivierung(aktivierungDOM);
		final UserDOM userDOM = userDAO.findUserByID(topicDTO.getUser().getUserId());

		EinstellungDOM einstellung = null;
		long einstellungId = -1;

		/** Wenn es noch keine Einstellung gibt uebespringe */
		if (userDOM.getEinstellung() != null){
			einstellung = userDOM.getEinstellung();
			einstellungId = einstellung.getEinstellungId();
		}

		einstellungDOM.setHexColor(topicDTO.getHexColor());

		/** Bidirektionale 1:1 Beziehung */
		einstellungDOM.setUser(userDOM);
		userDOM.setEinstellung(einstellungDOM);
		userDOM.setIs128BitLogin(topicDTO.getUser().is128BitLogin());

		em.merge(userDOM);
		if (einstellung != null){
			einstellungDOM.setEinstellungId(einstellungId);
			tabItemDAO.removeAll(einstellungId);
			templateDAO.removeAll(einstellungId);
			em.merge(einstellungDOM);
		}else {
			em.persist(einstellungDOM);
		}
		em.flush();


		notificationBean.setNotify(ENotify.SUCCESS);
		notificationBean.setMessage("Erfolgreich gespeichert");

		if (iDuplicateEntry.isDuplicate()){
			notificationBean.setNotify(ENotify.DUPLICATE_ENTRY);
			notificationBean.setMessage("Ein doppelter Tab Item Eintrag wurde gefunden und konnte nicht abgepeichert werden");
		}

		return notificationBean;
	}

	@Override
	public EinstellungDOM find(long idEinstellung) {
		return em.find(EinstellungDOM.class, idEinstellung);
	}

	/**
	 * Neuer Dummy Eintrag Einstellung
	 * @return
	 */
	@Override
	public EinstellungDTO getNewEinstellung(UserDOM userDOM){
		final UserDTO user = userDAO.findUserAndAdresse(userDOM.getUserId());

		final EinstellungDTO einstellungDTO = new EinstellungDTO();
		einstellungDTO.setUser(user);

		final TemplateDTO templateDTO = new TemplateDTO();
		templateDTO.setName("Designs");
		einstellungDTO.setTemplate(templateDTO);

		final List<TabItemDTO> tabItemDTOs = new ArrayList<>();
		final TabItemDTO qualifikation = new TabItemDTO(0, "Willkommen");
		qualifikation.setPosition(1);
		qualifikation.setTitle("Das Wichtigste im Fokus");
		qualifikation.setContent("Beruflicher Werdegang ...");
		qualifikation.setAnzeigen(true);
		tabItemDTOs.add(qualifikation);

		einstellungDTO.setTabItem(tabItemDTOs);

		return einstellungDTO;
	}
}