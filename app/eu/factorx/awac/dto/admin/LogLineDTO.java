package eu.factorx.awac.dto.admin;

import java.util.HashMap;
import java.util.List;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 5/09/14.
 */
public class LogLineDTO extends DTO{

    private Integer lineNb;

    private String name;

    private String logCategory;

    //message by LogType
    private HashMap<String, List<String>> messages;

    public LogLineDTO() {
    }

    public String getLogCategory() {
        return logCategory;
    }

    public void setLogCategory(String logCategory) {
        this.logCategory = logCategory;
    }

    public HashMap<String, List<String>> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, List<String>> messages) {
        this.messages = messages;
    }

    public Integer getLineNb() {
        return lineNb;
    }

    public void setLineNb(Integer lineNb) {
        this.lineNb = lineNb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LogLineDTO{" +
                "messages=" + messages +
                '}';
    }
}
