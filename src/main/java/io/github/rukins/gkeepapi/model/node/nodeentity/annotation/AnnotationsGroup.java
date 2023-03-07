package io.github.rukins.gkeepapi.model.node.nodeentity.annotation;

import java.util.List;

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
}
