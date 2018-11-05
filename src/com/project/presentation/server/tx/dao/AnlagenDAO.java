package com.project.presentation.server.tx.dao;

import com.project.presentation.server.tx.dom.AnlageDOM;
import com.project.presentation.shared.dto.AnlageDTO;

import java.io.File;
import java.util.List;

/**
 * Created by john on 13.05.2016.
 */
public interface AnlagenDAO {
    boolean deleteAttachment(Long id);

    AnlageDTO save(AnlageDOM zeugnis, long einstellungID);

    AnlageDTO load(Long attachmentID);

    File getUploadedFile(Long id);
}
