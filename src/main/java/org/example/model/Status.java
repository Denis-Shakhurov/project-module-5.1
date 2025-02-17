package org.example.model;

public enum Status {
    IN_PROGRESS,
    DONE,
    PAUSED;

    public Status getValue(int status) {
        return Status.values()[status];
    }

    public int getValue(Status status) {
        return status.ordinal();
    }
}
