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

    public ErrorCodes ValidateNewStudent(String name, String em, String address, int[] grades) {
        int j = grades.length;
        if (name.length() == 0 || em.length() == 0 || address.length() == 0 || grades.length == 0) {
            return ErrorCodes.EMPTY_FIELD;
        } else if (!em.contains("@")) {
            return ErrorCodes.EMPTY_FIELD;
        } else for (int i = 0; i < j; i++) {
            if (grades[i] < 1 || grades[i] > 10) {
                return ErrorCodes.INVALID_GRADE;
            }

        }
        return ErrorCodes.EMPTY_FIELD.OK;
    }
}
