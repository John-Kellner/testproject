package com.project.presentation.client.gui.components.togglebtn.client.button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.project.presentation.client.gui.components.togglebtn.client.css.ToggelButtonStyle;

/**
 * Created by john on 24.04.2016.
 */
public class ToggelButton extends Composite{
    private ToggleClickHandler handler;

    interface ToggelButtonUiBinder extends UiBinder<FlowPanel, ToggelButton> {}

    private static ToggelButtonUiBinder ourUiBinder = GWT.create(ToggelButtonUiBinder.class);

    private static ToggelButtonStyle.Resource style = ToggelButtonStyle.getStyle();
    private boolean state = true;

    @UiField
    protected FlowPanel toggel;

    @UiField
    protected FlowPanel border;

    @UiField
    protected Label title;

    public ToggelButton() {
        initWidget(ourUiBinder.createAndBindUi(this));
        toggel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                stateCheck(isState());
            }
        }, ClickEvent.getType());
    }

    /**
     * Check State on/off
     * @param state
     */
    private void stateCheck(final boolean state) {
        if (isState()){
            toggel.addStyleName(style.roundAnimatedFadeOff());
            toggel.removeStyleName(style.roundAnimatedFadeOn());

            border.addStyleName(style.borderAnimatedFadeOff());
            border.removeStyleName(style.borderAnimatedFadeOn());
            setState(false);
            if(handler != null){
                handler.onClick(false);
            }
        }else {
            toggel.addStyleName(style.roundAnimatedFadeOn());
            toggel.removeStyleName(style.roundAnimatedFadeOff());

            border.addStyleName(style.borderAnimatedFadeOn());
            border.removeStyleName(style.borderAnimatedFadeOff());
            setState(true);
            if (handler != null){
                handler.onClick(true);
            }
        }
    }

    /***
     * get state on / off
     * @return
     */
    public boolean isState() {
        return state;
    }

    private void setState(boolean state) {
        this.state = state;
    }

    /**
     * Add Click Handler
     * @param handler
     */
    public void setClickHandler(ToggleClickHandler handler){
        this.handler = handler;
    }

    public void setValue(boolean value) {
        this.state = !value;
        stateCheck(value);
    }

    @Override
    public void setTitle(String value) {
        this.title.setText(value);
    }
}