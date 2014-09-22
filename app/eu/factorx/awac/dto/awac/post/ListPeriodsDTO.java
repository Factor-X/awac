package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 19/09/14.
 */
public class ListPeriodsDTO extends DTO{

    private List<PeriodDTO> periodsList;

    public ListPeriodsDTO() {
    }

    public List<PeriodDTO> getPeriodsList() {
        return periodsList;
    }

    public void setPeriodsList(List<PeriodDTO> periodsList) {
        this.periodsList = periodsList;
    }

    @Override
    public String toString() {
        return "ListPeriodsDTO{" +
                "periodsList=" + periodsList +
                '}';
    }

    public void addPeriod(PeriodDTO period) {
        if(periodsList==null){
            periodsList = new ArrayList<>();
        }
        periodsList.add(period);
    }
}
