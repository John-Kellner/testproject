package com.project.presentation.client.management.studio.view.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by john on 22.06.2015.
 */
public class ManagementViewStyle {
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

        @ClassName("headerLabel")
        String headerLabel();

        @ClassName("customFooter")
        String customFooter();

        @ClassName("panel")
        String panel();

        @ClassName("gwt-material-title")
        String gwtMaterialTitle();
    }

    public interface ResourceBundle extends ClientBundle {

        @Source("ManagementView.css")
        Resource style();
    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
