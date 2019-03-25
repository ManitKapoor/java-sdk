package com.ibm.watson.developer_cloud.assistant.v1.model;

import com.google.gson.annotations.SerializedName;
import com.ibm.watson.developer_cloud.service.model.GenericModel;


public class Definition extends GenericModel {
    private String id, name;
    @SerializedName("worker_template_id")
    String workerTemplateId;

    public class Config extends GenericModel {
        @SerializedName("workspace_id")
        private String workspaceId;

        public String getWorkspaceId() {
            return workspaceId;
        }

        public void setWorkspaceId(String workspaceId) {
            this.workspaceId = workspaceId;
        }
    }
    private Config config;
    @SerializedName("tenant_id")
    private String tenantId;
    private String version, description;
    @SerializedName("skill_reference")
    private String skillReference;
    @SerializedName("next_skill_version")
    private String nextSkillVersion;
    @SerializedName("timestamp_created")
    private String timestampCreated;
    @SerializedName("timestamp_modified")
    private String timestampModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkerTemplateId() {
        return workerTemplateId;
    }

    public void setWorkerTemplateId(String workerTemplateId) {
        this.workerTemplateId = workerTemplateId;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkillReference() {
        return skillReference;
    }

    public void setSkillReference(String skillReference) {
        this.skillReference = skillReference;
    }

    public String getNextSkillVersion() {
        return nextSkillVersion;
    }

    public void setNextSkillVersion(String nextSkillVersion) {
        this.nextSkillVersion = nextSkillVersion;
    }

    public String getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(String timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public String getTimestampModified() {
        return timestampModified;
    }

    public void setTimestampModified(String timestampModified) {
        this.timestampModified = timestampModified;
    }
}
