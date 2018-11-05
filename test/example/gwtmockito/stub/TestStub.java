package example.gwtmockito.stub;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Created by john on 01.06.2016.
 */
public class TestStub {

    interface Template {
        String prepareMessage(String prepare);
    }

    @Test
    public void testStub(){
        Template template = mock(Template.class);
        Messanger sut = new Messanger();
        when(template.prepareMessage("Prepare")).thenReturn("Ready");

        assertEquals(sut.sendMessage(template), "Ready");
    }

    class Messanger {

        public String sendMessage(Template template) {
            return template.prepareMessage("Prepare");
        }
    }

}
