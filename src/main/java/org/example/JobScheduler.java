package org.example;

import java.time.Duration;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class JobScheduler {

    //Concepts Tested - Priority Queue, Comparable, encapsulation, clean OOP design.
    //System must execute the highest priority job first.
    //If two jobs have same priority, the one added earlier should run first.
    private final Queue<JobModel> jobs = new PriorityQueue<>(Comparator.comparing(JobModel::priority).reversed().thenComparing(JobModel::sequence));
    private final AtomicInteger sequence = new AtomicInteger();

    public boolean addJob(String name, int priority, int duration) {
        return jobs.offer(new JobModel(name, priority, duration, sequence.incrementAndGet()));
    }

    public JobModel runNextJob() throws InterruptedException {
        var jobRun = jobs.poll();
        if (jobRun == null) {
            System.out.println("No more jobs to run");
            return null;
        }
        System.out.println("Running job " +jobRun.name()+ " for " +jobRun.duration() + " ms");
        Thread.sleep(jobRun.duration());
        return jobRun;
    }

    public JobModel nextJobReadyToRun() {
        return jobs.peek();
    }

    public boolean hasJob() {
        return !jobs.isEmpty();
    }

    public static void main(String[] args) throws InterruptedException {
        JobScheduler scheduler = new JobScheduler();

        scheduler.addJob("Job1", 3, 2000);
        scheduler.addJob("Job2", 2, 1000);
        scheduler.addJob("Job3", 1, 5000);
        scheduler.addJob("Job4", 2, 1500);
        scheduler.addJob("Job5", 1, 1000);

        while (scheduler.hasJob()) {
            System.out.println("Next Job Ready to Run:: " + scheduler.nextJobReadyToRun());
            Thread.sleep(100);
            scheduler.runNextJob();
        }
    }
}
