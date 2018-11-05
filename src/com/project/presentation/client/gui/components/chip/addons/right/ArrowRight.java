package com.project.presentation.client.gui.components.chip.addons.right;

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
public class ArrowRight extends Composite {
    private ClickHandler handler;

    interface ArrowRightUiBinder extends UiBinder<HTML, ArrowRight> {}

    private static ArrowRightUiBinder ourUiBinder = GWT.create(ArrowRightUiBinder.class);

    @UiField
    protected HTML arrowRight;

    public ArrowRight() {
        initWidget(ourUiBinder.createAndBindUi(this));
        arrowRight.addClickHandler(new ClickHandler() {
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