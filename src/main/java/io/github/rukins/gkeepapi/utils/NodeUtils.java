package io.github.rukins.gkeepapi.utils;

import io.github.rukins.gkeepapi.exception.BadNodeTypeException;
import io.github.rukins.gkeepapi.model.NodePair;
import io.github.rukins.gkeepapi.model.node.NodeResponse;
import io.github.rukins.gkeepapi.model.node.nodeentity.Node;
import io.github.rukins.gkeepapi.model.node.userinfo.Label;
import io.github.rukins.gkeepapi.model.node.userinfo.UserInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeUtils {
    public static List<NodePair> getAllNodePairs(List<Node> allNodes) {
        Map<String, Node> idAndNodeMap = allNodes.stream().collect(Collectors.toMap(Node::getId, n -> n));

        return allNodes.stream()
                .filter(n -> !n.getParentId().equals("root"))
                .map(n -> {
                    try {
                        return new NodePair(idAndNodeMap.get(n.getParentId()), n);
                    } catch (BadNodeTypeException e) {
                        return null;
                    }
                }).toList();
    }

    public static NodePair getNodePairById(String noteId, List<Node> allNodes) throws BadNodeTypeException {
        return new NodePair(
                allNodes.stream()
                        .filter(n -> noteId.equals(n.getId()) || noteId.equals(n.getParentId()))
                        .sorted()
                        .toList()
        );
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

        return idAndNodeMap.values().stream().sorted().toList();
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
