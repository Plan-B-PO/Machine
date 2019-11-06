package com.baltic.machine.model;

public class Runnable {
    private String applicationId;
    private ComputationSteps computationSteps;
    private String version;

    public Runnable(String applicationId, ComputationSteps computationSteps, String version) {
        this.applicationId = applicationId;
        this.computationSteps = computationSteps;
        this.version = version;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public ComputationSteps getComputationSteps() {
        return computationSteps;
    }

    public void setComputationSteps(ComputationSteps computationSteps) {
        this.computationSteps = computationSteps;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
