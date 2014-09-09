package eu.factorx.awac.dto.admin;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 5/09/14.
 */
public class BADLogDTO extends DTO {

    List<LogLineDTO> logLines = new ArrayList<>();

    public BADLogDTO() {
    }

    public List<LogLineDTO> getLogLines() {
        return logLines;
    }

    public void setLogLines(List<LogLineDTO> logLines) {
        this.logLines = logLines;
    }

    @Override
    public String toString() {
        return "BADLogDTO{" +
                "logLines=" + logLines +
                '}';
    }
}