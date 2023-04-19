package io.github.rukins.gkeepapi.utils;

import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.Timestamps;
import io.github.rukins.gkeepapi.model.gkeep.node.NodeType;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.*;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.Label;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.UserInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeUtils {
    public static List<AbstractNode> getAssembledAbstractNodeList(List<AbstractNode> allNodes) {
        if (allNodes == null) {
            return null;
        }

        List<AbstractNode> result = new ArrayList<>();

        Map<String, List<AbstractNode>> idAndNodeListMap = allNodes.stream().collect(
                Collectors.groupingBy(
                        n -> {
                            if (n.getType() == NodeType.LIST_ITEM || n.getType() == NodeType.BLOB) {
                                return n.getParentId();
                            }
                            return n.getId();
                        },
                        Collectors.toList()
                )
        );

        for (List<AbstractNode> nodeList : idAndNodeListMap.values()) {
            if (nodeList.stream().allMatch(n -> n.getType() == null)) {
                result.addAll(nodeList);
                continue;
            }

            AbstractNode noteOrList = nodeList.stream()
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

    public static NoteNode getNoteNodeById(String noteNoteId, List<AbstractNode> allNodes) {
        return getNoteNodeById(noteNoteId, allNodes, true);
    }

    public static NoteNode getNoteNodeById(String noteNoteId, List<AbstractNode> allNodes, boolean isNodeListAssembled) {
        AbstractNode node;

        if (isNodeListAssembled) {
            node = allNodes.stream().filter(n -> noteNoteId.equals(n.getId())).findFirst().orElse(null);

            return node instanceof NoteNode ? (NoteNode) node : null;
        } else {
            Map<NodeType, List<AbstractNode>> nodeTypeAndNodeMap = allNodes.stream()
                    .filter(n -> noteNoteId.equals(n.getId()) || noteNoteId.equals(n.getParentId()))
                    .collect(Collectors.groupingBy(AbstractNode::getType, Collectors.toList()));

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

    public static ListNode getListNodeById(String noteNoteId, List<AbstractNode> allNodes) {
        return getListNodeById(noteNoteId, allNodes, true);
    }

    public static ListNode getListNodeById(String listNodeId, List<AbstractNode> allNodes, boolean isNodeListAssembled) {
        AbstractNode node;

        if (isNodeListAssembled) {
            node = allNodes.stream().filter(n -> listNodeId.equals(n.getId())).findFirst().orElse(null);

            return node instanceof ListNode ? (ListNode) node : null;
        } else {
            Map<NodeType, List<AbstractNode>> nodeTypeAndNodeMap = allNodes.stream()
                    .filter(n -> listNodeId.equals(n.getId()) || listNodeId.equals(n.getParentId()))
                    .collect(Collectors.groupingBy(AbstractNode::getType, Collectors.toList()));

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

    public static NodeResponse mergeNodeResponses(NodeResponse oldVersion, NodeResponse newVersion) {
        if (oldVersion == null || newVersion == null) {
            return null;
        }

        oldVersion.setFromVersion(newVersion.getFromVersion());
        oldVersion.setToVersion(newVersion.getToVersion());
        oldVersion.setTruncated(newVersion.getTruncated());
        oldVersion.setForceFullResync(newVersion.getForceFullResync());
        oldVersion.setResponseHeader(newVersion.getResponseHeader());

        if (newVersion.getNodes() != null) {
            oldVersion.setNodes(mergeListsOfAbstractNodes(oldVersion.getNodes(), newVersion.getNodes()));
        }
        if (newVersion.getUserInfo() != null) {
            oldVersion.setUserInfo(mergeUserInfos(oldVersion.getUserInfo(), newVersion.getUserInfo()));
        }

        return oldVersion;
    }

    public static List<AbstractNode> mergeListsOfAbstractNodes(List<? extends AbstractNode> oldNodes, List<? extends AbstractNode> newNodes) {
        if (oldNodes == null || newNodes == null) {
            return null;
        }

        Map<String, AbstractNode> idAndNodeMap = oldNodes.stream()
                .collect(Collectors.toMap(AbstractNode::getId, n -> n));

        for (AbstractNode newNode : newNodes) {
            if (newNode.getTimestamps().getDeleted() != null
                    && !newNode.getTimestamps().getDeleted().equals(Timestamps.DEFAULT_LOCALDATETIME)) {
                idAndNodeMap.remove(newNode.getId());
                continue;
            }

            if (idAndNodeMap.containsKey(newNode.getId())) {
                AbstractNode oldNode = idAndNodeMap.get(newNode.getId());

                if (!newNode.equals(oldNode)) {
                    idAndNodeMap.replace(newNode.getId(), NodeUtils.mergeAbstractNodes(oldNode, newNode));
                }
            } else {
                idAndNodeMap.put(newNode.getId(), newNode);
            }
        }

        return idAndNodeMap.values().stream().toList();
    }

    public static UserInfo mergeUserInfos(UserInfo oldUserInfo, UserInfo newUserInfo) {
        if (oldUserInfo == null || newUserInfo == null) {
            return oldUserInfo;
        }

        if (newUserInfo.getTimestamps() != null) {
            oldUserInfo.setTimestamps(newUserInfo.getTimestamps());
        }
        if (newUserInfo.getSettings() != null) {
            oldUserInfo.setSettings(newUserInfo.getSettings());
        }
        if (newUserInfo.getLabels() != null) {
            oldUserInfo.setLabels(mergeListsOfLabels(oldUserInfo.getLabels(), newUserInfo.getLabels()));
        }

        return oldUserInfo;
    }

    public static List<Label> mergeListsOfLabels(List<Label> oldLabels, List<Label> newLabels) {
        if (oldLabels == null || newLabels == null) {
            return null;
        }

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

    public static AbstractNode mergeAbstractNodes(AbstractNode oldNode, AbstractNode newNode) {
        try {
            if (oldNode instanceof NoteNode && newNode instanceof ListNode) {
                oldNode = (AbstractNode) mergeObjects(ListNode.builder().build(), oldNode);
            }
            else if (oldNode instanceof ListNode && newNode instanceof NoteNode) {
                oldNode = (AbstractNode) mergeObjects(NoteNode.builder().build(), oldNode);
            }

            AbstractNode mergedNode = (AbstractNode) mergeObjects(oldNode, newNode);

            if (mergedNode instanceof NoteNode && oldNode instanceof NoteNode && newNode instanceof NoteNode) {
                ((NoteNode) mergedNode).setBlobNodes(
                        mergeListsOfAbstractNodes(
                                ((NoteNode) oldNode).getBlobNodes(),
                                ((NoteNode) newNode).getBlobNodes()
                        ).stream().map(n -> (BlobNode) n).toList()
                );

                ((NoteNode) mergedNode).setLabelIds(
                        Stream.concat(
                                ((NoteNode) oldNode).getLabelIds().stream(),
                                ((NoteNode) newNode).getLabelIds().stream()
                        ).distinct().toList()
                );
            } else if (mergedNode instanceof ListNode && oldNode instanceof ListNode && newNode instanceof ListNode) {
                ((ListNode) mergedNode).setListItemNodes(
                        mergeListsOfAbstractNodes(
                                ((ListNode) oldNode).getListItemNodes(),
                                ((ListNode) newNode).getListItemNodes()
                        ).stream().map(n -> (ListItemNode) n).toList()
                );
                ((ListNode) mergedNode).setBlobNodes(
                        mergeListsOfAbstractNodes(
                                ((ListNode) oldNode).getBlobNodes(),
                                ((ListNode) newNode).getBlobNodes()
                        ).stream().map(n -> (BlobNode) n).toList()
                );

                ((ListNode) mergedNode).setLabelIds(
                        Stream.concat(
                                ((ListNode) oldNode).getLabelIds().stream(),
                                ((ListNode) newNode).getLabelIds().stream()
                        ).distinct().toList()
                );
            }

            if (mergedNode instanceof Node) {
                try {
                    Field typeField = mergedNode.getClass().getSuperclass().getDeclaredField("type");
                    typeField.setAccessible(true);
                    typeField.set(mergedNode, null);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }

            return mergedNode;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Label mergeLabels(Label oldLabel, Label newLabel) {
        try {
            Label mergedLabel = (Label) mergeObjects(oldLabel, newLabel);

            mergedLabel.setMergedIds(newLabel.getMergedIds());

            return mergedLabel;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object mergeObjects(Object oldObj, Object newObj) throws IllegalAccessException {
        if (oldObj == null || newObj == null) {
            return null;
        }

        List<Field> fieldsOfOldObj = new ArrayList<>();
        List<Field> fieldsOfNewObj = new ArrayList<>();

        if (oldObj.getClass().getSuperclass() == newObj.getClass().getSuperclass()
                && oldObj.getClass().getSuperclass() != Object.class) {
            fieldsOfOldObj.addAll(Arrays.stream(oldObj.getClass().getSuperclass().getDeclaredFields()).toList());
            fieldsOfNewObj.addAll(Arrays.stream(newObj.getClass().getSuperclass().getDeclaredFields()).toList());
        }

        if (oldObj.getClass() == newObj.getClass()) {
            fieldsOfOldObj.addAll(Arrays.stream(oldObj.getClass().getDeclaredFields()).toList());
            fieldsOfNewObj.addAll(Arrays.stream(newObj.getClass().getDeclaredFields()).toList());
        }

        for (int i = 0; i < fieldsOfNewObj.size(); i++) {
            fieldsOfNewObj.get(i).setAccessible(true);
            Object returnedNewObj = fieldsOfNewObj.get(i).get(newObj);

            if (returnedNewObj != null) {
                if (Modifier.isFinal(fieldsOfOldObj.get(i).getModifiers())
                        || Modifier.isStatic(fieldsOfOldObj.get(i).getModifiers())) {
                    continue;
                }

                fieldsOfOldObj.get(i).setAccessible(true);

                if (isBaseObject(returnedNewObj) || returnedNewObj.getClass().isEnum()) {
                    fieldsOfOldObj.get(i).set(oldObj, returnedNewObj);
                } else if (returnedNewObj instanceof Collection || returnedNewObj.getClass().isArray()) {
                    // TODO
                } else {
                    mergeObjects(fieldsOfOldObj.get(i).get(oldObj), returnedNewObj);
                }
            }
        }

        return oldObj;
    }

    private static Boolean isBaseObject(Object obj) {
        return obj instanceof Byte
                || obj instanceof Short
                || obj instanceof Integer
                || obj instanceof Long
                || obj instanceof Float
                || obj instanceof Double
                || obj instanceof Character
                || obj instanceof Boolean
                || obj instanceof String
                || obj instanceof LocalDateTime
                || obj instanceof Locale;
    }
}
