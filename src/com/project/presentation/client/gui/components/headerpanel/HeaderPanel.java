package com.project.presentation.client.gui.components.headerpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.project.presentation.client.gui.components.togglebtn.client.button.ToggelButton;
import com.project.presentation.client.gui.components.togglebtn.client.button.ToggleClickHandler;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;

/**
 * Created by john on 24.04.2016.
 */
public class HeaderPanel extends Composite {
    private static HeaderPanel1UiBinder ourUiBinder = GWT.create(HeaderPanel1UiBinder.class);
    interface HeaderPanel1UiBinder extends UiBinder<MaterialRow, HeaderPanel> {}
    private ToggleClickHandler toggelBtnHandler;
    public enum Headerstate {BILD, TEXT;}
    private HeaderListener listener;

    @UiField
    protected ToggelButton toggel;

    @UiField
    protected MaterialColumn bild, text;

    @UiField
    protected FlowPanel scoretext, scorebild;

    private Headerstate selectPageState = Headerstate.BILD;

    public HeaderPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
        bild.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                onStateChange(Headerstate.BILD);
            }
        }, ClickEvent.getType());
        text.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                onStateChange(Headerstate.TEXT);
            }
        }, ClickEvent.getType());
        scoretext.setVisible(false);

        toggel.setClickHandler(new ToggleClickHandler() {
            @Override
            public void onClick(boolean value) {
                if (toggelBtnHandler != null){
                    toggelBtnHandler.onClick(value);
                }
            }
        });
    }

    /** State Change */
    private void onStateChange(Headerstate headerstate){
        switch (headerstate){
            case BILD:
                scorebild.setVisible(true);
                scoretext.setVisible(false);
                this.selectPageState = Headerstate.BILD;
                if (listener != null){
                    listener.onSelect(selectPageState);
                }
                break;
            case TEXT:
                scorebild.setVisible(false);
                scoretext.setVisible(true);
                this.selectPageState = Headerstate.TEXT;
                if (listener != null){
                    listener.onSelect(selectPageState);
                }
                break;
        }
    }

    public void setListener(HeaderListener listener){
        this.listener = listener;
    }

    public boolean getToggelState(){
        return this.toggel.isState();
    }

    public void setToggelState(boolean state) {
        toggel.setValue(state);
    }
    public void setToggelListener(final ToggleClickHandler clickHandler) {
        this.toggelBtnHandler = clickHandler;
    }
}