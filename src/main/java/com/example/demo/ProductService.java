package com.example.demo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	private ProductRepo repo;
	Product pp = new Product();

	public Product save(Product product) {
		product.setDate(new Date());
		return repo.save(product);		
	}

	public Page<Product> findAll(int pageNumber, int pageSize) {
		
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

		return repo.findAll(pageRequest);
	}

	public Optional<Product> findById(int id) {
		return repo.findById(id);

	}

	public Optional<String> deleteByid(int id) {
		repo.deleteById(id);

		return Optional.ofNullable("Deleted id is = " + id);

	}

	public Optional<Product> updateEntityById(int id, Product updatedEntity) {
		Optional<Product> existingEntityOptional = repo.findById(id);

		if (existingEntityOptional.isPresent()) {
			Product existingEntity = existingEntityOptional.get();

			// Update the fields you want to update
			existingEntity.setProductName(updatedEntity.getProductName());
			existingEntity.setProductPrice(updatedEntity.getProductPrice());
			// Update other fields...

			repo.save(existingEntity);
			return Optional.of(existingEntity);
		} else {
			return Optional.empty(); // Entity with the given ID not found
		}
	}

//	
//	public ByteArrayInputStream getActualData() throws IOException {
//		List<Product> all = repo.findAll();
//		
//		ByteArrayInputStream byteArrayInputStream = MalwareHistoryDownloadHelper.dataToExcel(all);
//		return byteArrayInputStream;
//	}

	ByteArrayInputStream getDataDownload() throws IOException {

		List<Product> products = repo.findAll();

		ByteArrayInputStream data = UtilDownload.dataToExcel(products);

		return data;

	}

//	public ByteArrayInputStream getDataDownload2(Date startDate, Date endDate) throws IOException {
//		// TODO Auto-generated method stub
//		
//		List<Product> products  = repo.findByModifyDateBetween(startDate,endDate);
//		ByteArrayInputStream data = UtilDownload.dataToExcel(products);
//
//		return data;
//	}
	
//	Pageable pageable = PageRequest.of(page, size);
//    return  malwareUpdateCountAuditRepository.findByModifyDateBetween(startDate, endDate, pageable);

	
	   public List<Product> getRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
	        return repo.findByDateBetween(startDate, endDate);
	    }

	public List<Product> getRecordsByDateRange(Date startDate, Date endDate) {
		 return repo.findByDateBetween(startDate, endDate);
	}
	
	
}
