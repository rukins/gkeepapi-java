# gkeepapi-java
Unofficial Java client library for the Android Google Keep API

***If you have an Enterprise account, you can use official [Google Keep API](https://developers.google.com/keep)***

## Table of content
- [General info](#general-info)
- [Next improvements](#next-improvements)
- [Documentation](#documentation)
- [Download](#download)
- [Similar libraries](#similar-libraries)
- [Report Issues](#report-issues)
- [Contributing](#contributing)
- [License](#license)

## General info
The library can be used to access data from Google Keep as an Android device

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.NodeRequest;
import io.github.rukins.gkeepapi.model.NodeResponse;
import io.github.rukins.gkeepapi.model.node.Color;
import io.github.rukins.gkeepapi.model.node.nodeobject.ListItemNode;
import io.github.rukins.gkeepapi.model.node.nodeobject.NoteNode;
import io.github.rukins.gkeepapi.model.userinfo.Label;
import io.github.rukins.gkeepapi.utils.NodeUtils;
import io.github.rukins.gpsoauth.exception.AuthError;

public class App {
    public static void main(String[] args) throws AuthError {
        GKeepAPI gKeepAPI = new GKeepAPI("aas_et/**");

        NodeResponse fullData = gKeepAPI.getFullData();

        NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();

        NoteNode noteNode = nodeRequestBuilder.createNoteNode(
                NoteNode.builder()
                        .title("some title")
                        .listItemNode(
                                ListItemNode.builder()
                                        .text("some text")
                                        .build()
                        )
                        .build()
        );

        Label label = nodeRequestBuilder.createOrUpdateLabel(
                Label.builder()
                        .mainId(IdUtils.generateId())
                        .name("some label")
                        .build()
        );

        noteNode = nodeRequestBuilder.addLabelToNoteNode(noteNode, label);
        noteNode = nodeRequestBuilder.pinNoteNode(noteNode);
        
        noteNode.setColor(Color.RED);
        noteNode.setTitle("new title");
        noteNode.getListItemNode().setText("new text");
        
        noteNode = nodeRequestBuilder.createOrUpdateNoteNode(noteNode);
        
        System.out.println(noteNode);

        NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());

        NodeResponse newState = NodeUtils.mergeNodeResponse(fullData, nodeResponse);

        NodeUtils.getAssembledNodeList(newState.getNodes()).forEach(System.out::println);
    }
}
```

## Next improvements
In the current version of the library, basic manipulations with nodes and labels are available, 
but later uploading and downloading blobs will be also added.

*You can report any issues with improvements or modifications*

## Documentation
### *Available [here](DOCS.md)*

## Download
### *Available [here](https://mvnrepository.com/artifact/io.github.rukins/gkeepapi)*
#### Maven
```xml
<dependency>
    <groupId>io.github.rukins</groupId>
    <artifactId>gkeepapi</artifactId>
</dependency>
```
#### Gradle
```groovy
implementation group: 'io.github.rukins', name: 'gkeepapi'
```
#### Gradle (Kotlin)
```kotlin
implementation("io.github.rukins:gkeepapi")
```

## Similar libraries
- Python: https://github.com/kiwiz/gkeepapi

## Report Issues
In the [Issues](https://github.com/rukins/gkeepapi-java/issues) section you can suggest any improvements and report any bugs you find

## Contributing
This is an open-source project and all contributions are highly welcomed

## License
Released under [MIT License](LICENSE)