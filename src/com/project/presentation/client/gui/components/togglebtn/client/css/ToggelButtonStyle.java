package com.project.presentation.client.gui.components.togglebtn.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by john on 31.05.2015.
 */
public class ToggelButtonStyle {
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

        @ClassName("rahmen")
        String rahmen();

        @ClassName("round")
        String round();

        @ClassName("roundAnimatedFadeOff")
        String roundAnimatedFadeOff();

        @ClassName("roundAnimatedFadeOn")
        String roundAnimatedFadeOn();

        @ClassName("borderAnimatedFadeOff")
        String borderAnimatedFadeOff();

        @ClassName("borderAnimatedFadeOn")
        String borderAnimatedFadeOn();

        @ClassName("text")
        String text();
    }

    public interface ResourceBundle extends ClientBundle {

//        @Source("image/idsLogoMiniatur.png")
//        ImageResource idsLogoMinatur();

        @Source("ToggelButton.css")
        Resource style();
    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
