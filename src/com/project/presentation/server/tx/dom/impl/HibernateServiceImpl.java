package com.project.presentation.server.tx.dom.impl;

import com.project.presentation.server.base64.Base64Converter;
import com.project.presentation.server.email.SMTPConfigBean;
import com.project.presentation.server.email.SMTPMailSender;
import com.project.presentation.server.md5.MD5Generator;
import com.project.presentation.server.qr.QRCode;
import com.project.presentation.server.tx.dao.*;
import com.project.presentation.server.tx.dom.IHibernateService;
import com.project.presentation.server.upload.bean.DomainBean;
import com.project.presentation.server.upload.bean.UploadBean;
import com.project.presentation.server.upload.bean.WkHtmlBean;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.enumerations.ELoginState;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.enumerations.EUser;
import com.project.presentation.shared.view.ImageView;
import com.project.presentation.shared.view.StudioView;
import com.project.presentation.shared.view.UserView;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

@Component("HibernateService")
public class HibernateServiceImpl implements IHibernateService {
	private static final Logger logger = Logger.getLogger(HibernateServiceImpl.class);

	@Resource //Fotostudio
	private IFotoStDAO fotostudioDAO;

	@Resource //user
	private IUserDAO userDAO;

	@Resource //Einstellung
	private IEinstellungDAO einstellungDAO;

	@Resource //Images
	private IImagesDAO imageDAO;

	@Resource
	private IUserViewDAO userViewDAO;

	@Resource
	private IStudioViewDAO studioViewDAO;

	@Resource
	private AnlagenDAO attachmentDAO;

