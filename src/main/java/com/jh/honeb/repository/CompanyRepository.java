package com.jh.honeb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jh.honeb.domain.Company;

@Transactional
@Repository
public interface CompanyRepository extends GraphRepository<Company> {
	@Query("match (c: Company {name: {0}}) -[:has]-> (p: Position) return c as company, collect(p) as positions")
	public List<Map<String, Object>> fetchPositions(String name);
}
