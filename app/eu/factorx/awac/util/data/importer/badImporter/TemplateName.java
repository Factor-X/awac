package eu.factorx.awac.util.data.importer.badImporter;

/**
 * Created by florian on 29/08/14.
 */
public enum TemplateName {

    BAD("files/template/bad.vm");

    private final String url;

    private TemplateName(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
