package io.github.rukins.gkeepapi.model.node.nodeentity.annotation;

import java.util.List;
import java.util.Objects;

public class AnnotationsGroup {
    private String kind;

    private List<Annotation> annotations;

    public AnnotationsGroup(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public String getKind() {
        return kind;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        return "AnnotationsGroup{" +
                "kind='" + kind + '\'' +
                ", annotations=" + annotations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnnotationsGroup that)) return false;
        return Objects.equals(annotations, that.annotations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotations);
    }
}
