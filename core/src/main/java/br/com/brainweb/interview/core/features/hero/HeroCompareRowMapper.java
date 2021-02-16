package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.dto.HeroCompareDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class HeroCompareRowMapper implements RowMapper<HeroCompareDTO> {

    @Override
    public HeroCompareDTO mapRow(ResultSet rs, int i) throws SQLException {

        return HeroCompareDTO.builder()
            .id(UUID.fromString(rs.getString("id")))
            .strength(rs.getInt("strength"))
            .agility(rs.getInt("agility"))
            .dexterity(rs.getInt("dexterity"))
            .intelligence(rs.getInt("intelligence"))
            .build();
    }
}
