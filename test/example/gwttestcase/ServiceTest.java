package example.gwttestcase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.presentation.client.service.RPCServiceLaiquendi;
import com.project.presentation.client.service.RPCServiceLaiquendiAsync;
import com.project.presentation.shared.dto.LoginDTO;
import com.project.presentation.shared.view.AbstractView;

/**
 * Created by john on 22.06.2016.
 */
public class ServiceTest extends GWTTestCase{

    public void testName() throws Exception {
        final LoginDTO loginDTO = new LoginDTO("user@t-online.de","1234");
        RPCServiceLaiquendiAsync service = GWT.create(RPCServiceLaiquendi.class);
        service.login(loginDTO, true, new AsyncCallback<AbstractView>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(AbstractView abstractView) {
                System.out.println();
            }
        });
    }

    @Override
    public String getModuleName() {
        return "com.project.presentation.presentation";
    }
}
