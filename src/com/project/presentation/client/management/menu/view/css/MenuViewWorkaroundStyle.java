package com.project.presentation.client.management.menu.view.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * Created by john on 22.06.2015.
 */
public class MenuViewWorkaroundStyle {
    private static Resource style;
    private static ResourceBundle bundle;

    static {
        bundle = GWT.create(ResourceBundle.class);
        style = bundle.style();
        style.ensureInjected();
    }

    public interface Resource extends CssResource {

        String p();

        @ClassName("com-google-gwt-user-cellview-client-CellTable-Style-cellTableSelectedRow")
        String comGoogleGwtUserCellviewClientCellTableStyleCellTableSelectedRow();

        @ClassName("btn-large")
        String btnLarge();

        String btn();
    }

    public interface ResourceBundle extends ClientBundle {

        @Source("image/gostraight.png")
        ImageResource image();

        @Source("workaround.css")
        Resource style();
    }

    public static Resource getStyle(){
        return style;
    }

    public static ResourceBundle getBundle(){
        return  bundle;
    }
}
