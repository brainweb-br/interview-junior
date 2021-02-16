package br.com.brainweb.interview.core.mapper;

import br.com.brainweb.interview.model.dto.HeroDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HeroMapper implements RowMapper<HeroDto> {
    @Override
    public HeroDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        HeroDto dto = new HeroDto();

        dto.setId(rs.getLong("id"));
        dto.setName(rs.getString("name"));
        dto.setRace(rs.getString("race"));
        dto.setStrength(rs.getLong("strength"));
        dto.setAgility(rs.getLong("agility"));
        dto.setDexterity(rs.getLong("dexterity"));
        dto.setIntelligence(rs.getLong("intelligence"));

        return dto;
    }
}
