package com.project.presentation.client.management;

import com.google.gwt.place.shared.Place;
import com.project.presentation.client.management.login.place.LoginPlace;
import com.project.presentation.client.management.menu.place.MenuPlace;
import com.project.presentation.client.management.page.place.PagePlace;
import com.project.presentation.client.management.start.place.StartPlace;
import com.project.presentation.client.management.studio.place.ManagementPlace;
import com.project.presentation.client.management.user.place.UserPlace;

/**
 * Created by john on 28.06.2015.
 */
public class ManagedPlaces {
    private static final ManagementPlace studio = new ManagementPlace("Fotostudio");
    private static final StartPlace start = new StartPlace("Start");
    private static final LoginPlace login = new LoginPlace("Login");
    private static final UserPlace user = new UserPlace("User");
    private static final MenuPlace menu = new MenuPlace("Menu");
    private static final PagePlace page = new PagePlace("Page");

    public static Place get(EManagedPlace place){
        switch (place){
            case USER:
                return user;
            case STUDIO:
                return studio;
            case LOGIN:
                return login;
            case MENU:
                return menu;
            case PAGE:
                return page;
            case START:
                return start;
        }
        return null;
    }
}
