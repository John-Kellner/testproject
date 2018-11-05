package com.project.presentation.client.gui.layouts.defaulttheme.menubar;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.project.presentation.client.gui.layouts.defaulttheme.css.LayoutSytle;
import com.project.presentation.client.gui.layouts.defaulttheme.image.ImageBar;
import com.project.presentation.client.gui.layouts.defaulttheme.body.IHTMLHandler;
import com.project.presentation.shared.dto.TabItemDTO;
import com.project.widget.client.tabbar.TabBar;
import com.project.widget.client.tabbar.tabitem.TabItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 10.06.2015.
 */
public class MenuBar extends Composite {
    private final LayoutSytle.Resource style = LayoutSytle.getStyle();
    private final LayoutSytle.ResourceBundle bundle = LayoutSytle.getBundle();
    private Map<String,TabItemDTO> map = new HashMap<>();
    private TabItem selectedItem;
    private FlowPanel ground = new FlowPanel();
    private IHTMLHandler handler;
    private ImageBar imageHandler;

    public MenuBar(){
        ground.setStyleName(style.menuBarGround());
        initWidget(ground);
    }

    public void loadView(final List<TabItemDTO> tabItem){
        final TabBar tabBar = new TabBar();
        tabBar.setStyleName(style.tabBar());

        final List<TabItem> container = new ArrayList<>();

        for (final TabItemDTO tabItemDTO : tabItem) {
            TabItem item = new TabItem(tabItemDTO.getName());
            tabBar.addItem(item);
            container.add(item);
            map.put(item.getName(), tabItemDTO);

            tabBar.addSelectHandler(new TabBar.ISelectHandler() {
                @Override
                public void onSelect(TabItem tabItem) {
                    setSelectedTabItem(tabItem);
                    final TabItemDTO item = map.get(tabItem.getName());
                    handler.setMessage(item.getContent());

                    setImage(item);

                    if (item.getTitle() != null && item.getTitle().length() > 0) {
                        imageHandler.setTitle(item.getTitle());
                    } else {
                        imageHandler.setTitle("");
                    }
                }
            });
        }

        final TabItemDTO firstItem = tabItem.get(0);

        // Erstauswahl
        if(firstItem != null){
            if (container.get(0) != null){
                tabBar.setSelectedItem(container.get(0));
            }

            setImage(firstItem);
            if(firstItem.getContent() != null){
                handler.setMessage(firstItem.getContent());
            }

            if (firstItem.getTitle() != null){
                imageHandler.setTitle(firstItem.getTitle());
            }else {
                imageHandler.setTitle("");
            }
        }

        ground.add(tabBar);

    }

    public void addHandler(IHTMLHandler handler) {
        this.handler = handler;
    }

    public void addImageHandler(ImageBar imageBar) {
        this.imageHandler = imageBar;
    }

    private void setSelectedTabItem(TabItem item){
        this.selectedItem = item;
    }

    public TabItem getSelectedItem(){
        return this.selectedItem;
    }

    private void setImage(TabItemDTO item) {
        if (item != null && item.getImage() != null && item.getImage().getImage() != null) {
            imageHandler.setImage(item.getImage().getImage());
        } else {
            imageHandler.setImage(new Image(bundle.dummyImageAsPlaceholder()));
        }
    }
}
