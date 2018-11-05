package com.project.presentation.server.tx.dom.impl;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.project.presentation.server.tx.dao.AnlagenDAO;
import com.project.presentation.server.tx.dao.impl.AnlagenDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by john on 17.05.2016.
 */
@Component
public class ZeugnisLocator extends Locator<AnlagenDAOImpl, Long> {

    @Autowired
    protected AnlagenDAO zeugnisDao;

    @Override
    public AnlagenDAOImpl create(Class<? extends AnlagenDAOImpl> aClass) {
        return null;
    }

    @Override
    public AnlagenDAOImpl find(Class<? extends AnlagenDAOImpl> aClass, Long aLong) {
        return null;
    }

    @Override
    public Class<AnlagenDAOImpl> getDomainType() {
        return null;
    }

    @Override
    public Long getId(AnlagenDAOImpl zeugnisServiceDAO) {
        return null;
    }

    @Override
    public Class<Long> getIdType() {
        return null;
    }

    @Override
    public Object getVersion(AnlagenDAOImpl zeugnisServiceDAO) {
        return null;
    }
}
