package com.project.presentation.client.management.start.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by john on 12.09.2015.
 */
public class StartPlace extends Place{
    private String name;

    public StartPlace(String start) {
        this.name = start;
    }

    public String getName() {
        return name;
    }

    public static class Tokenizer implements PlaceTokenizer<StartPlace>{

        @Override
        public StartPlace getPlace(String name) {
            return new StartPlace(name);
        }

        @Override
        public String getToken(StartPlace place) {
            return place.getName();
        }
    }

}
