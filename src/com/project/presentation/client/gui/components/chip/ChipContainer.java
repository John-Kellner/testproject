package com.project.presentation.client.gui.components.chip;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.project.presentation.client.gui.components.chip.addons.plus.Plus;
import com.project.presentation.client.gui.components.chip.css.ChipStyle;
import com.project.presentation.client.gui.components.chip.handler.ChipChangeHandler;
import com.project.presentation.client.gui.components.chip.handler.ChipSelectHandler;
import com.project.presentation.shared.dto.TabItemDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 28.12.2015.
 */
public class ChipContainer<T extends Chip<TabItemDTO>> extends Composite {
    private static ChipStyle.Resource style = ChipStyle.getStyle();
    private final List<Chip<TabItemDTO>> container = new ArrayList<>();
    private ChipChangeHandler externalChangeHandler;
    private ChipSelectHandler selectionHandler;
    private FlowPanel root = new FlowPanel();
    private Chip<TabItemDTO> lastSelectedChip;
    private Plus plus = new Plus();

    public ChipContainer(){
        initWidget(root);
        root.setStyleName(style.ground());
        plus.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                chipChangeHandler.onAddChip(container.get(container.size() -1).getBean().getPosition() + 1);
            }
        });
    }

    /** Clean up */
    public void clear(){
        container.clear();
        root.clear();
    }

    /** Remove a specifiv Chip */
    public void remove(Chip<TabItemDTO> chip){
        container.remove(chip);
        root.remove(chip);
        plus.setVisible(true);
    }

    /** Add a single Chip */
    public void add(final Chip<TabItemDTO> chip) {
        container.add(chip);
        chip.addSelectHandler(chipSelectHandler);
        chip.addEditHandler(chipChangeHandler);
        chip.addEscapeHanlder(new Chip.EscapeHandler() {
            @Override
            public void enableAddButton() {
                //chipSelectHandler.onSelect(chip);
            }
        });
        chip.setSelected(false);
        root.add(chip);
        root.add(plus);
    }

    /** Selection listener */
    public void addSelectionHandler(ChipSelectHandler selectionHandler) {
        this.selectionHandler = selectionHandler;
    }

    /** Change Handler */
    public void addChangeHandler(ChipChangeHandler changeHandler){
        this.externalChangeHandler = changeHandler;
    }

    /** get selected - marked Item */
    public Chip<TabItemDTO> getSelectedItem() {
        for (Chip<TabItemDTO> chip : container) {
            if (chip.isSelected()){
                return chip;
            }
        }
        return null;
    }

    /** ChangeHandler */
    private final ChipChangeHandler<TabItemDTO> chipChangeHandler = new ChipChangeHandler<TabItemDTO>() {

        @Override
        public void onEdit(Chip<TabItemDTO> chip) {
            plus.setVisible(false);
            if (externalChangeHandler != null){
                externalChangeHandler.onEdit(chip);
            }
        }

        @Override
        public void onSave(Chip<TabItemDTO> chip) {
            if (externalChangeHandler != null){
                externalChangeHandler.onSave(chip);
            }
            chip.setActive();
            plus.setVisible(true);
        }

        @Override
        public void onDelete(Chip<TabItemDTO> chip) {
            if (externalChangeHandler != null){
                externalChangeHandler.onDelete(chip);
                remove(chip);
            }
        }

        @Override
        public void onMoveRight(Chip<TabItemDTO> chip) {
            // Neu Sortieren falls items geloescht wurden
            for (int i= 0;i < container.size(); i++){
                container.get(i).getBean().setPosition(i);
            };
            final int indexCurrentChip = container.indexOf(chip);
            final Chip<TabItemDTO> nextChip = container.get(indexCurrentChip + 1);
            final int newCurrentPosition = nextChip.getBean().getPosition();

            chip.getBean().setPosition(newCurrentPosition);
            nextChip.getBean().setPosition(indexCurrentChip);

            if (externalChangeHandler != null){
                externalChangeHandler.onMoveRight(chip);
            }
        }

        @Override
        public void onMoveLeft(Chip<TabItemDTO> chip) {
            // Neu Sortieren falls items geloescht wurden
            for (int i= 0;i < container.size(); i++){
                container.get(i).getBean().setPosition(i);
            };

            final int indexCurrentChip = container.indexOf(chip);
            final Chip<TabItemDTO> prevChip = container.get(indexCurrentChip - 1);
            final int newCurrentPosition = prevChip.getBean().getPosition();

            chip.getBean().setPosition(newCurrentPosition);
            prevChip.getBean().setPosition(indexCurrentChip);

            if (externalChangeHandler != null){
                externalChangeHandler.onMoveLeft(chip);
            }
        }

        @Override
        public void onAddChip(int pos) {
            if (externalChangeHandler != null){
                externalChangeHandler.onAddChip(pos);
            }
        }
    };

    /**
     * Select Handler
     * Chips werden resetet und den letzten aktuellen markiert
     * Handler wird informiert
     */
    private final ChipSelectHandler<Chip<TabItemDTO>> chipSelectHandler = new ChipSelectHandler<Chip<TabItemDTO>>() {

        @Override
        public void onSelect(Chip<TabItemDTO> chip) {
            for (Chip singleChip : container) {
                singleChip.setSelected(false);
            }

            chip.setSelected(true);

            //Erste Position, abschalten des rechten Buttons und einzug verringern
            if (chip.getBean().getPosition() == container.get(0).getBean().getPosition()){
                chip.disableLeftButton();
                chip.getElement().getStyle().setMarginLeft(0.0, Style.Unit.PX);
            }

            //Letzte Position
            plus.getElement().getStyle().setMarginLeft(16.0, Style.Unit.PX);
            if (chip.getBean().getPosition() == container.get(container.size() -1).getBean().getPosition()){
                chip.disableRightButton();
                plus.getElement().getStyle().setMarginLeft(-20.0, Style.Unit.PX);
            }

            if (selectionHandler != null){
                selectionHandler.onSelect(chip);
                lastSelectedChip = chip;
            }
        }
    };

    /**
     * Auswahl eines Chips aus anhand seines Namens
     * @param text
     */
    public void selectFirstElement(String text) {
        Chip gesuchterChip = null;
        if (!text.isEmpty()) {
            for (Chip chip : container) {
                if (chip.getText().equals(text)) {
                    gesuchterChip = chip;
                    break;
                }
            }
        }

        if (gesuchterChip != null) {
            gesuchterChip.setSelected(true);
            chipSelectHandler.onSelect(gesuchterChip);
        }
    }
}
