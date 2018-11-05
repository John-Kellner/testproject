package com.project.presentation.client.gui.components.dragablefiles.dropItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.project.presentation.client.console.log.Console;
import com.project.presentation.client.gui.components.dragablefiles.DragableConfig;
import com.project.presentation.client.gui.components.dragablefiles.IsSelected;
import com.project.presentation.client.gui.components.dragablefiles.Repaint;
import com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload.*;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;

/**
 * Created by john on 15.05.2016.
 */
public class DropItem extends Composite implements IsSelected{
    private Repaint repaint;
    private UploadHandler uploadHandler;

    interface DropItemUiBinder extends UiBinder<FlowPanel, DropItem> {}

    private static DropItemUiBinder ourUiBinder = GWT.create(DropItemUiBinder.class);

    @UiField
    protected DragUploadPanel drag;

    @UiField
    protected Label text, search;

    @UiField
    protected FlowPanel dropplace;

    public DropItem(final DragableConfig config) {
        initWidget(ourUiBinder.createAndBindUi(this));

        drag.setConfig(config);
        text.setText("Ziehen Sie das PDF hier rein, oder");
        search.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                drag.click();
            }
        });

        dropplace.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if(repaint != null){
                    repaint.repaint();
                }
            }
        }, ClickEvent.getType());

        dropplace.addDomHandler(new DragOverHandler() {
            @Override
            public void onDragOver(DragOverEvent dragOverEvent) {
                dragOverEvent.stopPropagation();
                dragOverEvent.preventDefault();
                if(repaint != null){
                    repaint.repaint();
                }
            }
        }, DragOverEvent.getType());

        dropplace.addDomHandler(new DropHandler() {
            @Override
            public void onDrop(DropEvent dropEvent) {
                dropEvent.stopPropagation();
                dropEvent.preventDefault();

                if (config.getEinstellungID() == -1){
                    MaterialToast.alert("Keine Einstellung vorhanden - Cache leeren!");
                    return;
                }

                receiveOnDropEvent(config, dropEvent);
            }
        }, DropEvent.getType());
    }

    /**
     * Receive and handle on Drop Event
     * @param config
     * @param dropEvent
     */
    private void receiveOnDropEvent(DragableConfig config, DropEvent dropEvent){
        MaterialLoader.showLoading(true);
        final String url = GWT.getModuleBaseURL() + config.getServletName() + "?EinstellungID=" + config.getEinstellungID();
        MultiUploadRequst.getFileNames(dropEvent.getDataTransfer(), url, new Progress() {

            @Override
            public void onComplete(String response) {
                MaterialLoader.showLoading(false);
                if(repaint != null){
                    repaint.repaint();
                }
                final UploadBean uploadBean = UploadHelper.checkResponse(response);
                if (uploadBean.isUploadSuccess() && uploadBean.getFilename().length() > 3){
                    uploadHandler.onUploadComplete(uploadBean);
                    Console.log("Upload Complete");
                }else {
                    MaterialToast.alert("Upload Failed : " + uploadBean.getReasonWhenFailed());
                }
            }

            @Override
            public void onProgress(Long progress, Long complete) {
                Console.log("Progress" + progress + "Complete " + complete);
            }

            @Override
            public void onError() {
                MaterialToast.alert("Upload Failed");
                if(repaint != null){
                    repaint.repaint();
                }
            }
        });
    }

    @Override
    public void onFocusLost() {

    }

    public void setRepaint(Repaint repaint) {
        this.repaint = repaint;
    }

    /**
     * Upload complete Handler, it has to be called, when an Upload has been done complete
     * @param uploadHandler
     */
    public void setUploadHandler(UploadHandler uploadHandler) {
        this.uploadHandler = uploadHandler;
    }

}
