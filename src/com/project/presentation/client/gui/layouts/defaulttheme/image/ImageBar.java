package com.project.presentation.client.gui.layouts.defaulttheme.image;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.project.presentation.client.gui.layouts.defaulttheme.css.LayoutSytle;

/**
 * Created by john on 09.06.2015.
 */
public class ImageBar extends Composite {
    private final LayoutSytle.Resource style = LayoutSytle.getStyle();
    private FlowPanel ground = new FlowPanel();
    private final FlowPanel background;
    private final Label label2;
    private final Label label;

    public ImageBar (){
        ground.setStyleName(style.imageBarGround());
        background = new FlowPanel();
        background.setStyleName(style.imageBarBackground());

        label = new Label("Bewerbung");
        label.setStyleName(style.imageLabel());
        ground.add(label);

        label2 = new Label("Bewerbung");
        label2.setStyleName(style.imageLabelShadow());
        ground.add(label2);

        ground.add(background);
        initWidget(ground);
    }

    /**
     * Base64 Image
     * @param name
     */
    public void setImage(String name){
        final Image image = new Image();
        image.setStyleName(style.imageMeeting());
        image.setUrl("data:image/png;base64," + name);

        background.clear();
        background.add(image);
    }

    /**
     * Image PNG JPG
     * @param image
     */
    public void setImage(Image image) {
        image.setStyleName(style.imageMeeting());

        background.clear();
        background.add(image);
    }

    public void setTitle(String title){
        label.setText(title);
        label2.setText(title);
    }
}
