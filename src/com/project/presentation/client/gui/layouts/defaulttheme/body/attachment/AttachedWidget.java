package com.project.presentation.client.gui.layouts.defaulttheme.body.attachment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.project.presentation.client.gui.layouts.defaulttheme.body.attachment.css.AttachedFilesStyle;
import com.project.presentation.client.gui.layouts.defaulttheme.body.attachment.popup.AttachedPopUp;
import com.project.presentation.shared.dto.AnlageDTO;

import java.util.List;

/**
 * Created by john on 17.07.2016.
 */
public class AttachedWidget extends Composite{
    interface AttachedWidgetUiBinder extends UiBinder<FlowPanel, AttachedWidget> {}
    private final AttachedPopUp popUp = new AttachedPopUp();
    private final AttachedFilesStyle.Resource style = AttachedFilesStyle.getStyle();

    private static AttachedWidgetUiBinder ourUiBinder = GWT.create(AttachedWidgetUiBinder.class);

    @UiField
    protected Label nr;

    @UiField
    protected FlowPanel root;

    public AttachedWidget(final List<AnlageDTO> anlagen) {
        initWidget(ourUiBinder.createAndBindUi(this));
        popUp.setAttachedFiles(anlagen);
        nr.setText("" + anlagen.size());
        root.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                createShaddow();
                root.add(popUp);
            }
        }, ClickEvent.getType());
    }

    /**
     * Shaddow over the complete Window to close the open Attached Files Menu
     */
    private void createShaddow() {
        final FlowPanel shaddow = new FlowPanel();
        shaddow.setStyleName(style.shaddow());
        shaddow.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                RootPanel.get().remove(shaddow);
                root.remove(popUp);
            }
        },ClickEvent.getType());
        RootPanel.get().add(shaddow);
    }
}