package com.project.presentation.server.userlink;

import com.project.presentation.shared.dto.UserDTO;

/**
 * Created by john on 20.04.2016.
 */
public class UserLink {

    public static String createUserUrl(UserDTO user, String domainname) {
        final String[] split = user.getName().split(" ");
        String userURL = null;
        if (split.length == 2) {
            userURL = domainname + split[0].toLowerCase() + "." + split[1].toLowerCase();
        }
        return userURL != null ? userURL : "";
    }

    public static String createMD5Url(UserDTO user, String domainname) {
        return domainname + user.getMd5();
    }
}
