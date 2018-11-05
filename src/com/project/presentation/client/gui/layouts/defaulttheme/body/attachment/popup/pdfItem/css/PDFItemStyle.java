package com.project.presentation.client.gui.layouts.defaulttheme.body.attachment.popup.pdfItem.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by john on 22.06.2015.
 */
public class PDFItemStyle {
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

        @ClassName("image")
        String image();

        @ClassName("text")
        String text();

        @ClassName("hinweis")
        String hinweis();

        @ClassName("panel")
        String panel();

        @ClassName("noSelect")
        String noSelect();
    }

    public interface ResourceBundle extends ClientBundle {

        @Source("PDFItem.css")
        Resource style();

    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
