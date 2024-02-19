package designpattern.chainofresponsibility;

public abstract class Logger {
    public static int LogInfo = 1;
    public static int LogDebug = 2;
    public static int LogError = 3;

    public Logger nextLogging;

    public Logger(Logger logger) {
        nextLogging = logger;
    }

    public void log(int level, String message) {
        if(nextLogging != null) {
            nextLogging.log(level, message);
        }
    }
}
