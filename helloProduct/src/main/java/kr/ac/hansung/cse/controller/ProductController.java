package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.producttable;
import kr.ac.hansung.cse.repo.ProductRepository;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ProductController {

	@Autowired
	ProductRepository repository;

	@GetMapping("/products")
	public ResponseEntity<List<producttable>> getAllProducts() {
		List<producttable> products = new ArrayList<>();
		try {
			repository.findAll().forEach(products::add);

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<producttable> getProductById(@PathVariable("id") long id) {
		Optional<producttable> productData = repository.findById(id);

		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(value = "products/category/{category}")
	public ResponseEntity<List<producttable>> findByCategory(@PathVariable String category) {
		try {
			List<producttable> customers = repository.findByCategory(category);

			if (customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}


	@PostMapping(value = "/products")
	public ResponseEntity<producttable> postProduct(@RequestBody producttable product) {
		try {
			producttable _product = repository.save(new producttable(product.getName(), product.getCategory(),product.getPrice(), product.getManufacturer(), product.getUnitInStock(), product.getDescription()));
			return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/products")
	public ResponseEntity<HttpStatus> deleteAllProducts() {
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

	
	@PutMapping("/products/{id}")
	public ResponseEntity<producttable> updateProduct(@PathVariable("id") long id, @RequestBody producttable product) {
		Optional<producttable> productData = repository.findById(id);

		if (productData.isPresent()) {
			producttable _product = productData.get();
			_product.setName(product.getName());
			_product.setCategory(product.getCategory());
			_product.setPrice(product.getPrice());
			_product.setManufacturer(product.getManufacturer());
			_product.setUnitInStock(product.getUnitInStock());
			_product.setDescription(product.getDescription());
			
			
			return new ResponseEntity<>(repository.save(_product), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}