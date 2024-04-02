package be.tftic.java.dao;

import be.tftic.java.models.Section;
import be.tftic.java.utils.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SectionDAOImpl implements ISectionDAO {

    @Override
    public List<Section> getAll() {
        String query = """
                       SELECT *
                       FROM section
                       """;

        try(
                Connection co = DBConfig.createConnection();
                PreparedStatement statement = co.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
        ) {

            List<Section> sections = new ArrayList<>();
            while( rs.next() ){
//                Section section = extract(rs);
                sections.add( extract(rs) );
            }
            return sections;

        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<Section> getOne(long id) {
        String query = """
                       SELECT *
                       FROM section
                       WHERE section_id = ?
                       """;

        try (
            Connection co = DBConfig.createConnection();
            PreparedStatement stmt = co.prepareStatement(query);
        ){
            stmt.setLong(1, id);
            try( ResultSet rs = stmt.executeQuery(); ){
                if( rs.next() ){
//                Section section = extract(rs);
                    return Optional.of( extract(rs) );
                }
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // 1-connection
    // 2-Statement
    // 3-execute(query)

    // 1- connection
    // 2- PreparedStatement(query)
    // 3- setParams
    // 4- execute

    @Override
    public void insert(Section toInsert) {
        String query = """
                       INSERT INTO section (
                            section_id,
                            section_name,
                            delegate_id
                       )
                       VALUES
                       (?, ?, ?)
                       """;

        try (
                Connection co = DBConfig.createConnection();
                PreparedStatement preparedStmt = co.prepareStatement(query);
        ){
            preparedStmt.setLong(1, toInsert.id());
            preparedStmt.setString(2, toInsert.name());
            preparedStmt.setLong(3, toInsert.delegateId());

            preparedStmt.executeUpdate();
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    private Section extract(ResultSet rs) throws SQLException {
        long id = rs.getLong("section_id");
        String name = rs.getString("section_name");
        long delegateId = rs.getLong("delegate_id");

        return new Section(id,name,delegateId);
    }
}
