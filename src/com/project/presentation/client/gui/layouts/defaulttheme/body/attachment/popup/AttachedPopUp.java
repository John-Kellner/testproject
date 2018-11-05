package com.project.presentation.client.gui.layouts.defaulttheme.body.attachment.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.project.presentation.client.gui.layouts.defaulttheme.body.attachment.popup.pdfItem.AttachedPDFItem;
import com.project.presentation.shared.dto.AnlageDTO;

import java.util.List;

/**
 * Created by john on 17.07.2016.
 */
public class AttachedPopUp extends Composite {

    interface AttachedPopUpUiBinder extends UiBinder<FlowPanel, AttachedPopUp> {}

    private static AttachedPopUpUiBinder ourUiBinder = GWT.create(AttachedPopUpUiBinder.class);

    public AttachedPopUp() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiField
    protected FlowPanel body;

    public void setAttachedFiles(List<AnlageDTO> files) {
        for (AnlageDTO file : files) {
            final AttachedPDFItem pdfItem = new AttachedPDFItem();
            pdfItem.setAttachment(file);
            body.add(pdfItem);
        }
    }

}