# gkeepapi-java
Unofficial Java client library for the Android Google Keep API

## Table of content
- [General info](#general-info)
- [Documentation](#documentation)
- [Download](#download)
- [Similar libraries](#similar-libraries)
- [Report Issues](#report-issues)
- [Contributing](#contributing)
- [License](#license)

## General info
The library can be used to access data from Google Keep as an Android device

```java
package example;

import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.NodeRequest;
import io.github.rukins.gkeepapi.model.NodeResponse;
import io.github.rukins.gkeepapi.model.node.Color;
import io.github.rukins.gkeepapi.model.node.nodeobject.ListItemNode;
import io.github.rukins.gkeepapi.model.node.nodeobject.NoteNode;
import io.github.rukins.gkeepapi.model.userinfo.Label;
import io.github.rukins.gpsoauth.Auth;
import io.github.rukins.gpsoauth.exception.AuthError;

public class App {
    public static void main(String[] args) throws AuthError {
        GKeepAPI gKeepAPI = new GKeepAPI("aas_et/**");

        NodeResponse fullData = gKeepAPI.getFullData();

        String noteNodeId = IdUtils.generateId();

        NoteNode noteNode = NoteNode.builder()
                .id(noteNodeId)
                .title("some title")
                .listItemNode(
                        ListItemNode.builder()
                                .id(IdUtils.generateId())
                                .parentId(noteNodeId)
                                .text("some text")
                                .build()
                )
                .build();

        Label label = Label.builder()
                .mainId(IdUtils.generateId())
                .name("some label")
                .build();

        NodeRequest nodeRequest = NodeRequestBuilder.builder()
                .createOrUpdateNoteNode(noteNode)
                .createLabel(label)
                .addLabelToNoteNode(noteNode, label)
                .pinNoteNode(noteNode)
                .createOrUpdateNoteNode(
                        NoteNode.builder()
                                .id(noteNode.getId())
                                .color(Color.RED)
                                .title("new title")
                                .build()
                )
                .build();

        NodeResponse nodeResponse = gKeepAPI.changes(nodeRequest);

        NodeResponse newState = NodeUtils.mergeNodeResponse(fullData, nodeResponse);

        NodeUtils.getAssembledNodeList(newState.getNodes()).forEach(System.out::println);
    }
}
```

## Documentation
*Will be available later*

## Download
*Will be available later*

## Similar libraries
- Python: https://github.com/kiwiz/gkeepapi

## Report Issues
In the [Issues](https://github.com/rukins/gkeepapi-java/issues) section you can suggest any improvements and report any bugs you find

## Contributing
This is an open-source project and all contributions are highly welcomed

## License
Released under [MIT License](LICENSE)