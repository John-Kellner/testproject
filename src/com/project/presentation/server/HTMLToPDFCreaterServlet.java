package com.project.presentation.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.presentation.server.html.HTMLGenerator;
import com.project.presentation.server.tx.dom.IHibernateService;
import com.project.presentation.server.upload.bean.WkHtmlBean;
import com.project.presentation.server.util.servlet.ServletUtil;
import com.project.presentation.shared.dto.PDFBean;
import com.project.presentation.shared.dto.TabItemDTO;
import com.project.presentation.shared.enumerations.ETheme;
import com.project.presentation.shared.view.UserView;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by john on 22.11.2015.
 */
public class HTMLToPDFCreaterServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(RemoteServiceServlet.class);
    private IHibernateService service;

    @Autowired
    @Qualifier("wkhtmltopdf")
    public WkHtmlBean wkHtmlBean;

    @Autowired
    @Required
    @Qualifier("HibernateService")
    public void setService(IHibernateService service){
        this.service = service;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config
                .getServletContext());
        AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        logger.info("HTMLToPDFCreaterServlet has been initialized successfully");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("HttpServletRequest " + request.toString());
        logger.info("PDF Wird erstellt zurückgegeben!");
        Map<String, String[]> parameterMap = request.getParameterMap();

        final String username = parameterMap.get("uid")[0].replace(" ","");
        final String tabName = parameterMap.get("tabName")[0].replace(" ", "");
        final String theme = parameterMap.get("theme")[0].replace(" ", "");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(tabName) || StringUtils.isEmpty(theme)){
            return;
        }

        final UserView userView = service.loadViewByUrl(username);
        final PDFBean pdfBean = generatePDF(userView, tabName, ETheme.valueOf(theme));

        //String destination = DESTINATION_DIR_PATH + File.separator + document + File.separator;
        File file = new File(pdfBean.getPdfName());
        if (!file.exists()){
            logger.error("PDF File could not be found for upload!");
            logger.error("File Path: " + file.getAbsoluteFile());
            logger.error("PDF Bean: Notification " + pdfBean.getPdfName() + " Message" + pdfBean.getNotification() );
            return;
        }

        /**
         * leads PDF File Stream forward to browser
         */
        ServletUtil.writePdfToResponse(file, response);
    }

    /**
     * PDF Generator - User view for generate PDF File to Download
     * HTMLToPDFTest.groovy
     * @param view complete User View
     * @param selectedItem from View which has been marked
     * @param defaultTheme Style
     */
    public PDFBean generatePDF(UserView view, String selectedItem, ETheme defaultTheme) {

        /** TabItem Suche */
        TabItemDTO item = null;
        for (TabItemDTO tabItemDTO : view.getEinstellung().getTabItem()) {
            if (tabItemDTO.getName().replace(" ","").equals(selectedItem)){
                item = tabItemDTO;
                break;
            }
        }

        final HTMLGenerator htmlGenerator = new HTMLGenerator(view, defaultTheme);
        final PDFBean pdf = htmlGenerator.createPDF(item.getTabItemID(), wkHtmlBean);

        logger.info("PDF has been created");
        logger.info("Message " +pdf.getNotification().getNotify() + " " + pdf.getNotification().getMessage());
        return pdf;
    }

}
