package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 21/10/14.
 */
public class ListDriverDTO extends DTO {

    private List<DriverDTO> list;

    public ListDriverDTO() {
    }

    public ListDriverDTO(List<DriverDTO> list) {
        this.list = list;
    }

    public List<DriverDTO> getList() {
        return list;
    }

    public void setList(List<DriverDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListDriverDTO{" +
                "list=" + list +
                '}';
    }
}
