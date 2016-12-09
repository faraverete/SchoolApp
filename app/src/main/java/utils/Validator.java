package utils;

/**
 * Created by faraverete on 08.12.2016.
 */

public class Validator {

    public ErrorCodes ValidateUsernameAndPassword(String username, String password) {
        if (username.length() == 0 || password.length() == 0) {
            return ErrorCodes.EMPTY_FIELD;
        }
        return ErrorCodes.OK;
    }
}
