package by.dudko.webproject.model.entity;

import java.io.Serializable;

public abstract class RootEntity implements Serializable {
    private long id;

    protected RootEntity() {}

    protected RootEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RootEntity that = (RootEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RootEntity{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
