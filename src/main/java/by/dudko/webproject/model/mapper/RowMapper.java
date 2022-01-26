package by.dudko.webproject.model.mapper;

import by.dudko.webproject.model.entity.RootEntity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RowMapper<T extends RootEntity> {
    Optional<T> mapRow(ResultSet resultSet);

    default List<T> mapRows(ResultSet resultSet) {
         ArrayList<T> entities = new ArrayList<>();
         while (true) {
             Optional<T> optionalEntity = mapRow(resultSet);
             if (optionalEntity.isEmpty()) {
                 break;
             }
             entities.add(optionalEntity.get());
         }
         return entities;
     }
}
