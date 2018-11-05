package com.project.presentation.client.management.login.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.login.ILoginView;
import com.project.presentation.client.management.login.activity.ILoginActivity;
import com.project.presentation.client.management.login.view.css.LoginViewStyle;
import com.project.presentation.shared.dto.LoginDTO;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.AbstractView;
import com.project.presentation.shared.view.StudioView;
import com.project.presentation.shared.view.UserView;
import gwt.material.design.client.ui.*;

/**
 * Created by john on 27.06.2015.
 */
public class LoginView extends Composite implements ILoginView {

    protected interface LoginViewUiBinder extends UiBinder<FlowPanel, LoginView> {}
    private ILoginActivity activity;
    private final String name;

    private static LoginViewUiBinder ourUiBinder = GWT.create(LoginViewUiBinder.class);
    private static final LoginViewStyle.ResourceBundle bundle = LoginViewStyle.getBundle();

    @UiField
    protected MaterialButton btnLogin;

    @UiField
    protected MaterialTextBox txtBoxEmail, txtBoxPassword;

    @UiField
    protected MaterialCheckBox checkBox;

    @UiField
    protected MaterialImage imagelogin;

    @Override
    protected void onAttach() {
        super.onAttach();
        ((InputElement) txtBoxEmail.getElement().getChild(2)).focus();
    }

    public LoginView(final String name){
        this.name = name;
        initWidget(ourUiBinder.createAndBindUi(this));

        btnLogin.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                login();
            }
        });
        imagelogin.setUrl(bundle.logoNeu().getSafeUri());

        Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(Event.NativePreviewEvent nativePreviewEvent) {
                if (nativePreviewEvent.getNativeEvent().getCharCode() == KeyCodes.KEY_ENTER){
                    login();
                }
            }
        });
    }

    private void login() {
        if (txtBoxEmail.getText().isEmpty() || txtBoxEmail.getText().equals("")){
            MaterialToast.alert("Bitte geben Sie ihre Email an !");
            return;
        }

        if (txtBoxPassword.getText().isEmpty() || txtBoxPassword.getText().equals("")){
            MaterialToast.alert("Bitte geben Sie ihr Passwort an !");
            return;
        }else {
            final LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail(txtBoxEmail.getText());
            loginDTO.setPassword(txtBoxPassword.getText());
            MaterialLoader.showLoading(true);

            activity.login(loginDTO, checkBox.getValue(), new AsyncCallback<AbstractView>() {
                @Override
                public void onFailure(Throwable throwable) {
                    MaterialLoader.showLoading(false);
                    MaterialToast.alert("Login Fehlgeschlagen : Server Fehler bitte versuchen sie es zu einem sp"+(char) 228+"teren Zeitpunkt noch einmal!");
                }

                @Override
                public void onSuccess(AbstractView view) {
                    if (view.getNotificationBean().getNotify() == ENotify.SUCCESS){
                        MaterialLoader.showLoading(false);

                        if (view instanceof UserView){
                            activity.show(EManagedPlace.MENU, view);
                        }

                        if (view instanceof StudioView){
                            activity.show(EManagedPlace.USER, view);
                        }
                    }else {
                        MaterialLoader.showLoading(false);
                        MaterialToast.alert(view.getNotificationBean().getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void setActivity(ILoginActivity activity) {
        this.activity = activity;
    }

    @Override
    public String getName() {
        return this.name;
    }
}