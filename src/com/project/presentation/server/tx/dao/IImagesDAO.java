/*
 * Access to the Topics in the database
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dao;


import com.project.presentation.server.tx.dom.ImageDOM;
import com.project.presentation.shared.dto.ImageDTO;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.view.ImageView;

import java.util.List;

/**
 * The Interface IFotostudioDAO offers methods to saveNewUser, delete and login topics.
 */
public interface IImagesDAO {

	ImageView save(ImageDTO image, long idEinstellung);

	List<ImageDTO> loadAllImages(long idEinstellung);

	NotificationBean deleteImage(long id);

	ImageDOM find(long imagesID);

	ImageDTO findImage(long imageID);
}
