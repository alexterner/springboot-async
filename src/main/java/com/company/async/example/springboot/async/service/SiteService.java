package com.company.async.example.springboot.async.service;


import com.company.async.example.springboot.async.dal.entity.Site;
import com.company.async.example.springboot.async.dal.repository.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
//@Transactional(readOnly = true)
public class SiteService {

    private final Logger log = LoggerFactory.getLogger(SiteService.class);

    @Autowired
    private SiteRepository siteRepository;


    @Async
    public Future<String> process() throws InterruptedException {
        log.info("###Start Processing with Thread id: " + Thread.currentThread().getId());

        // Sleep 3s for simulating the processing
        Thread.sleep(3000);

        String processInfo = String.format("Processing is Done with Thread id= %d", Thread.currentThread().getId());
        return new AsyncResult<>(processInfo);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture <Site> findSite(String  name) throws ExecutionException, InterruptedException {
        log.info("### Start Processing with Thread id: " + Thread.currentThread().getId());
        return CompletableFuture.supplyAsync ( () -> siteRepository.findByName( name ) );
    }

    @Transactional(readOnly = true)
    public Iterable<Site> getSites(){
        return siteRepository.findAll();
    }


    @Transactional
    public Site addSite(boolean isActive, String name){
        return siteRepository.save(new Site( isActive, name));
    }


    @Transactional(readOnly = true)
    public Iterable<Site> getActiveSites(int page, int size){
        return siteRepository.findByIsActive(true, new PageRequest( page, size ));
    }

    @Transactional
    public Site updateSite(Site newSite){
         Site existingSite = siteRepository.findById( newSite.getId() );
         if( existingSite != null ){
             newSite.setId( existingSite.getId() );
             return siteRepository.save( newSite );
         } else{
             log.warn("Can't find site id" + newSite.getId() );
         }
        return null;
    }
}
