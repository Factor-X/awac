package eu.factorx.awac.dto.awac.shared;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.dto.DTO;

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
