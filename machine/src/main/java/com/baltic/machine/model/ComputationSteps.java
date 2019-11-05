package com.baltic.machine.model;

public class ComputationSteps {
    private Params params;
    private String artifactUrl;
    private String command;

    public ComputationSteps(Params params, String artifactUrl, String command) {
        this.params = params;
        this.artifactUrl = artifactUrl;
        this.command = command;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public String getArtifactUrl() {
        return artifactUrl;
    }

    public void setArtifactUrl(String artifactUrl) {
        this.artifactUrl = artifactUrl;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
