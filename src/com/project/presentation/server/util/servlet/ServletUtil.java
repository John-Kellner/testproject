package com.project.presentation.server.util.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * Created by john on 17.07.2016.
 */
public class ServletUtil {

    private static Logger logger = Logger.getLogger(RemoteServiceServlet.class);

    /**
     * leads PDF File Stream forward to browser
     * PDF
     * @param fPdf
     * @param response
     */
    public static void writePdfToResponse(File fPdf, HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=" + fPdf);
        response.setContentLength((int) fPdf.length());
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(fPdf);
            OutputStream responseOutputStream = response.getOutputStream();
            int bytes;
            while ((bytes = fileInputStream.read()) != -1) {
                responseOutputStream.write(bytes);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }
}
