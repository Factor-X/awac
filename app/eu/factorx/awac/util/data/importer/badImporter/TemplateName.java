package eu.factorx.awac.util.data.importer.badImporter;

/**
 * Created by florian on 29/08/14.
 */
public enum TemplateName {

    BAD_ENTERPRISE("public/template/bad.vm", "app/eu/factorx/awac/service/knowledge/activity/contributor/enterprise/","eu.factorx.awac.service.knowledge.activity.contributor.enterprise"),
    BAD_MUNICIPALITY("public/template/bad.vm", "app/eu/factorx/awac/service/knowledge/activity/contributor/municipality/","eu.factorx.awac.service.knowledge.activity.contributor.municipality"),
    //BAD("public/template/bad.vm", "tmp/bad/"),
    BAD_TEST("public/template/badTest.vm", "test/eu/factorx/awac/buisness/bad/impl/","eu.factorx.awac.buisness.bad.impl"),
    BAD_TEST_MAIN("public/template/badTestMain.vm", "test/eu/factorx/awac/buisness/bad/","eu.factorx.awac.buisness.bad");

    private final String url;
    private final String path;
    private final String packageString;

    TemplateName(String url, String path, String packageString) {
        this.url = url;
        this.path = path;
        this.packageString = packageString;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getPackageString() {
        return packageString;
    }
}
