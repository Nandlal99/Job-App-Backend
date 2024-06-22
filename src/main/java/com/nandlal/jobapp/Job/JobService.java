package com.nandlal.jobapp.Job;

import java.util.List;

public interface JobService {
    List<Job> findAll();

    void createJob(Job job);

    Job getJobById(Long id);

    boolean deletedById(Long id);

    boolean updateJob(Long id, Job job);
}
