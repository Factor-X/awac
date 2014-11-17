/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */
package eu.factorx.awac.models.association;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Product;

import javax.persistence.*;

// import for JAXB annotations -- JAXB stack

@Entity
@Table(name = "account_product_association", uniqueConstraints =@UniqueConstraint(columnNames = {"product_id", "account_id"}))
@NamedQueries({
        @NamedQuery(name = AccountProductAssociation.FIND_BY_PRODUCT, query = "select a from AccountProductAssociation a where a.product.id = :product_id"),
        @NamedQuery(name = AccountProductAssociation.FIND_BY_ACCOUNT, query = "select a from AccountProductAssociation a where a.account = :account"),
})
public class AccountProductAssociation extends AuditedAbstractEntity {

    /**
     * :identifier = ...
     */
    public static final String FIND_BY_PRODUCT = "AccountProductAssociation.findByProduct";
    public static final String FIND_BY_ACCOUNT = "AccountProductAssociation.findByAccount";

    private static final long serialVersionUID = 1L;


    @OneToOne
    private Product product;

    @OneToOne
    private Account account;


    public AccountProductAssociation() {
    }

    public AccountProductAssociation(Product product, Account account) {
        this.product = product;
        this.account = account;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "AccountProductAssociation{" +
                "product=" + product +
                ", account=" + account +
                '}';
    }
}
