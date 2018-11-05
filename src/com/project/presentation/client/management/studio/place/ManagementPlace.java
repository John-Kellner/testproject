package com.project.presentation.client.management.studio.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by john on 27.06.2015.
 */
public class ManagementPlace extends Place {

    private String name;

    public ManagementPlace(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Tokenizer implements PlaceTokenizer<ManagementPlace> {

        @Override
        public ManagementPlace getPlace(String token) {
            return new ManagementPlace(token);
        }

        @Override
        public String getToken(ManagementPlace managementPlace) {
            return managementPlace.getName();
        }
    }
}
