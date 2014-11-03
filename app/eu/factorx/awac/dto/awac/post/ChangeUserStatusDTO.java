package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 3/11/14.
 */
public class ChangeUserStatusDTO extends DTO{

    private String identifier;

    private String newStatus;

    public ChangeUserStatusDTO() {
    }

    public ChangeUserStatusDTO(String identifier, String newStatus) {
        this.identifier = identifier;
        this.newStatus = newStatus;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    @Override
    public String toString() {
        return "ChangeUserStatusDTO{" +
                "identifier='" + identifier + '\'' +
                ", newStatus='" + newStatus + '\'' +
                '}';
    }
}
