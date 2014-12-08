package eu.factorx.awac.util.data.importer.badImporter;

/**
 * Created by florian on 29/08/14.
 */
public enum TemplateName {

    BAD_ENTERPRISE("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/enterprise/",
            "eu.factorx.awac.service.knowledge.activity.contributor.enterprise",
            //test
            "public/template/badTest.vm",
            "test/eu/factorx/awac/buisness/bad/enterprise/",
            "eu.factorx.awac.buisness.bad.enterprise"),

    BAD_MUNICIPALITY("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/municipality/",
            "eu.factorx.awac.service.knowledge.activity.contributor.municipality",
            //test
            "public/template/badTest.vm",
            "test/eu/factorx/awac/buisness/bad/municipality/",
            "eu.factorx.awac.buisness.bad.municipality"),

    BAD_LITTLE_EMITTER("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/littleEmitter/",
            "eu.factorx.awac.service.knowledge.activity.contributor.littleEmitter",
            //test
            "public/template/badTest.vm",
            "test/eu/factorx/awac/buisness/bad/littleEmitter/",
            "eu.factorx.awac.buisness.bad.littleEmitter"),

    BAD_HOUSEHOLD("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/household/",
            "eu.factorx.awac.service.knowledge.activity.contributor.household",
            //test
            "public/template/badTest.vm",
            "test/eu/factorx/awac/buisness/bad/household/",
            "eu.factorx.awac.buisness.bad.household"),

    BAD_EVENT("public/template/bad.vm",
            "app/eu/factorx/awac/service/knowledge/activity/contributor/event/",
            "eu.factorx.awac.service.knowledge.activity.contributor.event",
            //test
            "public/template/badTest.vm",
            "test/eu/factorx/awac/buisness/bad/event/",
            "eu.factorx.awac.buisness.bad.event");

    private final String template;
    private final String path;
    private final String packageString;
    private final String testTemplate;
    private final String testPath;
    private final String packageTest;

    TemplateName(String template, String path, String packageString, String testTemplate, String testPath, String packageTest) {
        this.template = template;
        this.path = path;
        this.packageString = packageString;
        this.testTemplate = testTemplate;
        this.testPath = testPath;
        this.packageTest = packageTest;
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
}
