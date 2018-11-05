package com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload;

import com.google.gwt.user.client.Event;

/**
 * Created by john on 16.05.2016.
 */
public interface Progress {
    void onComplete(String response);
    void onProgress(Long progress, Long total);
    void onError();

}
