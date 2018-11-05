package com.project.java.presentation.user;

import com.project.java.presentation.server.ServiceDatenbankTest;
import com.project.presentation.server.tx.dom.IHibernateService;
import com.project.presentation.shared.dto.UserDTO;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import util.PersistenceLoader;

/**
 * Created by john on 16.07.2015.
 */
public class UserDAOTest {

    private final IHibernateService service = (IHibernateService) PersistenceLoader
            .getApplicationContext().getBean("HibernateService");

    /** The logger. */
    private Logger logger = Logger.getLogger(ServiceDatenbankTest.class);

    @Test(groups = "ServiceTest")
    public void testFindUserAndAdress() {
        logger.info("Find User and Adress !");
        final UserDTO user = service.findUserAndAddress(3L);
        logger.info(user.getName() + " " + user.getAdresse());
    }

    @Test(groups = "ServiceTest")
    public void testLogin(){
        UserDTO user = new UserDTO();
        user.setEmail("user@t-online.de");
        user.setPassword("1234");
        final UserDTO dbUser = service.loginUser(user.getEmail(), user.getPassword());
        logger.info(dbUser.getUserId());
    }
}
