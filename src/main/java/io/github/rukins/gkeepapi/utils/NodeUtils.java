package io.github.rukins.gkeepapi.utils;

import io.github.rukins.gkeepapi.model.NodeResponse;
import io.github.rukins.gkeepapi.model.node.NodeType;
import io.github.rukins.gkeepapi.model.node.nodeobject.*;
import io.github.rukins.gkeepapi.model.userinfo.Label;
import io.github.rukins.gkeepapi.model.userinfo.UserInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeUtils {
    public static List<Node> getAssembledNodeList(List<Node> allNodes) {
        if (allNodes == null) {
            return null;
        }

        List<Node> result = new ArrayList<>();

        Map<String, List<Node>> idAndNodeListMap = allNodes.stream().collect(
                Collectors.groupingBy(
                        n -> {
                            if (n.getType() == NodeType.NOTE || n.getType() == NodeType.LIST) {
                                return n.getId();
                            }
                            return n.getParentId();
                        },
                        Collectors.toList()
                )
        );

        for (List<Node> nodeList : idAndNodeListMap.values()) {
            Node noteOrList = nodeList.stream()
                    .filter(n -> n.getType() == NodeType.NOTE || n.getType() == NodeType.LIST)
                    .findFirst().orElse(null);

            if (noteOrList != null) {
                List<ListItemNode> listItems = nodeList.stream()
                        .filter(n -> n.getType() == NodeType.LIST_ITEM)
                        .map(n -> (ListItemNode) n)
                        .toList();

                List<BlobNode> blobs = nodeList.stream()
                        .filter(n -> n.getType() == NodeType.BLOB)
                        .map(n -> (BlobNode) n)
                        .toList();

                if (noteOrList instanceof NoteNode) {
                    if (listItems.size() > 0)
                        ((NoteNode) noteOrList).setListItemNode(listItems.get(0));
                    ((NoteNode) noteOrList).setBlobNodes(blobs);
                }
                if (noteOrList instanceof ListNode) {
                    ((ListNode) noteOrList).setListItemNodes(listItems);
                    ((ListNode) noteOrList).setBlobNodes(blobs);
                }
                result.add(noteOrList);
            }
        }

        return result;
    }

    public static NoteNode getNoteNodeById(String noteNoteId, List<Node> allNodes) {
        return getNoteNodeById(noteNoteId, allNodes, true);
    }

    public static NoteNode getNoteNodeById(String noteNoteId, List<Node> allNodes, boolean isNodeListAssembled) {
        Node node;

        if (isNodeListAssembled) {
            node = allNodes.stream().filter(n -> noteNoteId.equals(n.getId())).findFirst().orElse(null);

            return node instanceof NoteNode ? (NoteNode) node : null;
        } else {
            Map<NodeType, List<Node>> nodeTypeAndNodeMap = allNodes.stream()
                    .filter(n -> noteNoteId.equals(n.getId()) || noteNoteId.equals(n.getParentId()))
                    .collect(Collectors.groupingBy(Node::getType, Collectors.toList()));

            if (nodeTypeAndNodeMap.get(NodeType.NOTE) != null
                    && nodeTypeAndNodeMap.get(NodeType.NOTE).size() == 1) {
                NoteNode noteNode = (NoteNode) nodeTypeAndNodeMap.get(NodeType.NOTE).get(0);

                if (nodeTypeAndNodeMap.get(NodeType.LIST_ITEM).size() == 1) {
                    noteNode.setListItemNode((ListItemNode) nodeTypeAndNodeMap.get(NodeType.LIST_ITEM).get(0));
                }
                noteNode.setBlobNodes(nodeTypeAndNodeMap.get(NodeType.BLOB).stream().map(n -> (BlobNode) n).toList());

                return noteNode;
            }
        }

        return null;
    }

    public static ListNode getListNodeById(String noteNoteId, List<Node> allNodes) {
        return getListNodeById(noteNoteId, allNodes, true);
    }

    public static ListNode getListNodeById(String listNodeId, List<Node> allNodes, boolean isNodeListAssembled) {
        Node node;

        if (isNodeListAssembled) {
            node = allNodes.stream().filter(n -> listNodeId.equals(n.getId())).findFirst().orElse(null);

            return node instanceof ListNode ? (ListNode) node : null;
        } else {
            Map<NodeType, List<Node>> nodeTypeAndNodeMap = allNodes.stream()
                    .filter(n -> listNodeId.equals(n.getId()) || listNodeId.equals(n.getParentId()))
                    .collect(Collectors.groupingBy(Node::getType, Collectors.toList()));

            if (nodeTypeAndNodeMap.get(NodeType.LIST) != null
                    && nodeTypeAndNodeMap.get(NodeType.LIST).size() == 1) {
                ListNode listNode = (ListNode) nodeTypeAndNodeMap.get(NodeType.LIST).get(0);

                listNode.setListItemNodes(
                        nodeTypeAndNodeMap.get(NodeType.LIST_ITEM).stream().map(n -> (ListItemNode) n).toList()
                );
                listNode.setBlobNodes(
                        nodeTypeAndNodeMap.get(NodeType.BLOB).stream().map(n -> (BlobNode) n).toList()
                );

                return listNode;
            }
        }

        return null;
    }

    public static NodeResponse mergeNodeResponse(NodeResponse oldVersion, NodeResponse newVersion) {
        if (oldVersion == null || newVersion == null) {
            return null;
        }

        oldVersion.setFromVersion(newVersion.getFromVersion());
        oldVersion.setToVersion(newVersion.getToVersion());
        oldVersion.setTruncated(newVersion.getTruncated());
        oldVersion.setForceFullResync(newVersion.getForceFullResync());
        oldVersion.setResponseHeader(newVersion.getResponseHeader());

        if (newVersion.getNodes() != null) {
            oldVersion.setNodes(mergeNodes(oldVersion.getNodes(), newVersion.getNodes()));
        }
        if (newVersion.getUserInfo() != null) {
            oldVersion.setUserInfo(mergeUserInfo(oldVersion.getUserInfo(), newVersion.getUserInfo()));
        }

        return oldVersion;
    }

    public static List<Node> mergeNodes(List<Node> oldNodes, List<Node> newNodes) {
        Map<String, Node> idAndNodeMap = oldNodes.stream()
                .collect(Collectors.toMap(Node::getId, n -> n));

        for (Node newNode : newNodes) {
            if (newNode.getTimestamps().getDeleted() != null) {
                continue;
            }

            if (idAndNodeMap.containsKey(newNode.getId())) {
                Node oldNode = idAndNodeMap.get(newNode.getId());

                if (!newNode.equals(oldNode)) {
                    idAndNodeMap.replace(newNode.getId(), newNode);
                }
            } else {
                idAndNodeMap.put(newNode.getId(), newNode);
            }
        }

        return idAndNodeMap.values().stream().toList();
    }

    public static UserInfo mergeUserInfo(UserInfo oldUserInfo, UserInfo newUserInfo) {
        if (oldUserInfo == null || newUserInfo == null) {
            return null;
        }

        if (newUserInfo.getTimestamps() != null) {
            oldUserInfo.setTimestamps(newUserInfo.getTimestamps());
        }
        if (newUserInfo.getSettings() != null) {
            oldUserInfo.setSettings(newUserInfo.getSettings());
        }
        if (newUserInfo.getLabels() != null) {
            oldUserInfo.setLabels(mergeLabels(oldUserInfo.getLabels(), newUserInfo.getLabels()));
        }

        return oldUserInfo;
    }

    public static List<Label> mergeLabels(List<Label> oldLabels, List<Label> newLabels) {
        Map<String, Label> idAndLabelMap = oldLabels.stream()
                .collect(Collectors.toMap(Label::getMainId, n -> n));

        for (Label newLabel : newLabels) {
            if (idAndLabelMap.containsKey(newLabel.getMainId())) {
                Label oldLabel = idAndLabelMap.get(newLabel.getMainId());

                if (!newLabel.equals(oldLabel)) {
                    idAndLabelMap.replace(newLabel.getMainId(), newLabel);
                }
            } else {
                idAndLabelMap.put(newLabel.getMainId(), newLabel);
            }
        }

        return idAndLabelMap.values().stream().toList();
    }

    public static Node mergeNode(Node oldNode, Node newNode) {
        try {
            return (Node) mergeObjectByGettersAndSetters(oldNode, newNode);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Label mergeLabel(Label oldLabel, Label newLabel) {
        try {
            return (Label) mergeObjectByGettersAndSetters(oldLabel, newLabel);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object mergeObjectByGettersAndSetters(Object oldObj, Object newObj) throws InvocationTargetException, IllegalAccessException {
        List<Method> getters = Arrays.stream(newObj.getClass().getMethods())
                .filter(m -> m.getName().startsWith("get"))
                .toList();

        for (Method getter : getters) {
            Object returnObj = getter.invoke(newObj);

            if (returnObj != null) {
                try {
                    oldObj.getClass().getMethod(
                            "set" + getter.getName().substring(3),
                            getter.getReturnType()
                    ).invoke(oldObj, returnObj);
                } catch (NoSuchMethodException ignored) {}
            }
        }

        return oldObj;
    }
}
