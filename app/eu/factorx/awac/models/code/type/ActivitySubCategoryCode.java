
package eu.factorx.awac.models.code.type;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "activitysubcategory"))})
public class ActivitySubCategoryCode extends Code {

    private static final long serialVersionUID = 1L;

    protected ActivitySubCategoryCode() {
        super(CodeList.ActivitySubCategory);
    }

    public ActivitySubCategoryCode(String key) {
        this();
        this.key = key;
    }
public static final ActivitySubCategoryCode ENERGIE_FOSSILE = new ActivitySubCategoryCode("1");
public static final ActivitySubCategoryCode ELECTRICITE = new ActivitySubCategoryCode("2");
public static final ActivitySubCategoryCode VAPEUR = new ActivitySubCategoryCode("3");
public static final ActivitySubCategoryCode REJET = new ActivitySubCategoryCode("4");
public static final ActivitySubCategoryCode FROID = new ActivitySubCategoryCode("5");
public static final ActivitySubCategoryCode AMONT = new ActivitySubCategoryCode("6");
public static final ActivitySubCategoryCode DPRO = new ActivitySubCategoryCode("7");
public static final ActivitySubCategoryCode DDT = new ActivitySubCategoryCode("8");
public static final ActivitySubCategoryCode DPRO_DVIS = new ActivitySubCategoryCode("9");
public static final ActivitySubCategoryCode MATIERE = new ActivitySubCategoryCode("10");
public static final ActivitySubCategoryCode FACTEUR_PROPRE = new ActivitySubCategoryCode("11");
public static final ActivitySubCategoryCode AVAL = new ActivitySubCategoryCode("12");}