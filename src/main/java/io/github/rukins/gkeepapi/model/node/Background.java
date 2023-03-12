package io.github.rukins.gkeepapi.model.node;

import java.util.Objects;

public class Background {
    private String name;

    private String origin;

    public Background(String name, String origin) {
        this.name = name;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "Background{" +
                "name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Background that)) return false;
        return Objects.equals(name, that.name)
                && Objects.equals(origin, that.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, origin);
    }
}
