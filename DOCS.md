# Welcome to the gkeepapi-java's documentation!

## Table of content
- [Client](#client)
    - [Master token](#master-token)
- [Building NodeRequest](#building-noderequest)
- [Nodes](#nodes)
    - [Node types](#node-types)
    - [Creating](#node-creating)
    - [Updating](#node-updating)
    - [Adding label](#node-adding-label)
    - [Trashing, restoring, deleting](#node-trashing-restoring-deleting)
    - [Pinning and archiving](#node-pinning-archiving)
- [Media (Blobs)](#blobs)
    - [Uploading image](#uploading-image)
    - [Pulling image from the server](#pulling-image-from-the-server)
- [Labels](#lables)
    - [Creating](#label-creating)
    - [Updating](#label-updating)
    - [Deleting](#label-deleting)
- [NodeUtils](#nodeutils)
    - [Manipulations with list of nodes](#manipulations-with-list-of-nodes)
    - [Merging](#merging)

## Client
`GKeepAPI` is a client for the Android Google Keep API.
It manages the receiving of access token and the update of current version.

You can create it in two ways:
1. with `masterToken`
2. with `masterToken` and `version`

Available methods:
1. `NodeResponse changes(NodeRequest nodeRequest)` \
Returns response from the server with changes that may have been updated by other devices and that were provided in `nodeRequest`
2. `NodeResponse changes()` \
Returns response from the server with changes that may have been updated by other devices. \
In case when the current version is null or incorrect, this method returns all nodes, labels and other data from the server. \
**it is recommended to first execute the method to get current version and save it somewhere**
3. `ImageBlob uploadImage(byte[] imageBytes, String blobServerId, String nodeServerId)` \
Returns `ImageBlob` where you can see info about uploaded image
4. `ImageData getImageData(String blobServerId, String nodeServerId)` \
Returns data of requested image
5. `String getCurrentVersion()` \
Returns current version
6. `void setCurrentVersion(String currentVersion)` \
Sets current version

### Master token
To get access to the Google keep API using this library, you need to receive master token. \
You can use [gpsoath-java library](https://github.com/rukins/gpsoauth-java). \
You just need an authentication token that can be obtained by logging in your Google account [here](https://accounts.google.com/EmbeddedSetup). (*How to receive a token is described in more detailed on GitHub Page of [gpsoath-java library](https://github.com/rukins/gpsoauth-java)*)

**better save a master token somewhere, it never expires**

Simple code:
```java
import io.github.rukins.gpsoauth.Auth;
import io.github.rukins.gpsoauth.exception.AuthError;
import io.github.rukins.gpsoauth.model.MasterToken;
import io.github.rukins.gpsoauth.MasterTokenRequestParams;

public class Main {
  public static void main(String[] args) throws AuthError {
    Auth auth = new Auth();

    MasterTokenRequestParams masterTokenRequestParams = MasterTokenRequestParams
            .withDefaultValues()
            .token("oauth2_4/***")
            .build();

    MasterToken masterToken = auth.getMasterToken(masterTokenRequestParams);
    
    System.out.println(masterToken.getMasterToken());
  }
}
```

## Building NodeRequest
```NodeRequest``` is what you will send to the server in the body.

To build it you can use ```Builder``` in the ```NodeRequest``` class what is not recommended. \
Better use ```NodeRequestBuilder``` class.
With this builder you can manipulate with nodes and labels more easily.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.gkeep.NodeRequest;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;

public class Main {
  public static void main(String[] args) {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();
    
    // do something

    NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());
  }
}
```

## Nodes

### Node types
In this implementation of the API each type corresponds to the following objects:
1. ```NoteNode``` - *NodeType.NOTE*
2. ```ListNode``` - *NodeType.LIST*
3. ```ListItemNode``` - *NodeType.LIST_ITEM*
4. ```BlobNode``` - *NodeType.BLOB*

They all extend abstract class ```Node```.

Each object can be built using Builder available in the object

*A node can be cast to the type it corresponds to, without any problem*

### Creating <a id="node-creating" />
New NOTEs are created with the `createNoteNode(NoteNode noteNode)` and `createOrUpdateNoteNode(NoteNode noteNode)` methods of `NodeRequestBuilder`.

It is better to add `listItemNode` to the `NoteNode` object (if you create a node with the `createOrUpdateNoteNode(NoteNode noteNode)` method), 
even if the `text` is empty, because it can cause problems in Android app 
(you will not be able to update the note text). \
The `createNoteNode(NoteNode noteNode)` method creates `listItemNode` if it is null and generates `id` automatically.

New LISTs are created with the `createListNode(ListNode listNode)` and `createOrUpdateListNode(ListNode listNode)` methods.

The `listItemNodes` are items that will be added to the list. \
The `createListNode(ListNode listNode)` method generates `id` automatically.

Sorting is available with `sortValue` parameter.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.ListItemNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.ListNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.NoteNode;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();

    NoteNode noteNode = nodeRequestBuilder.createOrUpdateNoteNode(
            NoteNode.builder()
                    .id(IdUtils.generateId())
                    .title("some note")
                    .listItemNode(
                            ListItemNode.builder()
                                    .id(IdUtils.generateId())
                                    .text("some text")
                                    .build()
                    )
                    .build()
    );

    ListNode listNode = nodeRequestBuilder.createListNode(
            ListNode.builder()
                    .title("some list")
                    .listItemNodes(
                            List.of(
                                    ListItemNode.builder()
                                            .text("unchecked")
                                            .build(),
                                    ListItemNode.builder()
                                            .text("checked")
                                            .checked(true)
                                            .build()
                            )
                    )
                    .build()
    );

    NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());
  }
}
```

### Updating <a id="node-updating" />
NOTEs are updated with the `createOrUpdateNoteNode(NoteNode noteNode)` method of `NodeRequestBuilder`.

LISTs are updated with the `createOrUpdateListNode(ListNode listNode)` method.

The `parentId` of child nodes is added automatically.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.node.Color;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.ListItemNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.ListNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.NoteNode;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();

    NoteNode noteNode = nodeRequestBuilder.createOrUpdateNoteNode(
            NoteNode.builder()
                    .id("noteNodeId")
                    .title("new title")
                    .archived(true)
                    .listItemNode(
                            ListItemNode.builder()
                                    .id("listItemId1")
                                    .text("new text")
                                    .build()
                    )
                    .build()
    );

    ListNode listNode = nodeRequestBuilder.createOrUpdateListNode(
            ListNode.builder()
                    .id("listId")
                    .title("new title")
                    .color(Color.RED)
                    .listItemNodes(
                            List.of(
                                    ListItemNode.builder()
                                            .id("listItemId2")
                                            .text("unchecked")
                                            .build(),
                                    ListItemNode.builder()
                                            .id("listItemId3")
                                            .text("checked")
                                            .checked(true)
                                            .build()
                            )
                    )
                    .build()
    );

    NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());
  }
}
```

### Adding label <a id="node-adding-label" />
You can add label to a node with `addLabelToNoteNode(NoteNode noteNode, Label label)` and `addLabelToListNode(ListNode listNode, Label label)` methods.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.ListNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.NoteNode;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.Label;

public class Main {
  public static void main(String[] args) {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();

    Label label = Label.builder()
            .mainId("some_id_of_existing_label")
            .build();

    NoteNode noteNode = nodeRequestBuilder.addLabelToNoteNode(
            NoteNode.builder()
                    .id("noteNodeId")
                    .build(),
            label
    );

    ListNode listNode = nodeRequestBuilder.addLabelToListNode(
            ListNode.builder()
                    .id("listId")
                    .build(),
            label
    );

    NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());
  }
}
```

### Trashing, restoring, deleting <a id="node-trashing-restoring-deleting" />
To trash, restore or delete forever `NoteNode`, `ListNode` or `ListItemNode` in `ListNode`,
use `trashNode(Node node)`, `restoreNode(Node node)` or `deleteNode(Node node)`.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.ListNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.NoteNode;

public class Main {
  public static void main(String[] args) {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();

    NoteNode noteNode = (NoteNode) nodeRequestBuilder.deleteNode(
            NoteNode.builder()
                    .id("noteNodeId")
                    .build()
    );

    ListNode listNode = (ListNode) nodeRequestBuilder.trashNode(
            ListNode.builder()
                    .id("listId")
                    .build()
    );

    NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());
  }
}
```

### Pinning and archiving <a id="node-pinning-archiving" />
NOTEs are pinned or unpinned with `pinNoteNode(NoteNode noteNode)` and `unpinNoteNode(NoteNode noteNode)` methods.
LISTs are pinned or unpinned with `pinListNode(ListNode listNode)` and `unpinListNode(ListNode listNode)` methods.

NOTEs are archived or unarchived with `archiveNoteNode(NoteNode noteNode)` and `unarchiveNoteNode(NoteNode noteNode)` methods.
LISTs are archived or unarchived with `archiveListNode(ListNode listNode)` and `unarchiveListNode(ListNode listNode)` methods.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.ListNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.NoteNode;

public class Main {
  public static void main(String[] args) {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();
    
    NoteNode noteNode = nodeRequestBuilder.pinNoteNode(
            NoteNode.builder()
                    .id("noteNodeId")
                    .build()
    );

    ListNode listNode = nodeRequestBuilder.archiveListNode(
            ListNode.builder()
                    .id("listId")
                    .build()
    );

    NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());
  }
}
```

## Media (Blobs) <a id="blobs" />
The uploading images and pulling it from the server are available.
You can do it just using `uploadImage(byte[] imageBytes, String blobServerId, String nodeServerId)` 
and `getImageData(String blobServerId, String nodeServerId)` methods of `GKeepAPI`.

### Uploading image
First you have to get image data using `ImageUtils.getImageData(File image)` method, 
create `NoteNode` or `ListNode` with `BlobNode` passing there received data and upload the image.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.model.gkeep.node.NodeType;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.ExtractionStatus;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.ImageBlob;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.BlobNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.ListItemNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.Node;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.NoteNode;
import io.github.rukins.gkeepapi.model.image.ImageData;
import io.github.rukins.gkeepapi.utils.ImageUtils;
import io.github.rukins.gkeepapi.utils.NodeUtils;
import io.github.rukins.gpsoauth.exception.AuthError;

import java.io.File;
import java.util.List;

public class Main { 
  public static void main(String[] args) throws Exception {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    ImageData imageData = ImageUtils.getImageData(new File("/path/to/dir/some.jpg"));

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();

    NoteNode noteNode = nodeRequestBuilder.createNoteNode(
        NoteNode.builder()
            .title("note with image")
            .listItemNode(
                ListItemNode.builder()
                    .text("some text")
                    .build()
            )
            .blobNodes(
                List.of(
                    BlobNode.builder()
                        .blob(
                            ImageBlob.builder()
                                .byteSize(imageData.getByteSize())
                                .height(imageData.getImageSize().getHeight())
                                .width(imageData.getImageSize().getWidth())
                                .mimetype(imageData.getMimeType())
                                .extractionStatus(ExtractionStatus.PROCESSING_REQUESTED)
                                .build()
                        )
                        .build()
                )
            )
            .build()
    );

    List<Node> assembledNodeList = NodeUtils.getAssembledNodeList(gKeepAPI.changes(nodeRequestBuilder.build()).getNodes());

    assembledNodeList.forEach(n -> {
        if (n.getType() == NodeType.NOTE) {
            ((NoteNode) n).getBlobNodes().forEach(blob -> {
                try {
                    ImageBlob imageBlob 
                            = gKeepAPI.uploadImage(imageData.getBytes(), blob.getServerId(), blob.getParentServerId());
                } catch (AuthError e) {
                    throw new RuntimeException(e);
                }
            });
        }
    });
    
    List<Node> nodes = NodeUtils.getAssembledNodeList(gKeepAPI.changes().getNodes());
  }
}
```

### Pulling image from the server
To pull image from the server you need `BlobNode` with `serverId` and `parentServerId`, with which
you can get image data using `getImageData(String blobServerId, String nodeServerId)` method.

And `ImageUtils.createImageFile(ImageData imageData, String pathToDirectory)` method can help you save the image on your device.

*Server names files with the default name `unnamed`.*

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.BlobNode;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.ListNode;
import io.github.rukins.gkeepapi.model.image.ImageData;
import io.github.rukins.gkeepapi.utils.ImageUtils;

import java.util.List;

public class Main {
  public static void main(String[] args) throws Exception {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    ListNode listNode = null; // some note you got from the server

    List<BlobNode> blobNodeList = listNode.getBlobNodes();

    for (int i = 0; i < blobNodeList.size(); i++) {
        ImageData imageData = gKeepAPI.getImageData(
                blobNodeList.get(i).getServerId(),
                blobNodeList.get(i).getParentServerId()
        );
        
        imageData.setFileName(i + imageData.getFileName());
        
        ImageUtils.createImageFile(
                imageData,
                "/path/to/dir/"
        );
    }
  }
}
```

## Labels

### Creating <a id="label-creating" />
Creating labels is available with `createLabel(Label label)` and `createOrUpdateLabel(Label label)` methods.

If you create with `createLabel(Label label)` method, `id` will be generated automatically.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.Label;

public class Main {
  public static void main(String[] args) {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();

    Label label = nodeRequestBuilder.createLabel(
            Label.builder()
                    .name("some label")
                    .build()
    );

    NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());
  }
}
```

### Updating <a id="label-updating" />
Updating labels is available with `createOrUpdateLabel(Label label)` method.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.Label;

public class Main {
  public static void main(String[] args) {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();

    Label label = nodeRequestBuilder.createOrUpdateLabel(
            Label.builder()
                    .mainId("label_id")
                    .name("new name")
                    .build()
    );

    NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());
  }
}
```

### Deleting <a id="label-deleting" />
Deleting labels is available with `deleteLabel(Label label)` method.

```java
import io.github.rukins.gkeepapi.GKeepAPI;
import io.github.rukins.gkeepapi.NodeRequestBuilder;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.Label;

public class Main {
  public static void main(String[] args) {
    GKeepAPI gKeepAPI = new GKeepAPI("aas_et/***", "current_version");

    NodeRequestBuilder nodeRequestBuilder = NodeRequestBuilder.builder();

    Label label = nodeRequestBuilder.deleteLabel(
            Label.builder()
                    .mainId("label_id")
                    .build()
    );

    NodeResponse nodeResponse = gKeepAPI.changes(nodeRequestBuilder.build());
  }
}
```

## NodeUtils

### Manipulations with list of nodes
With `getAssembledNodeList(List<Node> allNodes)` method you can get assembled list of nodes.
That means all list items and blobs will be added to `NoteNode` or `ListNode` it belongs to.

With `getNoteNodeById(...)` and `getListNodeById(...)` methods you can get a node by id in a provided list.
It can be assembled one or not, set it by `isNodeListAssembled` parameter. By default, assembled

### Merging
With methods provided in the NodeUtils, you can merge two `NodeResponse`'s, `List<Node>`'s,
`UserInfo`'s, `List<Label>`'s, `Node`'s or `Label`'s.
