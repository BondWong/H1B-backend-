package com.jh.honeb.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import com.jh.honeb.domain.Position;
import com.jh.honeb.domain.factory.Factory;
import com.jh.honeb.repository.PositionRepository;
import com.jh.honeb.web.helper.CollectionResult;

@RestController
@RequestMapping("/v1/")
public class PositionService {
	private static final int PAGESIZE = 10;
	@Autowired
	PositionRepository repository;

	@RequestMapping(value = "position", consumes = MediaType.APPLICATION_JSON_VALUE, method = POST)
	public ResponseEntity<Void> create(@RequestBody List<Map<String, String>> data) {
		List<Position> positions = new ArrayList<Position>();
		for (Map<String, String> d : data)
			positions.add(Factory.newInstance().create(d));
		repository.save(positions);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "position/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public Position getById(@PathVariable("id") Long id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "position/title/{title:.+}/{start:\\d{1,}}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public ResponseEntity<CollectionResult<Position>> getByTitle(@PathVariable("title") String title,
			@PathVariable("start") int start) {
		title = title.toLowerCase();
		Pageable pageable = new PageRequest(start, PAGESIZE);
		List<Position> data = repository.fetchByTitle(title, pageable);
		CollectionResult<Position> result = new CollectionResult<Position>(Lists.newArrayList(data.iterator()), start,
				PAGESIZE, data.size() == PAGESIZE);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "position/title/{title:.+}/address/{address:\\w{1,}}/{start:\\d{1,}}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public ResponseEntity<CollectionResult<Position>> getByTitleAndLocation(@PathVariable("title") String title,
			@PathVariable("address") String address, @PathVariable("start") int start) {
		title = title.toLowerCase();
		address = address.toLowerCase();
		Pageable pageable = new PageRequest(start, PAGESIZE);
		List<Position> data = repository.fetchByTitleAndLocation(title, address, pageable);
		CollectionResult<Position> result = new CollectionResult<Position>(Lists.newArrayList(data.iterator()), start,
				PAGESIZE, data.size() == PAGESIZE);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "position/address/{address:\\w{1,}}/{start:\\d{1,}}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public ResponseEntity<CollectionResult<Position>> getByLocation(@PathVariable("address") String address,
			@PathVariable("start") int start) {
		address = address.toLowerCase();
		Pageable pageable = new PageRequest(start, PAGESIZE);
		List<Position> data = repository.fetchByLocation(address, pageable);
		CollectionResult<Position> result = new CollectionResult<Position>(Lists.newArrayList(data.iterator()), start,
				PAGESIZE, data.size() == PAGESIZE);
		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "position/company/{company:.{1,}}/{start:\\d{1,}}", produces = MediaType.APPLICATION_JSON_VALUE, method = GET)
	public ResponseEntity<CollectionResult<Position>> getByCompany(@PathVariable("company") String company,
			@PathVariable("start") int start) {
		company = company.toLowerCase();
		Pageable pageable = new PageRequest(start, PAGESIZE);
		List<Position> data = repository.fetchByCompany(company, pageable);
		CollectionResult<Position> result = new CollectionResult<Position>(Lists.newArrayList(data.iterator()), start,
				PAGESIZE, data.size() == PAGESIZE);
		return ResponseEntity.ok(result);
	}

}
