package example.mockito.buttonClick2;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by john on 15.06.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ButtonClickHandler2Test {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testName() throws Exception {
        GUI gui = new GUI();

    }
}

class GUI extends FlowPanel {
    private Button button = new Button("OK");
    private int size;

    public GUI() {

    }

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

