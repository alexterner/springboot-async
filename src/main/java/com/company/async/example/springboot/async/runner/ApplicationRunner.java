package com.company.async.example.springboot.async.runner;

import com.company.async.example.springboot.async.dal.entity.Site;
import com.company.async.example.springboot.async.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    private final SiteService siteService;

    public ApplicationRunner(SiteService siteService) {
        this.siteService = siteService;
    }


    @Override
    public void run(String... args) throws Exception {

        logger.info("==> Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.", Arrays.toString(args));

        siteService.addSite(true, "Canada");
        siteService.addSite(true, "Australia");
        siteService.addSite(true, "New_Zealand");

        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<Site> page1 = siteService.findSite("Canada");
        CompletableFuture<Site> page2 = siteService.findSite("Australia");
        CompletableFuture<Site> page3 = siteService.findSite("New_Zealand");

        // Wait until they are all done
        CompletableFuture.allOf(page1,page2,page3).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());

    }
}
