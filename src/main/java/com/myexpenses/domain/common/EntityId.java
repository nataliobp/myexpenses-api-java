package com.myexpenses.domain.common;


public abstract class EntityId {

    private final String id;

    protected EntityId() {
        id = null;
    }

    protected EntityId(String anId) {
        this.id = anId;
    }

    public String id() {
        return id;
    }

    public boolean equals(Object o) {
        if (o.getClass() == getClass()) {
            return ((EntityId) o).id.equals(id);
        }

        return false;
    }

    public int hashCode() {
        return id.hashCode();
    }

    public String toString() {
        return id;
    }
}
