package com.project.presentation.client.management.menu.view.colorpicker;

import com.google.gwt.user.client.ui.Composite;
import com.project.presentation.client.gui.components.colorpicker.ColorPickerImpl;
import com.project.presentation.client.gui.components.colorpicker.handler.ColorPickerHandler;

/**
 * Created by john on 27.03.2016.
 */
public class ColorPickerWrapper extends Composite {
    private final ColorPickerImpl picker;
    private final IPicker handler;
    private String hexCode;

    public ColorPickerWrapper(final IPicker handler) {
        this.handler = handler;
        picker = new ColorPickerImpl(new ColorPickerHandler() {
            @Override
            public void onShow() {

            }

            @Override
            public void onChange(String hexColor) {
                hexCode = hexColor;
                handler.onChange(hexColor);
            }

            @Override
            public void onMove(String hexColor) {
                hexCode = hexColor;
                handler.onChange(hexColor);
            }

            @Override
            public void onHide(String hexColor) {

            }

            @Override
            public void onShowBefore(String hexColor) {

            }

            @Override
            public void onInitialize() {

            }
        });
        initWidget(picker);
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setColor(String color) {
        this.hexCode = color;
        picker.setHexCode(color);
        handler.onChange(color);
    }
}
