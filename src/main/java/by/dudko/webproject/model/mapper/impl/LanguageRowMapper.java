package by.dudko.webproject.model.mapper.impl;

import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LanguageRowMapper implements RowMapper<Language> {
    private static final Logger logger = LogManager.getLogger();
    private static final String LANGUAGE_ID = "language_id";
    private static final String LANGUAGE_NAME = "name";
    private static final LanguageRowMapper INSTANCE = new LanguageRowMapper();

    public static LanguageRowMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Language> mapRow(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                String languageName = resultSet.getString(LANGUAGE_NAME);
                Language language = new Language(languageName);
                language.setId(resultSet.getInt(LANGUAGE_ID));
                return Optional.of(language);
            }
        } catch (SQLException e) {
            logger.error("Language mapping error", e);
        }
        return Optional.empty();
    }

    private LanguageRowMapper() {}
}
