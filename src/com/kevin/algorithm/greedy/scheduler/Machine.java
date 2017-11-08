package com.kevin.algorithm.greedy.scheduler;

/**
 * @Author kevin
 * @Date 2016/10/22 11:31
 */
public class Machine implements Comparable<Machine> {
    int id;         // machine id
    int avail;      // free degree(the completion time when all the jobs in this machine were completed)

    Machine(int id, int avail) {
        this.id = id;
        this.avail = avail;
    }

    @Override
    public int compareTo(Machine o) {
        return avail < o.avail ? -1 : avail == o.avail ? 0 : 1;
    }
}