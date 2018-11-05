package com.project.presentation.server.tx.dao.impl.check;

import com.project.presentation.shared.dto.TabItemDTO;

import java.util.List;

/**
 * Created by john on 11.07.2015.
 */
public class DuplicateTabItemBean implements IDuplicateEntry{


    private boolean isDuplicate;
    private List<TabItemDTO> modifiedList;


    public void setIsDuplicate(boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public void setModifiedList(List<TabItemDTO> modifiedList) {
        this.modifiedList = modifiedList;
    }

    @Override
    public boolean isDuplicate() {
        return this.isDuplicate;
    }

    @Override
    public List<TabItemDTO> getModifiedList() {
        return this.modifiedList;
    }
}
