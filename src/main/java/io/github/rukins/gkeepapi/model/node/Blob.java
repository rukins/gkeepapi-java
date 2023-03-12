package io.github.rukins.gkeepapi.model.node;

public class Blob {
    private String kind;
    private String type;
    private String mimetype;

    public Blob(String type, String mimetype) {
        this.type = type;
        this.mimetype = mimetype;
    }

    public String getKind() {
        return kind;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    @Override
    public String toString() {
        return "Blob{" +
                "type='" + type + '\'' +
                ", mimetype='" + mimetype + '\'' +
                '}';
    }
}
