package com.project.presentation.client.management.menu.controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.project.presentation.client.console.log.Console;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.AbstractView;
import com.project.presentation.shared.view.UserView;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;

import java.util.logging.Logger;

/**
 * Created by john on 03.07.2015.
 */
public abstract class MenuViewController extends Composite {
    private RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);

    protected Logger logger = Logger.getLogger("MenuViewController");
    public abstract void setView(UserView view);
    public abstract void returnToLoginView();

    protected abstract void onImageDeleteSuccess();

    /** Loescht ein Bild */
    protected void deleteImage(long id){
        service.deleteImage(id, new AsyncCallback<NotificationBean>() {
            @Override
            public void onFailure(Throwable throwable) {
                MaterialToast.alert("Das Bild konnte nicht gel"+ (char) 246 +"scht werden!");
            }

            @Override
            public void onSuccess(NotificationBean notificationBean) {
                if (notificationBean.getNotify() == ENotify.SUCCESS){
                    MaterialToast.alert(notificationBean.getMessage());
                    onImageDeleteSuccess();
                }else {
                    MaterialToast.alert("Fehler: " + notificationBean.getMessage());
                }
            }
        });
    }

    protected void save(EinstellungDTO einstellungDTO){
        MaterialLoader.showLoading(true);
        service.saveSettings(einstellungDTO, new AsyncCallback<UserView>() {
            @Override
            public void onFailure(Throwable throwable) {
                MaterialLoader.showLoading(false);
                MaterialToast.alert("Server Fehler! Die Daten konnten nicht gespeichert werden");
            }

            @Override
            public void onSuccess(final UserView view) {
                final Timer timer = new Timer() {
                    @Override
                    public void run() {
                        MaterialLoader.showLoading(false);
                        if (view.getNotificationBean().getNotify() == ENotify.SUCCESS) {
                            MaterialToast.alert(view.getNotificationBean().getMessage());
                        } else {
                            MaterialToast.alert(view.getNotificationBean().getMessage());
                        }
                        setView(view);
                    }
                };

                timer.schedule(700);
            }
        });
    }

    /** Versuch auf eine Bestehende Session zuzugreifen */
    public void loginWithUserSession() {
        service.login(null, false, new AsyncCallback<AbstractView>() {
            @Override
            public void onFailure(Throwable throwable) {
                Console.log("Service Fehler! ");
            }

            @Override
            public void onSuccess(AbstractView view) {
                if (view != null && view.getNotificationBean().getNotify() == ENotify.SUCCESS){
                    if (view instanceof UserView){
                        setView((UserView)view);
                    }
                }else {
                    //returnToLoginView();
                }
            }
        });
    }

    public void saveMD5State(final UserDTO user){

        final Timer timer = new Timer() {
            @Override
            public void run() {
                service.saveMD5Key(user, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(Void aVoid) {
                        MaterialLoader.showLoading(true);
                        final Timer timer1 = new Timer() {
                            @Override
                            public void run() {
                                MaterialLoader.showLoading(false);
                            }
                        };
                        timer1.schedule(500);

                    }
                });
            }
        };
        timer.schedule(500);
    }

    /** Reload Image */
    public void reloadImage(long imageID, final ReloadImage reload) {
        service.reloadImage(imageID, new AsyncCallback<ImageDTO>() {

            @Override
            public void onFailure(Throwable throwable) {
                MaterialToast.alert("Fehler beim upload!");
            }

            @Override
            public void onSuccess(ImageDTO imageDTO) {
                reload.onReceive(imageDTO);
            }
        });
    }
}
