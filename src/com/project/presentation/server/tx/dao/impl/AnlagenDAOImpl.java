package com.project.presentation.server.tx.dao.impl;

import com.project.presentation.server.tx.dao.AnlagenDAO;
import com.project.presentation.server.tx.dom.EinstellungDOM;
import com.project.presentation.server.tx.dom.AnlageDOM;
import com.project.presentation.server.upload.bean.UploadBean;
import com.project.presentation.shared.dto.AnlageDTO;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.enumerations.ENotify;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.forceDelete;

/**
 * Created by john on 13.05.2016.
 */
@Repository
public class AnlagenDAOImpl implements AnlagenDAO {
    private static final Logger logger = Logger.getLogger(AnlagenDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    private UploadBean uploadBean;

    @Autowired
    @Required
    @Qualifier("uploadSource")
    public void setUploadBean(UploadBean bean){
        this.uploadBean = bean;
    }

    /**
     * Delete File on FileSystem
     * @param id
     * @return
     */
    @Override
    public boolean deleteAttachment(final Long id){
        try {
            // Delete File on Filesystem
            final File uploadedFile = getUploadedFile(id);
            if (uploadedFile.exists()){
                forceDelete(getUploadedFile(id));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            //Delete on Database
            final AnlageDOM attachment = em.find(AnlageDOM.class, id);
            if (attachment != null){
                attachment.setEinstellung(null);
                em.remove(attachment);
                em.flush();

                return true;
            }
        }
        return false;
    }

    @Override
    public AnlageDTO save(AnlageDOM anlage, long einstellungID) {
        final AnlageDTO anlageDTO = new AnlageDTO();
        final NotificationBean notification = new NotificationBean();
        try{
            anlage.setEinstellung(em.find(EinstellungDOM.class, einstellungID));
            em.persist(anlage);
            em.flush();
            notification.setNotify(ENotify.SUCCESS);
            notification.setMessage("Zeugnis wurde gespeichert");
            anlageDTO.setNotification(notification);
            anlageDTO.setId(anlage.getId());
            return anlageDTO;
        }catch (ConstraintViolationException cve){
            notification.setNotify(ENotify.FAILURE);
            notification.setMessage("Ein Zeugnis existiert bereits");
            anlageDTO.setNotification(notification);
            return anlageDTO;
        }catch (Exception ex){
            notification.setNotify(ENotify.FAILURE);
            notification.setMessage("Ein Zeugnis mit dem Namen existiert vermutlich bereits");
            anlageDTO.setNotification(notification);
            return anlageDTO;
        }
    }

    @Override
    public AnlageDTO load(Long attachmentID) {
        final AnlageDOM anlage = em.find(AnlageDOM.class, attachmentID);
        return AnlageDOM.convert(anlage);
    }

    /**
     * Relative Upload File
     * @param id
     * @return
     */
    @Override
    public File getUploadedFile(final Long id) {
        final AnlageDTO anlageDTO = load(id);
        final StringBuilder path = new StringBuilder();
        path.append(uploadBean.getZeugnisUploadDir());
        path.append(File.separator);
        path.append(anlageDTO.getEinstellungID());
        path.append(File.separator);
        path.append(anlageDTO.getFilename());
        return new File(path.toString());
    }
}
