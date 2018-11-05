package example.gwtmockito.state;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by john on 01.06.2016.
 */
public class ExpectExceptionTest {

    interface Car {
        boolean drive();
    }

    private Car myFerrari = mock(Car.class);

    @Test(expected = RuntimeException.class)
    public void throwException(){
        when(myFerrari.drive()).thenThrow(new RuntimeException());
        myFerrari.drive();
    }
}
