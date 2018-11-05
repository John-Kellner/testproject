package com.project.presentation.client.gui.layouts.defaulttheme;

import com.google.gwt.user.client.ui.FlowPanel;
import com.project.presentation.client.gui.layouts.Theme;
import com.project.presentation.client.gui.layouts.defaulttheme.body.Body;
import com.project.presentation.client.gui.layouts.defaulttheme.css.LayoutSytle;
import com.project.presentation.client.gui.layouts.defaulttheme.header.IHeader;
import com.project.presentation.client.gui.layouts.defaulttheme.image.ImageBar;
import com.project.presentation.client.gui.layouts.defaulttheme.header.impl.Header;
import com.project.presentation.client.gui.layouts.defaulttheme.menubar.MenuBar;
import com.project.presentation.shared.view.UserView;

/**
 * Created by john on 09.06.2015.
 */
public class DefaultTheme extends Theme implements IHeader {
    private final LayoutSytle.Resource style = LayoutSytle.getStyle();
    private final FlowPanel ground = new FlowPanel();
    private final Header header;
    private final MenuBar menuBar;
    private final ImageBar imageBar;
    private final Body body;
    private UserView view;

    public DefaultTheme(){
        header = new Header(this);
        menuBar = new MenuBar();
        body = new Body();
        imageBar = new ImageBar();
        //footer = new Footer();

        ground.setStyleName(style.root());
        ground.add(header);
        ground.add(menuBar);

        menuBar.addHandler(body);
        menuBar.addImageHandler(imageBar);
        ground.add(imageBar);
        ground.add(body);
        //ground.add(footer);
        initWidget(ground);
    }

    @Override
    public void onLoadView(final UserView view) {
        this.view = view;
        header.setText(view.getUser().getName());
        header.getElement().getStyle().setBackgroundColor(view.getEinstellung().getHexColor());
        menuBar.loadView(view.getEinstellung().getTabItem());
        body.setAttachedFiles(view.getAnlagen());
    }

    @Override
    public void downloadPDF() {
        downloadPDF(view, menuBar.getSelectedItem());
    }
}
