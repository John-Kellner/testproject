package com.project.presentation.server.tx.dao;

import com.project.presentation.shared.dto.LoginDTO;
import com.project.presentation.shared.view.UserView;

/**
 * Created by john on 24.10.2015.
 */
public interface IUserViewDAO {
    UserView loadUserView(LoginDTO loginDTO);
}
