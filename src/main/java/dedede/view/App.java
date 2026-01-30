package dedede.view;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class App {
    public static void main(String[] args) {
        Model model;
        try {
            Properties props = new Properties();
            props.setProperty("user", "root");
            props.setProperty("password", "root");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://10.11.83.201/biblioteca", props);
            model = new Model(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ViewManager viewManager = new ViewManager(model);
        viewManager.switchView(new ViewModo());
    }
}
