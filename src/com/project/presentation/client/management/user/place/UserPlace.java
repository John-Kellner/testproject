package com.project.presentation.client.management.user.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by john on 28.06.2015.
 */
public class UserPlace extends Place {
    private String name;

    public UserPlace(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Tokenizer implements PlaceTokenizer<UserPlace>{

        @Override
        public UserPlace getPlace(String name) {
            return new UserPlace(name);
        }

        @Override
        public String getToken(UserPlace userPlace) {
            return userPlace.getName();
        }
    }
}
