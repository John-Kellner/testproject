package example.mockito.buttonclick1;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwtmockito.GwtMock;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by john on 15.06.2016.
 */
@RunWith(GwtMockitoTestRunner.class)
public class ButtonClickHandlerTest {

    @GwtMock
    private Button btn;

    @Captor
    private ArgumentCaptor<ClickHandler> handler;

    @InjectMocks
    private GUI sut;

    @Before
    public void setUp() throws Exception {
        //spy(btn).addClickHandler(handler.capture());
    }

    @Test
    public void testName() throws Exception {
        spy(btn);
        when(btn.addClickHandler(handler.capture()));

        sut.init();

        handler.getValue().onClick(null);

        //then
        assertEquals(1, sut.getSize());
    }
}

class GUI extends FlowPanel {
    private Button button = new Button("OK");
    private int size;

    public void init(){
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
