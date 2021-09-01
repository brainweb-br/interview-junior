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
        return FindGuyParser.builder().id(UUID.fromString(rs.getString("id"))).name(rs.getString("name"))
                .race(Race.valueOf(rs.getString("race"))).strength(rs.getInt("strength"))
                .agility(rs.getInt("agility")).dexterity(rs.getInt("dexterity")).intelligence(rs.getInt("intelligence"))
                .build();
    }
}
