package com.goeuro;

/**
 * Entry point into the application.
 */
public class AppRunner {

    /** hidden default constructor. */
    private AppRunner() {
    }

    public static void main(String[] args) {

        if ((args == null) || (args.length == 0)) {
            System.out.println("Please provide a city name.");
            return;
        }

        BusinessLogic logic = new BusinessLogic();

        logic.execute(args);
        logic.close();          // free resources
    }
}
