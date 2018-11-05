package com.project.presentation.client.management.page.activity.impl;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.project.presentation.client.eventbus.ClientFactory;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.ManagedPlaces;
import com.project.presentation.client.management.page.IPageView;
import com.project.presentation.client.management.page.activity.IPageActivity;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;

/**
 * Created by john on 28.07.2015.
 */
public class PageActivity extends AbstractActivity implements IPageActivity {
    private final RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);
    private final ClientFactory clientFactory;
    private String name;

    public PageActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget widget, EventBus eventBus) {
        final IPageView view = clientFactory.getPageView();
        view.setActivity(this);
        widget.setWidget(view);
    }

    @Override
    public void show(EManagedPlace place, Object obj) {
        name = place.name();
        clientFactory.getPlaceController().goTo(ManagedPlaces.get(place));
    }

    @Override
    public String getTokenName(){
        return name;
    }

}
