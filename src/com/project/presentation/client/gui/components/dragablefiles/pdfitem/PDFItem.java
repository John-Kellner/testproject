package com.project.presentation.client.gui.components.dragablefiles.pdfitem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.project.presentation.client.console.log.Console;
import com.project.presentation.client.gui.components.dragablefiles.IsSelected;
import com.project.presentation.client.gui.components.dragablefiles.Repaint;
import com.project.presentation.client.gui.components.dragablefiles.css.DragableFileStyle;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;
import gwt.material.design.client.ui.MaterialToast;

/**
 * Created by john on 15.05.2016.
 */
public class PDFItem extends Composite implements IsSelected {
    private static DropItemUiBinder ourUiBinder = GWT.create(DropItemUiBinder.class);
    private RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);
    private DragableFileStyle.Resource style = DragableFileStyle.getStyle();
    interface DropItemUiBinder extends UiBinder<FlowPanel, PDFItem> {}
    private boolean isSelected = false;
    private Long attachmentID = -1L;
    private Repaint repaint;

    @UiField
    protected Label text, document;

    @UiField
    protected FlowPanel border, btnDelete, btnDownload;

    public PDFItem(final Long attachmentID ,final String filename) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.attachmentID = attachmentID;
        text.setText(filename);
        border.addDomHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent clickEvent) {
                setSelected(false);
            }
        }, ClickEvent.getType());

        btnDelete.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                deleteAttachment();
            }
        }, ClickEvent.getType());

        btnDownload.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                downloadPDF();
            }
        }, ClickEvent.getType());
    }

    /**
     * Download PDF File
     */
    private void downloadPDF(){
            final StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(GWT.getHostPageBaseURL());
            urlBuilder.append("presentation/attachedpdfdownload?uid=" + attachmentID);
            Window.open(urlBuilder.toString() , "_blank","");
    }

    /**
     * Deletes a single Attachment
     */
    private void deleteAttachment() {
        service.deleteAttachment(this.attachmentID, new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable throwable) {
                Console.log(throwable.getMessage());
            }

            @Override
            public void onSuccess(Boolean isRemoved) {
                checkDelete(isRemoved);
            }
        });
    }

    /**
     * Check if delete if done fine
     * @param isRemoved
     */
    private void checkDelete(Boolean isRemoved) {
        if (isRemoved){
            removeFromParent();
        }else {
            MaterialToast.alert("Anlage konnte nicht gelöscht werden!");
        }
    }

    /**
     * external global selected state
     * @param forceDisable
     */
    public void setSelected(boolean forceDisable){
        if (forceDisable){
            isSelected = false;
            setStateDisSelected();
        }else {
            if (isSelected){
                isSelected = false;
                setStateDisSelected();
            }else {
                if (repaint != null){
                    repaint.repaint();
                }

                isSelected = true;
                setStateSelected();
            }
            btnDelete.setVisible(isSelected);
            btnDownload.setVisible(isSelected);
        }
    }

    /**
     * Disable State
     */
    private void setStateDisSelected(){
        isSelected = false;
        border.setStyleName(style.pdfItem());
        btnDelete.setVisible(false);
        btnDownload.setVisible(false);
    }

    /**
     * Enable State
     */
    private void setStateSelected(){
        border.setStyleName(style.pdfItemBorderSelected());
    }

    /**
     * OnFocusLost
     */
    @Override
    public void onFocusLost() {
        isSelected = false;
        setVisible(true);
        setStateDisSelected();
    }

    /**
     * External Callback
     * @param repaintCallback
     */
    public void setRepaintCallback(Repaint repaintCallback) {
        this.repaint = repaintCallback;
    }
}
