package com.project.presentation.client.gui.components.chip.addons.plus;

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
public class Plus extends Composite {
    private ClickHandler handler;

    interface PlusUiBinder extends UiBinder<HTML, Plus> {}

    private static PlusUiBinder ourUiBinder = GWT.create(PlusUiBinder.class);

    @UiField
    protected HTML plus;

    public Plus() {
        initWidget(ourUiBinder.createAndBindUi(this));
        plus.addClickHandler(new ClickHandler() {
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