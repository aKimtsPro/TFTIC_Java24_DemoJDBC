package be.tftic.java.dao;

import be.tftic.java.models.Section;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SectionRepository implements ISectionDAO{


    @Override
    public List<Section> getAll() {
        return QueryCreator.createQuery("SELECT * FROM section", this::extract)
                .fetchList();
    }

    public List<Section> getStartsWith(String startWith){
        return QueryCreator.createQuery("SELECT * FROM section WHERE section_name LIKE ?%", this::extract)
                .withParam(1, startWith)
                .fetchList();
    }

    @Override
    public Optional<Section> getOne(long id) {
        return QueryCreator.createQuery("SELECT * FROM section WHERE section_id = ?", this::extract)
                .withParam(1, id)
                .fetchOne();
    }

    @Override
    public void insert(Section toInsert) {

    }

    private Section extract(ResultSet rs) throws SQLException {
        long id = rs.getLong("section_id");
        String name = rs.getString("section_name");
        long delegateId = rs.getLong("delegate_id");

        return new Section(id,name,delegateId);
    }
}
