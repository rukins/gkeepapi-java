package io.github.rukins.gkeepapi.typeadapter;

import com.google.gson.*;
import io.github.rukins.gkeepapi.model.node.nodeobject.*;
import io.github.rukins.gkeepapi.model.node.*;

import java.lang.reflect.Type;

public class NodeTypeAdapter implements JsonSerializer<Node>, JsonDeserializer<Node>  {
    @Override
    public JsonElement serialize(Node node, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(node, node.getClass());
    }

    @Override
    public Node deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return switch (NodeType.valueOf(jsonElement.getAsJsonObject().get("type").getAsString())) {
            case NOTE -> jsonDeserializationContext.deserialize(jsonElement, NoteNode.class);
            case LIST -> jsonDeserializationContext.deserialize(jsonElement, ListNode.class);
            case LIST_ITEM -> jsonDeserializationContext.deserialize(jsonElement, ListItemNode.class);
            case BLOB -> jsonDeserializationContext.deserialize(jsonElement, BlobNode.class);
        };
    }
}
