package io.github.rukins.gkeepapi.model.node.nodesettings;

public class NodeSettings {
    public static final NodeSettings DEFAULT_NODE_SETTINGS = new NodeSettings(
            NewListItemPlacement.TOP,
            CheckedListItemsPolicy.GRAVEYARD,
            GraveyardState.EXPANDED
    );

    private NewListItemPlacement newListItemPlacement;

    private CheckedListItemsPolicy checkedListItemsPolicy;

    private GraveyardState graveyardState;

    public NodeSettings(NewListItemPlacement newListItemPlacement,
                        CheckedListItemsPolicy checkedListItemsPolicy,
                        GraveyardState graveyardState) {
        this.newListItemPlacement = newListItemPlacement;
        this.checkedListItemsPolicy = checkedListItemsPolicy;
        this.graveyardState = graveyardState;
    }

    public NewListItemPlacement getNewListItemPlacement() {
        return newListItemPlacement;
    }

    public void setNewListItemPlacement(NewListItemPlacement newListItemPlacement) {
        this.newListItemPlacement = newListItemPlacement;
    }

    public CheckedListItemsPolicy getCheckedListItemsPolicy() {
        return checkedListItemsPolicy;
    }

    public void setCheckedListItemsPolicy(CheckedListItemsPolicy checkedListItemsPolicy) {
        this.checkedListItemsPolicy = checkedListItemsPolicy;
    }

    public GraveyardState getGraveyardState() {
        return graveyardState;
    }

    public void setGraveyardState(GraveyardState graveyardState) {
        this.graveyardState = graveyardState;
    }

    @Override
    public String toString() {
        return "NodeSettings{" +
                "newListItemPlacement=" + newListItemPlacement +
                ", checkedListItemsPolicy=" + checkedListItemsPolicy +
                ", graveyardState=" + graveyardState +
                '}';
    }
}
