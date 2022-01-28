package by.dudko.webproject.model.mapper.impl;

import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRowMapper implements RowMapper<User> {
    private static final Logger logger = LogManager.getLogger();
    private static final String USER_ID = "user_id";
    private static final String ROLE = "role";
    private static final String STATUS = "status";
    private static final String EMAIL = "email";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final UserRowMapper INSTANCE = new UserRowMapper();

    public static UserRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<User> mapRow(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                User.UserBuilder builder = User.getBuilder();
                String roleString = resultSet.getString(ROLE).toUpperCase();
                String statusString = resultSet.getString(STATUS).toUpperCase();
                builder.id(resultSet.getLong(USER_ID))
                        .role(User.Role.valueOf(roleString))
                        .status(User.Status.valueOf(statusString))
                        .email(resultSet.getString(EMAIL))
                        .login(resultSet.getString(LOGIN))
                        .password(resultSet.getString(PASSWORD))
                        .firstName(resultSet.getString(FIRST_NAME))
                        .lastName(resultSet.getString(LAST_NAME))
                        .phoneNumber(resultSet.getString(PHONE_NUMBER));
                return Optional.of(builder.buildUser());
            }
        } catch (SQLException e) {
            logger.error("User mapping error", e);
        }
        return Optional.empty();
    }

    private UserRowMapper() {}
}


