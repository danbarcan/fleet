package com.web.dd.fleet.repository;

import com.web.dd.fleet.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "operation", path = "operation")
public interface OperationRepository extends JpaRepository<Operation, Long> {
}
