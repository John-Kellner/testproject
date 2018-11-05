package com.project.presentation.client.gui.layouts.defaulttheme.header.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.project.presentation.client.gui.layouts.defaulttheme.css.LayoutSytle;
import com.project.presentation.client.gui.layouts.defaulttheme.header.IHeader;

/**
 * Created by john on 09.06.2015.
 */
public class Header extends Composite {
    private final Label label;
    private final LayoutSytle.Resource style = LayoutSytle.getStyle();

    public Header(final IHeader header){
        label = new Label();
        label.setStyleName(style.headerLabel());

        final FlowPanel ground = new FlowPanel();
        ground.setStyleName(style.headerGround());
        ground.add(label);

        final Image image = new Image(GWT.getHostPageBaseURL() + "resource/svg/ic_cloud_download.svg");
        image.setStyleName(style.imagePDFDownload());
        image.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                header.downloadPDF();
            }
        });
        ground.add(image);

        final Label label = new Label("PDF");
        label.setStyleName(style.downloadPDFText());
        ground.add(label);
        initWidget(ground);
    }

    public void setText(String text){
        label.setText(text);
    }
}
