package com.project.presentation.client.management.login.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by john on 27.06.2015.
 */
public class LoginPlace extends Place {
    private String name;

    public LoginPlace(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace>{

        @Override
        public LoginPlace getPlace(String token) {
            return new LoginPlace(token);
        }

        @Override
        public String getToken(LoginPlace loginPlace) {
            return loginPlace.getName();
        }
    }
}
