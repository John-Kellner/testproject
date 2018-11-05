package com.project.presentation.server.tx.dao;

import com.project.presentation.shared.dto.LoginDTO;
import com.project.presentation.shared.view.StudioView;

/**
 * Created by john on 24.10.2015.
 */
public interface IStudioViewDAO {

    StudioView loadStudioView(LoginDTO login);
}
