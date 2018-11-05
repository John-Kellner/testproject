/*
 * Access to the Topics in the database
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dao;


import com.project.presentation.server.tx.dom.UserDOM;
import com.project.presentation.server.upload.bean.DomainBean;
import com.project.presentation.shared.dto.AvailableUserDTO;
import com.project.presentation.shared.dto.FotostudioDTO;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.dto.UserDTO;


import java.util.List;

/**
 * The Interface IFotostudioDAO offers methods to saveNewUser, delete and login topics.
 */
public interface IUserDAO {


	NotificationBean saveNewUser(UserDTO user);

	UserDTO findUserLogin(String email, String password);

	UserDOM findUserByID(long id);

	UserDTO findUserAndAdresse(long userID);

	UserDTO findUserByUrl(String username);

	List<AvailableUserDTO> findAllUserByStudio(FotostudioDTO fotostudioDTO, DomainBean domainBean);

	boolean findUser(UserDTO user);

	NotificationBean updateQrCode(UserDTO user);

	NotificationBean delete(UserDTO user);

	NotificationBean updateMD5HashCode(UserDTO userDTO);

	NotificationBean updateQRCodeMD5(UserDTO user);

	NotificationBean updateEncryptionState(UserDTO user);
}
