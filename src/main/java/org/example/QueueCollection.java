/*
package org.example;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class QueueCollection {

    static Queue<JobModel> jobs = new PriorityQueue<>(Comparator.comparing(JobModel::priority));

    public static boolean addJob(JobModel job) {
        return jobs.add(job);
    }

    public static void main(String[] args) {
        addJob(new JobModel("Job1", 4));
        addJob(new JobModel("Job2", 2));
        addJob(new JobModel("Job3", 1));
        addJob(new JobModel("Job4", 1));

        System.out.println(jobs.peek().jobId());
        System.out.println(jobs.size());
        jobs.poll();
        System.out.println(jobs.peek().jobId());
        System.out.println(jobs.size());
    }
}
*/
