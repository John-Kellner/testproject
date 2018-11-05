package com.project.presentation.client.gui.components.dragablefiles.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created by john on 22.06.2015.
 */
public class DragableFileStyle {
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

        @ClassName("scrollDropPanel")
        String scrollDropPanel();

        @ClassName("dropItem")
        String dropItem();

        @ClassName("emptyItem")
        String emptyItem();

        @ClassName("pdfImage")
        String pdfImage();

        @ClassName("innerPanel")
        String innerPanel();

        @ClassName("imagepanel")
        String imagepanel();

        @ClassName("textpanel")
        String textpanel();

        @ClassName("dropImage")
        String dropImage();

        @ClassName("text")
        String text();

        @ClassName("durchsuchen")
        String durchsuchen();

        @ClassName("pdfName")
        String pdfName();

        @ClassName("pdfItem")
        String pdfItem();

        @ClassName("pdfItemBorderSelected")
        String pdfItemBorderSelected();

        @ClassName("modalPanel")
        String modalPanel();

        @ClassName("actions")
        String actions();

        @ClassName("btnPdfDelete")
        String btnPdfDelete();

        @ClassName("btnDownload")
        String btnDownload();

        @ClassName("document")
        String document();
    }

    public interface ResourceBundle extends ClientBundle {

        @Source("DragableFile.css")
        Resource style();

        @Source("image/Adobe_PDF_file_icon_32x32.png")
        ImageResource pdfLogo();

    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
