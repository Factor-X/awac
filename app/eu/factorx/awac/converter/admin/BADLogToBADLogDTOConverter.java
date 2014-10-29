package eu.factorx.awac.converter.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import eu.factorx.awac.dto.admin.BADLogDTO;
import eu.factorx.awac.dto.admin.LogLineDTO;
import eu.factorx.awac.util.data.importer.badImporter.BADLog;

/**
 * Created by florian on 5/09/14.
 */
public class BADLogToBADLogDTOConverter implements Converter<BADLog, BADLogDTO> {

    @Override
    public BADLogDTO convert(BADLog badLog) {
        BADLogDTO badLogDTO = new BADLogDTO();


        List<LogLineDTO> logLineDTOs = new ArrayList<>();

        for (Map.Entry<Integer, BADLog.LogLine> logLine : badLog.getLogLines().entrySet()) {

            LogLineDTO logLineDTO = new LogLineDTO();

            HashMap<String, List<String>> map = new HashMap<>();

            logLineDTO.setName(logLine.getValue().getId());
            logLineDTO.setLogCategory(logLine.getValue().getLogCat().toString());

            for (Map.Entry<BADLog.LogType, List<String>> entry : logLine.getValue().getMessages().entrySet()) {


                map.put(entry.getKey().toString(), entry.getValue());
            }

            logLineDTO.setLineNb(logLine.getKey());

            logLineDTO.setMessages(map);

            logLineDTOs.add(logLineDTO);
        }


        badLogDTO.setLogLines(logLineDTOs);


        return badLogDTO;
    }
}
