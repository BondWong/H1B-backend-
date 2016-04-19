package com.jh.honeb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jh.honeb.domain.Position;

@Repository
@Transactional
public interface PositionRepository extends GraphRepository<Position> {
	@Query("match (ac: Address) <-[:at]- (c: Company) <-[:of]- (p: Position) -[:at]-> (a: Address) "
			+ "where p.name =~ {0} return p as position, c as company, a as address, ac as companyAddress skip {1} limit {2}")
	List<Map<String, Object>> fetchByTitle(String title, int start, int pageSize);

	@Query("match (ac: Address) <-[:at]- (c: Company) <-[:of]- (p: Position) -[:at]-> (a: Address) "
			+ "where p.name =~ {0} and (a.city =~ {1} or a.state =~ {1}) "
			+ "return p as position, c as company, a as address, ac as companyAddress skip {2} limit {3}")
	List<Map<String, Object>> fetchByTitleAndLocation(String title, String address, int start, int pageSize);

	@Query("match (ac: Address) <-[:at]- (c: Company) <-[:of]- (p: Position) -[:at]-> (a: Address) "
			+ "where a.city =~ {0} or a.state =~ {0}"
			+ "return p as position, c as company, a as address, ac as companyAddress skip {1} limit {2}")
	List<Map<String, Object>> fetchByLocation(String address, int start, int pageSize);

	@Query("match (ac: Address) <-[:at]- (c: Company) <-[:of]- (p: Position) -[:at]-> (a: Address) "
			+ "where c.name =~ {0}"
			+ "return p as position, c as company, a as address, ac as companyAddress skip {1} limit {2}")
	List<Map<String, Object>> fetchByCompany(String company, int start, int pageSize);
}
