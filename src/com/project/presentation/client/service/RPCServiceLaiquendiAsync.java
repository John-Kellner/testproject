package com.project.presentation.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.view.AbstractView;
import com.project.presentation.shared.view.UserView;

public interface RPCServiceLaiquendiAsync {

    void saveNewUser(UserDTO user, AsyncCallback<NotificationBean> async);

    void saveSettings(EinstellungDTO view, AsyncCallback<UserView> async);

    void deleteImage(long id, AsyncCallback<NotificationBean> async);

    void loadViewByUrl(String username, AsyncCallback<UserView> async);

    void login(LoginDTO user, boolean isAlwaysLoggedIn, AsyncCallback<AbstractView> async);

    void reloadImage(Long imageID, AsyncCallback<ImageDTO> async);

    void saveMD5Key(UserDTO user, AsyncCallback<Void> async);

    void deleteAttachment(Long anlagenID, AsyncCallback<Boolean> async);
}
