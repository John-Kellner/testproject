package com.project.presentation.server.tx.dao.impl;

import com.project.presentation.server.tx.dao.IFotoStDAO;
import com.project.presentation.server.tx.dao.IImagesDAO;
import com.project.presentation.server.tx.dao.IUserDAO;
import com.project.presentation.server.tx.dao.IUserViewDAO;
import com.project.presentation.server.tx.dom.*;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.UserView;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by john on 24.10.2015.
 */

@Repository
public class UserViewDAOImpl implements IUserViewDAO {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private IUserDAO userDAO;

    @Resource
    private IImagesDAO imagesDAO;

    @Resource
    private IFotoStDAO iFotoStDAO;

    @Resource
    private AnlagenService zertifikate;

    @Override
    public UserView loadUserView(LoginDTO loginDTO){
        final UserView userView = new UserView();

        final TypedQuery<EinstellungDOM> query = em.createQuery("SELECT e FROM EinstellungDOM AS e WHERE e.user.userId = :id", EinstellungDOM.class).setParameter("id", loginDTO.getUserID());
        final List<EinstellungDOM> resultList = query.getResultList();

        if (resultList.size() == 1){
            userView.setLogin(loginDTO);
            final EinstellungDOM einstellungDOM = resultList.get(0);
            if (einstellungDOM != null){
                final EinstellungDTO einstellungDTO = new EinstellungDTO();
                einstellungDTO.setEinstellungId(einstellungDOM.getEinstellungId());
                userView.setEinstellung(einstellungDTO);

                /** Templdate hinzufuegen */
                final TemplateDOM templateDOM = einstellungDOM.getTemplate();
                if (templateDOM != null){
                    final TemplateDTO templateDTO = new TemplateDTO();
                    templateDTO.setTemplateId(templateDOM.getTemplateId());
                    templateDTO.setName(templateDOM.getName());
                    userView.getEinstellung().setTemplate(templateDTO);
                }

                /** Tabitems durchlafen */
                final List<TabItemDTO> tabItemDTOs = new ArrayList<>();
                final List<TabItemDOM> tabItems = einstellungDOM.getTabItem();
                for (TabItemDOM tabItemDOM : tabItems) {
                    final TabItemDTO tabItemDTO = new TabItemDTO();
                    tabItemDTO.setTabItemID(tabItemDOM.getTabItemId());
                    tabItemDTO.setName(tabItemDOM.getUeberschrift());
                    tabItemDTO.setTitle(tabItemDOM.getTitle());
                    tabItemDTO.setContent(tabItemDOM.getContent());
                    tabItemDTO.setAnzeigen(tabItemDOM.isAnzeigen());
                    tabItemDTO.setPosition(tabItemDOM.getPosition());

                    final ImageDOM image = tabItemDOM.getImage();
                    if (image != null){
                        final ImageDTO imageDTO = new ImageDTO();
                        imageDTO.setImagesID(image.getImagesId());
                        imageDTO.setImage(image.getImage());
                        imageDTO.setName(image.getName());
                        tabItemDTO.setImage(imageDTO);
                    }

                    tabItemDTOs.add(tabItemDTO);
                }
                Collections.sort(tabItemDTOs);
                userView.getEinstellung().setTabItem(tabItemDTOs);

                /** loadUserView Images */
                userView.setImages(imagesDAO.loadAllImages(userView.getEinstellung().getEinstellungId()));

                /** Aktivierungen */
                final List<AktivierungDTO> aktivierungDTOs = new ArrayList<>();
                if (einstellungDOM.getAktivierung() != null){
                    final List<AktivierungDOM> aktivierungen = einstellungDOM.getAktivierung();
                    for (AktivierungDOM aktivierungDOM : aktivierungen) {
                        final AktivierungDTO aktivierungDTO = new AktivierungDTO();
                        aktivierungDTO.setAktivierungID(aktivierungDOM.getAktivierungID());
                        aktivierungDTO.setDatumVon(aktivierungDOM.getDatumVon());
                        aktivierungDTO.setDatumBis(aktivierungDOM.getDatumBis());
                        aktivierungDTO.setDauer(aktivierungDOM.getDauer());
                        aktivierungDTOs.add(aktivierungDTO);
                    }
                    userView.getEinstellung().setAktivierung(aktivierungDTOs);
                }

                /** User */
                final UserDTO user = userDAO.findUserAndAdresse(loginDTO.getUserID());

                userView.setUser(user);
                if (user.getFotostudio() != null){
                    userView.setStudio(user.getFotostudio());
                }

                /** Foto Studio*/
                final FotostudioDOM studioDOM = iFotoStDAO.findStudioByUserID(loginDTO.getUserID());
                if (studioDOM != null) {
                    userView.setStudio(iFotoStDAO.convert(studioDOM));
                }

                userView.getEinstellung().setHexColor(einstellungDOM.getHexColor());

                // Zertifikate
                userView.setAnlagen(AnlageDOM.convert(einstellungDOM.getAnlagen()));

                /** Notification */
                final NotificationBean notificationBean = new NotificationBean();
                notificationBean.setNotify(ENotify.SUCCESS);
                notificationBean.setMessage("Status OK");
                userView.setNotificationBean(notificationBean);
                return userView;
            }
        }

        return null;
    }
}
