package by.dudko.webproject.model.entity;

import java.io.Serializable;

public abstract class RootEntity<T> implements Serializable {
    private T id;

    protected RootEntity() {}

    protected RootEntity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RootEntity<?> that = (RootEntity<?>) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RootEntity{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
