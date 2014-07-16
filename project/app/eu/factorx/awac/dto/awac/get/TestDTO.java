package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;
import eu.factorx.awac.dto.validation.annotations.Validate;


public class TestDTO extends DTO {

    @NotNull
    private String name;

    @Pattern(regexp=Pattern.EMAIL, message ="mauvais email ")
    private String streetNumber;

    private List<String> roles;

    @Validate
    private List<SmallTestDTO> smalls;


    private List<? extends Object> resultList;

    public TestDTO() {
        super();
        this.roles = new ArrayList<>();
        this.smalls = new ArrayList<>();
    }

    public TestDTO(List<? extends Object> resultList) {
        super();
        this.resultList = resultList;
        this.roles = new ArrayList<>();
        this.smalls = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<SmallTestDTO> getSmalls() {
        return smalls;
    }

    public void setSmalls(List<SmallTestDTO> smalls) {
        this.smalls = smalls;
    }

    public List<? extends Object> getResultList() {
        return resultList;
    }

    public void setResultList(List<? extends Object> resultList) {
        this.resultList = resultList;
    }



}
