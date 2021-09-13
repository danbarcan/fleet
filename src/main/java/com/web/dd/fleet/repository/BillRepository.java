package com.web.dd.fleet.repository;

import com.web.dd.fleet.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bill", path = "bill")
public interface BillRepository extends JpaRepository<Bill, Long> {
}
