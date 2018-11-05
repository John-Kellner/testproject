package com.project.presentation.client.management.page.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.project.presentation.client.gui.layouts.Theme;
import com.project.presentation.client.gui.layouts.defaulttheme.DefaultTheme;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.page.IPageView;
import com.project.presentation.client.management.page.activity.IPageActivity;
import com.project.presentation.client.management.page.controller.PageViewController;
import com.project.presentation.client.management.page.view.css.PageViewStyle;
import com.project.presentation.shared.dto.TabItemDTO;
import com.project.presentation.shared.view.UserView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 28.07.2015.
 */
public class PageView extends PageViewController implements IPageView{

    protected interface PageViewUiBinder extends UiBinder<ScrollPanel, PageView> {}
    private static PageViewUiBinder ourUiBinder = GWT.create(PageViewUiBinder.class);
    private boolean isStartPage = false;
    private IPageActivity actvity;
    private Theme theme;

    @UiField
    protected FlowPanel panel;

    public PageView(String name) {

        final Map<String, List<String>> map = checkURLIsEmpty();

        /** Switch auf User */
        String username = "";
        if (!map.isEmpty()){
            for (String s : map.keySet()) {
                username = s;
            }
            loadView(username);
            initWidget(ourUiBinder.createAndBindUi(this));
        }
    }

    /** URL Check on Blank*/
    private Map<String, List<String>> checkURLIsEmpty(){
        final Map<String, List<String>> parameterMap = Window.Location.getParameterMap();

        /** Switch auf willkommensseite*/
        if (parameterMap.size() == 0){
            isStartPage = true;
        }
        return parameterMap;
    }

    @Override
    public void setActivity(IPageActivity actvity) {
        this.actvity = actvity;
        if (isStartPage){
            this.actvity.show(EManagedPlace.LOGIN, null);
        }
    }

    /**
     * Callback an das Template
     * Die Ansichten, die nicht angezeigt werden sollen werden verworfen
     * @param view
     */
    @Override
    public void onLoadView(UserView view) {
        if (view != null){
            panel.clear();
            panel.setStyleName(PageViewStyle.getStyle().root());
            this.theme = new DefaultTheme();
            panel.add(theme);

            final List<TabItemDTO> tabItem = view.getEinstellung().getTabItem();
            final List<TabItemDTO> tmpTabItem = new ArrayList<>();
            for (TabItemDTO tabItemDTO : tabItem) {
                if (tabItemDTO.isVisible()){
                    tmpTabItem.add(tabItemDTO);
                }
            }
            view.getEinstellung().setTabItem(tmpTabItem);

            /** Callback */
            this.theme.onLoadView(view);
        }else {
            actvity.show(EManagedPlace.LOGIN, null);
        }
    }
}
