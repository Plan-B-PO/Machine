package com.baltic.machine.model;

import org.springframework.data.mongodb.core.mapping.Document;

public class Machine {
    private String id;
    private String appUser;
    private String artifactUrl;
    private String applicationId;
    private String version;

    public Machine(String id, String appUser, String artifactUrl, String applicationId, String version) {
        this.id = id;
        this.appUser = appUser;
        this.artifactUrl = artifactUrl;
        this.applicationId = applicationId;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

    public String getArtifactUrl() {
        return artifactUrl;
    }

    public void setArtifactUrl(String artifactUrl) {
        this.artifactUrl = artifactUrl;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id='" + id + '\'' +
                ", appUser='" + appUser + '\'' +
                ", artifactUrl='" + artifactUrl + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
