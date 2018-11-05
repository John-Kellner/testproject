package com.project.presentation.client.gui.components.chip.addons.left;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

/**
 * Created by john on 30.12.2015.
 */
public class ArrowLeft extends Composite {
    private ClickHandler handler;

    interface ArrowLeftUiBinder extends UiBinder<HTML, ArrowLeft> {}

    private static ArrowLeftUiBinder ourUiBinder = GWT.create(ArrowLeftUiBinder.class);

    @UiField
    protected HTML arrowLeft;

    public ArrowLeft() {
        initWidget(ourUiBinder.createAndBindUi(this));
        arrowLeft.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (handler != null){
                    handler.onClick(clickEvent);
                }
            }
        });
    }

    public void addClickHandler(ClickHandler handler){
        this.handler = handler;
    }
}