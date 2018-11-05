package com.project.presentation.client.gui.layouts.defaulttheme.footer;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.project.presentation.client.gui.layouts.defaulttheme.css.LayoutSytle;

/**
 * Created by john on 10.06.2015.
 */
public class Footer extends Composite {
    private final LayoutSytle.Resource style = LayoutSytle.getStyle();

    public Footer(){
        final FlowPanel ground = new FlowPanel();
        //ground.setStyleName(style.footerGround());
        initWidget(ground);
    }

}
