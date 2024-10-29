package console;

import session.Session;

public abstract class TextInterface {
    public TextInterface() {
        isDebugging = false;
    }

    public TextInterface(boolean isDebugging) {
        this.isDebugging = isDebugging;
    }

    protected boolean isDebugging;

    /**
     * Lists options currently available to the user.
     */
    public abstract void listOptions();

    /**
     * Handles a command by the user.
     */
    public abstract void handleCommand(String choice);

    public void logout() {
        Session.clearSession();
        System.out.println("You've been logged out.");
    }

    public void exitProgram() {
        System.out.println("Bye :)");
        System.exit(0);
    }
}
