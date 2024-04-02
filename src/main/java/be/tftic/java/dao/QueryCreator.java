package be.tftic.java.dao;

import be.tftic.java.utils.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class QueryCreator {

    public static <T> TypedQuery<T> createQuery(String query, DBExtractor<T> extractor){
        return new TypedQuery<>(query, extractor);
    }

    public static class TypedQuery<T> {
        private final String query;
        private final DBExtractor<T> extractor;
        private final Map<Integer, Object> params = new HashMap<>();


        private TypedQuery(String query, DBExtractor<T> extractor) {
            this.query = query;
            this.extractor = extractor;
        }

        public TypedQuery<T> withParam(int position, Object value){
            this.params.put(position, value);
            return this;
        }

        public List<T> fetchList(){
            try(
                    Connection co = DBConfig.createConnection();
                    PreparedStatement statement = co.prepareStatement(query);
            ) {

                for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                    switch (entry.getValue()){
                        case String string -> statement.setString(entry.getKey(), string);
                        case Long l -> statement.setLong(entry.getKey(), l);
                        default -> {}
                    }
                }

                try ( ResultSet rs = statement.executeQuery() ){
                    List<T> elements = new ArrayList<>();
                    while( rs.next() ){
                        elements.add( extractor.extract(rs) );
                    }
                    return elements;
                }
            } catch (SQLException ex){
                throw new RuntimeException(ex);
            }
        }

        public Optional<T> fetchOne(){
            try(
                    Connection co = DBConfig.createConnection();
                    PreparedStatement statement = co.prepareStatement(query);
            ) {

                for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                    switch (entry.getValue()){
                        case String string -> statement.setString(entry.getKey(), string);
                        case Long l -> statement.setLong(entry.getKey(), l);
                        default -> {}
                    }
                }

                try ( ResultSet rs = statement.executeQuery() ) {
                    if (rs.next()) {
                        return Optional.of(extractor.extract(rs));
                    }
                    return Optional.empty();
                }

            } catch (SQLException ex){
                throw new RuntimeException(ex);
            }

        }

    }

}
