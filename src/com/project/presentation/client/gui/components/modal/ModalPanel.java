package com.project.presentation.client.gui.components.modal;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.project.presentation.client.gui.components.dialog.DialogBox;
import com.project.presentation.client.gui.components.modal.css.ModalStyle;

/**
 * Created by john on 17.01.2016.
 */
public class ModalPanel {
    private final ModalStyle.Resource style = ModalStyle.getStyle();
    final FlowPanel modal = new FlowPanel();

    private ModalPanel(){
        modal.setStyleName(style.modal());

        final Timer timer = new Timer() {
            @Override
            public void run() {
                modal.addStyleName(style.modalFade());
            }
        };

        timer.schedule(200);
        RootPanel.get().add(modal);
    }

    public ModalPanel(DialogBox dialogBox) {
        this();

        final FlowPanel ground = new FlowPanel();
        ground.setStyleName(style.ground());
        ground.add(dialogBox);

        dialogBox.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                RootPanel.get().remove(ground);
                RootPanel.get().remove(modal);
            }
        });

        RootPanel.get().add(ground);
    }
}
