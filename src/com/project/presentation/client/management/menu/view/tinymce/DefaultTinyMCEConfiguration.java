package com.project.presentation.client.management.menu.view.tinymce;

/**
 * Created by john on 16.08.2015.
 */
public class DefaultTinyMCEConfiguration extends AbstractTinyMCEConfiguration{
    private final TinyMCEInitListener listener;

    DefaultTinyMCEConfiguration(TinyMCEInitListener listener){
        this.listener = listener;
    }

    @Override
    protected void onTinyMCEInitialized() {
        listener.onLoadSuccess();
    }
}
