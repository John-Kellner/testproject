package com.project.presentation.client.management.user.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.project.presentation.client.console.log.Console;
import com.project.presentation.client.constants.LiquendiMessages;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.menu.view.table.TableAddedUser;
import com.project.presentation.client.management.start.activity.impl.EDocumentPage;
import com.project.presentation.client.management.user.IUserView;
import com.project.presentation.client.management.user.activity.impl.UserActivity;
import com.project.presentation.client.management.user.view.controller.UserViewController;
import com.project.presentation.client.management.user.view.css.UserViewStyle;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.StudioView;
import gwt.material.design.client.ui.*;

import java.util.List;

/**
 * Created by john on 28.06.2015.
 */
public class UserViewM extends UserViewController implements IUserView {

    private LiquendiMessages messages = GWT.create(LiquendiMessages.class);
    private final UserViewStyle.Resource style = UserViewStyle.getStyle();
    private final MaterialTextBox passwordAgain = new MaterialTextBox();
    private final MaterialTextBox loginName = new MaterialTextBox();
    private final MaterialTextBox password = new MaterialTextBox();
    private final MaterialTextBox strasse = new MaterialTextBox();
    private final MaterialTextBox email = new MaterialTextBox();
    private final MaterialTextBox land = new MaterialTextBox();
    private final MaterialTextBox plz = new MaterialTextBox();
    private final MaterialLink speichern = new MaterialLink();
    private final MaterialTextBox ort = new MaterialTextBox();
    private FlowPanel panelUserLinks = new FlowPanel();
    private List<AvailableUserDTO> availableUserDTO;
    private boolean isSetViewAlreadyCalled;
    private UserActivity activity;
    private FotostudioDTO studio;
    private final String name;
    private UserDTO user;

    @UiField
    protected Image mImage;

    @UiField
    protected Label welcome;

    @UiField
    protected FlowPanel userAddPanel;

    @UiField
    protected MaterialCollapsibleItem allUsers;

    @UiField
    protected  HTML mFirma, mAdresse, mLand, mTel, mFax, mEmail, mPage, mOeffnungszeiten;

    @UiField
    protected MaterialLink btnImpressum, btnDatenschutz;

    @UiHandler("logout")
    protected void clickHandler(ClickEvent e){
        activity.show(EManagedPlace.LOGIN, null);
    }

    interface UserViewUiBinder extends UiBinder<ScrollPanel, UserViewM> {
    }

    private static UserViewUiBinder ourUiBinder = GWT.create(UserViewUiBinder.class);

    public UserViewM(String user) {
        this.name = user;
        initWidget(ourUiBinder.createAndBindUi(this));
        createFormNewUser();
    }

    @Override
    public void setActivity(UserActivity activity) {
        this.activity = activity;
    }

