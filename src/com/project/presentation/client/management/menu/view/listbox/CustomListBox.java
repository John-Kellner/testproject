package com.project.presentation.client.management.menu.view.listbox;

import com.google.gwt.user.client.ui.ListBox;

/**
 * Created by john on 05.07.2015.
 */

//TODO JKE : Klasse entfernen, wenn MenuView umgebaut ist
public class CustomListBox extends ListBox {
    private final String id;

    public CustomListBox()
    {
        this.id = "blubb";
        getElement().setId(this.id);
    }

    @Override
    protected void onLoad()
    {
        super.onLoad();
        createInternalChangeHandler(this.id, this);
    }

    private void onChangeInternal()
    {
        // Do your thing!
    }

    private static native void createInternalChangeHandler(String id, CustomListBox self)
    /*-{
        var callback = $entry(function()
        {
            self.@com.project.presentation.client.management.menu.view.listbox.CustomListBox::onChangeInternal()();
        });

        $wnd.jQuery('#' + id).material_select();
        $wnd.jQuery('#' + id).change(callback);
    }-*/;
}
