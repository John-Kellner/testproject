package com.project.presentation.client.management.menu.view.tabswitcher;

import com.project.presentation.shared.dto.TabItemDTO;

import java.util.*;


/**
 * Tabswitcher ist ein Managed Bean
 *
 * Verwaltung der Umschaltfunktion der Combobox
 * Created by john on 04.07.2015.
 */
public class TabItemSwitcher {
    private List<ITabItemHandler> handler = new ArrayList<ITabItemHandler>();
    private Map<String, TabItemDTO> map = new HashMap<>();
    private enum EState{
        INIT, UPDATE
    }

    public void init(List<TabItemDTO> tabItemDTOs){
        map.clear();
        for (TabItemDTO tabItemDTO : tabItemDTOs) {
            map.put(tabItemDTO.getName(), tabItemDTO);
        }
        callItemUpdate(EState.INIT);
    }

    /**
     * Refresh View
     */
    public void doUpdate(){
        callItemUpdate(EState.UPDATE);
    }

    public void addItem(TabItemDTO item){
        map.put(item.getName(), item);
        callItemUpdate(EState.UPDATE);
    }

    public void removeItem(String key){
        map.remove(key);
    }

    public TabItemDTO getKey(String imageName){
        return map.get(imageName);
    }

    /** Rueckgabe der Liste */
    public List<TabItemDTO> getTabAllTabItems(){
        final List<TabItemDTO> tabItemDTOs = new ArrayList<TabItemDTO>();

        final SortedSet<Map.Entry<String, TabItemDTO>> entries = entriesSortedByValues(map);

        for (Map.Entry<String, TabItemDTO> map : entries){
            tabItemDTOs.add(map.getValue());
        }
        return tabItemDTOs;
    }

    public void addListener(ITabItemHandler handler){
        this.handler.add(handler);
    }

    private void callItemUpdate(EState state){
        if (handler != null){
            for (ITabItemHandler iTabItemHandler : handler) {
                switch (state){
                    case UPDATE:
                        iTabItemHandler.onItemUpdate();
                        break;
                    case INIT:
                        //FIXME JKE fuehlt sich nicht gut an!
                        iTabItemHandler.onItemUpdate();
                        iTabItemHandler.onInitialDataReceived();
                        break;
                }
            }
        }
    }

    /** Comparator */
    static SortedSet<Map.Entry<String,TabItemDTO>> entriesSortedByValues(Map<String,TabItemDTO> map) {
        SortedSet<Map.Entry<String,TabItemDTO>> sortedEntries = new TreeSet<Map.Entry<String,TabItemDTO>>(
                new Comparator<Map.Entry<String,TabItemDTO>>() {
                    @Override public int compare(Map.Entry<String,TabItemDTO> e1, Map.Entry<String,TabItemDTO> e2) {
                        if (e1.getValue().getPosition() == e2.getValue().getPosition()){
                            return 0;
                        }

                        if (e1.getValue().getPosition() > e2.getValue().getPosition()){
                            return 1;
                        }

                        return -1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

}
