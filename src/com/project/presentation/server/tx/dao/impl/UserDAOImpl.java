package com.project.presentation.server.tx.dao.impl;

import com.project.presentation.server.tx.dao.IEinstellungDAO;
import com.project.presentation.server.tx.dao.IFotoStDAO;
import com.project.presentation.server.tx.dao.IUserDAO;
import com.project.presentation.server.tx.dom.*;
import com.project.presentation.server.upload.bean.DomainBean;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.enumerations.ENotify;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements IUserDAO {

	@PersistenceContext
	private EntityManager em;
	private String hql;

	@Resource
	private IFotoStDAO iFotoStDAO;

	@Resource
	private IEinstellungDAO einstellungDAO;

	@Override
	public NotificationBean saveNewUser(UserDTO user) {
		final NotificationBean notificationBean = new NotificationBean();

		// Wenn getStudio Null ist , darf niemals abgespeichert werden
		if (user.getFotostudio() == null) {
			notificationBean.setNotify(ENotify.FAILURE);
			notificationBean.setMessage("Es wurde kein Fotostudio zum User gefunden!");
			return notificationBean;
		}

		/** Umbauen von DTO nach DOM */
		final UserDOM userDOM = new UserDOM();
		userDOM.fillDTO(user);

		final List<FotostudioDOM> studioDOM = new ArrayList<>();
		final long studioID = user.getFotostudio().getFotostudioId();
		studioDOM.add(iFotoStDAO.findStudioByStudioID(studioID));
		userDOM.setFotostudios(studioDOM);

		/** Umbau von DTO nach DOM*/
		final AdresseDOM adresseDOM = new AdresseDOM();
		adresseDOM.fillDTO(user.getAdresse());
		adresseDOM.setUser(userDOM);

		if (user.getUserId() == 0) {
			em.persist(userDOM);
			em.persist(adresseDOM);

			//add default Template
			einstellungDAO.save(einstellungDAO.getNewEinstellung(userDOM));
		} else {
			em.merge(userDOM);
			em.merge(adresseDOM);
		}

		em.flush();

		notificationBean.setNotify(ENotify.SUCCESS);
		notificationBean.setMessage("Erfolgreich gespeichert");
		return notificationBean;
	}

	@Override
	public UserDTO findUserLogin(String email, String password) {
		hql = "SELECT user FROM UserDOM user ";
		hql += "WHERE user.email=:email ";
		hql += "AND user.password=:password";

		final List<UserDOM> resultTopic = em.createQuery(hql, UserDOM.class).setParameter("email", email).setParameter("password", password).setMaxResults(1).getResultList();
		if (resultTopic.size() == 0) {
			return null;
		}

		if (resultTopic.size() == 1) {
			final UserDOM userDOM = resultTopic.get(0);

			final UserDTO userDTO = new UserDTO();
			userDTO.setUserId(userDOM.getUserId());
			userDTO.setPassword(userDOM.getPassword());
			userDTO.setName(userDOM.getName());
			userDTO.setEmail(userDOM.getEmail());
			userDTO.setQrcode(userDOM.getQrcode());
			userDTO.setQrcodeMD5(userDOM.getQrCodeMD5());
			userDTO.setMd5(userDOM.getMd5());
			userDTO.setIs128BitLogin(userDOM.is128bitLogin());

			final AdresseDTO adresseDTO = new AdresseDTO();
			if (userDOM.getAdresse() != null) {
				adresseDTO.setAdresseID(userDOM.getAdresse().getAdresseID());
				adresseDTO.setStrasse(userDOM.getAdresse().getStrasse());
				adresseDTO.setPLZ(userDOM.getAdresse().getPlz());
				adresseDTO.setLand(userDOM.getAdresse().getLand());
				adresseDTO.setOrt(userDOM.getAdresse().getOrt());
				userDTO.setAdresse(adresseDTO);
			}
			return userDTO;
		}
		return null;
	}

	@Override
	public UserDOM findUserByID(long id) {
		return em.find(UserDOM.class, id);
	}

	@Override
	public UserDTO findUserAndAdresse(long userID) {
		hql = "SELECT a FROM UserDOM a JOIN FETCH a.fotostudios WHERE a.userId=:userId";
		final UserDOM user = em.createQuery(hql, UserDOM.class).setParameter("userId", userID).setMaxResults(1).getSingleResult();

		UserDTO userDTO = null;

		if (user != null) {
			userDTO = new UserDTO();
			userDTO.setUserId(user.getUserId());
			userDTO.setName(user.getName());
			userDTO.setEmail(user.getEmail());
			userDTO.setQrcode(user.getQrcode());
			userDTO.setQrcodeMD5(user.getQrCodeMD5());
			userDTO.setMd5(user.getMd5());
			userDTO.setIs128BitLogin(user.is128bitLogin());
			if (user.getAdresse() != null) {
				final AdresseDOM adresse = user.getAdresse();
				final AdresseDTO adresseDTO = new AdresseDTO();
				adresseDTO.setAdresseID(adresse.getAdresseID());
				adresseDTO.setStrasse(adresse.getStrasse());
				adresseDTO.setPLZ(adresse.getPlz());
				adresseDTO.setOrt(adresse.getOrt());
				adresseDTO.setLand(adresse.getLand());
				userDTO.setAdresse(adresseDTO);
			}
		}

		return userDTO;
	}

	/**
	 * Find User by url represents a UserDTO
	 * by login with the given surename.lastname OR md5 hash algorithm in url path
	 * @param urlpath example : max.mustermann OR 92c74504e601837c1aac7240e2dd944b
	 * @return userDTO
     */
	@Override
	public UserDTO findUserByUrl(String urlpath) {
		final String url = urlpath.toLowerCase().replace(" ","").trim();
		hql = "SELECT u FROM UserDOM u WHERE Lower(REPLACE(u.name, ' ', '.')) = '" + url + "' AND u.is128BitLogin = false";
		List<UserDOM> users = em.createQuery(hql, UserDOM.class).getResultList();

		if (users.size() == 0){
			hql = "SELECT u FROM UserDOM u WHERE u.md5 = '" + url + "' AND u.is128BitLogin = true";
			users = em.createQuery(hql, UserDOM.class).getResultList();
		}

		UserDTO userDTO = null;
		if (users.size() == 1) {
			final UserDOM user = users.get(0);
			if (user != null) {
				if (user != null) {
					userDTO = new UserDTO();
					userDTO.setUserId(user.getUserId());
					userDTO.setName(user.getName());
					userDTO.setEmail(user.getEmail());
					userDTO.setQrcode(user.getQrcode());
					userDTO.setQrcodeMD5(user.getQrCodeMD5());
					userDTO.setMd5(user.getMd5());
					userDTO.setIs128BitLogin(user.is128bitLogin());
					if (user.getAdresse() != null) {
						final AdresseDOM adresse = user.getAdresse();
						final AdresseDTO adresseDTO = new AdresseDTO();
						adresseDTO.setAdresseID(adresse.getAdresseID());
						adresseDTO.setStrasse(adresse.getStrasse());
						adresseDTO.setPLZ(adresse.getPlz());
						adresseDTO.setOrt(adresse.getOrt());
						adresseDTO.setLand(adresse.getLand());
						userDTO.setAdresse(adresseDTO);
					}
				}
			}
		}

		return userDTO;
	}

	@Override
	public List<AvailableUserDTO> findAllUserByStudio(FotostudioDTO fotostudioDTO, DomainBean domainBean) {
		hql = "SELECT u FROM UserDOM u INNER JOIN FETCH u.fotostudios f WHERE f.fotostudioId =:id";
		final List<UserDOM> users = em.createQuery(hql, UserDOM.class).setParameter("id", fotostudioDTO.getFotostudioId()).getResultList();
//		von DOM nach DTO umbauen
		final List<AvailableUserDTO> availableUserDTOs = new ArrayList<>();
		for (UserDOM user : users) {
			final AvailableUserDTO availableUserDTO = new AvailableUserDTO();
			availableUserDTO.setName(user.getName());
			final String[] split = user.getName().split(" ");
			if (split.length == 2) {
				availableUserDTO.setUserUrl(domainBean.getDomainname() + split[0].toLowerCase() + "." + split[1].toLowerCase());

			}
			availableUserDTOs.add(availableUserDTO);

		}
		return availableUserDTOs;
	}

	/**
	 * Sucht nach einem vorhandenen User
	 * @param user
	 * @return gefunden
	 */
	@Override
	public boolean findUser(UserDTO user) {
		hql = "FROM UserDOM user ";
		hql += "WHERE user.email=:email ";

		/** User ueber die Email Adresse gefunden */
		final List<UserDOM> users = em.createQuery(hql, UserDOM.class).setParameter("email",user.getEmail()).getResultList();
		if(users.size() == 1){
			return true;
		}

		/** User ueber die URL Adresse gefunden */
		final UserDTO userByUrl = findUserByUrl(user.getName());
		if (userByUrl != null){
			return true;
		}

		/** User wurde nicht gefunden */
		return false;
	}

	/**
	 * Generierung des QRCode
	 * @param user
	 * @return
     */
	@Override
	public NotificationBean updateQrCode(UserDTO user) {
		final NotificationBean notificationBean = new NotificationBean();
		final UserDOM userDOM = em.find(UserDOM.class,user.getUserId());
		userDOM.setQrcode(user.getQrcode());
		em.merge(userDOM);
		em.flush();
		notificationBean.setNotify(ENotify.SUCCESS);
		notificationBean.setMessage("Update Success");
		return notificationBean;
	}

	@Override
	public NotificationBean updateQRCodeMD5(UserDTO user) {
		final NotificationBean notificationBean = new NotificationBean();
		final UserDOM userDOM = em.find(UserDOM.class,user.getUserId());
		userDOM.setQrCodeMD5(user.getQrcodeMD5());
		em.merge(userDOM);
		em.flush();
		notificationBean.setNotify(ENotify.SUCCESS);
		notificationBean.setMessage("Update Success");
		return notificationBean;
	}

	@Override
	public NotificationBean updateEncryptionState(UserDTO user) {
		final NotificationBean notification = new NotificationBean();

		try{
			final UserDOM userDOM = em.find(UserDOM.class, user.getUserId());
			userDOM.setIs128BitLogin(user.is128BitLogin());
			em.merge(userDOM);
			em.flush();
		}catch (Exception ex){
			notification.setNotify(ENotify.FAILURE);
			notification.setMessage(ex.getMessage());
		}

		notification.setNotify(ENotify.SUCCESS);
		notification.setMessage("Succcess");
		return notification;
	}

	/**
	 * Generierung des MD5 Codes
	 * @param user
	 * @return
     */
	@Override
	public NotificationBean updateMD5HashCode(UserDTO user) {
		final NotificationBean notificationBean = new NotificationBean();
		final UserDOM userDOM = em.find(UserDOM.class,user.getUserId());
		userDOM.setMd5(user.getMd5());
		em.merge(userDOM);
		em.flush();
		notificationBean.setNotify(ENotify.SUCCESS);
		notificationBean.setMessage("Update Success");
		return notificationBean;
	}

	/**
	 * User wird geloescht
	 * @param user
	 * @return
     */
	@Override
	public NotificationBean delete(UserDTO user){
		final NotificationBean notification = new NotificationBean();
		final UserDOM userDOM = em.find(UserDOM.class, user.getUserId());
		em.remove(userDOM.getEinstellung());
		em.remove(userDOM.getAdresse());
		em.remove(userDOM);
		notification.setNotify(ENotify.SUCCESS);
		notification.setMessage("User wurde erfolgreich geloescht !");
		return notification;
	}

}