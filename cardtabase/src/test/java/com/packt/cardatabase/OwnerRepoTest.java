package com.packt.cardatabase;

import java.util.Arrays;  // java.util.Arrays 임포트

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@SpringBootTest
public class OwnerRepoTest {
    @Autowired
    private OwnerRepository oRepository;
    
    @Autowired
    private CarRepository cRepository;
    
    @Test
    public void insertTest() {
        // 소유자 만들고 데이터베이스에 저장
        Owner owner1 = new Owner("John", "Johneson");
        Owner owner2 = new Owner("Mary", "Roblinson");
        Owner owner3 = new Owner("Steve", "Smith");  // owner3 객체 추가
        
        oRepository.saveAll(Arrays.asList(owner1, owner2, owner3));  // 모든 소유자 저장
        
        // 자동차를 추가하고 소유자와 연결한 후 데이터베이스에 저장
        Car car1 = new Car("Ford", "Mustang", "Red", "ADF-1121", 2021, 59000, owner1);
        Car car2 = new Car("Nissan", "Leaf", "White", "SSJ-3002", 2019, 29000, owner2);
        Car car3 = new Car("Toyota", "Prius", "Silver", "KKO-0212", 2020, 39000, owner3);
        
        // 자동차 객체들을 저장
        cRepository.saveAll(Arrays.asList(car1, car2, car3));  
    }
}
