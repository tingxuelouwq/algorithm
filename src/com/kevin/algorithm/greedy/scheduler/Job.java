package com.kevin.algorithm.greedy.scheduler;

/**
 * @Author kevin
 * @Date 2016/10/22 11:32
 */
public class Job {
    int id;         // job id
    int time;       // the usage of time to complete this job

    Job(int id, int time) {
        this.id = id;
        this.time = time;
    }
}
