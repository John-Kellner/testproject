package com.project.presentation.client.gui.components.chip;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.project.presentation.client.gui.components.chip.addons.left.ArrowLeft;
import com.project.presentation.client.gui.components.chip.addons.remove.RemoveButton;
import com.project.presentation.client.gui.components.chip.addons.right.ArrowRight;
import com.project.presentation.client.gui.components.chip.css.ChipStyle;
import com.project.presentation.client.gui.components.chip.handler.ChipChangeHandler;
import com.project.presentation.client.gui.components.chip.handler.ChipSelectHandler;

/**
 * Created by john on 28.12.2015.
 */
public class Chip<T> extends Composite {

    private String hexColor;

    interface CircleUiBinder extends UiBinder<FlowPanel, Chip> {}

    public interface EscapeHandler {

        void enableAddButton();

    }
    private static CircleUiBinder ourUiBinder = GWT.create(CircleUiBinder.class);
    private static ChipStyle.Resource style = ChipStyle.getStyle();

    private ChipChangeHandler changeHandler;
    private EscapeHandler escapeHandler;
    private RemoveButton removeButton;
    private final Chip chip = this;
    private final FlowPanel panel;
    private ArrowRight arrowright;
    private String originalName;
    private ArrowLeft arrowleft;
    private boolean isSelected;
    private boolean isdeleted;
    private T value;
    @UiField
    protected HTML text, editBtn, saveBtn2, tabAnzeige;
    @UiField
    protected TextBox textbox;

    public Chip(String value) {
        panel = ourUiBinder.createAndBindUi(this);
        initWidget(panel);
        setStyleName(style.root());
        setText(value);
        originalName = value;

        arrowleft = new ArrowLeft();
        arrowleft.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (changeHandler != null){
                    changeHandler.onMoveLeft(chip);
                }
            }
        });
        arrowright = new ArrowRight();
        arrowright.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (changeHandler != null){
                    changeHandler.onMoveRight(chip);
                }
            }
        });
        removeButton = new RemoveButton();
        removeButton.addListener(new ClickHandler() {
             @Override
             public void onClick(ClickEvent clickEvent) {
                 isdeleted = true;
                 if (changeHandler != null){
                    changeHandler.onDelete(chip);
                 }
             }
         });

        panel.add(arrowleft);
        panel.add(arrowright);

        final Chip chip = this;

        /** Focus has been lost */
        textbox.addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent blurEvent) {
                final Timer timer = new Timer() {
                    @Override
                    public void run() {
                            text.setText(textbox.getValue());
                            if (changeHandler != null){
                                changeHandler.onSave(chip);
                            }
                        arrowleft.setVisible(true);
                        arrowright.setVisible(true);

                        panel.remove(removeButton);
                        hideEditMode();
                    }
                };
                timer.schedule(300);
            }
        });

        /** Window Listener auf alle Tasteneinganben fuer alle Chips */
        Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(Event.NativePreviewEvent nativePreviewEvent) {
                final NativeEvent nativeEvent = nativePreviewEvent.getNativeEvent();
                if ("keydown".equals(nativeEvent.getType())) {
                    if (nativeEvent.getKeyCode() == KeyCodes.KEY_ESCAPE) {
                        hideEditMode();
                        setSelected(isSelected);
                        if (escapeHandler != null){
                            escapeHandler.enableAddButton();
                        }
                    }

                    if (nativeEvent.getKeyCode() == KeyCodes.KEY_ENTER){
                        hideEditMode();
                        setSelected(isSelected);
                    }
                }
            }
        });
    }

    /** Editiermodus einschalten */
    public void showEditMode() {
        text.setVisible(false);
        textbox.setVisible(true);
        editBtn.setVisible(false);
        saveBtn2.setVisible(true);
    }

    /** Editiermodus ausschalten */
    public void hideEditMode(){
        textbox.setVisible(false);
        text.setVisible(true);
        editBtn.setVisible(true);
        saveBtn2.setVisible(false);
    }

    /** Switch zwischen markiertem und unmarkiertem Zustand */
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        /** Markierter Zustand */
        if(selected){
            String hexColor = this.hexColor != null ? this.hexColor : "#404148";
            panel.getElement().getStyle().setBackgroundColor(hexColor);
            panel.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
            panel.getElement().getStyle().setColor("white");
            panel.getElement().getStyle().setMarginRight(32.0, Style.Unit.PX);
            panel.getElement().getStyle().setMarginLeft(32.0, Style.Unit.PX);
            arrowright.setVisible(true);
            arrowleft.setVisible(true);
            editBtn.setVisible(true);
            saveBtn2.setVisible(false);
            tabAnzeige.setVisible(false);
        } else {
            /** Ausmaskierter Zustand */
            panel.getElement().getStyle().setBackgroundColor("lightgray");
            panel.getElement().getStyle().setFontWeight(Style.FontWeight.NORMAL);
            panel.getElement().getStyle().setColor("gray");
            panel.getElement().getStyle().setMarginRight(0.0, Style.Unit.PX);
            panel.getElement().getStyle().setMarginLeft(0.0, Style.Unit.PX);
            arrowright.setVisible(false);
            arrowleft.setVisible(false);
            editBtn.setVisible(false);
            saveBtn2.setVisible(false);
            tabAnzeige.setVisible(true);
        }
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
        if(this.isSelected()){
            String color = this.hexColor != null ? this.hexColor : "#404148";
            panel.getElement().getStyle().setBackgroundColor(color);
        }
    }

    /** Setzt den Text von extern */
    public void setText(String name){
        this.text.setHTML(name);
    }

    /** Zeigt den Text an */
    public String getText(){
        return text.getText();
    }

    /** Edit handler */
    public void addEditHandler(final ChipChangeHandler handler){
        this.changeHandler = handler;
        editBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                showEditMode();
                if (text.getText() != null){
                    chip.originalName = textbox.getValue(); //alter Text merken
                    textbox.setText(text.getText());
                    textbox.setFocus(true);
                    textbox.setCursorPos(text.getText().length());
                }
                arrowleft.setVisible(false);
                arrowright.setVisible(false);

                panel.add(removeButton);
                handler.onEdit(chip);
            }
        });
    }

    /** Select Handler */
    public void addSelectHandler(final ChipSelectHandler handler){
        final Chip chip = this;
        text.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                handler.onSelect(chip);
            }
        });

        tabAnzeige.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                handler.onSelect(chip);
            }
        });
    }

    /**
     * Setzt ein Bean in den Chip
     * @param value
     */
    public void setBean(T value) {
        this.value = value;
    }

    /**
     * Holt ein Bean aus dem Chip
     * @return
     */
    public T getBean(){
        return this.value;
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public void setActive() {
        setSelected(true);
    }

    /**
     * Schaltet den rechten Button ab
     */
    public void disableRightButton() {
        arrowright.setVisible(false);
    }

    /**
     * Schaltet den linken Button ab
     */
    public void disableLeftButton() {
        arrowleft.setVisible(false);
    }

    public String getOriginalName() {
        return originalName;
    }

    public void addEscapeHanlder(EscapeHandler escapeHandler) {
        this.escapeHandler = escapeHandler;
    }

}