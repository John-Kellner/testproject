package com.project.presentation.client.gui.components.dialog.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by john on 31.05.2015.
 */
public class DialogStyle {
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

        @ClassName("box")
        String box();

        @ClassName("title")
        String title();

        @ClassName("content")
        String content();

        @ClassName("close")
        String close();
    }

    public interface ResourceBundle extends ClientBundle {

//        @Source("image/idsLogoMiniatur.png")
//        ImageResource idsLogoMinatur();

        @Source("Dialog.css")
        Resource style();
    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
