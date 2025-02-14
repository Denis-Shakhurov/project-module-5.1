package org.example.model;

public enum Status {
    IN_PROGRESS,
    DONE,
    PAUSED;

    int getValue(Status status) {
        return status.getValue(status);
    }
}
