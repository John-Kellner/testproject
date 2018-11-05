package com.project.presentation.server.tx.dao.impl;

import com.project.presentation.server.tx.dao.ITabItemsDAO;
import com.project.presentation.server.tx.dao.impl.check.DuplicateTabItemBean;
import com.project.presentation.server.tx.dao.impl.check.IDuplicateEntry;
import com.project.presentation.server.tx.dom.TabItemDOM;
import com.project.presentation.shared.dto.TabItemDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TabItemsDAOImpl implements ITabItemsDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public TabItemDOM find(long id){
		final TabItemDOM tabItemDOM = em.find(TabItemDOM.class, id);
		return tabItemDOM;
	}

	@Override
	public void removeAll(long einstellungId) {
		final Query query = em.createQuery("DELETE FROM TabItemDOM AS tid WHERE tid.einstellung.einstellungId =:id").setParameter("id", einstellungId);
		query.executeUpdate();
	}

	@Override
	public IDuplicateEntry checkDuplicatedEntry(List<TabItemDTO> tabItems) {
		final List<TabItemDTO> tmpCopy = new ArrayList<>();
		boolean duplicatedEntry = false;

		for (TabItemDTO reference: tabItems) {
			if (addNewEntries(reference, tmpCopy)){
				tmpCopy.add(reference);
			}else {
				duplicatedEntry = true;
			}
		}

		final DuplicateTabItemBean bean = new DuplicateTabItemBean();
		bean.setIsDuplicate(duplicatedEntry);
		bean.setModifiedList(tmpCopy);
		return bean;
	}

	private boolean addNewEntries(TabItemDTO reference, List<TabItemDTO> tmpItems) {
		for (TabItemDTO tabItem : tmpItems) {

			/** ID bereits vorhanden, Der Eintag ist schon in tmpCopy
			 * braucht nicht mehr hinzugefuegt werden
			 */
			if (reference.getTabItemID() == tabItem.getTabItemID()  && tabItem.getTabItemID() != 0){
				return false;
			}


			/**
			 * Wenn ein Item bereits in der Liste gefuehrt ist
			 * dann pruefe ob der Name identisch ist und nimm die hohere ID
			 * Schmeise die andere raus.
			 */
			if (reference.getTabItemID() > tabItem.getTabItemID() && (reference.getName().equals(tabItem.getName()))){
				tmpItems.remove(tabItem);
				tmpItems.add(reference);
				return false;
			}

			/** Name bereits gleich */
			if (reference.getName().equals(tabItem.getName())){
				return false;
			}

		}
		return true;
	}
}