package by.dudko.webproject.model.mapper.impl;

import by.dudko.webproject.model.entity.DishCategory;
import by.dudko.webproject.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DishCategoryRowMapper implements RowMapper<DishCategory> {
    private static final Logger logger = LogManager.getLogger();
    private static final String CATEGORY_ID = "category_id";
    private static final String IMAGE_URL = "image_url";
    private static final String CATEGORY_NAME = "name";
    private static final DishCategoryRowMapper INSTANCE = new DishCategoryRowMapper();

    public static DishCategoryRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<DishCategory> mapRow(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                DishCategory category = new DishCategory();
                category.setId(resultSet.getInt(CATEGORY_ID));
                category.setImageUrl(resultSet.getString(IMAGE_URL));
                category.setName(resultSet.getString(CATEGORY_NAME));
                return Optional.of(category);
            }
        } catch (SQLException e) {
            logger.error("Category mapping error", e);
        }
        return Optional.empty();
    }

    private DishCategoryRowMapper() {}
}
