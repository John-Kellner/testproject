package com.project.presentation.client.gui.layouts.defaulttheme.body;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.project.presentation.client.gui.layouts.defaulttheme.body.attachment.AttachedWidget;
import com.project.presentation.client.gui.layouts.defaulttheme.css.LayoutSytle;
import com.project.presentation.shared.dto.AnlageDTO;

import java.util.List;

/**
 * Created by john on 10.06.2015.
 */
public class Body extends Composite implements IHTMLHandler{
    private final LayoutSytle.Resource style = LayoutSytle.getStyle();
    private final HTMLPanel ground = new HTMLPanel("");
    private final HTML html = new HTML();

    public Body(){
        html.setStyleName(style.htmlMessage());

        ground.add(html);

        ground.setStyleName(style.ground());
        initWidget(ground);
    }

    @Override
    public void setMessage(String text){
        html.setHTML(text);
    }

    public void setAttachedFiles(List<AnlageDTO> attachedFiles) {
        if (attachedFiles != null){
            ground.add(new AttachedWidget(attachedFiles));
        }
    }
}
