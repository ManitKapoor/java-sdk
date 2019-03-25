package com.ibm.watson.developer_cloud.assistant.v1.model;

import com.ibm.watson.developer_cloud.service.model.GenericModel;

import java.util.List;


public class SnapshotCollection extends GenericModel {
    private List<Snapshot> snapshots;

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }
}
