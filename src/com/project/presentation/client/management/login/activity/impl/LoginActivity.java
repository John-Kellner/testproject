package com.project.presentation.client.management.login.activity.impl;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.ManagedPlaces;
import com.project.presentation.client.management.login.ILoginView;
import com.project.presentation.client.management.login.activity.ILoginActivity;
import com.project.presentation.client.eventbus.ClientFactory;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;
import com.project.presentation.shared.dto.LoginDTO;
import com.project.presentation.shared.view.AbstractView;
import com.project.presentation.shared.view.StudioView;
import com.project.presentation.shared.view.UserView;

/**
 * Created by john on 27.06.2015.
 */
public class LoginActivity extends AbstractActivity implements ILoginActivity {

    private final RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);
    private final ClientFactory clientFactory;

    public LoginActivity(ClientFactory factory) {
        this.clientFactory = factory;
    }

    @Override
    public void start(AcceptsOneWidget widget, EventBus eventBus) {
        final ILoginView view = clientFactory.getLoginView();
        view.setActivity(this);
        widget.setWidget(view);
    }

    @Override
    public void login(LoginDTO user, boolean isAlwaysLoggedIn, AsyncCallback<AbstractView> view){
        service.login(user, isAlwaysLoggedIn, view);
    }

    @Override
    public void show(EManagedPlace management, Object obj){
        if (obj != null){
            if (obj instanceof UserView){
                clientFactory.getMenuView().setView((UserView) obj);
            }

            if(obj instanceof StudioView){
                clientFactory.getUserView().setView((StudioView) obj);
            }
        }
        clientFactory.getPlaceController().goTo((ManagedPlaces.get(management)));
    }

}
