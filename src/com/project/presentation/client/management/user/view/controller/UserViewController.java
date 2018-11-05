package com.project.presentation.client.management.user.view.controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.project.presentation.client.console.log.Console;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.AbstractView;
import com.project.presentation.shared.view.StudioView;
import com.project.presentation.shared.view.UserView;

/**
 * Created by john on 05.12.2015.
 */
public abstract class UserViewController extends Composite {

    private RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);
    public abstract void setView(StudioView view);
    public abstract void returnToLoginView();

    /** Versuch auf eine Bestehende Session zuzugreifen */
    public void loginWithUserSession() {
        service.login(null, false, new AsyncCallback<AbstractView>() {
            @Override
            public void onFailure(Throwable throwable) {
                Console.log("Service Fehler! ");
            }

            @Override
            public void onSuccess(AbstractView view) {
                if (view != null && view.getNotificationBean().getNotify() == ENotify.SUCCESS){
                    if (view instanceof StudioView){
                        setView((StudioView)view);
                    }

                }else {
                    returnToLoginView();
                }
            }
        });
    }
}
