package eu.factorx.awac.util.data.importer.badImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by florian on 4/09/14.
 */
public class BADLog {

    private HashMap<Integer, LogLine> logLines = new HashMap<>();

    public void addToLog(LogType logType, int line, String message) {

        if(!logLines.containsKey(line)){
            logLines.put(line,new LogLine());
        }

        logLines.get(line).add(logType, message);


        switch (logType) {
            case ID:
                play.Logger.info("Line " + line + "----------------------------------------> This is a BAD " + message);
                break;
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

    public HashMap<Integer, LogLine> getLogLines() {
        return logLines;
    }

    public void setLogLines(HashMap<Integer, LogLine> logLines) {
        this.logLines = logLines;
    }

    public static class LogLine{

        private HashMap<LogType, List<String>> messages = new HashMap<>();

        public void add(LogType logType, String message) {
            if(!messages.containsKey(logType)){
                messages.put(logType,new ArrayList<String>());
            }
            if(logType != LogType.ID) {
                message = (messages.get(logType).size() + 1) + ". " + message;
            }

            messages.get(logType).add(message);
        }

        public HashMap<LogType, List<String>> getMessages() {
            return messages;
        }

        public void setMessages(HashMap<LogType, List<String>> messages) {
            this.messages = messages;
        }

        @Override
        public String toString() {
            return "LogLine{" +
                    "messages=" + messages +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BADLog{" +
                "logLines=" + logLines +
                '}';
    }

    public enum LogType {
        ID,INFO, WARNING, ERROR, DEBUG;
    }
}
