package example.gwtmockito.interaction;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by john on 01.06.2016.
 */
public class VerificationTakeOverIndirectInputsTest {

    interface MailServer {
        void send(String s);
    }

    private MailServer mailServer = mock(MailServer.class);

    @Test
    public void takeOverControllTest() {

        final Messanger messanger = new Messanger(mailServer);
        messanger.setEmail("someone@gmail.com");
        messanger.sendMessage();

        verify(mailServer).send("someone@gmail.com");
    }

    class Messanger {
        private final MailServer mailServer;
        private String email;

        public Messanger(MailServer mailServer) {
            this.mailServer = mailServer;
        }

        public void sendMessage() {
            mailServer.send(email);
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
