import java.util.UUID;

/**
 * Created by mac on 2017/6/28.
 */
public class JavaTest {
    public static void main(String[] args) {
        //B917E9505497A186D470EE1DD7BC567A
        String accessToken = UUID.randomUUID().toString().replace("-","").toUpperCase();
        System.out.println(accessToken);
    }
}
