package eu.factorx.awac.models.data.file;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.forms.VerificationRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({ @NamedQuery(name = StoredFile.FIND_BY_STORED_NAME, query = "select q from StoredFile q where q.storedName in :storedName"),
})
public class StoredFile  extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_STORED_NAME = "StoredFile.findByStoredName";

    private String originalName;

    private String storedName;

    private Integer size;

    @ManyToOne(optional = false)
    private Account account;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mm_storedfile_organization",
            joinColumns  = @JoinColumn(name = "storedfile_id", referencedColumnName = "id"),
            inverseJoinColumns= @JoinColumn(name = "organization_id", referencedColumnName = "id"))
    private List<Organization> organizationList;


    public StoredFile() {
    }

    public StoredFile(String originalName, String storedName, Integer size, Account account) {
        this.originalName = originalName;
        this.storedName = storedName;
        this.size = size;
        this.account = account;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
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

    public void addOrganization(Organization organization) {
        if(this.organizationList==null){
            this.organizationList = new ArrayList<>();
        }
        this.organizationList.add(organization);
    }

    @Override
    public String toString() {
        return "StoredFile{" +
                super.toString()+
                "originalName='" + originalName + '\'' +
                ", storedName='" + storedName + '\'' +
                ", size=" + size +
                ", account=" + account +
                '}';
    }
}
