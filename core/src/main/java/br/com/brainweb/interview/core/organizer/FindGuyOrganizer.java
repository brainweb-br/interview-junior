package br.com.brainweb.interview.core.organizer;

import br.com.brainweb.interview.model.enums.Race;
import br.com.brainweb.interview.model.parser.FindGuyParser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FindGuyOrganizer implements RowMapper<FindGuyParser> {
    @Override
    public FindGuyParser mapRow(ResultSet rs, int unused) throws SQLException {
        FindGuyParser organized = new FindGuyParser();

        organized.setId(UUID.fromString(rs.getString("id")));
        organized.setName(rs.getString("name"));
        organized.setRace(Race.valueOf(rs.getString("race")));
        organized.setStrength(rs.getInt("strength"));
        organized.setAgility(rs.getInt("agility"));
        organized.setDexterity(rs.getInt("dexterity"));
        organized.setIntelligence(rs.getInt("intelligence"));

        return organized;
    }
}
