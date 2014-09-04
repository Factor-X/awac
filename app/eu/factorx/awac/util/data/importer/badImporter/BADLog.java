package eu.factorx.awac.util.data.importer.badImporter;

/**
 * Created by florian on 4/09/14.
 */
public class BADLog {

    public void addToLog(LogType logType, int line, String message) {
        switch (logType) {
            case INFO:
                play.Logger.info("Line " + line + "=>" + message);
                break;
            case ERROR:
                play.Logger.error("Line " + line + "=>" + message);
                break;
            case WARNING:
                play.Logger.warn("Line " + line + "=>" + message);
                break;
            case DEBUG:
                play.Logger.debug("Line " + line + "=>" + message);
                break;
        }
    }

    public enum LogType {
        INFO, WARNING, ERROR, DEBUG;


    }
}
