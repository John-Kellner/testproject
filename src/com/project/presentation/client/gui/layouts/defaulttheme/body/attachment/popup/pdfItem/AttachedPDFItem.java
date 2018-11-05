package com.project.presentation.client.gui.layouts.defaulttheme.body.attachment.popup.pdfItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.project.presentation.shared.dto.AnlageDTO;

/**
 * Created by john on 17.07.2016.
 */
public class AttachedPDFItem extends Composite{

    private AnlageDTO attachment;

    interface AttachedPDFItemUiBinder extends UiBinder<FlowPanel, AttachedPDFItem> {}

    private static AttachedPDFItemUiBinder ourUiBinder = GWT.create(AttachedPDFItemUiBinder.class);

    @UiField
    protected Label lbl;

    @UiField
    protected FlowPanel root;

    public AttachedPDFItem() {
        initWidget(ourUiBinder.createAndBindUi(this));
        root.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                downloadPDF();
            }
        }, ClickEvent.getType());
    }

    /**
     * Set Attached File
     * @param attachment
     */
    public void setAttachment(AnlageDTO attachment) {
        this.attachment = attachment;
        this.lbl.setText(attachment.getFilename());
    }

    /**
     * Download PDF File
     */
    private void downloadPDF(){
        final StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(GWT.getHostPageBaseURL());
        urlBuilder.append("presentation/attachedpdfdownload?uid=" + attachment.getId());
        Window.open(urlBuilder.toString() , "_blank","");
    }
}