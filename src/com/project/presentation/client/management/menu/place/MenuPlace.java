package com.project.presentation.client.management.menu.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by john on 28.06.2015.
 */
public class MenuPlace extends Place {
    private String name;

    public MenuPlace(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Tokenizer implements PlaceTokenizer<MenuPlace>{

        @Override
        public MenuPlace getPlace(String name) {
            return new MenuPlace(name);
        }

        @Override
        public String getToken(MenuPlace place) {
            return place.getName();
        }
    }
}
