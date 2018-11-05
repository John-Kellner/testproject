package com.project.presentation.client.gui.components.colorpicker.handler;

/**
 * Created by john on 27.03.2016.
 */
public interface ColorPickerHandler {
    void onShow();
    void onChange(String hexColor);
    void onMove(String hexColor);
    void onHide(String hexColor);
    void onShowBefore(String hexColor);
    void onInitialize();
}
