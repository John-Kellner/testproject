package com.project.presentation.client.management.start.view.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;

/**
 * Created by john on 22.06.2015.
 */
public class StartViewStyle {
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
    }

    public interface ResourceBundle extends ClientBundle {

        @Source("StartView.css")
        Resource style();

        @Source("doc/Impressum.txt")
        TextResource impressumText();

        @Source("doc/Datenschutzerklarrung.txt")
        TextResource datenschutzerklarung();
    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
