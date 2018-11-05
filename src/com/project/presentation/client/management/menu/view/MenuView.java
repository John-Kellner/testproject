package com.project.presentation.client.management.menu.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import com.project.presentation.client.console.log.Console;
import com.project.presentation.client.constants.LiquendiMessages;
import com.project.presentation.client.gui.components.chip.Chip;
import com.project.presentation.client.gui.components.chip.ChipContainer;
import com.project.presentation.client.gui.components.chip.handler.ChipChangeHandler;
import com.project.presentation.client.gui.components.chip.handler.ChipSelectHandler;
import com.project.presentation.client.gui.components.dialog.DialogBox;
import com.project.presentation.client.gui.components.dragablefiles.DragableConfig;
import com.project.presentation.client.gui.components.dragablefiles.DragableFiles;
import com.project.presentation.client.gui.components.headerpanel.HeaderListener;
import com.project.presentation.client.gui.components.headerpanel.HeaderPanel;
import com.project.presentation.client.gui.components.modal.ModalPanel;
import com.project.presentation.client.gui.components.table.bean.TableBean;
import com.project.presentation.client.gui.components.togglebtn.client.button.ToggelButton;
import com.project.presentation.client.gui.components.togglebtn.client.button.ToggleClickHandler;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.login.view.css.LoginViewStyle;
import com.project.presentation.client.management.menu.IMenuView;
import com.project.presentation.client.management.menu.activity.impl.MenuActivity;
import com.project.presentation.client.management.menu.controller.MenuViewController;
import com.project.presentation.client.management.menu.controller.ReloadImage;
import com.project.presentation.client.management.menu.view.colorpicker.ColorPickerWrapper;
import com.project.presentation.client.management.menu.view.colorpicker.IPicker;
import com.project.presentation.client.management.menu.view.css.MenuViewStyle;
import com.project.presentation.client.management.menu.view.css.MenuViewWorkaroundStyle;
import com.project.presentation.client.management.menu.view.navbar.NavBar;
import com.project.presentation.client.management.menu.view.table.TableAddedUser;
import com.project.presentation.client.management.menu.view.tabswitcher.ITabItemHandler;
import com.project.presentation.client.management.menu.view.tabswitcher.TabItemSwitcher;
import com.project.presentation.client.management.menu.view.tinymce.TinyMCE;
import com.project.presentation.client.management.menu.view.tinymce.TinyMCEInitListener;
import com.project.presentation.client.management.menu.view.upload.UploadComplete;
import com.project.presentation.client.management.menu.view.upload.imageupload.FormUpload;
import com.project.presentation.client.management.start.view.css.StartViewStyle;
import com.project.presentation.shared.dto.*;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.UserView;
import gwt.material.design.client.ui.*;

import java.util.List;

/**
 * Created by john on 28.06.2015.
 */
public class MenuView extends MenuViewController implements IMenuView {

    private String updateColorHex;
    interface MenuViewUiBinder extends UiBinder<ScrollPanel, MenuView> {}

    private LiquendiMessages messages = GWT.create(LiquendiMessages.class);

    private static final LoginViewStyle.ResourceBundle loginBundle = LoginViewStyle.getBundle();
    private static final MenuViewStyle.ResourceBundle bundle = MenuViewStyle.getBundle();

    private StartViewStyle.ResourceBundle bundleStart = StartViewStyle.getBundle();
    private static final MenuViewWorkaroundStyle.Resource workaround = MenuViewWorkaroundStyle.getStyle();

    private static MenuViewUiBinder ourUiBinder = GWT.create(MenuViewUiBinder.class);
    private final TabItemSwitcher itemSwitcher = new TabItemSwitcher();
    private MaterialDropDown comboBoxImage = new MaterialDropDown();
    private MenuViewStyle.Resource style = MenuViewStyle.getStyle();

