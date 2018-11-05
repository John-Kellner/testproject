package com.project.presentation.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.server.email.SMTPConfigBean;
import com.project.presentation.server.tx.dom.IHibernateService;
import com.project.presentation.server.upload.bean.DomainBean;
import com.project.presentation.server.upload.bean.UploadBean;
import com.project.presentation.server.userlink.UserLink;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.AbstractView;
import com.project.presentation.shared.view.StudioView;
import com.project.presentation.shared.view.UserView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.List;

/**
 * Created by john on 27.06.2015.
 */
public class RPCServiceLaiquendiImpl extends RemoteServiceServlet implements RPCServiceLaiquendi {
    private static final Logger logger = Logger.getLogger(RPCServiceLaiquendiImpl.class);

    @Autowired
    @Qualifier("domain")
    public DomainBean domainBean;

    @Autowired
    @Qualifier("smtp")
    public SMTPConfigBean smtpBean;

    @Autowired
    @Qualifier("uploadSource")
    public UploadBean uploadBean;

    @Autowired
    @Qualifier("HibernateService")
    public IHibernateService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config
                .getServletContext());
        AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        logger.info("goStraight Service initialize Successfully!");
    }

    /**
     * Log in
     * @param loginDTO
     * @param isAlwaysLoggedIn
     * @return
     */
    @Override
    public AbstractView login(LoginDTO loginDTO, boolean isAlwaysLoggedIn) {
        if (loginDTO == null){
            //Sucht den vorhandenen Session User
            loginDTO = (LoginDTO) getThreadLocalRequest().getSession(true).getAttribute("UserSession");
            if(loginDTO != null){
                return service.loadUserView(loginDTO);
            }
            return null;
        }else {
            loginDTO = service.userOrStudio(loginDTO);

            /** Speicherung in der Usersession */
            if (loginDTO != null){
                if (isAlwaysLoggedIn){
                    /** Benutzer in der Session */
                    getThreadLocalRequest().getSession(true).setAttribute("UserSession", loginDTO);
                }

                switch (loginDTO.getUserTyp()){
                    case USER:
                        final UserView userView = service.loadUserView(loginDTO);
                        putAdvancedSettings(userView);
                        return userView;
                    case STUDIO:
                        final StudioView studioView = service.loadStudioView(loginDTO);
                        putAdvancedStudioSettings(studioView);
                        return studioView;
                }
            } else {
                final NotificationBean notificationBean = new NotificationBean();
                notificationBean.setNotify(ENotify.FAILURE);
                notificationBean.setMessage("Benutzer nicht gefunden oder Passwort falsch!");

                final UserView errorView = new UserView();
                errorView.setNotificationBean(notificationBean);
                return errorView;
            }
        }
        return null;
    }

    /**
     * Anlegen eines neuen Users
     * @param user
     * @return
     */
    @Override
    public NotificationBean saveNewUser(UserDTO user) {
        boolean exists = service.checkUserAlreadyExists(user);
        NotificationBean notification = new NotificationBean();

        if(!exists){
            notification = service.saveNewUser(user);

            /** Email versenden */
            if(notification.getNotify() == ENotify.SUCCESS){
                final UserDTO userDTO = service.findUserForLogin(user.getEmail(), user.getPassword());
                if (userDTO != null){
                    service.sendEmail(smtpBean, userDTO);
                    return notification;
                }
            }
        }else {
            notification.setNotify(ENotify.DUPLICATE_ENTRY);
            notification.setMessage("Benutzer ist bereits vorhanden!");
        }

        return notification;
    }

    @Override
    public UserView saveSettings(EinstellungDTO view) {
        return service.addEinstellung(view);
    }

    @Override
    public NotificationBean deleteImage(long id) {
        return service.deleteImage(id);
    }

    /** Ladet den User in der Webansicht */
    @Override
    public UserView loadViewByUrl(String username){
        final UserView userView = service.loadViewByUrl(username);
        if (userView.getUser() != null){
            return userView;
        }
        return null;
    }

    /**
     * Stellt zusaetzliche Informationen beim User bereit
     * Bsp.: Alle User, die zum Studio gehoeren
     * und den aktuellen User - Link Info ueber seine Seite
     *
     * + QR Code + MD5 Hash Erzeugung falls noch keiner vorhanden
     */
    private void putAdvancedSettings(UserView view){
        //FIXME JKE Fuer den User soll nur seine eigene URL erscheinen
        final List<AvailableUserDTO> availableUserDTOs = service.loadAllUserFromStudio(view.getStudio(), domainBean);
        view.setAvailableUsers(availableUserDTOs);

        /** Ermittlung der UserURL */
        final String userUrl = UserLink.createUserUrl(view.getUser(), domainBean.getDomainname());
        final String md5Url = UserLink.createMD5Url(view.getUser(), domainBean.getDomainname());

        /** Bei allen verfuegbaren Benutzer die zu einem Studio gehoeren wird der Link geladen */
        final AvailableUserDTO currentAvailableUser = new AvailableUserDTO();
        currentAvailableUser.setName(view.getUser().getName());
        currentAvailableUser.setUserUrl(userUrl);
        currentAvailableUser.setMd5Url(md5Url);
        view.setCurrentAvailableUser(currentAvailableUser);

        try { /** MD5 Hash generierung */
            service.createMD5LoginKeyIfNotExist(view.getUser(), userUrl);
        }catch (Exception ex){
            logger.error("MD5 Hash generierung " + ex.getMessage());
        }

        try { /** QR Code generierung*/
            service.createQRCodeIfNotExist(view.getUser(), userUrl, uploadBean);
        }catch (Exception ex){
            logger.error("QRCode generierung " + ex.getMessage());
        }

        try { /** QR Code generierung*/
            service.createQRCodeMD5IfNotExist(view.getUser(), md5Url, uploadBean);
        }catch (Exception ex){
            logger.error("QRCodeMitMD5 generierung " + ex.getMessage());
        }
    }

    @Override
    public ImageDTO reloadImage(Long imageID) {
        return service.reloadImage(imageID);
    }

    public void putAdvancedStudioSettings(StudioView view){
        /** Bei allen verfuegbaren Benutzer die zu einem Studio gehoeren wird der Link geladen */
        final List<AvailableUserDTO> availableUserDTOs = service.loadAllUserFromStudio(view.getStudio(), domainBean);
        view.setAvailableUser(availableUserDTOs);
    }

    @Override
    public void saveMD5Key(UserDTO user){
        service.updateEncyrptionState(user);
    }

    @Override
    public boolean deleteAttachment(Long attachmentID) {
        return service.deleteAttachment(attachmentID);
    }

}
