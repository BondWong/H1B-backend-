package com.jh.honeb.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.jh.honeb.domain.Address;
import com.jh.honeb.domain.Company;
import com.jh.honeb.domain.Position;
import com.jh.honeb.domain.factory.CompanyFactory;
import com.jh.honeb.domain.factory.PositionFactory;
import com.jh.honeb.repository.CompanyRepository;
import com.jh.honeb.repository.PositionRepository;
import com.jh.honeb.web.helper.CollectionResult;
import com.jh.honeb.web.helper.NameTuple;

@RestController
@RequestMapping("/v1/")
public class PositionService {
	private static final int PAGESIZE = 30;
	@Autowired
	PositionRepository repository;
	@Autowired
	CompanyRepository cRepository;

	/*
	 * This method is trying to minimize duplication
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "position", consumes = MediaType.APPLICATION_JSON_VALUE, method = POST)
	public ResponseEntity<Void> create(@RequestBody List<Map<String, String>> data) {
		System.out.println(data);
		List<Position> positions = new ArrayList<Position>();
		List<Company> updatedCompanies = new ArrayList<Company>();
		Set<NameTuple> isVisited = new HashSet<NameTuple>();
		outer: for (Map<String, String> d : data) {
			NameTuple nt = new NameTuple(d.get("name"), d.get("title"));
			if (isVisited.contains(nt))
				continue;
			isVisited.add(nt);

			List<Map<String, Object>> tuples = cRepository.fetchPositions(d.get("name"));
			if (tuples == null || tuples.size() == 0) {
				Position position = PositionFactory.newInstance().create(d);
				Company company = CompanyFactory.newInstance().create(d);
				position.setCompany(company);
				positions.add(position);
				company.addPosition(position);
				continue;
			}

			Map<String, Object> tuple = tuples.get(0);
			if (tuple == null || tuple.isEmpty()) {
				Position position = PositionFactory.newInstance().create(d);
				Company company = CompanyFactory.newInstance().create(d);
				position.setCompany(company);
				positions.add(position);
				company.addPosition(position);
				continue;
			}

			List<Position> positionList = (List<Position>) tuple.get("positions");
			for (Position p : positionList)
				if (p.getName().equals((String) d.get("title")))
					continue outer;
			Position position = PositionFactory.newInstance().create(d);
			Company company = (Company) tuple.get("company");
			position.setCompany(company);
			company.setPositions(positionList);
			company.addPosition(position);
			updatedCompanies.add(company);
		}

		repository.save(positions);
		cRepository.save(updatedCompanies);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "position/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public Position getById(@PathVariable("id") Long id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "position/title/{title:.+}/{start:\\d{1,}}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public ResponseEntity<CollectionResult<Position>> getByTitle(@PathVariable("title") String title,
			@PathVariable("start") int start) {
		List<Map<String, Object>> data = repository.fetchByTitle("(?i).*" + title + ".*", start, PAGESIZE);
		List<Position> positions = toPositions(data);
		CollectionResult<Position> result = new CollectionResult<Position>(Lists.newArrayList(positions.iterator()),
				start, PAGESIZE, data.size() == PAGESIZE);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "position/title/{title:.+}/address/{address:.{1,}}/{start:\\d{1,}}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public ResponseEntity<CollectionResult<Position>> getByTitleAndLocation(@PathVariable("title") String title,
			@PathVariable("address") String address, @PathVariable("start") int start) {
		List<Map<String, Object>> data = repository.fetchByTitleAndLocation("(?i).*" + title + ".*",
				"(?i).*" + address + ".*", start, PAGESIZE);
		List<Position> positions = toPositions(data);
		CollectionResult<Position> result = new CollectionResult<Position>(Lists.newArrayList(positions.iterator()),
				start, PAGESIZE, data.size() == PAGESIZE);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "position/address/{address:.{1,}}/{start:\\d{1,}}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public ResponseEntity<CollectionResult<Position>> getByLocation(@PathVariable("address") String address,
			@PathVariable("start") int start) {
		List<Map<String, Object>> data = repository.fetchByLocation("(?i).*" + address + ".*", start, PAGESIZE);
		List<Position> positions = toPositions(data);
		CollectionResult<Position> result = new CollectionResult<Position>(Lists.newArrayList(positions.iterator()),
				start, PAGESIZE, data.size() == PAGESIZE);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "position/company/{company:.{1,}}/{start:\\d{1,}}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public ResponseEntity<CollectionResult<Position>> getByCompany(@PathVariable("company") String company,
			@PathVariable("start") int start) {
		List<Map<String, Object>> data = repository.fetchByCompany("(?i).*" + company + ".*", start, PAGESIZE);
		List<Position> positions = toPositions(data);
		CollectionResult<Position> result = new CollectionResult<Position>(Lists.newArrayList(positions.iterator()),
				start, PAGESIZE, data.size() == PAGESIZE);
		return ResponseEntity.ok(result);
	}

	private List<Position> toPositions(List<Map<String, Object>> data) {
		List<Position> positions = new ArrayList<Position>();
		for (Map<String, Object> d : data) {
			Position pos = (Position) d.get("position");
			pos.setCompany((Company) d.get("company"));
			pos.setLocation((Address) d.get("address"));
			pos.getCompany().setAddress((Address) d.get("companyAddress"));
			positions.add(pos);
		}

		return positions;
	}

}
