package com.project.presentation.client.gui.components.togglebtn.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.project.presentation.client.gui.components.togglebtn.client.button.ToggelButton;

/**
 * Created by john on 24.04.2016.
 */
public class ToggleButtonModul implements EntryPoint {
    public void onModuleLoad() {
        final ToggelButton toggelButton = new ToggelButton();
        toggelButton.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
        toggelButton.getElement().getStyle().setLeft(50.0, Style.Unit.PCT);
        toggelButton.getElement().getStyle().setTop(50.0, Style.Unit.PCT);
        final Button button = new Button("ON");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                     toggelButton.setValue(true);
            }
        });

        final Button button2 = new Button("OFF");
        button2.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                toggelButton.setValue(false);
            }
        });
        RootPanel.get().add(button);
        RootPanel.get().add(button2);
        RootPanel.get().add(toggelButton);
    }
}
