package eu.factorx.awac.models.email;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class EmailVerificationContent implements Serializable {

    protected String email;

    @Column(columnDefinition = "text")
    protected String content;

    protected String phoneNumber;

    public EmailVerificationContent() {
    }

    public EmailVerificationContent(String email, String content, String phoneNumber) {
        this.email = email;
        this.content = content;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "emailVerificationContent{" +
                "email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
