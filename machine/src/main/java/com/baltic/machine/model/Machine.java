package com.baltic.machine.model;

public class Machine {
    private String id;
    private Long appUser;
    private Runnable runnable;
    private String logger;

    public Machine(String id, Long appUser, Runnable runnable, String logger) {
        this.id = id;
        this.appUser = appUser;
        this.runnable = runnable;
        this.logger = logger;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAppUser() {
        return appUser;
    }

    public void setAppUser(Long appUser) {
        this.appUser = appUser;
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
}