    /** Formular Benutzer Anlegen */
    public void createFormNewUser(){
        userAddPanel.clear();

        final MaterialLink anlegen = new MaterialLink();
        anlegen.setText("anlegen");
        anlegen.setIcon("mdi-action-perm-identity");

        final MaterialCard materialCard = new MaterialCard();
        materialCard.setTitle("Benutzer anlegen");
        materialCard.setTitle("Hier legen Sie einen Benutzer an");
        materialCard.setType("basic");
        materialCard.addWidget(anlegen);
        anlegen.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                anlegen.setVisible(false);
                materialCard.addWidget(getUserPanel());
            }
        });
        userAddPanel.add(materialCard);

        if (!isSetViewAlreadyCalled){
            super.loginWithUserSession();
        }

        allUsers.addContent(panelUserLinks);

        btnImpressum.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                activity.show(EManagedPlace.START, EDocumentPage.IMPRESSEUM);
            }
        });

        btnDatenschutz.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                activity.show(EManagedPlace.START, EDocumentPage.DATENSCHUTZERKLARUNG);
            }
        });
    }

    /**
     * Validierung Formular Benutzer anlegen
     * @return
     */
    public boolean isFormValidateOK() {
        if ((password.getText().equals("") || password.getText().length() == 0)){
            MaterialToast.alert("Bitte geben Sie ein Passwort ein!");
            return false;
        }
        if (password.getText().equals(passwordAgain.getText())){
            return true;
        }else {
            password.setText("");
            passwordAgain.setText("");
            MaterialToast.alert("Die Passw" + (char) 246 + "rter stimmen nicht " + (char) 252 + "berein!");
        }
        return false;
    }

    /**
     * Benutzer anlegen Panel
     * @return
     */
    private FlowPanel getUserPanel(){
        final FlowPanel container = new FlowPanel();
        loginName.setPlaceholder("Name");
        email.setPlaceholder("Email");
        strasse.setPlaceholder("Stra" + (char) 223 + "e");
        plz.setPlaceholder("PLZ");
        ort.setPlaceholder("Ort");
        password.setPlaceholder("Passwort");
        password.setType("password");
        passwordAgain.setPlaceholder("Passwort erneut eingeben");
        passwordAgain.setType("password");
        land.setPlaceholder("Land");
        speichern.setText("Speichern");
        speichern.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (isFormValidateOK()) {
                    MaterialLoader.showLoading(true);
                    activity.saveUser(getBean(), new AsyncCallback<NotificationBean>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            MaterialLoader.showLoading(false);
                        }

                        @Override
                        public void onSuccess(NotificationBean notificationBean) {
                            MaterialLoader.showLoading(false);
                            switch (notificationBean.getNotify()){
                                case SUCCESS:
                                    loginName.setText("");
                                    email.setText("");
                                    password.setText("");
                                    passwordAgain.setText("");
                                    strasse.setText("");
                                    plz.setText("");
                                    ort.setText("");
                                    land.setText("");

                                    final Image image = new Image();
                                    image.setStyleName("mdi-action-done-all");
                                    container.add(image);
                                    MaterialToast.alert("Benutzer wurde gespeichert !");
                                    break;
                                case DUPLICATE_ENTRY:
                                    MaterialToast.alert("Benutzer konnte nicht angelegt werden, er existiert bereits !");
                                    break;
                            }
                        }
                    });
                    createFormNewUser();
                }
            }
        });

        container.setStyleName(style.cardUserAdd());
        container.add(loginName);
        container.add(email);
        container.add(password);
        container.add(passwordAgain);
        container.add(strasse);
        container.add(plz);
        container.add(ort);
        container.add(land);
        container.add(speichern);
        return container;
    }

    private UserDTO getBean(){
        final UserDTO userDTO = new UserDTO();
        userDTO.setName(loginName.getText());
        userDTO.setEmail(email.getText());
        userDTO.setPassword(password.getText());

        final AdresseDTO adresseDTO = new AdresseDTO();
        adresseDTO.setStrasse(strasse.getText());
        adresseDTO.setPLZ(plz.getText());
        adresseDTO.setOrt(ort.getText());
        adresseDTO.setLand(land.getText());
        userDTO.setAdresse(adresseDTO);
        if (studio != null){
            userDTO.setFotostudio(studio);
        }
        return userDTO;
    }

    @Override
    public void setView(StudioView view) {
        Console.log("View is arrived !");
        if (view != null && view.getNotificationBean() != null){
            if(view.getNotificationBean().getNotify() == ENotify.SUCCESS){
                createFotostudio(view);

                if(view.getStudio() != null){
                    studio = view.getStudio();
                }

                welcome.setText(messages.welcome() + " " + studio.getName());

                if (view.getAvailableUser() != null){
                    availableUserDTO = view.getAvailableUser();
                }

                if (view.getAvailableUser() != null){
                    panelUserLinks.clear();
                    final HTML table = TableAddedUser.getTable(view.getAvailableUser());
                    panelUserLinks.add(table);
                }

            } else if (view !=null && view.getNotificationBean().getNotify() == ENotify.FAILURE){
                MaterialToast.alert("Serverfehler Fehler beim Laden der Ansicht !");
            }
        }
        isSetViewAlreadyCalled = true;
    }

    /** Ihr Fotostudio - Panel */
    private void createFotostudio(StudioView view){
        if (view.getStudio() != null){
            final FotostudioDTO studio = view.getStudio();
            if (studio.getLogo() != null){
                mImage.setUrl("data:image/gif;base64," + studio.getLogo());
            }
            mFirma.setHTML("<h2>Studio: " + studio.getName() + "</h2>");
            mAdresse.setHTML("<h4>"
                    + studio.getAdresse().getStrasse() + " "
                    + studio.getAdresse().getPlz() + " "
                    + studio.getAdresse().getOrt()
                    + "</h4>");
            mLand.setHTML("<h4>" + studio.getAdresse().getLand() + "</h4><br>");
            mTel.setHTML("<h4>Tel: " + studio.getTelephone_number() + "</h4>");
            mFax.setHTML("<h4>Fax: " + studio.getFax() +"</h4>");
            mEmail.setHTML("<h4>EMail: <a href='mailto:'" + studio.getEmail() + ">" + studio.getEmail() + "</a></h4>");
            mPage.setHTML("<h4>Homepage: <a href=" + studio.getPage() + " target='_blank'>" + studio.getPage() + "</a></h4>");
            mOeffnungszeiten.setHTML("<h4>Öffnungszeiten: " + studio.getOeffnungszeiten() + "</h4>");
        }
    }

    @Override
    public void returnToLoginView() {
        if (activity != null){
            activity.show(EManagedPlace.LOGIN, null);
        }else {
            Console.log("FIXME activity! null");
        }
    }
}
