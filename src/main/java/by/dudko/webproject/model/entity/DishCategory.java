package by.dudko.webproject.model.entity;

public class DishCategory extends RootEntity {
    private String name;
    private String imageUrl;

    public DishCategory() {}

    public DishCategory(long id, String name, String imageUrl) {
        super(id);
        this.name = name;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DishCategory that = (DishCategory) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return imageUrl != null ? imageUrl.equals(that.imageUrl) : that.imageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DishCategory{");
        sb.append("name='").append(name).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
