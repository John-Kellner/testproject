package com.project.presentation.server.tx.dao.impl.check;

import com.project.presentation.shared.dto.TabItemDTO;

import java.util.List;

/**
 * Created by john on 11.07.2015.
 */
public interface IDuplicateEntry {
    boolean isDuplicate();
    List<TabItemDTO> getModifiedList();
}
