package io.github.rukins.gkeepapi.model.node.nodeentity.annotation;

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
}
