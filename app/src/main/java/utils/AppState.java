package utils;

/**
 * Created by faraverete on 09.12.2016.
 */

public class AppState {

    public static boolean LOGGED = false;

    public static void Login() {
        LOGGED = true;
    }

    public static void Logout() {
        LOGGED = false;
    }
}
