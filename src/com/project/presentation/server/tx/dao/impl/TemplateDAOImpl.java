package com.project.presentation.server.tx.dao.impl;

import com.project.presentation.server.tx.dao.ITemplateDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
	public class TemplateDAOImpl implements ITemplateDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void removeAll(long einstellungId) {
		final Query query = em.createQuery("DELETE FROM TemplateDOM AS t WHERE t.einstellung.einstellungId =:id").setParameter("id", einstellungId);
		query.executeUpdate();
	}
}