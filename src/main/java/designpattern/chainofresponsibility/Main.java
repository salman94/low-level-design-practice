package designpattern.chainofresponsibility;

public class Main {
    public static void main(String[] args) {
        Logger logger = new LogInfo(new LogDebug(new LogError(null)));
        logger.log(Logger.LogInfo, "Log information");
        logger.log(Logger.LogDebug, "Log debug");
        logger.log(Logger.LogError, "Log Error");
    }
}
