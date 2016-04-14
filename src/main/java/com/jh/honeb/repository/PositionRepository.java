package com.jh.honeb.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jh.honeb.domain.Position;

@Repository
@Transactional
public interface PositionRepository extends GraphRepository<Position> {
	@Query("match (p: Position) where p.name =~ {0} return p")
	List<Position> fetchByTitle(String title, Pageable pageable);

	@Query("match (p: Position) -[:at]-> (a: Address) where p.name =~ {0} and (a.city =~ {1} or a.state =~ {1}) return p")
	List<Position> fetchByTitleAndLocation(String title, String address, Pageable pageable);

	@Query("match (p: Position) -[:at]-> (a: Address) where a.city =~ {0} or a.state =~ {0} return p")
	List<Position> fetchByLocation(String address, Pageable pageable);

	@Query("match (p: Position) -[:of]-> (c: Company) where c.name =~ {0} return p")
	List<Position> fetchByCompany(String company, Pageable pageable);
}
