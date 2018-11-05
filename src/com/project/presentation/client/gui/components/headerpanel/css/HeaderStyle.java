package com.project.presentation.client.gui.components.headerpanel.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by john on 31.05.2015.
 */
public class HeaderStyle {
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

        @ClassName("toggelbtn")
        String toggelbtn();

        @ClassName("leftlblrahmen")
        String leftlblrahmen();

        @ClassName("bildlblrahmen")
        String bildlblrahmen();

        @ClassName("gridrow")
        String gridrow();

        @ClassName("itemlbl")
        String itemlbl();

        @ClassName("underscore")
        String underscore();

        @ClassName("underscoredisabled")
        String underscoredisabled();
    }

    public interface ResourceBundle extends ClientBundle {

//        @Source("image/idsLogoMiniatur.png")
//        ImageResource idsLogoMinatur();

        @Source("HeaderPanel.css")
        Resource style();
    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
