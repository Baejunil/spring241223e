package com.packt.cardatabase.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CarRepository  extends 
CrudRepository<Car, Long>
{
	//브랜드로 자동차를 검색
	List<Car> findByBrand(@Param String brand);
	
	
	
//	@Query 어노테이션을 이용하면 sql문으로 쿼리를 만들 수 도 있다
	@Query("select c from Car c where c.brand like %?1")
	List<Car> findByBrand(String brand);
}
