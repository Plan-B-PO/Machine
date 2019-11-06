package com.baltic.machine.model;

public class Machine {
    private String id;
    private Long appUserId;
    private Runnable runnable;
    private String logger;

    public Machine(String id, Long appUserId, Runnable runnable, String logger) {
        this.id = id;
        this.appUserId = appUserId;
        this.runnable = runnable;
        this.logger = logger;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id='" + id + '\'' +
                ", appUser='" + appUserId + '\'' +
                ", runnable=" + runnable +
                ", logger='" + logger + '\'' +
                '}';
    }
}
