package com.project.presentation.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.view.AbstractView;
import com.project.presentation.shared.view.UserView;

/**
 * Created by john on 27.06.2015.
 */
@RemoteServiceRelativePath("laiquendi")
public interface RPCServiceLaiquendi extends RemoteService {
    AbstractView login(LoginDTO user, boolean isAlwaysLoggedIn);

    NotificationBean saveNewUser(UserDTO user);

    UserView saveSettings(EinstellungDTO view);

    NotificationBean deleteImage(long id);

    UserView loadViewByUrl(String username);

    ImageDTO reloadImage(Long imageID);

    void saveMD5Key(UserDTO user);

   boolean deleteAttachment(Long anlagenID);
}
