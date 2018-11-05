package example.gwtmockito.interaction;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by john on 01.06.2016.
 */
public class VerificationTest {
    interface Car {
        void driveTo(String s);
        boolean needFuel();
    }

    private Car car = mock(Car.class);


    @Test
    public void testVerification(){
        car.driveTo("Sweet Home Alabama");
        car.needFuel();

        verify(car).driveTo("Sweet Home Alabama");
        verify(car).needFuel();
    }
}
