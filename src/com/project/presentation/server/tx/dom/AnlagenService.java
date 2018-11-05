package com.project.presentation.server.tx.dom;

import com.project.presentation.shared.dto.AnlageDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * Created by john on 13.05.2016.
 */
public interface AnlagenService {
    AnlageDTO saveZeugnis(AnlageDOM zeugnis, long einstellungID);

    AnlageDTO loadAttachment(Long attachmentID);

    File getUploadedFile(Long id);
}
