package com.project.presentation.server.tx.dao.impl;

import com.project.presentation.server.tx.dao.IFotoStDAO;
import com.project.presentation.server.tx.dao.IStudioViewDAO;
import com.project.presentation.shared.dto.FotostudioDTO;
import com.project.presentation.shared.dto.LoginDTO;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.StudioView;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by john on 24.10.2015.
 */

@Repository
public class StudioViewDAOImpl implements IStudioViewDAO {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private IFotoStDAO iFotoStDAO;

    @Override
    public StudioView loadStudioView(LoginDTO login) {
        final StudioView view = new StudioView();
        view.setLogin(login);

        final FotostudioDTO studio = iFotoStDAO.convert(iFotoStDAO.findStudioByStudioID(login.getUserID()));
        view.setStudio(studio);

        /** Notification */
        final NotificationBean notificationBean = new NotificationBean();
        notificationBean.setNotify(ENotify.SUCCESS);
        notificationBean.setMessage("Status OK");
        view.setNotificationBean(notificationBean);
        return view;
    }
}
