package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.get.ScopeDTO;
import eu.factorx.awac.dto.validation.annotations.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 6/07/14.
 */
public class ProductDTO extends ScopeDTO {

    private Long id;

    @Size(min = 1, max = 255)
    private String name;

    private List<PeriodDTO> listPeriodAvailable;

    private List<PersonDTO> listPersons;

    public ProductDTO() {
    }

    public ProductDTO(String name) {
        this.name = name;
    }

    public List<PeriodDTO> getListPeriodAvailable() {
        return listPeriodAvailable;
    }

    public void setListPeriodAvailable(List<PeriodDTO> listPeriodAvailable) {
        this.listPeriodAvailable = listPeriodAvailable;
    }

    public List<PersonDTO> getListPersons() {
        return listPersons;
    }

    public void setListPersons(List<PersonDTO> listPersons) {
        this.listPersons = listPersons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }



    /***********************
     *  NOT AUTO_GENERATED !!
     *********************/

    public void addPeriodAvailable(PeriodDTO periodDTO){
        if(this.listPeriodAvailable==null){
            this.listPeriodAvailable = new ArrayList<>();
        }
        this.listPeriodAvailable.add(periodDTO);
    }


    public void addPerson(PersonDTO personDTO) {
        if(this.listPersons==null){
            this.listPersons= new ArrayList<>();
        }
        this.listPersons.add(personDTO);
    }
}
