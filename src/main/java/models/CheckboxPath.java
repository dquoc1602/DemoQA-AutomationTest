package models;

public class CheckboxPath {
    private String nodeKey;
    private String path;

    public CheckboxPath(String nodeKey, String path) {
        this.nodeKey = nodeKey;
        this.path = path;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return nodeKey + " -> " + path;
    }
}
