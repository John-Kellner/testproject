package com.project.presentation.server.util;

import org.hibernate.Query;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QueryParamHelper {
	Map<String, Object> map;

	public QueryParamHelper() {
		map = new HashMap<String, Object>();
	}

	public void addProperty(String str, Object obj) {
		if (str != null) {
			map.put(str, obj);
		}
	}

	public void clear() {
		map.clear();
	}

	public Query applyParameter(Query query) {
		for (Map.Entry<String, Object> m : map.entrySet()) {
			if (m.getValue() instanceof Date) {
				query.setDate(m.getKey(), (Date) m.getValue());
			}
			if (m.getValue() instanceof String) {
				query.setString(m.getKey(), (String) m.getValue());
			}
			if (m.getValue() instanceof Long) {
				query.setLong(m.getKey(), (Long) m.getValue());
			}
			if (m.getValue() instanceof Integer) {
				query.setInteger(m.getKey(), (Integer) m.getValue());
			}
			if (m.getValue() instanceof Boolean) {
				query.setBoolean(m.getKey(), (Boolean) m.getValue());
			}
			if (m.getValue() instanceof Character) {
				query.setCharacter(m.getKey(), (Character) m.getValue());
			}
			if (m.getValue() instanceof Double) {
				query.setDouble(m.getKey(), (Double) m.getValue());
			}
		}
		return query;
	}

	public TypedQuery<?> applyParameter(TypedQuery<?> query) {
		for (Map.Entry<String, Object> m : map.entrySet()) {
			if (m.getValue() instanceof Date) {
				query.setParameter(m.getKey(), (Date) m.getValue());
			}
			if (m.getValue() instanceof String) {
				query.setParameter(m.getKey(), (String) m.getValue());
			}
			if (m.getValue() instanceof Long) {
				query.setParameter(m.getKey(), (Long) m.getValue());
			}
			if (m.getValue() instanceof Integer) {
				query.setParameter(m.getKey(), (Integer) m.getValue());
			}
			if (m.getValue() instanceof Boolean) {
				query.setParameter(m.getKey(), (Boolean) m.getValue());
			}
			if (m.getValue() instanceof Character) {
				query.setParameter(m.getKey(), (Character) m.getValue());
			}
			if (m.getValue() instanceof Double) {
				query.setParameter(m.getKey(), (Double) m.getValue());
			}
		}
		return query;
	}
}
