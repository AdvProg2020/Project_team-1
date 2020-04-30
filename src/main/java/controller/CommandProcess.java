package controller;

public interface CommandProcess {
    String commandProcessor(String command) throws Exception;

    String runCommand(String command);
}
