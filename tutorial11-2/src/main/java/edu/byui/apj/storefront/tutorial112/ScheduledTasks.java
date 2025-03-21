package edu.byui.apj.storefront.tutorial112;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import org. slf4j. Logger;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));

    }

    @Scheduled(cron = "0 31 19 * * *")
    public void additionalTask() {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        try {
            List<String> names = Arrays.asList(
                    "Alice", "Bob", "Charlie", "David", "Emma",
                    "Frank", "Grace", "Henry", "Ivy", "Jack",
                    "Karen", "Liam", "Mia", "Noah", "Olivia",
                    "Paul", "Quinn", "Ryan", "Sophia", "Thomas"
            );

            int middle = names.size() / 2;

            Callable<Void> task1 = () -> {
                List<String> sublist = names.subList(0, middle);
                for (String name : sublist) {
                    System.out.println(name + " " + dateFormat.format(new Date()));
                }
                return null;
            };

            Callable<Void> task2 = () -> {
                List<String> sublist = names.subList(middle, names.size());
                for (String name : sublist) {
                    System.out.println(name + " " + dateFormat.format(new Date()));
                }
                return null;
            };

            List<Callable<Void>> tasks = Arrays.asList(task1, task2);

            for (Future<Void> future : executorService.invokeAll(tasks)) {
                future.get();
            }

            System.out.println("All done here!");
        } catch  (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}