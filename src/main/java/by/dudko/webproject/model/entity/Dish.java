package by.dudko.webproject.model.entity;

import java.math.BigDecimal;

public class Dish extends RootEntity<Integer> {
    private String name;
    private String category;
    private String description;
    private int weight;
    private String imageUrl;
    private BigDecimal price;
    private BigDecimal discount;

    public static DishBuilder getBuilder() {
        return new DishBuilder();
    }

    public Dish() {}

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal calculateDiscountedPrice() {
        BigDecimal discountAmount = price.multiply(discount);
        return price.subtract(discountAmount);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static class DishBuilder {
        private final Dish dish;

        DishBuilder() {
            dish = new Dish();
        }

        public DishBuilder price(BigDecimal price) {
            dish.setPrice(price);
            return this;
        }

        public DishBuilder discount(BigDecimal discount) {
            dish.setDiscount(discount);
            return this;
        }

        public DishBuilder weight(int weight) {
            dish.setWeight(weight);
            return this;
        }

        public DishBuilder imageUrl(String imageUrl) {
            dish.setImageUrl(imageUrl);
            return this;
        }

        public DishBuilder name(String name) {
            dish.setName(name);
            return this;
        }

        public DishBuilder description(String description) {
            dish.setDescription(description);
            return this;
        }

        public DishBuilder category(String category) {
            dish.setCategory(category);
            return this;
        }

        public DishBuilder id(Integer id) {
            dish.setId(id);
            return this;
        }

        public Dish buildDish() {
            return dish;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Dish dish = (Dish) o;

        if (weight != dish.weight) return false;
        if (price != null ? !price.equals(dish.price) : dish.price != null) return false;
        if (discount != null ? !discount.equals(dish.discount) : dish.discount != null) return false;
        if (imageUrl != null ? !imageUrl.equals(dish.imageUrl) : dish.imageUrl != null) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (description != null ? !description.equals(dish.description) : dish.description != null) return false;
        return category != null ? category.equals(dish.category) : dish.category == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + weight;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dish{");
        sb.append("price=").append(price);
        sb.append(", discount=").append(discount);
        sb.append(", weight=").append(weight);
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categoryName='").append(category).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
