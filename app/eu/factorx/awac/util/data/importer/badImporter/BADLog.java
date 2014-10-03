package eu.factorx.awac.util.data.importer.badImporter;

import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by florian on 4/09/14.
 */
public class BADLog {

    private HashMap<Integer, LogLine> logLines = new HashMap<>();

    public LogLine getLogLine(int line, LogCat  logCat,String id){
        //Logger.info("LOG : line : "+line);
        if (!logLines.containsKey(line)) {
            logLines.put(line, new LogLine(logCat,id));
        }
        return logLines.get(line);
    }

    public HashMap<Integer, LogLine> getLogLines() {
        return logLines;
    }

    public void setLogLines(HashMap<Integer, LogLine> logLines) {
        this.logLines = logLines;
    }

    public static class LogLine {

        private HashMap<LogType, List<String>> messages = new HashMap<>();

        private LogCat logCat;

        private String id;

        public LogLine(LogCat logCat, String id) {
            this.logCat = logCat;
            this.id = id;
        }

        public void addWarn(String message) {
            //Logger.warn(message);
            add(LogType.WARNING, message);
        }


        public void addError(String message) {
            //Logger.error(message);
            add(LogType.ERROR, message);
        }


        public void addInfo(String message) {
            //Logger.info(message);
            add(LogType.INFO, message);
        }

        public void addDebug(String message) {

            add(LogType.DEBUG, message);
        }

        public String getId() {
            return id;
        }

        public LogCat getLogCat() {
            return logCat;
        }

        private void add(LogType logType, String message) {
            if (!messages.containsKey(logType)) {
                messages.put(logType, new ArrayList<String>());
            }
                message = (messages.get(logType).size() + 1) + ". " + message;

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
        INFO, WARNING, ERROR, DEBUG;
    }

    public enum LogCat {
        BAD,QUESTION;
    }
}
