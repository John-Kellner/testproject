package com.project.presentation.client.gui.components.chip.handler;


import com.project.presentation.client.gui.components.chip.Chip;

/**
 * Created by john on 28.12.2015.
 */
public interface ChipChangeHandler<T> {
    void onEdit(Chip<T> chip);
    void onSave(Chip<T> chip);
    void onDelete(Chip<T> chip);
    void onMoveRight(Chip<T> chip);
    void onMoveLeft(Chip<T> chip);
    void onAddChip(int position);
}
