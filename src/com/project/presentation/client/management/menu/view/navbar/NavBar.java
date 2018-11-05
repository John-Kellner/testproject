package com.project.presentation.client.management.menu.view.navbar;

import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.Element;
import gwt.material.design.client.custom.CustomHeader;
import gwt.material.design.client.ui.MaterialNavBar;

/**
 * Created by john on 27.03.2016.
 */
public class NavBar extends MaterialNavBar{

    public NavBar() {
        setColor("#404148");
    }

    public void setColor(String hexcode){
        final CustomHeader navBar = getNavBar();
        final Element element = navBar.getElement();
        final Node header = element.getChild(0);
        final Node navbar = header.getFirstChild();
        navbar.getParentElement().getStyle().setBackgroundColor(hexcode);
    }
}
