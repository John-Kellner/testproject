/*
 * Access to the Topics in the database
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dao;

import com.project.presentation.server.tx.dom.FotostudioDOM;
import com.project.presentation.shared.dto.FotostudioDTO;
import com.project.presentation.shared.dto.NotificationBean;
//import com.project.presentation.shared.dto.UserDTO;


/**
 * The Interface IFotostudioDAO offers methods to saveNewUser, delete and login topics.
 */
public interface IFotoStDAO {

	NotificationBean save(FotostudioDTO studio);

	FotostudioDTO find(String name);

	FotostudioDOM findStudioByUserID(long userID);

	FotostudioDTO findStudioLogin(String email, String password);

	FotostudioDTO convert(FotostudioDOM fotostudioDOM);

	FotostudioDOM findStudioByStudioID(long fotostudioId);

}
