package by.dudko.webproject.model.mapper.impl;

import by.dudko.webproject.model.entity.Dish;
import by.dudko.webproject.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DishRowMapper implements RowMapper<Dish> {
    private static final String DISH_ID = "dish_id";
    private static final String DISH_CATEGORY = "category";
    private static final String PRICE = "price";
    private static final String DISCOUNT = "discount";
    private static final String WEIGHT = "weight";
    private static final String DESCRIPTION = "description";
    private static final String DISH_NAME = "dish_name";
    private static final String IMAGE_URL = "image_url";
    private static final Logger logger = LogManager.getLogger();
    private static final DishRowMapper INSTANCE = new DishRowMapper();

    public static DishRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Dish> mapRow(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                Dish.DishBuilder builder = Dish.getBuilder();
                builder.id(resultSet.getInt(DISH_ID))
                        .price(resultSet.getBigDecimal(PRICE))
                        .discount(resultSet.getBigDecimal(DISCOUNT))
                        .weight(resultSet.getInt(WEIGHT))
                        .description(resultSet.getString(DESCRIPTION))
                        .name(resultSet.getString(DISH_NAME))
                        .category(resultSet.getString(DISH_CATEGORY))
                        .imageUrl(resultSet.getString(IMAGE_URL));
                return Optional.of(builder.buildDish());
            }
        } catch (SQLException e) {
            logger.error("Dish mapping error", e);
        }
        return Optional.empty();
    }

    private DishRowMapper() {}
}
