package example.gwtmockito;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwtmockito.GwtMock;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by john on 21.05.2016.
 */
@RunWith(GwtMockitoTestRunner.class)
public class ExceptionTest {

    @GwtMock
    private ClickHandler clickHandler;

    @Spy
    private Button btn;

    private GUI gui;

    @Before
    public void setUp() {
        when(btn.addClickHandler(any(ClickHandler.class))).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock aInvocation) throws Throwable {
                clickHandler = (ClickHandler) aInvocation.getArguments()[0];
                return null;
            }
        });

        gui = new GUI();
    }

    @Test
    public void runner() {
        clickHandler.onClick(new ClickEvent(){});
        Assert.assertEquals(1, gui.getSize());
    }
}


class GUI extends FlowPanel {
    private final Button button = new Button("OK");
    private int size;

    public GUI() {
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                size += 1;
            }
        });
    }

    public int getSize(){
        return this.size;
    }
}



