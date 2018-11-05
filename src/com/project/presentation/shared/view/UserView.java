package com.project.presentation.shared.view;

import com.project.presentation.shared.dto.*;
import java.io.Serializable;
import java.util.List;
/**
 * Created by John on 28.06.2015.
 */
public class UserView extends AbstractView implements Serializable {
    //FIXME JKE available User nach StudioView
    private List<AvailableUserDTO> availableUsers;
    private AvailableUserDTO currentAvailableUser;
    private EinstellungDTO einstellung;
    private List<AnlageDTO> anlagen;
    private List<ImageDTO> images;
    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public EinstellungDTO getEinstellung() {
        return einstellung;
    }

    public void setEinstellung(EinstellungDTO einstellung) {
        this.einstellung = einstellung;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setAvailableUsers(List<AvailableUserDTO> availableUsers) {
        this.availableUsers = availableUsers;
    }

    public List<AvailableUserDTO> getAvailableUsers() {
        return availableUsers;
    }

    public void setCurrentAvailableUser(AvailableUserDTO currentAvailableUser) {
        this.currentAvailableUser = currentAvailableUser;
    }

    public AvailableUserDTO getCurrentAvailableUser() {
        return currentAvailableUser;
    }

    public void setAnlagen(List<AnlageDTO> PDFDocuments) {
        this.anlagen = PDFDocuments;
    }

    public List<AnlageDTO> getAnlagen() {
        return anlagen;
    }
}
