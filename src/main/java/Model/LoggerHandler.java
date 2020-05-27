package Model;
import java.util.logging.*;

public class LoggerHandler {
    //todo need to change the path in server
    public static FileHandler loggerEventFileHandler = createLogFile("C:\\Users\\Hila\\Desktop\\log_file\\TheEventsLog_%g.log",Level.INFO);;
    public static FileHandler loggerErrorFileHandler = createLogFile("C:\\Users\\Hila\\Desktop\\log_file\\TheErrorsLog_%g.log",Level.WARNING);

    public static FileHandler createLogFile(String path, Level level){
        try {
            FileHandler logFileHandler = new FileHandler(path,50000000, 100, true);
            logFileHandler.setFormatter(new SimpleFormatter());
            logFileHandler.setFilter(record -> level.equals(record.getLevel()));
            return logFileHandler;
        }catch (Exception e){
            System.out.println("can't create file holder");
        }
        return null;
    }
}
