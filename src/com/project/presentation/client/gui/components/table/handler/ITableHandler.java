package com.project.presentation.client.gui.components.table.handler;

import com.project.presentation.client.gui.components.table.bean.TableBean;

/**
 * Created by john on 03.07.2015.
 */
public interface ITableHandler {
    public void onItemSelect(TableBean bean);
    public void onRefresh();
}
