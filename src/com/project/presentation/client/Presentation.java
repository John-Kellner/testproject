package com.project.presentation.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.project.presentation.client.gui.layouts.defaulttheme.css.LayoutSytle;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.ManagedPlaces;
import com.project.presentation.client.eventbus.impl.AppActivityMapper;
import com.project.presentation.client.eventbus.token.AppPlaceHistorMapper;
import com.project.presentation.client.eventbus.ClientFactory;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Presentation implements EntryPoint {
    private final LayoutSytle.ResourceBundle bundle = LayoutSytle.getBundle();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        /** Font Roboto */
        bundle.robotoRegular().ensureInjected();
        bundle.robotoCondensedBold().ensureInjected();
        bundle.robotoBoldItalic().ensureInjected();
        bundle.robotoItalic().ensureInjected();
        bundle.robotoLight().ensureInjected();
        bundle.robotoLightItalic().ensureInjected();

        /*final Cover cover = new Cover();
        cover.show(new Cover.HasFinished() {
            @Override
            public void onFinish() {
                RootPanel.get().add(new defaulttheme());
            }
        });*/

        //RootPanel.get().add(new DefaultTheme());
        //RootPanel.get().add(new ManagementView());

        final ScrollPanel root = new ScrollPanel();
        //final LoginPlace defaultPlace = new LoginPlace("Login");

        final ClientFactory factory = GWT.create(ClientFactory.class);
        EventBus eventBus = factory.getEventBus();
        PlaceController placeController = factory.getPlaceController();

        /** Activity Manager */
        final ActivityMapper mapper = new AppActivityMapper(factory);
        ActivityManager manager = new ActivityManager(mapper,eventBus);
        manager.setDisplay(root);

        /** Place History Handler */
        final AppPlaceHistorMapper historyMapper = GWT.create(AppPlaceHistorMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, ManagedPlaces.get(EManagedPlace.PAGE));
        RootPanel.get().add(root);
        historyHandler.handleCurrentHistory();
    }
}