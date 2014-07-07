package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.NotNull;


public class TestDTO extends DTO {

    @NotNull
    private String name;


    private List<? extends Object> resultList;

    public TestDTO(List<? extends Object> resultList) {
        super();
        this.resultList = resultList;
    }

    public TestDTO() {
        super();
    }

    public List<? extends Object> getResultList() {
        return resultList;
    }

    public void setResultList(List<? extends Object> resultList) {
        this.resultList = resultList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
