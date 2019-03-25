package com.ibm.watson.developer_cloud.assistant.v1.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.ibm.watson.developer_cloud.service.model.GenericModel;


public class Snapshot extends GenericModel {
    private String id, description;
    @SerializedName("snapshot_name")
    private String snapshotName;
    @SerializedName("workspace_id")
    private String workspaceId;
    @SerializedName("worker_definition_id")
    private String workerDefinitionId;
    @SerializedName("reference_id")
    private String referenceId;
    @SerializedName("tenant_id")
    private String tenantId;
    @SerializedName("timestamp_created")
    private String timestampCreated;
    private Counts counts;

    @SerializedName("exported_data")
    private JsonObject exportedData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSnapshotName() {
        return snapshotName;
    }

    public void setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getWorkerDefinitionId() {
        return workerDefinitionId;
    }

    public void setWorkerDefinitionId(String workerDefinitionId) {
        this.workerDefinitionId = workerDefinitionId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(String timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Counts getCounts() {
        return counts;
    }

    public void setCounts(Counts counts) {
        this.counts = counts;
    }

    public JsonObject getExportedData() {
        return exportedData;
    }

    public void setExportedData(JsonObject exportedData) {
        this.exportedData = exportedData;
    }
}
