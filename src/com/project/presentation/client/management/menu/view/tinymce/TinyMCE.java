package com.project.presentation.client.management.menu.view.tinymce;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.project.presentation.client.console.log.Console;
import gwt.material.design.client.ui.MaterialToast;

/**
 * Created by john on 16.08.2015.
 */
public class TinyMCE extends Composite {

    private TextArea ta;
    private String id;
    private AbstractTinyMCEConfiguration config;

    public TinyMCE(AbstractTinyMCEConfiguration config) {
        super();
        if (config == null) {
            GWT.log("The configuration cannot be null", null);
            return;
        }
        this.config = config;
        initWidget(initTinyMCE());
    }

    public TinyMCE(TinyMCEInitListener listener) {
        this(new DefaultTinyMCEConfiguration(listener));
    }

    private VerticalPanel initTinyMCE() {
        int width = 65 ;
        int height = 30 ;
        VerticalPanel panel = new VerticalPanel();
        panel.setWidth("100%");

        id = HTMLPanel.createUniqueId();
        ta = new TextArea();
        ta.setCharacterWidth(width);
        ta.setVisibleLines(height);
        DOM.setElementAttribute(ta.getElement(), "id", id);
        DOM.setStyleAttribute(ta.getElement(), "width", "100%");
        DOM.setStyleAttribute(ta.getElement(), "height", "450px"); // delete
        // line?
        panel.add(ta);
        return panel;
    }

    /**
     * getID() -
     *
     * @return the MCE element's ID
     */
    public String getID() {
        return id;
    }

    /**
     * setText() -
     *
     * NOTE:
     *
     * @param text
     */
    public void setText(String text) {
        ta.setText(text);
    }

    public void clearContent(){
        cleanUpActiveEditor(id);
    }

    public native boolean isEditorInitialized() /*-{
        return $wnd.tinyMCE.activeEditor.initialized;
    }-*/;

    protected native void cleanUpActiveEditor(String id) /*-{
        $wnd.tinyMCE.activeEditor.setContent('');
        $wnd.tinymce.get(id).execCommand('resetMCE')
    }-*/;

    protected native void clear() /*-{
        $wnd.tinyMCE.activeEditor.execCommand('resetMCE');
        $wnd.tinyMCE.activeEditor.setContent(' ');
    }-*/;

    public native void setContent(String text) /*-{
        $wnd.tinyMCE.activeEditor.setContent(text);
    }-*/;

    public String getText() {
        getTextData();
        return ta.getText();
    }

    /**
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    protected void onLoad() {
        super.onLoad();

        DeferredCommand.addCommand(new Command() {
            public void execute() {
                setWidth("100%");
                init(config);
                setTextAreaToTinyMCE(id);
                focusMCE(id);
                //setContent("<h1>dsfds</h1>");
            }
        });
    }

    /**
     * focusMCE() -
     *
     * Use this to set the focus to the MCE area
     *
     * @param id
     *            - the element's ID
     */
    protected native void focusMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceFocus', true, id);
    }-*/;

    /**
     * resetMCE() -
     *
     * Use this if reusing the same MCE element, but losing focus
     */
    protected native void resetMCE() /*-{
        $wnd.tinyMCE.execCommand('mceResetDesignMode', true);
    }-*/;

    /**
     * unload() -
     *
     * Unload this MCE editor instance from active memory. I use this in the
     * onHide function of the containing widget. This helps to avoid problems,
     * especially when using tabs.
     */
    public void unload() {
        unloadMCE(id);
    }

    /**
     * unloadMCE() -
     *
     * @param id
     *            - The element's ID JSNI method to implement unloading the MCE
     *            editor instance from memory
     */
    protected native void unloadMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceRemoveControl', false, id);
    }-*/;

    /**
     * updateContent() -
     *
     * Update the internal referenced content. Use this if you programatically
     * change the original text area's content (eg. do a clear)
     *
     * @param id
     *            - the ID of the text area that contains the content you wish
     *            to copy
     */
    protected native void updateContent(String id) /*-{
        $wnd.tinyMCE.selectedInstance = $wnd.tinyMCE.getInstanceById(id);
        $wnd.tinyMCE.setContent($wnd.document.getElementById(id).value);
    }-*/;

    /**
     * getTextArea() -
     *
     */
    protected native void getTextData() /*-{
        $wnd.tinyMCE.triggerSave();
    }-*/;

    /**
     * encodeURIComponent() -
     *
     * Wrapper for the native URL encoding methods
     *
     * @param text
     *            - the text to encode
     * @return the encoded text
     */
    protected native String encodeURIComponent(String text) /*-{
        return encodeURIComponent(text);
    }-*/;

    /**
     * setTextAreaToTinyMCE() -
     *
     * Change a text area to a tiny MCE editing field
     *
     * @param id
     *            - the text area's ID
     */
    protected native void setTextAreaToTinyMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceAddControl', true, id);
    }-*/;

    /**
     * removeMCE() -
     *
     * Remove a tiny MCE editing field from a text area
     *
     * @param id
     *            - the text area's ID
     */
    protected native void removeMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceRemoveControl', true, id);
    }-*/;

    /*public native void setContent(String content) /*-{
        $wnd.tinyMCE.setContent(content);
    }-*/;

    public String initMode(){
        return config.getMode();
    }



    /**
     * TODO: use a configuration class to configure as like the editor (see codemirror configuration or ask Sam)
     * Initialize Tiny MCE editor
     * http://wiki.moxiecode.com/index.php/TinyMCE:Configuration for details
     */
    protected native void init(AbstractTinyMCEConfiguration conf) /*-{
        $wnd.tinyMCE.init({
            // General options
            mode : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getMode()(),
            theme : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getTheme()(),
            skin : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getSkin()(),
            entity_encoding : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getEntityEncoding()(),
            plugins : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getPlugins()(),

            removed_menuitems: 'newdocument',
            language : "de",

            // Theme options
            // excluded buttons: ,fontselect,fontsizeselect,preview,image,help,|,forecolor,backcolor tablecontrols,|,,emotions,media,|,print
            theme_advanced_buttons1 : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getThemeAdvancedButtons1()(),
            theme_advanced_buttons2 : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getThemeAdvancedButtons2()(),
            theme_advanced_buttons3 : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getThemeAdvancedButtons3()(),
            //theme_advanced_buttons4 : conf.@gr.open.client.AbstractTinyMCEConfiguration::getThemeAdvancedButtons4()(),
            theme_advanced_toolbar_location : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getThemeAdvancedToolbarLocation()(),
            theme_advanced_toolbar_align : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getThemeAdvancedToolbarAlign()(),
            theme_advanced_statusbar_location : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getThemeAdvancedStatusbarLocation()(),
            theme_advanced_resizing : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getThemeAdvancedResizing()(),

            // Example content CSS (should be your site CSS)
            //content_css : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getContentCss()(),

            // Drop lists for link/image/media/template dialogs
            template_external_list_url : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getTemplateExternalListUrl()(),
            external_link_list_url : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getExternalLinkListUrl()(),
            external_image_list_url : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getExternalImageListUrl()(),
            media_external_list_url : conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::getMediaExternalListUrl()(),

            // Replace values for the template plugin
            template_replace_values : {
                username : "Some User",
                staffid : "991234"
            },

            //Initialisierung des editors
            setup: function(editor) {
                editor.on('init', function() {
                    conf.@com.project.presentation.client.management.menu.view.tinymce.AbstractTinyMCEConfiguration::onTinyMCEInitialized()()
                });
            }
        });
    }-*/;


    public AbstractTinyMCEConfiguration getConfig() {
        return config;
    }
}
