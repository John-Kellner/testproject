package com.project.presentation.client.management.page.controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;
import com.project.presentation.shared.view.UserView;
import gwt.material.design.client.ui.MaterialLoader;
import java.util.logging.Logger;

/**
 * Created by john on 28.07.2015.
 */
public abstract class PageViewController extends Composite {
    private RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);
    protected Logger logger = Logger.getLogger("PageViewController");
    public abstract void onLoadView(UserView view);

    protected void loadView(String username){
        MaterialLoader.showLoading(true);
        service.loadViewByUrl(username, new AsyncCallback<UserView>() {
            @Override
            public void onFailure(Throwable throwable) {
                MaterialLoader.showLoading(false);
            }

            @Override
            public void onSuccess(UserView view) {
                MaterialLoader.showLoading(false);
                onLoadView(view);
            }
        });
    }
}
