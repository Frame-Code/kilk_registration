package service;

/**
 * @author Daniel Mora Cantillo
 */
public abstract class DocumentGenerator {
    protected String path;

    public DocumentGenerator(String path) {
        this.path = path;
    }

    public abstract void generate(String content);
}
