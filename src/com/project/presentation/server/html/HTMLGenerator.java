package com.project.presentation.server.html;

import com.project.presentation.server.upload.bean.WkHtmlBean;
import com.project.presentation.shared.dto.PDFBean;
import com.project.presentation.shared.dto.TabItemDTO;
import com.project.presentation.shared.enumerations.ETheme;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.enumerations.ENotify;
import com.project.presentation.shared.view.UserView;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

/**
 * Created by john on 15.11.2015.
 */
public class HTMLGenerator {
    private static final Logger logger = Logger.getLogger(HTMLGenerator.class);

    private WkHtmlBean wkHtmlBean;
    private final UserView view;
    private final ETheme theme;
    private String htmlExportPath;

    public HTMLGenerator(UserView view, ETheme theme) {
        this.view = view;
        this.theme = theme;
    }

    /**
     * Create a dynamic HTML Page from user view
     * @param tabID from TabItem
     * @return NotificationBean bean
     */
    public PDFBean createPDF(long tabID, WkHtmlBean bean) {
        setPath(bean);

        final NotificationBean notification = writeHTMLProfile(tabID);
        if (notification.getNotify() == ENotify.FAILURE){
            final PDFBean pdfBean = new PDFBean();
            pdfBean.setNotification(notification);
            return pdfBean;
        }

        return createPDFWithExternalAPP(tabID);
    }

    /**
     * Read HTML tags from unmodiefied HTML Template
     * @return StringBuilder
     */
    private StringBuilder loadHTMLTemplate() {
        final StringBuilder sb = new StringBuilder();

        final BufferedReader br;
        try {
            //FIXME JKE Einbau einer Ueberpruefung des Template files mit Logausgabe

            br = new BufferedReader(new FileReader(getHtmlTemplate() + "index.html"));
            String line = br.readLine();

            while (line != null){
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb;
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
            return null;
        } catch (IOException e) {
            logger.info(e.getMessage());
            return null;
        }
    }

    private void setPath(WkHtmlBean bean){
        final long userId = view.getUser().getUserId();
        this.htmlExportPath = bean.getHtmlExportDir() + File.separator + userId + File.separator;
        this.wkHtmlBean = bean;
    }

    private String getHTMLExportPath(){
        return this.htmlExportPath;
    }

    /**
     * Write modified HTML Template and CSS file to file System
     * @return NotificationBean
     */
    private NotificationBean writeHTMLProfile(long tabID){
        final NotificationBean notification = new NotificationBean();
        try {
            final String template = loadHTMLTemplate().toString();
            final String html = replaceCDATAFromDOM(tabID, template);

            FileUtils.writeStringToFile(new File(getHTMLExportPath()+ "index.html"), html);
            FileUtils.copyFile(new File(getHtmlTemplate() + "index.css"), new File(getHTMLExportPath() + "index.css"));

        } catch (FileNotFoundException e) {
            notification.setNotify(ENotify.FAILURE);
            notification.setMessage(e.getMessage());
        } catch (IOException e) {
            notification.setNotify(ENotify.FAILURE);
            notification.setMessage(e.getMessage());
        }

        notification.setNotify(ENotify.SUCCESS);
        notification.setMessage("Write successfull");
        return notification;
    }

    /**
     * Parse HTML File and replace it with CDATA information
     *
     * @param tabID
     * @param html unmodified HTML file
     * @return StringBuilder which DOM Structure has replaced with <!CDATA> placeholder </!CDATA> Tag
     */
    private final String replaceCDATAFromDOM(long tabID, String html){
        final List<TabItemDTO> tabItems = view.getEinstellung().getTabItem();

        for (TabItemDTO tabItem : tabItems) {
            if (tabItem.getTabItemID() == tabID){
                if (tabItem.getImage() == null){
                    break;
                }
                final String name = html.replace("<![CDATA[name]]>",view.getUser().getName());
                final String title = name.replace("<![CDATA[title]]>", tabItem.getTitle());
                final String image = title.replace("<![CDATA[image]]>", "<img src='data:image/png;base64," + tabItem.getImage().getImage() + "' />");
                final String content = image.replace("<![CDATA[content]]>",tabItem.getContent());
                return content;
            }
        }
        return html.toString();
    }

    /**
     * Create PDF File with external Tool WKHtmlToPDF converter
     * @return
     */
    private PDFBean createPDFWithExternalAPP (long tabID){
        final PDFBean pdfBean = new PDFBean();
        final NotificationBean notificationBean = new NotificationBean();

        /** PDF Dateinamen */
        final String filename = getPDFName(tabID);
        final String replace = filename.replaceAll("ä", "ae").replaceAll("Ä","AE").replaceAll("ü","ue").replaceAll("Ü","UE").replaceAll("ö","oe").replaceAll("Ö","OE").replaceAll("ß","ss").replaceAll("\\s","");
        final String pdfName = getHTMLExportPath() + replace + ".pdf";

        /** Export Path */
        final String html = getHTMLExportPath() + "index.html";

        final String wkhtml = System.getenv(wkHtmlBean.getApplicationPath()) + File.separator +  wkHtmlBean.getApplicationName();
        logger.info("Print pdf name : " + pdfName);
        final ProcessBuilder pb = new ProcessBuilder(wkhtml, "--encoding", "utf-8", "--print-media-type", "--page-width", "1440px", "--disable-smart-shrinking", "--zoom", getZoom() , "-L", "0mm", "-R", "0mm", "-T", "0mm", "--dpi", "300", html, pdfName);
        pb.environment().put("LANG","de_DE_UTF-8");
        Process process = null;
        try {
            process = pb.start();
            final BufferedReader errStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = errStreamReader.readLine();
            while(line != null)
            {
                logger.info(line);
                line = errStreamReader.readLine();
            }
        } catch (IOException e) {
            notificationBean.setNotify(ENotify.FAILURE);
            notificationBean.setMessage(e.getMessage());
            pdfBean.setNotification(notificationBean);
            return  pdfBean;
        }

        notificationBean.setNotify(ENotify.SUCCESS);
        notificationBean.setMessage("PDF File has been successfully created");
        pdfBean.setNotification(notificationBean);
        pdfBean.setPdfName(pdfName);
        return pdfBean;
    }

    /**
     * Generate PDF Name
     * @param tabID
     * @return
     */
    private String getPDFName(long tabID){
        final List<TabItemDTO> tabItem = view.getEinstellung().getTabItem();
        for (TabItemDTO tabItemDTO : tabItem) {
            if (tabItemDTO.getTabItemID() == tabID){
                return tabItemDTO.getName();
            }
        }
        return "Default";
    }

    private String getHtmlTemplate(){
        String source = "";

        if (SystemUtils.IS_OS_WINDOWS){
            source = wkHtmlBean.getHtmlTemplateSourceDirWindows();
        }

        if(SystemUtils.IS_OS_LINUX){
            source = wkHtmlBean.getHtmlTemplateSourceDirLinux();
        }
        return source;
    }

    private String getZoom(){
        String zoom = "0.700";
        if (SystemUtils.IS_OS_WINDOWS){
            zoom = wkHtmlBean.getZoomPDFWindows();
        }

        if(SystemUtils.IS_OS_LINUX){
            zoom = wkHtmlBean.getZoomPDFLinux();
        }
        return zoom;
    }

}
