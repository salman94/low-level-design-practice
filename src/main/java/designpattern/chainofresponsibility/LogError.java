package designpattern.chainofresponsibility;

public class LogError extends Logger{
    public LogError(Logger logger) {
        super(logger);
    }

    @Override
    public void log(int level, String message) {
        if(level == Logger.LogError) {
            System.out.println(message);
        } else {
            super.log(level, message);
        }
    }
}
