package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.InterfaceTypeCode;

/**
 * Created by florian on 29/08/14.
 */
public enum TemplateName {

    BAD_ENTERPRISE("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/enterprise/",
            "eu.factorx.awac.service.knowledge.activity.contributor.enterprise",
            //test
            "public/template/badTestMainSite.vm",
            "public/template/badTestSite.vm",
            "test/eu/factorx/awac/buisness/bad/enterprise/",
            "eu.factorx.awac.buisness.bad.enterprise"
            ,"user1",
            InterfaceTypeCode.ENTERPRISE),

    BAD_MUNICIPALITY("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/municipality/",
            "eu.factorx.awac.service.knowledge.activity.contributor.municipality",
            //test
            "public/template/badTestMainOrganization.vm",
            "public/template/badTestOrganizaiton.vm",
            "test/eu/factorx/awac/buisness/bad/municipality/",
            "eu.factorx.awac.buisness.bad.municipality"
            ,"user3",
            InterfaceTypeCode.MUNICIPALITY),

    BAD_LITTLEEMITTER("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/littleemitter/",
            "eu.factorx.awac.service.knowledge.activity.contributor.littleemitter",
            //test
            "public/template/badTestMainOrganization.vm",
            "public/template/badTestOrganization.vm",
            "test/eu/factorx/awac/buisness/bad/littleemitter/",
            "eu.factorx.awac.buisness.bad.littleemitter"
            ,"user20",
            InterfaceTypeCode.LITTLEEMITTER),

    BAD_HOUSEHOLD("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/household/",
            "eu.factorx.awac.service.knowledge.activity.contributor.household",
            //test
            "public/template/badTestMainOrganization.vm",
            "public/template/badTestOrganization.vm",
            "test/eu/factorx/awac/buisness/bad/household/",
            "eu.factorx.awac.buisness.bad.household"
            ,"user30",
            InterfaceTypeCode.HOUSEHOLD),

    BAD_EVENT("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/event/",
            "eu.factorx.awac.service.knowledge.activity.contributor.event",
            //test
            "public/template/badTestMainProduct.vm",
            "public/template/badTestProduct.vm",
            "test/eu/factorx/awac/buisness/bad/event/",
            "eu.factorx.awac.buisness.bad.event"
            ,"user40",
            InterfaceTypeCode.EVENT);

    private final String template;
    private final String path;
    private final String packageString;
    private final String testMainTemplate;
    private final String testTemplate;
    private final String testPath;
    private final String packageTest;
    private String userIdentifier;
    private InterfaceTypeCode interfaceTypeCode;

    TemplateName(String template, String path, String packageString, String testMainTemplate, String testTemplate, String testPath, String packageTest,String userIdentifier,InterfaceTypeCode interfaceTypeCode) {
        this.template = template;
        this.path = path;
        this.packageString = packageString;
        this.testTemplate = testTemplate;
        this.testPath = testPath;
        this.packageTest = packageTest;
        this.testMainTemplate = testMainTemplate;
        this.userIdentifier=userIdentifier;
        this.interfaceTypeCode=interfaceTypeCode;
    }

    public String getTestMainTemplate() {
        return testMainTemplate;
    }

    public String getTemplate() {
        return template;
    }

    public String getPath() {
        return path;
    }

    public String getPackageString() {
        return packageString;
    }

    public String getTestTemplate() {
        return testTemplate;
    }

    public String getTestPath() {
        return testPath;
    }

    public String getPackageTest() {
        return packageTest;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public InterfaceTypeCode getInterfaceTypeCode() {
        return interfaceTypeCode;
    }
}
