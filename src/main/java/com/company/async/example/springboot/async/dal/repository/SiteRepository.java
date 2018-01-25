package com.company.async.example.springboot.async.dal.repository;



import com.company.async.example.springboot.async.dal.entity.Site;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface SiteRepository extends CrudRepository<Site, Integer> {

    List<Site> findByIsActive(boolean isActive, Pageable pageable);

    Site findById(Long id);

    @Async
    CompletableFuture<Site> findByName(String name);

}
