package repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DropDownRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getMakeDropDownList() {
		return jdbcTemplate.queryForList(
				"select * from RLA_DPDN_MAKE where MAKE_TYPE = 'Make' and MAKE_ACT = 'Y' order by MAKE_DESC");
	}

	public List<Map<String, Object>> getModelDropDownList(int makeID) {
		return jdbcTemplate
				.queryForList("select * from RLA_DPDN_MODL where MAKE_ID = " + makeID + " order by MODL_DESC");
	}

	public List<Map<String, Object>> getYearDropDownList() {
		return jdbcTemplate.queryForList("select * from RLA_DPDN_YEAR where YEAR_ACT = 'Y' order by YEAR_DESC;");
	}
}
