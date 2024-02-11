package com.example.demo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductRepo repo;

	@PostMapping("/add")
	ResponseEntity<Product> addProduct(@RequestBody Product product) {

		Product pp = null;

		try {
			pp = service.save(product);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Product>(pp, HttpStatus.OK);

	}

//	@GetMapping("/getall")
//	Page<Product> getProduct(int page, int size) {
//
//		PageRequest pageRequest = PageRequest.of(page, size);
//		return repo.findAll(pageRequest);
//
//	}
	
	@GetMapping("/getall")
	public List<Product> getProduct1() {

		return repo.findAll();

	}
	

	@GetMapping("/id")
	ResponseEntity<Optional<Product>> getByid(@RequestParam int id) {

		Optional pp = service.findById(id);

		if (pp.isEmpty()) {
			return new ResponseEntity<Optional<Product>>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(pp, HttpStatus.OK);
		}

	}

	@DeleteMapping("/id")
	ResponseEntity<Optional<String>> deleteById(@RequestParam int id) {

		Optional<String> pp = service.deleteByid(id);

		if (pp.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(pp, HttpStatus.OK);

		}
	}

	@PutMapping("/id")
	public ResponseEntity<Product> updateEntityById(@RequestParam int id, @RequestBody Product updatedEntity) {
		Optional<Product> result = service.updateEntityById(id, updatedEntity);

		return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable int id) {

		Product product = repo.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));

		repo.delete(product);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

//	 @GetMapping("/product-download")
//		public ResponseEntity<Resource> download() throws IOException {
//			
//			String filename = "employee.csv";
//			ByteArrayInputStream actualData =   service.getActualData();
//
//			InputStreamResource file = new InputStreamResource(actualData);
//			
//			ResponseEntity<Resource> body =    ResponseEntity.ok()
//			.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,"attachement; filename="+filename)
//			.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
//			
//			return body;
//			
//		}

	@GetMapping("/download")
	public ResponseEntity<InputStreamResource> download() throws IOException {

		String fileName = "products.csv";

		ByteArrayInputStream inputStream = service.getDataDownload();

		InputStreamResource response = new InputStreamResource(inputStream);

		ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
				.header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=" + fileName)
				.contentType(org.springframework.http.MediaType.parseMediaType("application/vnd.ms-excel"))
				.body(response);

		return responseEntity;
	}

	@GetMapping("/download3")
	public ResponseEntity<InputStreamResource> downloadRecordsByDateRange(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws IOException {

		List<Product> products = service.getRecordsByDateRange(startDate, endDate);

		// Generate Excel file
		ByteArrayInputStream excelStream = ExcelGenerator.generateExcel(products);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=records.xlsx");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
				.body(new InputStreamResource(excelStream));
	}

	@GetMapping("/printProduct")
	public Page<Product> getAllProducts(Pageable pageable) {
		return repo.findAll(pageable);
	}

	
	@GetMapping("/by-name")
	public List<Product> getByName(@RequestParam String name){
		return repo.findByProductName(name);
	}
	
}
