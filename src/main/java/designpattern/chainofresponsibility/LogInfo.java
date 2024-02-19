package designpattern.chainofresponsibility;

public class LogInfo extends Logger{

    public LogInfo(Logger logger) {
        super(logger);
    }

    @Override
    public void log(int level, String message) {
        if(level == Logger.LogInfo) {
            System.out.println(message);
        } else {
            super.log(level, message);
        }
    }
}
