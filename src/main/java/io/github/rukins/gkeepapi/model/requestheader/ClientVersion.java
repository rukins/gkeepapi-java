package io.github.rukins.gkeepapi.model.requestheader;

public class ClientVersion {
    public static final ClientVersion DEFAULT_CLIENT_VERSION = new ClientVersion(
            62, 5, 23, "0"
    );

    private Integer build;

    private Integer major;

    private Integer minor;

    private String revision;

    public ClientVersion(Integer build, Integer major, Integer minor, String revision) {
        this.build = build;
        this.major = major;
        this.minor = minor;
        this.revision = revision;
    }

    public Integer getBuild() {
        return build;
    }

    public void setBuild(Integer build) {
        this.build = build;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Override
    public String toString() {
        return "ClientVersion{" +
                "build=" + build +
                ", major=" + major +
                ", minor=" + minor +
                ", revision='" + revision + '\'' +
                '}';
    }
}
