package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 29/09/14.
 */
public class FormsClosingDTO extends DTO {

    private Boolean closed;

    private Boolean closeable;

    public FormsClosingDTO() {
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Boolean getCloseable() {
        return closeable;
    }

    public void setCloseable(Boolean closeable) {
        this.closeable = closeable;
    }

    @Override
    public String toString() {
        return "FormsClosedDTO{" +
                "closed=" + closed +
                ", closeable=" + closeable +
                '}';
    }
}
