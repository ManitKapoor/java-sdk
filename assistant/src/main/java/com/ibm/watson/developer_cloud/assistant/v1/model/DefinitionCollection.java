package com.ibm.watson.developer_cloud.assistant.v1.model;

import com.ibm.watson.developer_cloud.service.model.GenericModel;

import java.util.List;

public class DefinitionCollection extends GenericModel {

    private List<Definition> definitions;

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }
}
