package eu.factorx.awac.util.data.importer.badImporter;

/**
 * Created by florian on 29/08/14.
 */
public enum TemplateName {

    //BAD("public/template/bad.vm", "app/eu/factorx/awac/service/knowledge/activity/contributor/tps/"),
    BAD("public/template/bad.vm", "tmp/bad/"),
    BAD_TEST("public/template/badTest.vm", "test/eu/factorx/awac/buisness/bad/impl/");

    private final String url;
    private final String targetPath;

    TemplateName(String url, String targetPath) {
        this.url = url;
        this.targetPath = targetPath;
    }

    public String getUrl() {
        return url;
    }

    public String getTargetPath() {
        return targetPath;
    }
}
