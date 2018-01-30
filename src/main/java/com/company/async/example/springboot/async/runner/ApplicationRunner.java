package com.company.async.example.springboot.async.runner;

import com.company.async.example.springboot.async.dal.entity.Site;
import com.company.async.example.springboot.async.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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

        /*Site page1 = siteService.findSite("Canada");
        Site page2 = siteService.findSite("Australia");
        Site page3 = siteService.findSite("New_Zealand");*/

        // Wait until they are all done
        CompletableFuture.allOf(page1,page2,page3).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());

        /*logger.info("--> " + page1);
        logger.info("--> " + page2);
        logger.info("--> " + page3);*/

       /* Future<String> process1 = siteService.process();
        Future<String> process2 = siteService.process();
        Future<String> process3 = siteService.process();

        // Wait until They are all Done
        // If all are not Done. Pause 2s for next re-check
        while(!(process1.isDone() && process2.isDone() && process3.isDone())){
            Thread.sleep(2000);
        }
        logger.info("All Processes are DONE!");
        // Log results
        logger.info("Process 1: " + process1.get());
        logger.info("Process 2: " + process2.get());
        logger.info("Process 3: " + process3.get());*/


    }
}
