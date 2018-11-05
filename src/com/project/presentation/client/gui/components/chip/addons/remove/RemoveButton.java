package com.project.presentation.client.gui.components.chip.addons.remove;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

/**
 * Created by john on 30.12.2015.
 */
public class RemoveButton extends Composite{
    private ClickHandler listener;

    interface RemoveButtonUiBinder extends UiBinder<HTML, RemoveButton> {
    }

    private static RemoveButtonUiBinder ourUiBinder = GWT.create(RemoveButtonUiBinder.class);

    @UiField
    protected HTML remove;

    public RemoveButton() {
        initWidget(ourUiBinder.createAndBindUi(this));
        remove.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (listener !=null){
                    listener.onClick(clickEvent);
                }
            }
        });
    }

    public void addListener(ClickHandler listener){
        this.listener = listener;
    }
}