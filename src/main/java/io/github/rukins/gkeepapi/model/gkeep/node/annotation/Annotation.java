package io.github.rukins.gkeepapi.model.gkeep.node.annotation;

import java.util.Objects;

public class Annotation {
    private String id;

    private TopicCategory topicCategory;

    public Annotation(String id, TopicCategory topicCategory) {
        this.id = id;
        this.topicCategory = topicCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TopicCategory getTopicCategory() {
        return topicCategory;
    }

    public void setTopicCategory(TopicCategory topicCategory) {
        this.topicCategory = topicCategory;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "id='" + id + '\'' +
                ", topicCategory=" + topicCategory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Annotation that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(topicCategory, that.topicCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topicCategory);
    }
}
