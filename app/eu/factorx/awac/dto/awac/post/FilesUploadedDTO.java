package eu.factorx.awac.dto.awac.post;


import eu.factorx.awac.dto.DTO;

public class FilesUploadedDTO extends DTO {

    private Long id;

    private String name;

    public FilesUploadedDTO() {
    }

    public FilesUploadedDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "FilesUploadedDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
