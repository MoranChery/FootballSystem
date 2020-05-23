package Model;
import java.util.logging.*;

public class LoggerHandler {
    private Logger loggerEvents;
    private Logger loggerErrors;
    private FileHandler loggerEventFileHandler;
    private FileHandler loggerErrorFileHandler;

    public LoggerHandler(String className) {
         loggerEvents = Logger.getLogger(className + "Events");
        this.loggerEvents = LogManager.getLogManager().getLogger(className + "Events");

        loggerErrors = Logger.getLogger(className + "Errors");
        this.loggerErrors = LogManager.getLogManager().getLogger(className + "Errors");
        //todo need to change the path in server
        loggerEventFileHandler  = createLogFile("C:\\Users\\noyha\\IdeaProjects\\TheEventsLog_%u.log",Level.INFO);
        loggerErrorFileHandler  = createLogFile("C:\\Users\\noyha\\IdeaProjects\\TheErrorsLog_%u.log",Level.WARNING);
        this.loggerEvents.addHandler(loggerEventFileHandler);
        this.loggerErrors.addHandler(loggerErrorFileHandler);
    }

    public void logError(String msg, Exception error) {
        loggerErrors.log(Level.SEVERE, msg, error);
    }
    public FileHandler createLogFile(String path, Level level){
        try {
            FileHandler logFileHandler = new FileHandler(path,true);
            logFileHandler.setFormatter(new SimpleFormatter());
            logFileHandler.setLevel(level);
            return logFileHandler;
        }catch (Exception e){
            System.out.println("can't create file holder");
        }
        return null;
    }

    public Logger getLoggerErrors() {
        return loggerErrors;
    }

    public void setLoggerErrors(Logger loggerErrors) {
        this.loggerErrors = loggerErrors;
    }

    public Logger getLoggerEvents() {
        return loggerEvents;
    }

    public void setLoggerEvents(Logger loggerEvents) {
        this.loggerEvents = loggerEvents;
    }
}