    private final FormUpload uploadForm = new FormUpload();
    private final DragableFiles zertifikatUpload;
    private final ColorPickerWrapper colorPickerWrapper;
    private FlowPanel panelUserLinks = new FlowPanel();
    private String originalChipTextBeforeChange;
    private boolean isTinyMCEEditorInitialized;
    private AvailableUserDTO availableUserDTO;
    private boolean isSetViewAlreadyCalled;
    private TinyMCE simpleEditor;
    private String lastSelectedKey;
    private MenuActivity activity;
    private TemplateDTO template;
    private FlowPanel panelWelcome;
    private boolean isAddingChip;
    private long einstellungId = -1;
    private UserDTO user;
    private UserView view;

    @UiField
    protected MaterialLink btnSaveBoard;

    @UiField
    protected MaterialCollapsible container;

    @UiField
    protected MaterialCollapsibleItem colapseItem;

    @UiField
    protected MaterialLink btnImpressumNavbar, btnDatenschutz, btnOnline;

    @UiField
    protected FlowPanel upload, zeugnis, bild, text, picture;

    @UiField
    protected MaterialButton jTableImageDeleteBtn;

    @UiField
    protected MaterialTextBox tabItemUeberschrift;

    @UiField
    protected FlowPanel tabItemPanel, comboTabItemImagePanel, colorPickerWidget;

    @UiField
    protected ChipContainer<Chip<TabItemDTO>> chipContainer;

    @UiField
    protected ToggelButton md5toggel;

    @UiField
    protected HTML qrLinkText ;

    @UiField
    protected  HTML mFirma, mAdresse, mLand, mTel, mFax, mEmail, mPage, mOeffnungszeiten;

    @UiField
    protected Image mImage, qrCodeImage;

    @UiField
    protected HTML qrCodeDownload;

    @UiField
    protected NavBar navbar;

    @UiField
    protected Label linkmd5lbl;

    @UiField
    protected HeaderPanel headerpanel;

    @UiHandler("logout")
    protected void logoutNavbar(ClickEvent e){
        logout();
    }

    @UiHandler("btnLogoutSidebar")
    protected void logoutSidebar(ClickEvent e){
        logout();
    }

    @UiHandler("btnImpressumNavbar")
    protected void showImpressumSidebar(ClickEvent e){
        showImpressum();
    }

    @UiHandler("btnImpressumSidebar")
    protected void showImpressumNavbar(ClickEvent e) {
        showImpressum();
    }

    @UiHandler("btnDatenschutz")
    protected void showDatenschutzNabvar(ClickEvent e){
        showDatenschutz();
    }

    @UiHandler("btnDatenschutzSidebar")
    protected void showDatenschutzSidebar(ClickEvent e){
        showDatenschutz();
    }

    @UiHandler("btnSaveBoard")
    protected void saveNavbar(ClickEvent e){
        save();
    }

    @UiHandler("btnSaveBoardSidebar")
    protected  void saveSidebar(ClickEvent e){
        save();
    }

    @UiHandler("btnOnline")
    protected void onlineNavbar(ClickEvent e){
        showOnlineProfile();
    }

    @UiHandler("btnOnlineSidebar")
    protected void onlineSidebar(ClickEvent e){
        showOnlineProfile();
    }

    private void showOnlineProfile() {
        if (availableUserDTO != null){
            String userUrl = "";
            if (user.is128BitLogin()){
                userUrl = availableUserDTO.getMd5Url();
            }else {
                userUrl = availableUserDTO.getUserUrl();
            }
            com.google.gwt.user.client.Window.open(userUrl, "_blank", "");
        }
    }

    private void save() {
        /** Speichern des Aktuellen Zustands */
        saveCurrentTabItemToLastSelectedState();

        /** Speichern der Einstellung */
        final EinstellungDTO einstellungDTO = new EinstellungDTO();
        einstellungDTO.setUser(user);
        einstellungDTO.setTemplate(template);
        einstellungDTO.setEinstellungId(einstellungId);
        einstellungDTO.setTabItem(itemSwitcher.getTabAllTabItems());
        einstellungDTO.setHexColor(colorPickerWrapper.getHexCode());
        save(einstellungDTO);
    }

