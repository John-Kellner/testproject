package com.project.presentation.client.gui.components.dragablefiles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.project.presentation.client.gui.components.dragablefiles.css.DragableFileStyle;
import com.project.presentation.client.gui.components.dragablefiles.dropItem.DropItem;
import com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload.UploadBean;
import com.project.presentation.client.gui.components.dragablefiles.dropItem.fromupload.UploadHandler;
import com.project.presentation.client.gui.components.dragablefiles.pdfitem.PDFItem;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;
import com.project.presentation.shared.dto.AnlageDTO;

import java.util.List;

/**
 * Created by john on 15.05.2016.
 */
public class DragableFiles extends FlowPanel implements Repaint {
    private DragableFileStyle.Resource style = DragableFileStyle.getStyle();
    private RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);
    private DropItem dropItem;

    public DragableFiles(DragableConfig config) {
        setStyleName(style.scrollDropPanel());
        dropItem = new DropItem(config);
        dropItem.setRepaint(this);
        dropItem.setUploadHandler(new UploadHandler() {
            @Override
            public void onUploadComplete(UploadBean bean) {
                final PDFItem pdfItem = new PDFItem(bean.getAttachmentID(), bean.getFilename());
                pdfItem.setRepaintCallback(DragableFiles.this);
                add(pdfItem);

                repaint();
            }
        });
        add(dropItem);
        repaint();
    }

    /**
     * Disable all Selected Documents
     */
    @Override
    public void repaint(){
        for (int i=0;i<getWidgetCount();i++){
            final Widget widget = getWidget(i);
            if (widget instanceof IsSelected){
                IsSelected hasSelectd = (IsSelected) widget;
                hasSelectd.onFocusLost();
            }
        }
    }

    /**
     * Load All PDF Documents after it has been loaded from RPC Service
     * @param pdfs
     */
    public void setAnlagen(List<AnlageDTO> pdfs) {
        clear();
        add(dropItem);
        for (AnlageDTO pdf : pdfs) {
            final PDFItem pdfItem = new PDFItem(pdf.getId(), pdf.getFilename());
            pdfItem.setRepaintCallback(DragableFiles.this);
            add(pdfItem);

            repaint();
        }
    }
}
