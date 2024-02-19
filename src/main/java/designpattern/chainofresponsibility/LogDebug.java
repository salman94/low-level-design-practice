package designpattern.chainofresponsibility;

public class LogDebug extends Logger{
    public LogDebug(Logger logger) {
        super(logger);
    }

    @Override
    public void log(int level, String message) {
        if(level == Logger.LogDebug) {
            System.out.println(message);
        } else {
            super.log(level, message);
        }
    }
}
