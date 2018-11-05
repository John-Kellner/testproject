package com.project.presentation.client.management.start.activity.impl;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.project.presentation.client.console.log.Console;
import com.project.presentation.client.eventbus.ClientFactory;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.ManagedPlaces;
import com.project.presentation.client.management.start.IStartView;
import com.project.presentation.client.management.start.activity.IStartActivity;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;

/**
 * Created by john on 12.09.2015.
 */
public class StartActivity extends AbstractActivity implements IStartActivity {
    private final RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);
    private final ClientFactory clientFactory;

    public StartActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget widget, EventBus eventBus) {
        final IStartView view = clientFactory.getPageStart();
        view.setActivity(this);
        widget.setWidget(view);
    }

    @Override
    public void show(EManagedPlace place, Object view) {
        clientFactory.getPlaceController().goTo(ManagedPlaces.get(place));
        if (view != null){
            ((IStartView) clientFactory.getPageStart()).setPage((EDocumentPage) view);
        }
    }
}
