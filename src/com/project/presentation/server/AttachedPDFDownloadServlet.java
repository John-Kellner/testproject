package com.project.presentation.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.presentation.server.tx.dom.AnlagenService;
import com.project.presentation.server.util.servlet.ServletUtil;
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
import java.io.IOException;
import java.util.Map;

/**
 * Created by john on 22.11.2015.
 */
public class AttachedPDFDownloadServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(RemoteServiceServlet.class);
    private AnlagenService service;

    @Autowired
    @Required
    @Qualifier("ZeugnisService")
    public void setAnlagenService(AnlagenService anlagenService){
        this.service = anlagenService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config
                .getServletContext());
        AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
        logger.info("AttachedPDFDownloadServlet has been initialized successfully");
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
        logger.info("PDF Wird geladen!");

        final Long id = parseID(request.getParameterMap());
        if (id == null){ return; }

        /**
         * PDF File Stream forward to browser
         */
        ServletUtil.writePdfToResponse(service.getUploadedFile(id), response);
    }

    /**
     * Load ID from request Parameter
     * @param parameterMap
     * @return
     */
    private Long parseID(Map<String, String[]> parameterMap){
        final String attachmentID = parameterMap.get("uid")[0].replace(" ", "");

        if (StringUtils.isEmpty(attachmentID)) {
            return null;
        }

        return Long.parseLong(attachmentID);
    }
}
