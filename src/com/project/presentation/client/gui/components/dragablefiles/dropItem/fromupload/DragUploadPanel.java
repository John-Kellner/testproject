package com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.project.presentation.client.console.log.Console;
import com.project.presentation.client.gui.components.dragablefiles.DragableConfig;
import com.project.presentation.client.gui.components.table.bean.TableBean;
import com.project.presentation.client.gui.message.Message;
import com.project.presentation.client.management.menu.view.upload.UploadComplete;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;

/**
 * Created by john on 29.06.2015.
 */
public class DragUploadPanel extends FormPanel {
    private final FileUpload upload = new FileUpload();
    private UploadComplete handler;

    public DragUploadPanel() {
        upload.setName("upload");
        upload.setVisible(true);
        upload.getElement().setAttribute("multiple","multiple");
        upload.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                submit();
            }
        });
        setWidget(upload);

        // Add an event handler to the form.
        addSubmitHandler(new SubmitHandler() {
            public void onSubmit(SubmitEvent event) {
                if (upload.getFilename().endsWith(".pdf")){
                    Console.log("Filename: " + upload.getFilename());
                    MaterialLoader.showLoading(true);
                }else {
                    MaterialToast.alert("Dateityp muss unbedingt vom Typ PDF sein, die gr" + Message.OE_K + Message.SS_K + "e von 1,5MB nicht " + Message.UE_K + "berschreiten!");
                    event.cancel();
                }
            }
        });

        addSubmitCompleteHandler(new SubmitCompleteHandler() {
            public void onSubmitComplete(SubmitCompleteEvent event) {
                // When the form submission is successfully completed, this event is
                // fired. Assuming the service returned a response of type text/html,
                // we can get the result text here (see the FormPanel documentation for
                // further explanation).
                //Window.alert(event.getResults());

                //Console.log("Response: " + event.getResults());
                /** Parse Resultstring */
                MaterialLoader.showLoading(false);
                if (event.getResults().contains("true")){
                    final String[] split = event.getResults().split(";");
                    Console.log("ID: " + split[1].replace("id=", ""));
                    Console.log("FILENAME: " + split[2].replace("filename=", ""));

                    final long receivedID = Long.parseLong(split[1].replace("id=", ""));
                    final String filename = split[2].replace("filename=", "");

                    final TableBean tableBean = new TableBean(receivedID, filename);
                    upload.getElement().setPropertyString("value", "");
                    onSuccessAction(tableBean);
                    Console.log("Image ID: " + receivedID + " Filename: " + filename);
                }else {
                    Console.log("Fehler beim speichern des Zertifikates!");
                }
            }
        });
    }

    // Inform Frontend
    private void onSuccessAction(final TableBean bean) {
        if (handler != null){
            handler.onUploadComplete(bean);
        }
    }

    public void click() {
        upload.click();
    }

    public void setConfig(DragableConfig config) {
        this.handler = config.getUploadHandler();

        setAction(GWT.getModuleBaseURL() + config.getServletName() + "?EinstellungID=" + config.getEinstellungID());
        setEncoding(DragUploadPanel.ENCODING_MULTIPART);
        setMethod(DragUploadPanel.METHOD_POST);
    }

}