    private void showDatenschutz() {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setTitle("DATENSCHUTZERKL"+(char) 196+ "RUNG");
        dialogBox.setContent(bundleStart.datenschutzerklarung().getText());
        new ModalPanel(dialogBox);
    }

    private void logout() {
        if (panelWelcome != null){
            RootPanel.get().remove(panelWelcome);
        }
        activity.show(EManagedPlace.LOGIN, null);
    }

    private void showImpressum() {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setTitle("IMPRESSUM");
        dialogBox.setContent(bundleStart.impressumText().getText());
        new ModalPanel(dialogBox);
    }

    private final Timer updateColorTimer = new Timer() {
        @Override
        public void run() {
            if (chipContainer != null){
                final Chip<TabItemDTO> item = chipContainer.getSelectedItem();
                if (item != null){
                    item.setHexColor(updateColorHex);
                }
            }
        }
    };

    public MenuView(String name) {
        initWidget(ourUiBinder.createAndBindUi(this));
        workaround.ensureInjected();

        createEditor();

        /** Upload Panel */
        upload.add(uploadForm);
        uploadForm.addUploadHandler(new UploadComplete() {
            @Override
            public void onItemSelect(TableBean bean) {
                uploadForm.setImage(bean);
                jTableImageDeleteBtn.setVisible(true);
            }

            @Override
            public void onRefresh() {
                fillTabItemImage(uploadForm.getAllImages());
                jTableImageDeleteBtn.setVisible(false);
            }

            @Override
            public void onUploadComplete(final TableBean bean) {
                reloadImage(bean.getId(), new ReloadImage() {
                    @Override
                    public void onReceive(ImageDTO image) {
                        bean.setImage(image.getImage());
                        uploadForm.setImage(bean);
                    }
                });
            }
        });

        /** Bild wird geloscht */
        jTableImageDeleteBtn.setVisible(false);
        jTableImageDeleteBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                deleteImage(uploadForm.getSelectedRow().getId());
                uploadForm.setFirstElement();
            }
        });

        zertifikatUpload = new DragableFiles(new DragableConfig() {
            @Override
            public String getServletName() {
                return "uploadAttachment";
            }

            @Override
            public long getEinstellungID() {
                return einstellungId;
            }

            @Override
            public UploadComplete getUploadHandler() {
                return new UploadComplete() {
                    @Override
                    public void onUploadComplete(TableBean bean) {

                    }

                    @Override
                    public void onItemSelect(TableBean bean) {

                    }

                    @Override
                    public void onRefresh() {

                    }
                };
            }
        });

        zeugnis.add(zertifikatUpload);


        /** onUpdate nach initiallisierung, hinzufuegen oder loeschen */
        itemSwitcher.addListener(new ITabItemHandler() {
            @Override
            public void onItemUpdate() {

                /** Reset Chipcontainer */
                chipContainer.clear();

                /** Befuellen des Chip Container  */
                for (TabItemDTO tabAllTabItem : itemSwitcher.getTabAllTabItems()) {
                    final Chip<TabItemDTO> chip = new Chip(tabAllTabItem.getName());
                    if (view != null){
                        chip.setHexColor(view.getEinstellung().getHexColor());
                    }
                    chip.setBean(tabAllTabItem);
                    chipContainer.add(chip);
                }
            }

            @Override
            public void onInitialDataReceived() {
                //FIXME Bugfix endlosschleife mit onItem Update initswitcher onSelect
                final Timer timer = new Timer() {
                    @Override
                    public void run() {
                        final TabItemDTO tabItemDTO = itemSwitcher.getTabAllTabItems().get(0);
                        chipContainer.selectFirstElement(tabItemDTO.getName());
                    }
                };
                timer.schedule(200);
            }
        });

        if (!isSetViewAlreadyCalled){
            super.loginWithUserSession();
        }

        colorPickerWrapper = new ColorPickerWrapper(new IPicker() {
            @Override
            public void onChange(String hexcode) {
                navbar.setColor(hexcode);
                reload(hexcode);
            }
        });
        colorPickerWidget.add(colorPickerWrapper);
        headerpanel.setListener(new HeaderListener() {
            @Override
            public void onSelect(HeaderPanel.Headerstate state) {
                //TAB switch Bild - Text
                switch (state){
                    case BILD:
                        bild.setVisible(true);
                        text.setVisible(false);
                        break;
                    case TEXT:
                        bild.setVisible(false);
                        text.setVisible(true);
                        break;
                }
            }
        });
        headerpanel.setToggelListener(new ToggleClickHandler() {
            @Override
            public void onClick(boolean state) {
                checkToggelSateAndFadeImageOnOFF(state);
            }
        });
    }

    /**
     * Image
     * @param visible
     */
    private void checkToggelSateAndFadeImageOnOFF(boolean visible) {
        if (visible){
            this.picture.removeStyleName(style.pictureFadeOFF());
            this.picture.addStyleName(style.pictureFadeON());
        }else {
            this.picture.removeStyleName(style.pictureFadeON());
            this.picture.addStyleName(style.pictureFadeOFF());
        }
    }

    private void reload(String hexcode){
        this.updateColorHex = hexcode;
        updateColorTimer.cancel();
        updateColorTimer.schedule(10);
    }

    private void createLogo(String name){
        if (panelWelcome != null){
            RootPanel.get().remove(panelWelcome);
        }

        final Label lblWillkommen = new Label("Willkommen");
        lblWillkommen.setStyleName(style.lblWillkommen());

        final Label lblName = new Label(name);
        lblName.setStyleName(style.lblName());

        panelWelcome = new FlowPanel();
        panelWelcome.add(lblWillkommen);
        panelWelcome.add(lblName);

        RootPanel.get().add(panelWelcome);
    }

    /** initialize Chips */
    private void initChips() {
        /** Auswahl eines Chips bei Select */
        chipContainer.addSelectionHandler(new ChipSelectHandler<Chip<TabItemDTO>>() {
            @Override
            public void onSelect(Chip chip) {
                saveCurrentTabItemToLastSelectedState(); // nur auf die Objekte die keinen ToggleClickHandler haben

                lastSelectedKey = chipContainer.getSelectedItem().getText();
                final TabItemDTO dto = itemSwitcher.getKey(lastSelectedKey);

                fillFormularSeiteEinrichten(dto);
                chip.setHexColor(updateColorHex);
            }
        });

        /** Edit, Save, Delete, Move a Chip */
        chipContainer.addChangeHandler(new ChipChangeHandler<TabItemDTO>() {

            @Override
            public void onEdit(Chip chip) {
                //do Something
                isAddingChip = true;
                originalChipTextBeforeChange = chip.getText();
            }

            @Override
            public void onSave(Chip<TabItemDTO> chip) {
                // Warum muss hier das Item removed werden ? geht das auch anderst ?

                if (itemSwitcher.getKey(chip.getText()) != null && isAddingChip){
                    if (!chip.getText().equals(originalChipTextBeforeChange)){
                        chip.setText(originalChipTextBeforeChange);
                        chipContainer.selectFirstElement(originalChipTextBeforeChange);
                        MaterialToast.alert("Eintrag bereits vorhanden!");
                    }
                    itemSwitcher.doUpdate();
                }else {
                    final TabItemDTO item = itemSwitcher.getKey(chip.getBean().getName());
                    itemSwitcher.removeItem(item.getName());
                    item.setName(chip.getText());
                    itemSwitcher.addItem(item);
                }
                selectChipAgain(chip);
                isAddingChip = false;
            }

            @Override
            public void onDelete(Chip<TabItemDTO> chip) {
                itemSwitcher.removeItem(chipContainer.getSelectedItem().getText());
                final String name = itemSwitcher.getTabAllTabItems().get(0).getName();
                chipContainer.selectFirstElement(name);
            }

            @Override
            public void onAddChip(int position) {
                isAddingChip = true;
                final String newEntry = "Neuer Eintrag";
                if (itemSwitcher.getKey(newEntry) != null){
                    MaterialToast.alert("Bitte editieren Sie den Reiter 'Neuer Eintrag' bevor Sie einen neuen anlegen!");
                }else {
                    final TabItemDTO tabItemDTO = new TabItemDTO(0, newEntry);
                    tabItemDTO.setPosition(position);
                    tabItemDTO.setAnzeigen(true);
                    itemSwitcher.addItem(tabItemDTO);
                    itemSwitcher.doUpdate();
                }
                chipContainer.selectFirstElement(newEntry);
            }

            @Override
            public void onMoveRight(Chip chip) {
                itemSwitcher.doUpdate();
                selectChipAgain(chip);
            }

            @Override
            public void onMoveLeft(Chip chip) {
                itemSwitcher.doUpdate();
                selectChipAgain(chip);
            }
        });
    }

    /**
     * Wiederauswahl eines Chips nach Aktuallisierung
     * @param chip
     */
    private void selectChipAgain(Chip chip){
        chipContainer.selectFirstElement(chip.getText());

    }

    @Override
    public void setActivity(MenuActivity activity) {
        this.activity = activity;
        createEditor();
    }

    /**
     * Editor erstellen und nach der Initialisierung des Editors die View aktuallisieren
     */
    public void createEditor(){
        String oldText = "";
        if (simpleEditor != null){
            oldText = simpleEditor.getText();
            tabItemPanel.remove(simpleEditor);
        }
        simpleEditor = new TinyMCE(new TinyMCEInitListener() {
            @Override
            public void onLoadSuccess() {
                isTinyMCEEditorInitialized = true;
                if (view != null){
                    setCurrentView();
                    initChips();
                }
            }
        });
        simpleEditor.getConfig().setTheme("modern");
        simpleEditor.setText(oldText);
        simpleEditor.getElement().getStyle().setTop(-43.0, Style.Unit.PX);
        simpleEditor.getElement().getStyle().setPosition(Style.Position.RELATIVE);
        simpleEditor.getElement().getStyle().setMarginBottom(-61.0, Style.Unit.PX);

        tabItemPanel.add(simpleEditor);
    }

    @Override
    protected void onImageDeleteSuccess() {
        uploadForm.removeSelectedRow();
    }

    /**
     * Uebergabe von LoginView
     * @param view
     */
    @Override
    public void setView(UserView view) {
        this.view = view;

        /** Reload view after Save Methode */
        if (isTinyMCEEditorInitialized) {
            setCurrentView();
            initChips();
        }

        if (panelWelcome != null){
            RootPanel.get().add(panelWelcome);
        }
    }

    private void setCurrentView(){
        if (view != null && view.getNotificationBean() != null){
            if(view.getNotificationBean().getNotify() == ENotify.SUCCESS){
                createFotostudio(view);

                user = view.getUser();
                createLogo(user.getName());

                createEinstellung(view.getEinstellung());

                // Befuellung von upload Table + Combobox Image
                if (!view.getImages().isEmpty()){
                    fillTabItemImage(view.getImages());
                    uploadForm.setTableBeans(view.getImages());
                }

                if (view.getCurrentAvailableUser() != null){
                    availableUserDTO = view.getCurrentAvailableUser();
                    createQRCodePanel();
                }

                if (!view.getAvailableUsers().isEmpty()){
                    panelUserLinks.clear();
                    final HTML table = TableAddedUser.getTable(view.getAvailableUsers());
                    panelUserLinks.add(table);
                }

                if (!view.getAnlagen().isEmpty()){
                    zertifikatUpload.setAnlagen(view.getAnlagen());
                }

            } else if (view !=null && view.getNotificationBean().getNotify() == ENotify.FAILURE){
                MaterialToast.alert("Serverfehler Fehler beim Laden der Ansicht !");
            }
        }
        isSetViewAlreadyCalled = true;
    }

    /** QRCode Panel */
    private void createQRCodePanel() {
        md5toggel.setValue(user.is128BitLogin());
        if (user.is128BitLogin()){
            if (user.getQrcodeMD5() != null){
                qrCodeImage.setUrl("data:image/png;base64," + user.getQrcodeMD5());
            }
            linkmd5lbl.setText("Link Verschlüsselung (aktiv)");
            createQRCodeLink(availableUserDTO.getMd5Url());
        }else {
            if (user.getQrcode() != null){
                qrCodeImage.setUrl("data:image/png;base64," + user.getQrcode());
            }
            linkmd5lbl.setText("Link Verschlüsselung (inaktiv)");
            createQRCodeLink(availableUserDTO.getUserUrl());
        }

        qrCodeDownload.setHTML("<a download='QRCode.PNG' href='" + qrCodeImage.getUrl() + "'>Download</a>");

        md5toggel.setClickHandler(new ToggleClickHandler() {
            @Override
            public void onClick(boolean state) {
                user.setIs128BitLogin(md5toggel.isState());
                saveMD5State(user);
                if (md5toggel.isState()){
                    if (user.getQrcodeMD5() != null){
                        qrCodeImage.setUrl("data:image/png;base64," + user.getQrcodeMD5());
                    }
                    createQRCodeLink(availableUserDTO.getMd5Url());
                    linkmd5lbl.setText("Link Verschlüsselung (aktiv)");
                }else {
                    if (user.getQrcode() != null){
                        qrCodeImage.setUrl("data:image/png;base64," + user.getQrcode());
                    }
                    createQRCodeLink(availableUserDTO.getUserUrl());
                    linkmd5lbl.setText("Link Verschlüsselung (inaktiv)");
                }
            }
        });
    }
    private void createQRCodeLink(String link){
        qrLinkText.setHTML("  <a href='"+link+"' target='_blank'>"+link+"</a>");
    }

    @Override
    public void returnToLoginView() {
        activity.show(EManagedPlace.LOGIN, null);
    }

    /** Ihr Fotostudio - Panel */
    private void createFotostudio(UserView view){
        if (view.getStudio() != null){
            final FotostudioDTO studio = view.getStudio();
            if (studio.getLogo() != null){
                mImage.setUrl("data:image/gif;base64," + studio.getLogo());
            }
            mFirma.setHTML("<h3>Fotostudio: <br><br><b>" + studio.getName() + "</b></h3>");
            mAdresse.setHTML(studio.getAdresse().getStrasse() + " <br>"
                    + studio.getAdresse().getPlz() + " "
                    + studio.getAdresse().getOrt());
            mLand.setHTML(studio.getAdresse().getLand());
            mTel.setHTML("Tel: " + studio.getTelephone_number());
            mFax.setHTML("Fax: " + studio.getFax());
            mEmail.setHTML("<a href='mailto:'" + studio.getEmail() + ">" + studio.getEmail() + "</a>");
            mPage.setHTML("Homepage: <a href=" + studio.getPage() + " target='_blank'>" + studio.getPage() + "</a>");
            mOeffnungszeiten.setHTML("Öffnungszeiten: <br>" + studio.getOeffnungszeiten());
        }
    }

    /** Einstellungen */
    private void createEinstellung(EinstellungDTO einstellung) {
        if (einstellung != null){
            einstellungId = einstellung.getEinstellungId();

            //Setzen der Einstellung ID fuer den Upload der Images darf nicht 0 sein
            uploadForm.setEinstellung(einstellungId);

            /** Template */
            template = einstellung.getTemplate();

            /** Aktivierung */

            /** TabItems werden in die jeweiligen Views geschrieben */
            itemSwitcher.init(einstellung.getTabItem());
            if(colorPickerWrapper != null){
                colorPickerWrapper.setColor(einstellung.getHexColor());
            }
        }
    }

    /**
     * Combobox Image
     * Erzeugen der Combobox (Images) mit all seinen Items inklusive refresh */
    private void fillTabItemImage(final List<ImageDTO> container){
        comboTabItemImagePanel.clear();
        comboBoxImage = new MaterialDropDown();
        /**
         *  Vorsicht muss initiallisiert werden !! Workaround Combobox !!  */
        comboBoxImage.setText("Bild aussuchen");

        for (ImageDTO image : container) {
            final MaterialLink link = new MaterialLink(image.getName(), "black");
            link.setObject(image);
            link.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    comboBoxImage.setText(link.getText());
                    final TabItemDTO selectedItem = itemSwitcher.getKey(chipContainer.getSelectedItem().getText());
                    if (selectedItem != null) {
                        selectedItem.setImage((ImageDTO)link.getObject());
                        setPicture(selectedItem);
                    } else {
                        Console.log("TabItem NULL kann nicht gespeichert werden !!!");
                    }
                }
            });
            comboBoxImage.addWidget(link);
        }

        comboTabItemImagePanel.add(comboBoxImage);
    }

    /**
     * Set Picture to board
     * 'Reiter bearbeiten'
     * @param tabItem
     */
    private void setPicture(final TabItemDTO tabItem){
        picture.clear();

        final Image image = new Image();

        if (tabItem != null && tabItem.getImage() != null && tabItem.getImage().getImagesID() != null){
            final ImageDTO selectedImage = uploadForm.getImage(tabItem.getImage().getImagesID());
            if (selectedImage != null){
                image.setUrl("data:image/png;base64," + selectedImage.getImage());
                comboBoxImage.setText(selectedImage.getName());
            }
        }else {
            image.setResource(bundle.dummy());
            comboBoxImage.setText(" ");
        }
        image.setWidth("100%");
        image.setHeight("auto");
        image.getElement().getStyle().setMarginTop(-72.0, Style.Unit.PX);
        picture.add(image);

        if (tabItem != null){
            //check state and fade
            checkToggelSateAndFadeImageOnOFF(tabItem.isVisible());
            headerpanel.setToggelState(tabItem.isVisible());
        }
    }

    /**
     * Speichert den Zustand des Formulars 'Seite einrichten'
     * Manuelles Ereigniss, da kein CLickhandler verfuegbar ist.
     * Befuellt das TabItemDTO als sich selbst mit den neuen Werten aus den Widgets
     */
    private void saveCurrentTabItemToLastSelectedState() {
        final TabItemDTO selectedItem = itemSwitcher.getKey(lastSelectedKey);
        if (selectedItem != null){
            selectedItem.setTitle(tabItemUeberschrift.getText());
            if (simpleEditor.getText() != null){
                selectedItem.setContent(simpleEditor.getText());
            }else {
                selectedItem.setContent("");
            }
            selectedItem.setAnzeigen(headerpanel.getToggelState());
        }
    }

    /** Seite Konfigurieren wird mit all seinen Elementen gefuellt */
    public void fillFormularSeiteEinrichten(TabItemDTO tabItemDTO){

        // Ueberschrift wird gesetzt
        tabItemUeberschrift.setText(tabItemDTO.getTitle());

        // Beschreibung wird gesetzt
        simpleEditor.clearContent();
        if (!tabItemDTO.getContent().isEmpty()){
            simpleEditor.setContent(tabItemDTO.getContent());
        }else {
            simpleEditor.setContent("");
        }

        setPicture(tabItemDTO);

        headerpanel.setToggelState(tabItemDTO.isVisible());
    }
}