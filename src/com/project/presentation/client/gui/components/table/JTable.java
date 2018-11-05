package com.project.presentation.client.gui.components.table;

import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.project.presentation.client.gui.components.table.bean.TableBean;
import com.project.presentation.client.gui.components.table.handler.ITableHandler;
import com.project.presentation.client.gui.components.table.model.JSelectionModel;
import com.project.presentation.client.gui.components.table.model.JTableOnSelect;
import com.project.presentation.client.management.menu.view.css.MenuViewStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 02.07.2015.
 */
public class JTable extends Composite {
    private CellTable<TableBean> table;
    private FlowPanel panel = new FlowPanel();
    private List<TableBean> beans;
    private TableBean selectedElement;
    private ITableHandler handler;
    private JSelectionModel model;

    public JTable() {
        initWidget(panel);
        beans = new ArrayList<TableBean>();
        createTable();
    }

    public void addBlurHandler(BlurHandler handler){
        table.addDomHandler(handler, BlurEvent.getType());
    }

    public void addRows(List<TableBean> beans){
        this.beans = beans;
        setRows();
    }

    private void createTable(){
        table = new CellTable<TableBean>();

        /** table selection Model */
        model = new JSelectionModel(new JTableOnSelect() {
            @Override
            public void onSelect(TableBean bean) {
                selectedElement = bean;
                if (handler != null){
                    handler.onItemSelect(bean);
                }
            }
        });

        table.setSelectionModel(model);
        table.addColumn(new TextColumn<TableBean>() {
            @Override
            public String getValue(TableBean s) {
                return s.getText();
            }
        });

        setRows();

        table.setRowStyles(new RowStyles<TableBean>() {
            @Override
            public String getStyleNames(TableBean tableBean, int i) {
                if (i % 2 == 0){
                    return MenuViewStyle.getStyle().comGoogleGwtUserCellviewClientCellTableStyleCellTableEvenRow();
                }
                return MenuViewStyle.getStyle().comGoogleGwtUserCellviewClientCellTableStyleCellTableOddRow();
            }
        });

        panel.add(table);
    }

    private void setRows(){
        table.setRowData(beans);
        if (handler != null){
            handler.onRefresh();
        }
    }

    public TableBean getSelectedElement(){
        return this.selectedElement;
    }

    /** remove selected handler */
    public void removeSelectedElement() {
        beans.remove(selectedElement);
        setRows();
    }

    /** add a table handler */
    public void addTableHander(ITableHandler handler){
        this.handler = handler;
        this.handler.onRefresh();
    }

    public void removeAllRows(){
        beans.clear();
    }

    public List<TableBean> getAllElements() {
        return this.beans;
    }

    public void addRow(TableBean row) {
        beans.add(row);
        table.setRowData(beans);
        if (handler != null){
            handler.onRefresh();
        }
    }

    public void setSelectedRow(TableBean bean){
        model.setSelected(bean, true);
    }
}