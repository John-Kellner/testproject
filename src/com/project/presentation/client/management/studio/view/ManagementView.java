package com.project.presentation.client.management.studio.view;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.axeiya.gwtckeditor.client.Toolbar;
import com.axeiya.gwtckeditor.client.ToolbarLine;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.project.presentation.client.console.log.Console;
import com.project.presentation.client.management.studio.IManagementView;
import com.project.presentation.client.management.studio.activity.IManagementActivity;

/**
 * Created by john on 26.06.2015.
 */
public class ManagementView extends Composite implements IManagementView {
    private final String name;
    private IManagementActivity activity;

    @UiField
    protected FlowPanel editor;

    interface ManagementPlatformUiBinder extends UiBinder<ScrollPanel, ManagementView> {

    }
    private static ManagementPlatformUiBinder ourUiBinder = GWT.create(ManagementPlatformUiBinder.class);

    public ManagementView(String name) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.name = name;
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        final CKEditor cke = new CKEditor(CKConfig.full);
        //RootPanel.get("full").add(cke);

        final CKEditor ckeb = new CKEditor(CKConfig.basic);
        //RootPanel.get("basic").add(ckeb);

        CKConfig CONFIG_MODIFICATION = new CKConfig();
        // Creating personalized toolbar
        ToolbarLine line = new ToolbarLine();
        // Define the first line
        CKConfig.TOOLBAR_OPTIONS[] t1 = { CKConfig.TOOLBAR_OPTIONS.Save, CKConfig.TOOLBAR_OPTIONS._,
                CKConfig.TOOLBAR_OPTIONS.Cut, CKConfig.TOOLBAR_OPTIONS.Copy, CKConfig.TOOLBAR_OPTIONS.Paste, CKConfig.TOOLBAR_OPTIONS.PasteText, CKConfig.TOOLBAR_OPTIONS.PasteFromWord, CKConfig.TOOLBAR_OPTIONS._,
                CKConfig.TOOLBAR_OPTIONS.SpellChecker, CKConfig.TOOLBAR_OPTIONS.Scayt, CKConfig.TOOLBAR_OPTIONS._,
                CKConfig.TOOLBAR_OPTIONS.Undo, CKConfig.TOOLBAR_OPTIONS.Redo, CKConfig.TOOLBAR_OPTIONS._,
                CKConfig.TOOLBAR_OPTIONS.Find, CKConfig.TOOLBAR_OPTIONS.Replace, CKConfig.TOOLBAR_OPTIONS._ };
        line.addAll(t1);

        // Define the second line
        /*ToolbarLine line2 = new ToolbarLine();
        TOOLBAR_OPTIONS[] t2 = {
                TOOLBAR_OPTIONS.Bold, TOOLBAR_OPTIONS.Italic,TOOLBAR_OPTIONS.Underline, TOOLBAR_OPTIONS.Strike,
                TOOLBAR_OPTIONS.Subscript, TOOLBAR_OPTIONS.Superscript, TOOLBAR_OPTIONS._,
                TOOLBAR_OPTIONS.NumberedList,TOOLBAR_OPTIONS.BulletedList, TOOLBAR_OPTIONS._,
                TOOLBAR_OPTIONS.Outdent, TOOLBAR_OPTIONS.Indent,TOOLBAR_OPTIONS.Blockquote, TOOLBAR_OPTIONS._,
                TOOLBAR_OPTIONS.JustifyLeft, TOOLBAR_OPTIONS.JustifyCenter,     TOOLBAR_OPTIONS.JustifyRight, TOOLBAR_OPTIONS.JustifyBlock,     TOOLBAR_OPTIONS._,
                TOOLBAR_OPTIONS.Image,TOOLBAR_OPTIONS.Table, TOOLBAR_OPTIONS.HorizontalRule,TOOLBAR_OPTIONS.SpecialChar };
        line2.addAll(t2);*/

        //define the third line
        /*ToolbarLine line3 = new ToolbarLine();
        TOOLBAR_OPTIONS[] t3 = {
                TOOLBAR_OPTIONS.Styles, TOOLBAR_OPTIONS.Format, TOOLBAR_OPTIONS.Font, TOOLBAR_OPTIONS.FontSize, TOOLBAR_OPTIONS._,
                TOOLBAR_OPTIONS.TextColor, TOOLBAR_OPTIONS.BGColor,     TOOLBAR_OPTIONS._,
                TOOLBAR_OPTIONS.ShowBlocks };
        line3.addAll(t3);*/

        // Creates the toolbar
        Toolbar t = new Toolbar();
        t.add(line);
        t.addSeparator();
        //t.add(line2);
        //t.addSeparator();
        //t.add(line3);

        // sets some params
        CONFIG_MODIFICATION.setResizeMinWidth(520);
        CONFIG_MODIFICATION.setResizeMinHeight(250);
        CONFIG_MODIFICATION.setResizeMaxWidth(1600);
        CONFIG_MODIFICATION.setResizeMaxHeight(1600);
        CONFIG_MODIFICATION.setEntities_latin(false);
        CONFIG_MODIFICATION.setEntities(false);
        CONFIG_MODIFICATION.setWidth(Window.getClientWidth() / 2 + "");
        // Set the toolbar to the config (replace the FULL preset toolbar)
        CONFIG_MODIFICATION.setToolbar(t);

        final CKEditor ckec = new CKEditor(CONFIG_MODIFICATION);

        editor.add(cke);
        Console.log("Ckedit fertig");

    }

    @Override
    public void setActivity(IManagementActivity activity) {
        this.activity = activity;
    }

    @Override
    public String getName() {
        return this.name;
    }
}