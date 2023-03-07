package io.github.rukins.gkeepapi.model.node.nodeentity.annotation;

public class TopicCategory {
    private String category;

    public TopicCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "TopicCategory{" +
                "category='" + category + '\'' +
                '}';
    }
}
