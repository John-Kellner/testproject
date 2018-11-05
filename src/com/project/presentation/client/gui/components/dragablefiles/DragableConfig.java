package com.project.presentation.client.gui.components.dragablefiles;

import com.project.presentation.client.management.menu.view.upload.UploadComplete;

/**
 * Created by john on 15.05.2016.
 */
public interface DragableConfig {
    String getServletName();
    long getEinstellungID();
    UploadComplete getUploadHandler();
}
