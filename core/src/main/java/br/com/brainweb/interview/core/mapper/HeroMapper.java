package br.com.brainweb.interview.core.mapper;

import br.com.brainweb.interview.model.dto.HeroWithStatsDto;

import org.springframework.jdbc.core.RowMapper;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HeroMapper implements RowMapper<HeroWithStatsDto> {

    @Override
    public HeroWithStatsDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        HeroWithStatsDto hero = new HeroWithStatsDto();
        hero.setId(rs.getLong("id"));
        hero.setName(rs.getString("name"));
        hero.setRace(rs.getString("race"));
        hero.setStrength(rs.getInt("strength"));
        hero.setAgility(rs.getInt("agility"));
        hero.setDexterity(rs.getInt("dexterity"));
        hero.setIntelligence(rs.getInt("intelligence"));
        hero.setCreatedAt(rs.getString("created_at").toString());
        hero.setCreatedAt(rs.getString("updated_at").toString());

        return hero;

    }

}
