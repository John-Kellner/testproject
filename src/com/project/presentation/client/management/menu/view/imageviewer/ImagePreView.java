package com.project.presentation.client.management.menu.view.imageviewer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Created by john on 11.10.2015.
 */
public class ImagePreView extends Composite {
    interface ImagePreViewUiBinder extends UiBinder<FlowPanel, ImagePreView> {}

    private static ImagePreViewUiBinder ourUiBinder = GWT.create(ImagePreViewUiBinder.class);

    @UiField
    protected Image image;

    @UiField
    protected Label text;

    public ImagePreView(String text, Image image) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.image = image;
        this.text.setText(text);
    }
}