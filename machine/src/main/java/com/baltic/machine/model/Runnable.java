package com.baltic.machine.model;

public class Runnable {
    private Long ApplicationId;
    private ComputationSteps computationSteps;
    private String version;

    public Runnable(Long applicationId, ComputationSteps computationSteps, String version) {
        ApplicationId = applicationId;
        this.computationSteps = computationSteps;
        this.version = version;
    }

    public Long getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(Long applicationId) {
        ApplicationId = applicationId;
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
