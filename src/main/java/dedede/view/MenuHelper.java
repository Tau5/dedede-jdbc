package dedede.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;


public class MenuHelper {
    private record Option(String name, Supplier<View> supplier) {}
    public static Scanner sc = new Scanner(System.in);

    Map<Integer, Option> options;

    public MenuHelper() {
        this.options = new HashMap<>();
    }

    public void registerOption(int key, String name, Supplier<View> func) {
        options.put(key, new Option(name, func));
    }

    public View chooseAndExecute(String prompt) {
        options.forEach((key, option) -> {
            System.out.println(key + ": " + option.name);
        });

        int chosen;
        do {
            chosen = getNumber(prompt);

            if (options.containsKey(chosen)) {
                break;
            } else {
                System.out.println("Opción incorrecta");
            }

        } while (!options.containsKey(chosen));

        return options.get(chosen).supplier.get();
    }

    public static int getNumber(String prompt) {
        System.out.print(prompt + " ");
        int value = 0;

        // Get line
        String line = sc.nextLine();
        // Remove all characters which are not digits
        line = line.replaceAll("\\D", "");

        try {
            value = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
        return value;
    }

}
