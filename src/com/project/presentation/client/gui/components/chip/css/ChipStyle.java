package com.project.presentation.client.gui.components.chip.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by john on 31.05.2015.
 */
public class ChipStyle {
    private static Resource style;
    private static ResourceBundle bundle;

    static {
        bundle = GWT.create(ResourceBundle.class);
        style = bundle.style();
        style.ensureInjected();
    }

    public interface Resource extends CssResource {

        @ClassName("close")
        String close();

        @ClassName("root")
        String root();

        @ClassName("text")
        String text();

        @ClassName("ground")
        String ground();

        @ClassName("textBox")
        String textBox();

        @ClassName("save")
        String save();

        @ClassName("label")
        String label();

        @ClassName("anzeige")
        String anzeige();

        @ClassName("arrowright")
        String arrowright();

        @ClassName("arrowleft")
        String arrowleft();

        @ClassName("remove")
        String remove();

        @ClassName("plus")
        String plus();
    }

    public interface ResourceBundle extends ClientBundle {

//        @Source("image/idsLogoMiniatur.png")
//        ImageResource idsLogoMinatur();

        @Source("Chip.css")
        Resource style();
    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
