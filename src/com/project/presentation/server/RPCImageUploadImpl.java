package com.project.presentation.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.project.presentation.server.tx.dom.IHibernateService;
import com.project.presentation.server.upload.bean.UploadBean;
import com.project.presentation.server.util.Base64Decoder;
import com.project.presentation.shared.view.ImageView;
import com.project.presentation.shared.dto.ImageDTO;
import com.project.presentation.shared.dto.NotificationBean;
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

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class RPCImageUploadImpl extends HttpServlet {
	private static Logger logger = Logger.getLogger(RemoteServiceServlet.class);
	private static final long serialVersionUID = 2299533685765928L;
	private IHibernateService service;
	private UploadBean uploadBean;
	private UploadBean bean;

	@Autowired
	@Required
	@Qualifier("HibernateService")
	public void setService(IHibernateService service){
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

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// check that we have a file upload request
		if (ServletFileUpload.isMultipartContent(request)) {
			processFiles(request, response);
		} else {
			logger.error("No File Request");
		}
	}

	private File tmpDir;
	private File destinationDir;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config
				.getServletContext());
		AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);

		bean = (UploadBean) ctx.getBean("uploadSource");
		//bean = new UploadBean();
		//bean.setUploadDir("upload");

		tmpDir = new File(bean.getImageUploadDir());
		tmpDir.mkdirs();

		if (!tmpDir.isDirectory()) {
			logger.error("Filepath: " + tmpDir.getAbsolutePath());
			logger.error(tmpDir.toString() + " is not a directory - TEMP");
			throw new ServletException(tmpDir.toString() + " is not a directory");
		}

		logger.info("tmpDir:" + tmpDir.toString());

//        String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
		destinationDir = new File(bean.getImageUploadDir());
		destinationDir.mkdirs();

		createFolderIfNotExist();

		if (!destinationDir.isDirectory()) {
			logger.error(tmpDir.getAbsolutePath());
			logger.error(bean.getImageUploadDir() + " is not a directory - Destination");
			throw new ServletException(bean.getImageUploadDir() + " is not a directory");
		}
		logger.info("FileUploadServlet has been initialized !");
	}

	private void createFolderIfNotExist(){
		File theDir = new File(bean.getImageUploadDir());

		if (!theDir.exists()) {
			boolean result = false;

			try{
				theDir.mkdir();
				result = true;
			} catch(SecurityException se){
				logger.info(se.getStackTrace());
			}
			if(result) {
				logger.info("Verzeichnis "+ bean.getImageUploadDir() + " wurde erstellt !");
			}
		}
	}

	private void processFiles(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("HttpServletRequest " + request.toString());
		logger.info("Content Type " + request.getContentType());
		logger.info("ContentLenght " + request.getContentLength());
		logger.info("processFiles called");
		logger.info("TempDir " + tmpDir);
		logger.info("Real Destination Dir" + destinationDir);

		logger.info("Instantiate DiskFileItemFactory");

		// create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// set the size threshold, above which content will be stored on disk
		factory.setSizeThreshold(1 * 1024 * 1024); // 1 MB Maximum

		// set the temporary directory (this is where files that exceed the threshold will be stored)
		factory.setRepository(tmpDir);

		logger.info("Instantiate Apache ServletFileUpload");
		// create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			// parse the request
			List<FileItem> items = upload.parseRequest(request);
			logger.info("Items Size: " + items.size());

			// process the uploaded items
			Iterator<FileItem> itr = items.iterator();
			logger.info("Iterator : " + itr.toString());

			long id = -1;
			String filename = "";

			while (itr.hasNext()) {
				logger.info("Iterator has next : " + itr.toString());
				FileItem item = (FileItem) itr.next();

				// write the uploaded file to the application's file staging area
				final File file = new File(destinationDir, FilenameUtils.getName(item.getName()));
				logger.info("Item Name : " + FilenameUtils.getName(item.getName()));
				item.write(file);
				logger.info("Datei wurde auf den Server geschrieben !");

				final BufferedImage img = ImageIO.read(file);

				final String[] split = file.getName().split("\\.");
				final String prefix = split[split.length - 1];

				if (file.getName().endsWith(prefix)){
					String base64 = Base64Decoder.encodeToString(img,prefix);

					final ImageDTO image = new ImageDTO();
					image.setName(file.getName());
					image.setImage(base64);

					filename = file.getName();

					try{
						final Long einstellungID = new Long(request.getParameter("EinstellungID"));
						final ImageView imageView = service.saveImage(image, einstellungID);
						final NotificationBean notification = imageView.getNotification();
						logger.info(notification.getNotify() + " " + notification.getMessage());

						/** return image ID for request */
						id = imageView.getId();
					}catch (Exception ex){
						ex.printStackTrace();
						logger.error(ex.getMessage());
					}
				}
			}

			/** Response Header ans Frontend FormUpload */
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			if (id != -1){
				writer.print("upload=" + true + ";id=" + id + ";filename=" + filename);
			}else {
				writer.print("upload=" + false + ";id=-1" + ";filename=-1");
			}

		} catch (FileUploadException e) {
			logger.error(e.getStackTrace());
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}
	}
}
