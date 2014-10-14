package eu.factorx.awac.dto.awac.shared;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.verification.get.VerificationRequestDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 9/10/14.
 */
public class ListDTO<T extends DTO> extends DTO {

    private List<T> list;

    public ListDTO() {
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListDTO{" +
                "list=" + list +
                '}';
    }

    public void add(T dto) {
        if(list==null){
            list = new ArrayList<>();
        }
        list.add(dto);

    }
}
