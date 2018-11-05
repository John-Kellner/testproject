package com.project.presentation.shared.view;

import com.project.presentation.shared.dto.AvailableUserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by john on 24.10.2015.
 */
public class StudioView extends AbstractView implements Serializable {

    private List<AvailableUserDTO> availableUser;

    public List<AvailableUserDTO> getAvailableUser() {
        return availableUser;
    }

    public void setAvailableUser(List<AvailableUserDTO> availableUser) {
        this.availableUser = availableUser;
    }
}
