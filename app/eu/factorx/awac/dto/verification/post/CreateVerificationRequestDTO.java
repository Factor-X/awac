package eu.factorx.awac.dto.verification.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

/**
 * Created by florian on 8/10/14.
 */
public class CreateVerificationRequestDTO extends DTO{

    @NotNull
    @Pattern(regexp = Pattern.PASSWORD, message = "Password between 5 and 20 letters")
    private String password;

    @NotNull
    private String periodKey;

    @NotNull
    private Long scopeId;

    @NotNull
    private String email;

    private String comment;

    private String phoneNumber;


    public CreateVerificationRequestDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "CreateVerificationRequestDTO{" +
                "password='" + password + '\'' +
                ", periodKey='" + periodKey + '\'' +
                ", scopeId=" + scopeId +
                ", email='" + email + '\'' +
                ", comment='" + comment + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
