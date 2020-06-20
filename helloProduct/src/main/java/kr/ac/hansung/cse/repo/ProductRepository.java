package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.producttable;

public interface ProductRepository extends CrudRepository<producttable, Long> {
	List<producttable> findByCategory(String category);
}