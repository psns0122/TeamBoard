package BoardTeamPractice.config;

import java.util.ResourceBundle;

public class ResourceBundleTest {

    public static void main(String[] args) {

        ResourceBundle bundle = ResourceBundle.getBundle("BoardTeamPractice/config/dbinfo");

        System.out.println("driver: " + bundle.getString("driver"));
        System.out.println("url: " + bundle.getString("url"));
        System.out.println("user: " + bundle.getString("user"));
        System.out.println("password: " + bundle.getString("password"));


    }



}





