package learn.myapps.expensestracker.util;

import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
import org.springframework.util.Assert;

public class CustomAssert extends Assert {

    public static void isResourcePresent(boolean expression, String message) throws ResourceNotFoundException {
        if (!expression) {
            throw new ResourceNotFoundException(message);
        }
    }
}
