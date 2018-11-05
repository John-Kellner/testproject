/*
 * Service for database
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dom;

import com.project.presentation.server.email.SMTPConfigBean;
import com.project.presentation.server.upload.bean.DomainBean;
import com.project.presentation.server.upload.bean.UploadBean;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.view.ImageView;
import com.project.presentation.shared.view.StudioView;
import com.project.presentation.shared.view.UserView;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The Interface IHibernateService.
 */
public interface IHibernateService {

	void saveFotostudio(FotostudioDTO fotostudio);

	FotostudioDTO findFotostudio(String name);

	UserView addEinstellung(EinstellungDTO einstellungDTO);

	NotificationBean saveNewUser(UserDTO us);

	LoginDTO userOrStudio(LoginDTO userDTO);

	UserView loadUserView(LoginDTO login);

	List<ImageDTO> loadAllImages(long einstellungID);

	ImageView saveImage(ImageDTO image, long idEinstellung);

	StudioView loadStudioView(LoginDTO loginDTO);

	NotificationBean updateQrCodeMD5(UserDTO user);

	List<AvailableUserDTO> loadAllUserFromStudio(FotostudioDTO fotostudioDTO, DomainBean domainBean);

	NotificationBean sendEmail(SMTPConfigBean smtpBean, UserDTO user);

	UserView loadViewByUrl(String username);

	UserDTO findUserAndAddress(long l);

	FotostudioDTO loadStudio(long l);

	FotostudioDTO loadStudio(String name);

	UserDTO loginUser(String email, String password);

	UserDTO findUserForLogin(String emal, String password);

	NotificationBean deleteImage(long id);

	boolean checkUserAlreadyExists(UserDTO user);

	NotificationBean updateQrCode(UserDTO user);

	ImageDTO reloadImage(Long id);

	NotificationBean deleteUser(UserDTO user);

	void createQRCodeIfNotExist(UserDTO user, String userURL, UploadBean uploadBean);

	void createMD5LoginKeyIfNotExist(UserDTO user, String userUrl);

	void createQRCodeMD5IfNotExist(UserDTO user, String md5Url, UploadBean uploadBean);

	NotificationBean updateEncyrptionState(UserDTO user);

	boolean deleteAttachment(Long id);
}

