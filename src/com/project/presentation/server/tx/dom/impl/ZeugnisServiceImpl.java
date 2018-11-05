package com.project.presentation.server.tx.dom.impl;

import com.project.presentation.server.tx.dao.AnlagenDAO;
import com.project.presentation.server.tx.dom.AnlagenService;
import com.project.presentation.server.tx.dom.AnlageDOM;
import com.project.presentation.shared.dto.AnlageDTO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by john on 13.05.2016.
 */
@Component("ZeugnisService")
public class ZeugnisServiceImpl implements AnlagenService {

    @Resource
    private AnlagenDAO attachmentDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AnlageDTO saveZeugnis(AnlageDOM zeugnis, long einstellungID){
        return attachmentDao.save(zeugnis, einstellungID);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AnlageDTO loadAttachment(Long attachmentID) {
        return attachmentDao.load(attachmentID);
    }

    /**
     * Upload Path loader
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public File getUploadedFile(final Long id) {
        return attachmentDao.getUploadedFile(id);
    }

}
