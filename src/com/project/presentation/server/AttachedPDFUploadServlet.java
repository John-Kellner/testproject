package com.project.presentation.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.presentation.server.tx.dom.AnlagenService;
import com.project.presentation.server.tx.dom.AnlageDOM;
import com.project.presentation.server.upload.bean.UploadBean;
import com.project.presentation.shared.dto.AnlageDTO;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.enumerations.ENotify;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
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
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class AttachedPDFUploadServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(RemoteServiceServlet.class);
	private static final long serialVersionUID = 2299533685765928L;
	private AnlagenService service;
	private UploadBean uploadBean;
	private File uploadDir;
	private File userDir;

	@Autowired
	@Required
	@Qualifier("ZeugnisService")
	public void setService(AnlagenService service){
		this.service = service;
	}

	@Autowired
	@Required
	@Qualifier("uploadSource")
	public void setUploadBean(UploadBean bean){
		this.uploadBean = bean;
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

	/**
	 * Initial WebContext Loader
	 * @param config
	 * @throws ServletException
     */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config
				.getServletContext());
		AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);

		uploadDir = new File(uploadBean.getZeugnisUploadDir());
		createFolderIfNotExist(uploadDir);

		logger.info("ZerifikatUploadServlet has been initialized !");
	}

	private void processFiles(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("HttpServletRequest " + request.toString());
		logger.info("Content Type " + request.getContentType());
		logger.info("ContentLenght " + request.getContentLength());
		logger.info("processFiles called");
		logger.info("Destination Dir" + uploadDir);

		long einstellungID = -1;
		try{
			einstellungID = new Long(request.getParameter("EinstellungID"));
			userDir = new File(uploadDir + File.separator + einstellungID);
			createFolderIfNotExist(userDir);

			//logger.info(notification.getNotify() + " " + notification.getMessage());

			/** return image ID for request */
			//id = imageView.getId();
		}catch (Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}

		logger.info("Instantiate DiskFileItemFactory");

		// create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// set the size threshold, above which content will be stored on disk
		factory.setSizeThreshold(1 * 1024 * 1024); // 1 MB Maximum

		// set the temporary directory (this is where files that exceed the threshold will be stored)
		factory.setRepository(userDir);

		logger.info("Instantiate Apache ServletFileUpload");
		// create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		NotificationBean notification = new NotificationBean();
		notification.setNotify(ENotify.FAILURE);
		notification.setMessage("");
		String filename = "";
		AnlageDTO anlageDTO = null;
		try {
			// parse the request
			List<FileItem> items = upload.parseRequest(request);
			logger.info("Items Size: " + items.size());

			// process the uploaded items
			Iterator<FileItem> itr = items.iterator();
			logger.info("Iterator : " + itr.toString());

			while (itr.hasNext()) {
				logger.info("Iterator has next : " + itr.toString());
				FileItem item = itr.next();

				// write the uploaded file to the application's file staging area
				final File file = new File(userDir, FilenameUtils.getName(item.getName()));
				logger.info("Item Name : " + FilenameUtils.getName(item.getName()));
				item.write(file);
				logger.info("Datei wurde auf den Server geschrieben !");

				filename = file.getName();

				final AnlageDOM anlageDOM = new AnlageDOM();
				anlageDOM.setFilename(filename);
				anlageDTO = service.saveZeugnis(anlageDOM, einstellungID);
				notification = anlageDTO.getNotification();

			}
		} catch (FileUploadException e) {
			logger.error(e.getStackTrace());
		} catch (Exception e) {
			notification.setMessage("Eine Datei mit dem selben namen exisitert bereits!");
			logger.error(e.getStackTrace());
		}

		/** Response Header ans Frontend FormUpload */
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();

		if (notification != null && notification.getNotify() == ENotify.SUCCESS){
			long id = -1;
			if (anlageDTO != null){
				id = anlageDTO.getId();
			}
			writer.print("upload=" + true + ";id=" + id + ";filename=" + filename);
		}else if (notification != null && notification.getNotify() == ENotify.FAILURE) {
			writer.print("upload=" + false + ";id=-1" + ";filename=-1"+ ";reason=" + notification.getMessage());
		}
	}

	/**
	 * RPC Initial Folder creation if folder not exists
	 */
	private static void createFolderIfNotExist(File file){
		if (!file.isDirectory()) {
			logger.error("Filepath: " + file.getAbsolutePath());
			logger.error(file.toString() + " is not a directory - TEMP");
			try {
				throw new ServletException(file.toString() + " is not a directory");
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}

		if (!file.exists()) {
			try{
				file.mkdir();
				logger.info("User Verzeichnis "+ file + " wurde erstellt !");
			} catch(SecurityException se){
				logger.info(se.getStackTrace());
			}
		}
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// check that we have a file upload request
		if (ServletFileUpload.isMultipartContent(request)) {
			processFiles(request, response);
		} else {
			logger.error("No File Request");
		}
	}
}
