package com.project.presentation.client.management.menu.view.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created by john on 22.06.2015.
 */
public class MenuViewStyle {
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

        @ClassName("cards")
        String cards();

        @ClassName("grid")
        String grid();

        @ClassName("panel")
        String panel();

        @ClassName("hline")
        String hline();

        @ClassName("imagespace")
        String imagespace();

        @ClassName("reiter-auswahl")
        String reiterAuswahl();

        @ClassName("com-google-gwt-user-cellview-client-CellTable-Style-cellTableEvenRow")
        String comGoogleGwtUserCellviewClientCellTableStyleCellTableEvenRow();

        @ClassName("com-google-gwt-user-cellview-client-CellTable-Style-cellTableOddRow")
        String comGoogleGwtUserCellviewClientCellTableStyleCellTableOddRow();

        @ClassName("qrCodeLink")
        String qrCodeLink();

        @ClassName("qrDownloadLink")
        String qrDownloadLink();

        @ClassName("header-liquendi")
        String headerliquendi();

        @ClassName("menuheader")
        String menuheader();

        @ClassName("lblWillkommen")
        String lblWillkommen();

        @ClassName("lblName")
        String lblName();

        @ClassName("modal")
        String modal();

        @ClassName("modalFade")
        String modalFade();

        @ClassName("md5checkbox")
        String md5checkbox();

        @ClassName("md5linkactivetext")
        String md5linkactivetext();

        @ClassName("persoenlicherlinktext")
        String persoenlicherlinktext();

        @ClassName("headerpanel")
        String headerpanel();

        @ClassName("tabitemDescribtion")
        String tabitemDescribtion();

        @ClassName("picture")
        String picture();

        @ClassName("pictureFadeOFF")
        String pictureFadeOFF();

        @ClassName("pictureFadeON")
        String pictureFadeON();

        @ClassName("image")
        String image();

        @ClassName("contactlabel")
        String contactlabel();

        @ClassName("oeffnunggszeiten")
        String oeffnunggszeiten();

        @ClassName("maxwidth")
        String maxwidth();

        @ClassName("contactpanel")
        String contactpanel();

        @ClassName("imageupload")
        String imageupload();

        @ClassName("roboto")
        String roboto();

        @ClassName("logoHeader")
        String logoHeader();
    }

    public interface ResourceBundle extends ClientBundle {

        @Source("MenuView.css")
        Resource style();

        @Source("image/gostraight.png")
        ImageResource image();

        @Source("image/dummy.png")
        ImageResource dummy();
    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
