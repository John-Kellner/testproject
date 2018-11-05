package com.project.presentation.client.management.user.activity.impl;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.ManagedPlaces;
import com.project.presentation.client.management.start.activity.impl.EDocumentPage;
import com.project.presentation.client.management.user.IUserView;
import com.project.presentation.client.management.user.activity.IUserActivity;
import com.project.presentation.client.eventbus.ClientFactory;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.dto.UserDTO;

/**
 * Created by john on 28.06.2015.
 */
public class UserActivity extends AbstractActivity implements IUserActivity {
    private RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);

    private ClientFactory clientFactory;

    public UserActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget widget, EventBus eventBus) {
        final IUserView view = clientFactory.getUserView();
        view.setActivity(this);
        widget.setWidget(view);
    }

    @Override
    public void saveUser(UserDTO user, AsyncCallback<NotificationBean> asyncCallback){
        service.saveNewUser(user, asyncCallback);
    }

    @Override
    public void show(EManagedPlace management, Object view) {
        clientFactory.getPlaceController().goTo((ManagedPlaces.get(management)));
        if (management == EManagedPlace.START && view != null){
            clientFactory.getPageStart().setPage((EDocumentPage) view);
        }
    }
}
