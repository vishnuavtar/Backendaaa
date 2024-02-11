package com.country;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class CountryController {
	
	@Autowired
	CountryRepo crepo;

	
	@PostMapping("/addcountry")
	public Country save(@RequestBody Country c) {
		return crepo.save(c);
	}
	
	@GetMapping("/id")
	public Optional<Country> findById(@RequestParam int id) {
		return crepo.findById(id);
	}
	
	@GetMapping("/getallcountry")
	public List<Country> findAll(){
		return crepo.findAll();
	}
}
