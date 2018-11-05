package com.project.java.presentation.image;

import com.project.java.presentation.server.ServiceDatenbankTest;
import com.project.presentation.server.tx.dom.IHibernateService;
import com.project.presentation.shared.dto.EinstellungDTO;
import com.project.presentation.shared.dto.ImageDTO;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import util.PersistenceLoader;

import java.util.List;

/**
 * Created by john on 15.07.2015.
 */
public class ImageDAOTest {
    /** The service. */
    private final IHibernateService service = (IHibernateService) PersistenceLoader
            .getApplicationContext().getBean("HibernateService");

    /** The logger. */
    private Logger logger = Logger.getLogger(ServiceDatenbankTest.class);

    /**
     * Ladet alle Bilder zu der Einstellung mit der einstellungID = 1
     */
    @Test(groups = "ServiceTest", enabled = true)
    public void loadAllImagesForOneSetting() {

        final EinstellungDTO einstellungDTO = new EinstellungDTO();
        einstellungDTO.setEinstellungId(1);

        final List<ImageDTO> container = service.loadAllImages(einstellungDTO.getEinstellungId());
        logger.info("Images found " + container.size());

    }
}