	@Resource
	private WkHtmlBean wkHtmlBean;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveFotostudio(FotostudioDTO fotostudio) {
		fotostudioDAO.save(fotostudio);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public FotostudioDTO findFotostudio(String name) {
		return fotostudioDAO.find(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public NotificationBean saveNewUser(UserDTO us) {
		return userDAO.saveNewUser(us);
	}

	@Override //Einstellung
	@Transactional(propagation = Propagation.REQUIRED)
	public UserView addEinstellung(EinstellungDTO einstellungDTO) {
		final NotificationBean notification = einstellungDAO.save(einstellungDTO);

		final LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUserID(einstellungDTO.getUser().getUserId());
		loginDTO.setUserTyp(EUser.USER);

		final UserView view = userViewDAO.loadUserView(loginDTO);
		view.setNotificationBean(notification);
		return view;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public LoginDTO userOrStudio(LoginDTO userDTO){
		final LoginDTO login = new LoginDTO();

		final UserDTO user = userDAO.findUserLogin(userDTO.getEmail(), userDTO.getPassword());
		if (user != null){
			login.setUserID(user.getUserId());
			login.setEmail(user.getEmail());
			login.setUserTyp(EUser.USER);
			login.setState(ELoginState.AUTHORIZED);
		}else {
			final FotostudioDTO studio = fotostudioDAO.findStudioLogin(userDTO.getEmail(), userDTO.getPassword());
			if (studio != null){
				login.setUserID(studio.getFotostudioId());
				login.setEmail(studio.getEmail());
				login.setUserTyp(EUser.STUDIO);
				login.setState(ELoginState.AUTHORIZED);
			}
		}
		return login;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserView loadUserView(LoginDTO login) {
		return userViewDAO.loadUserView(login);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public StudioView loadStudioView(LoginDTO login) {
		return studioViewDAO.loadStudioView(login);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ImageDTO> loadAllImages(long einstellungID) {
		return imageDAO.loadAllImages(einstellungID);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ImageView saveImage(ImageDTO image, long idEinstellung) {
		return imageDAO.save(image, idEinstellung);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public FotostudioDTO loadStudio(long l) {
		return fotostudioDAO.convert(fotostudioDAO.findStudioByUserID(l));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public FotostudioDTO loadStudio(String name) {
		return fotostudioDAO.find(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserDTO loginUser(String email, String password) {
		return userDAO.findUserLogin(email, password);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserDTO findUserForLogin(String email, String password) {
		return userDAO.findUserLogin(email, password);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public NotificationBean deleteImage(long id) {
		return imageDAO.deleteImage(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean checkUserAlreadyExists(UserDTO user) {
		return userDAO.findUser(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public NotificationBean updateQrCode(UserDTO user) {
		return userDAO.updateQrCode(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public NotificationBean updateQrCodeMD5(UserDTO user) {
		return userDAO.updateQRCodeMD5(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<AvailableUserDTO> loadAllUserFromStudio(FotostudioDTO fotostudioDTO, DomainBean domainBean) {
		return userDAO.findAllUserByStudio(fotostudioDTO, domainBean);
	}

	@Override
	public NotificationBean sendEmail(final SMTPConfigBean smtpBean, final UserDTO user) {
		final SMTPMailSender smtp = new SMTPMailSender();
		return smtp.sendWelcomeMail(smtpBean, user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserView loadViewByUrl(String username) {
		final UserDTO user = userDAO.findUserByUrl(username);
		if (user != null){
			final LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUserID(user.getUserId());
			return userViewDAO.loadUserView(loginDTO);
		}else {
			final UserView view = new UserView();
			final NotificationBean notificationBean = new NotificationBean();
			notificationBean.setNotify(ENotify.FAILURE);
			notificationBean.setMessage("Benutzer nicht vorhanden !");
			view.setNotificationBean(notificationBean);
			return view;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public UserDTO findUserAndAddress(long l) {
		return userDAO.findUserAndAdresse(l);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ImageDTO reloadImage(Long imageID) {
		return imageDAO.findImage(imageID);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public NotificationBean deleteUser(UserDTO user){
		return userDAO.delete(user);
	}

	/**
	 * Erzeugt und speichert den QR Code, falls nicht vorhanden
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createQRCodeIfNotExist(final UserDTO user, final String userURL, final UploadBean uploadBean) {
		if (user.getQrcode() == null && userURL != null || user.getQrcode().length() == 0){
			final String filename = uploadBean.getQrCodeDir() + "UserQRCodeUUID" + user.getUserId() + ".png";
			if (QRCode.createCode(userURL, filename)){
				user.setQrcode(Base64Converter.convert(filename));
				final NotificationBean updateUser = updateQrCode(user);
				if (updateUser.getNotify() == ENotify.SUCCESS){
					logger.info("QRCode wurde generiert und in der DB gespeichert!");
				}else if (updateUser.getNotify() == ENotify.FAILURE){
					logger.error("QRCode konnte nicht gespeichert werden!");
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createQRCodeMD5IfNotExist(UserDTO user, String md5Url, UploadBean uploadBean) {
		if (user.getQrcodeMD5() == null && md5Url != null || user.getQrcodeMD5().length() == 0){
			final String filename = uploadBean.getQrCodeDir() + "UserQRCodeMD5UUID" + user.getUserId() + ".png";
			if (QRCode.createCode(md5Url, filename)){
				user.setQrcodeMD5(Base64Converter.convert(filename));
				final NotificationBean updateUser = updateQrCodeMD5(user);
				if (updateUser.getNotify() == ENotify.SUCCESS){
					logger.info("QRCode wurde generiert und in der DB gespeichert!");
				}else if (updateUser.getNotify() == ENotify.FAILURE){
					logger.error("QRCode konnte nicht gespeichert werden!");
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createMD5LoginKeyIfNotExist(UserDTO user, String userUrl) {
		if (user != null && user.getMd5() == null && userUrl != null) {
			user.setMd5(MD5Generator.createMD5Key(userUrl));
			userDAO.updateMD5HashCode(user);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public NotificationBean updateEncyrptionState(UserDTO user) {
		return userDAO.updateEncryptionState(user);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean deleteAttachment(Long id) {
		return attachmentDAO.deleteAttachment(id);
	}
}
