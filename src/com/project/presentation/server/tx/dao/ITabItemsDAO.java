/*
 * Access to the Topics in the database
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dao;


import com.project.presentation.server.tx.dao.impl.check.IDuplicateEntry;
import com.project.presentation.server.tx.dom.TabItemDOM;
import com.project.presentation.shared.dto.TabItemDTO;

import java.util.List;

/**
 * The Interface IFotostudioDAO offers methods to saveNewUser, delete and login topics.
 */
public interface ITabItemsDAO {

	TabItemDOM find(long id);

	void removeAll(long einstellungId);

	IDuplicateEntry checkDuplicatedEntry(List<TabItemDTO> tabItems);
}
