package io.github.rukins.gkeepapi.config.typeadapter;

import com.google.gson.*;
import io.github.rukins.gkeepapi.model.gkeep.node.NodeType;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.*;

import java.lang.reflect.Type;

public class NodeTypeAdapter implements JsonSerializer<AbstractNode>, JsonDeserializer<AbstractNode>  {
    @Override
    public JsonElement serialize(AbstractNode node, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(node, node.getClass());
    }

    @Override
    public AbstractNode deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!jsonElement.getAsJsonObject().has("type"))
            return jsonDeserializationContext.deserialize(jsonElement, Node.class);

        return switch (NodeType.valueOf(jsonElement.getAsJsonObject().get("type").getAsString())) {
            case NOTE -> jsonDeserializationContext.deserialize(jsonElement, NoteNode.class);
            case LIST -> jsonDeserializationContext.deserialize(jsonElement, ListNode.class);
            case LIST_ITEM -> jsonDeserializationContext.deserialize(jsonElement, ListItemNode.class);
            case BLOB -> jsonDeserializationContext.deserialize(jsonElement, BlobNode.class);
        };
    }
}
