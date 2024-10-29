import console.Console;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        Test.doTest();

        if (Arrays.asList(args).contains("-D"))
            Console.doInterface(true);
        else
            Console.doInterface();
    }
}
