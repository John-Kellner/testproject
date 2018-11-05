package com.project.presentation.client.management.studio.activity.impl;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.ManagedPlaces;
import com.project.presentation.client.management.studio.IManagementView;
import com.project.presentation.client.management.studio.activity.IManagementActivity;
import com.project.presentation.client.eventbus.ClientFactory;

/**
 * Created by john on 27.06.2015.
 */
public class ManagementActivity extends AbstractActivity implements IManagementActivity {

    private ClientFactory clientFactory;

    public ManagementActivity(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget widget, EventBus eventBus) {
        final IManagementView view = clientFactory.getManagementView();
        view.setActivity(this);
        widget.setWidget(view);
    }

    @Override
    public void show(EManagedPlace management, Object view) {
        clientFactory.getPlaceController().goTo((ManagedPlaces.get(management)));
    }
}
