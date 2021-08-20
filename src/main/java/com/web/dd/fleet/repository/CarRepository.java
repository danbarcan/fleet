package com.web.dd.fleet.repository;

import com.web.dd.fleet.entity.Car;
import com.web.dd.fleet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "car", path = "car")
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByUser(User u);
}
