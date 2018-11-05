package com.project.presentation.client.management.page.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by john on 28.07.2015.
 */
public class PagePlace extends Place {
    private String name;

    public PagePlace(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Tokenizer implements PlaceTokenizer<PagePlace>{

        @Override
        public PagePlace getPlace(String name) {
            return new PagePlace(name);
        }

        @Override
        public String getToken(PagePlace place) {
            return place.getName();
        }
    }
}
