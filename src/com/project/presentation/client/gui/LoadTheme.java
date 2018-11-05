package com.project.presentation.client.gui;

import com.google.gwt.user.client.ui.RootPanel;
import com.project.presentation.client.gui.layouts.defaulttheme.DefaultTheme;
import com.project.presentation.shared.enumerations.ETheme;

/**
 * Created by john on 28.07.2015.
 */
public class LoadTheme {

    /** ladet den Theme */
    public LoadTheme(ETheme theme) {
        if (theme == ETheme.DEFAULT_THEME){
            RootPanel.get().add(new DefaultTheme());
        }
    }
}
