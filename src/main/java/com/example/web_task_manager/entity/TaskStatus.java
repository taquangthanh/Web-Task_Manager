package com.example.web_task_manager.entity;

public enum TaskStatus {
    OPEN("Open"),
    IN_PROGRESS("Inprogress"),
    DONE("Done" );

    private final String code;

    TaskStatus(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
