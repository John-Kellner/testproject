package com.project.presentation.client.eventbus.impl;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.project.presentation.client.eventbus.ClientFactory;
import com.project.presentation.client.management.login.place.LoginPlace;
import com.project.presentation.client.management.login.activity.impl.LoginActivity;
import com.project.presentation.client.management.menu.activity.impl.MenuActivity;
import com.project.presentation.client.management.menu.place.MenuPlace;
import com.project.presentation.client.management.page.activity.impl.PageActivity;
import com.project.presentation.client.management.page.place.PagePlace;
import com.project.presentation.client.management.start.activity.impl.StartActivity;
import com.project.presentation.client.management.start.place.StartPlace;
import com.project.presentation.client.management.studio.activity.impl.ManagementActivity;
import com.project.presentation.client.management.studio.place.ManagementPlace;
import com.project.presentation.client.management.user.activity.impl.UserActivity;
import com.project.presentation.client.management.user.place.UserPlace;

/**
 * Created by john on 27.06.2015.
 */
public class AppActivityMapper implements ActivityMapper {

    private ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory factory){
        super();
        this.clientFactory = factory;
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof LoginPlace){
            return new LoginActivity(clientFactory);
        } else if (place instanceof ManagementPlace){
            return new ManagementActivity(clientFactory);
        } else if (place instanceof UserPlace){
            return new UserActivity(clientFactory);
        } else if (place instanceof MenuPlace){
            return new MenuActivity(clientFactory);
        } else if (place instanceof PagePlace){
            return new PageActivity(clientFactory);
        } else if (place instanceof StartPlace){
            return new StartActivity(clientFactory);
        }
        return null;
    }
}
