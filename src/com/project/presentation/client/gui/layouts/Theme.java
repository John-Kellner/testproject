package com.project.presentation.client.gui.layouts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.project.presentation.shared.enumerations.ETheme;
import com.project.presentation.shared.view.UserView;
import com.project.widget.client.tabbar.tabitem.TabItem;

/**
 * Created by john on 29.07.2015.
 * Alle Themes muessen von Theme erben
 */
public abstract class Theme extends Composite {
    public abstract void onLoadView(UserView view);

    public void downloadPDF(UserView view, TabItem selectedItem){
        final StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(GWT.getHostPageBaseURL());
        urlBuilder.append("presentation/htmltopdfdownload?uid="+ view.getUser().getName());
        urlBuilder.append("&tabName=" + selectedItem.getName());
        urlBuilder.append("&theme=" + ETheme.DEFAULT_THEME.toString());
        Window.open(urlBuilder.toString() , "_blank","");
    }
}
