/*
 * Access to the Topics in the database
 * 
 * @author Fabian Dietenberger
 * @date 09.10.2014
 */
package com.project.presentation.server.tx.dao;


import com.project.presentation.server.tx.dom.EinstellungDOM;
import com.project.presentation.server.tx.dom.UserDOM;
import com.project.presentation.shared.dto.EinstellungDTO;
import com.project.presentation.shared.dto.NotificationBean;

/**
 * The Interface IFotostudioDAO offers methods to saveNewUser, delete and login topics.
 */
public interface IEinstellungDAO {

	/**
	 * Save a topic.
	 *
	 * @param topic the topic. topicId is not needed
	 */
	public NotificationBean save(EinstellungDTO topic);

	EinstellungDOM find(long idEinstellung);

	EinstellungDTO getNewEinstellung(UserDOM userDTO);
}
