package com.project.presentation.client.gui.layouts.defaulttheme.body.attachment.popup.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by john on 22.06.2015.
 */
public class AttachedPopUpStyle {
    private static Resource style;
    private static ResourceBundle bundle;

    static {
        bundle = GWT.create(ResourceBundle.class);
        style = bundle.style();
        style.ensureInjected();
    }

    public interface Resource extends CssResource {

        @ClassName("root")
        String root();

        @ClassName("header")
        String header();

        @ClassName("headerTitel")
        String headerTitel();

        @ClassName("body")
        String body();

        @ClassName("noSelect")
        String noSelect();
    }

    public interface ResourceBundle extends ClientBundle {

        @Source("AttachedPopUpFiles.css")
        Resource style();

    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
