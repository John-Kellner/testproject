package com.project.presentation.client.gui.components.table.model;

import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.project.presentation.client.gui.components.table.bean.TableBean;

/**
 * Created by john on 03.07.2015.
 */
public class JSelectionModel extends SingleSelectionModel<TableBean> {

    public JSelectionModel(final JTableOnSelect handler) {
        addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                handler.onSelect(getSelectedObject());
            }
        });
    }
}
