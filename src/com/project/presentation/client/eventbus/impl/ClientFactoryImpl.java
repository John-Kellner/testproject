package com.project.presentation.client.eventbus.impl;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.project.presentation.client.eventbus.ClientFactory;
import com.project.presentation.client.management.login.ILoginView;
import com.project.presentation.client.management.login.view.LoginView;
import com.project.presentation.client.management.menu.IMenuView;
import com.project.presentation.client.management.menu.view.MenuView;
import com.project.presentation.client.management.page.IPageView;
import com.project.presentation.client.management.page.view.PageView;
import com.project.presentation.client.management.start.IStartView;
import com.project.presentation.client.management.start.view.StartView;
import com.project.presentation.client.management.studio.IManagementView;
import com.project.presentation.client.management.studio.view.ManagementView;
import com.project.presentation.client.management.user.IUserView;
import com.project.presentation.client.management.user.view.UserViewM;

/**
 * Created by john on 27.06.2015.
 */
public class ClientFactoryImpl implements ClientFactory {

    private final IManagementView managementView = new ManagementView("FotoStudio");
    private final ILoginView loginView = new LoginView("Login");
    private final IUserView userView = new UserViewM("User");
    private final IMenuView menuView = new MenuView("Menu");
    private final IPageView pageView = new PageView("Page");
    private final IStartView startView = new StartView("Start");

    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(eventBus);

    @Override
    public EventBus getEventBus() {
        return this.eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return this.placeController;
    }

    @Override
    public ILoginView getLoginView() {
        return loginView;
    }

    @Override
    public IManagementView getManagementView() {
        return managementView;
    }

    @Override
    public IUserView getUserView() {
        return userView;
    }

    @Override
    public IMenuView getMenuView() {
        return menuView;
    }

    @Override
    public IPageView getPageView() {
        return pageView;
    }

    @Override
    public IStartView getPageStart() {
        return startView;
    }

}
