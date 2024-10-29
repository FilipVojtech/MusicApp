import console.ConsoleController;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        Test.doTest();

        if (Arrays.asList(args).contains("-D"))
            ConsoleController.doInterface(true);
        else
            ConsoleController.doInterface();
    }
}
