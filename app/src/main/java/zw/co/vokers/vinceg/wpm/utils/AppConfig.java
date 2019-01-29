package zw.co.vokers.vinceg.wpm.utils;

/**
 * Created by Vince G on 28/11/2018
 */

public class AppConfig {
    // Server base url
    public static final String BASE_URL = "http://192.168.1.242/wpm/mobile";

    // Server user login url
    public static final String URL_LOGIN = BASE_URL + "/login.php";

    // Server user register url
    public static final String URL_REGISTER = BASE_URL + "/register.php";

    // Server user register url
    public static final String URL_WIN = BASE_URL + "/adding_win.php";

    // Server user reset or change password
    public static final String URL_CHGERESET = BASE_URL + "/chngeFrgtPwd.php";

    public static final String ABOUT_US_URL = BASE_URL +"api.php";

    public static final String IMAGE_PATH_URL = BASE_URL +"images/";

    public static final String EMP_NAME="name";
    public static final String JOB_TITLE="job_title";
    public static final String PAY_NUMBER="pay_number";
    public static final String APP_DESC="description";

}
