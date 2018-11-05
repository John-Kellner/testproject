package com.project.presentation.client.management.start.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.project.presentation.client.gui.components.dialog.DialogBox;
import com.project.presentation.client.management.start.IStartView;
import com.project.presentation.client.management.start.activity.impl.EDocumentPage;
import com.project.presentation.client.management.start.activity.impl.StartActivity;
import com.project.presentation.client.management.start.controller.StartViewController;
import com.project.presentation.client.management.start.view.css.StartViewStyle;

/**
 * Created by john on 12.09.2015.
 */
public class StartView extends StartViewController implements IStartView {

    private StartViewStyle.ResourceBundle bundle = StartViewStyle.getBundle();
    private static StartViewUiBinder ourUiBinder = GWT.create(StartViewUiBinder.class);
    private StartActivity activity;

    interface StartViewUiBinder extends UiBinder<ScrollPanel, StartView> {}

    @UiField
    protected FlowPanel panel;

    public StartView(String start) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setActivity(StartActivity activity) {
        this.activity = activity;
    }

    @Override
    public void setPage(EDocumentPage page) {
        final DialogBox dialogBox = new DialogBox();

        switch (page){
            case IMPRESSEUM:
                dialogBox.setTitle("IMPRESSUM");
                dialogBox.setContent(bundle.impressumText().getText());
                break;

            case DATENSCHUTZERKLARUNG:
                dialogBox.setTitle("DATENSCHUTZERKL"+(char) 196+ "RUNG");
                dialogBox.setContent(bundle.datenschutzerklarung().getText());
                break;
        }

        panel.clear();
        panel.add(dialogBox);
    }

}
