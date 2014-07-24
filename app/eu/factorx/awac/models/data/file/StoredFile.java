package eu.factorx.awac.models.data.file;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;

import javax.persistence.*;

@Entity
public class StoredFile  extends AuditedAbstractEntity {

    private String originalName;

    private String storedName;

    private Integer size;

    @ManyToOne(optional = false)
    private Account account;

    public StoredFile() {
    }

    public StoredFile(String originalName, String storedName, Integer size, Account account) {
        this.originalName = originalName;
        this.storedName = storedName;
        this.size = size;
        this.account = account;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "StoredFile{" +
                "originalName='" + originalName + '\'' +
                ", storedName='" + storedName + '\'' +
                ", size=" + size +
                ", account=" + account +
                '}';
    }
}