package com.project.presentation.client.gui.components.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import gwt.material.design.client.ui.MaterialTitle;

/**
 * Created by john on 27.06.2015.
 */
public class DialogBox extends Composite {
    interface DialogBoxUiBinder extends UiBinder<FlowPanel, DialogBox> {}

    private static DialogBoxUiBinder ourUiBinder = GWT.create(DialogBoxUiBinder.class);

    @UiField
    protected Label title;

    @UiField
    protected HTML content, close;

    public DialogBox() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void addClickHandler(ClickHandler handler) {
        close.addClickHandler(handler);
    }

    public void setContent(String content){
        this.content.setHTML(content);
    }
}