package com.project.presentation.client.gui.layouts.defaulttheme.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import org.helios.gwt.fonts.client.FontResource;

/**
 * Created by john on 31.05.2015.
 */
public class LayoutSytle {
    private static Resource style;
    private static ResourceBundle bundle;

    static {
        bundle = GWT.create(ResourceBundle.class);
        style = bundle.style();
        style.ensureInjected();
    }

    public interface Resource extends CssResource {

        @ClassName("headerGround")
        String headerGround();

        @ClassName("root")
        String root();

        @ClassName("imageBarGround")
        String imageBarGround();

        @ClassName("menuBarGround")
        String menuBarGround();

        @ClassName("imageBarBackground")
        String imageBarBackground();

        @ClassName("imageMeeting")
        String imageMeeting();

        @ClassName("headerLabel")
        String headerLabel();

        @ClassName("tabBar")
        String tabBar();

        @ClassName("shadow")
        String shadow();

        @ClassName("htmlMessage")
        String htmlMessage();

        @ClassName("ground")
        String ground();

        @ClassName("imageLabel")
        String imageLabel();

        @ClassName("imageLabelShadow")
        String imageLabelShadow();

        @ClassName("imagePDFDownload")
        String imagePDFDownload();

        @ClassName("downloadPDFText")
        String downloadPDFText();
    }

    public interface ResourceBundle extends ClientBundle {

        @Source("font/RobotoCondensed-Regular.ttf")
        FontResource robotoRegular();

        @Source("font/RobotoCondensed-Bold.ttf")
        FontResource robotoCondensedBold();

        @Source("font/RobotoCondensed-BoldItalic.ttf")
        FontResource robotoBoldItalic();

        @Source("font/RobotoCondensed-Italic.ttf")
        FontResource robotoItalic();

        @Source("font/RobotoCondensed-Light.ttf")
        FontResource robotoLight();

        @Source("font/RobotoCondensed-LightItalic.ttf")
        FontResource robotoLightItalic();

        @Source("Header.css")
        Resource style();

        @Source("image/dummy.png")
        ImageResource dummyImageAsPlaceholder();
    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
