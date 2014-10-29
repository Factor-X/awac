package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 29/07/14.
 */
public class ListPeriodsDTO extends DTO{

    List<PeriodDTO> periodDTOList = new ArrayList<>();

    public ListPeriodsDTO() {
    }

    public ListPeriodsDTO(List<PeriodDTO> periodDTOList) {
        this.periodDTOList = periodDTOList;
    }

    public List<PeriodDTO> getPeriodDTOList() {
        return periodDTOList;
    }

    public void setPeriodDTOList(List<PeriodDTO> periodDTOList) {
        this.periodDTOList = periodDTOList;
    }

    @Override
    public String toString() {
        return "ListPeriodsDTO{" +
                "periodDTOList=" + periodDTOList +
                '}';
    }
}
