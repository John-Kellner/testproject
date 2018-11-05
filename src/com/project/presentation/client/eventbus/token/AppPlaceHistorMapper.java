package com.project.presentation.client.eventbus.token;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.project.presentation.client.management.login.place.LoginPlace;
import com.project.presentation.client.management.menu.place.MenuPlace;
import com.project.presentation.client.management.page.place.PagePlace;
import com.project.presentation.client.management.start.place.StartPlace;
import com.project.presentation.client.management.studio.place.ManagementPlace;
import com.project.presentation.client.management.user.place.UserPlace;

/**
 * Created by john on 27.06.2015.
 */
@WithTokenizers({LoginPlace.Tokenizer.class, ManagementPlace.Tokenizer.class, UserPlace.Tokenizer.class, MenuPlace.Tokenizer.class, PagePlace.Tokenizer.class, StartPlace.Tokenizer.class})
public interface AppPlaceHistorMapper extends PlaceHistoryMapper {

}
