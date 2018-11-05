package com.project.presentation.client.gui.components.modal.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by john on 31.05.2015.
 */
public class ModalStyle {
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

        @ClassName("modal")
        String modal();

        @ClassName("modalFade")
        String modalFade();

        @ClassName("ground")
        String ground();
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
