package dedede.view;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Model model;
        try {
            model = new Model(
                new File("data/books.csv"),
                new File("data/users.csv")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ViewManager viewManager = new ViewManager(model);
        viewManager.switchView(new ViewModo());
    }
}
