package com.ibm.watson.developer_cloud.assistant.v1.model;

public class Counts {
    private int intents, entities, dialog_nodes;

    public int getIntents() {
        return intents;
    }

    public void setIntents(int intents) {
        this.intents = intents;
    }

    public int getEntities() {
        return entities;
    }

    public void setEntities(int entities) {
        this.entities = entities;
    }

    public int getDialog_nodes() {
        return dialog_nodes;
    }

    public void setDialog_nodes(int dialog_nodes) {
        this.dialog_nodes = dialog_nodes;
    }
}
