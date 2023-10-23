package com.ntica.springbatch.controller;

import com.ntica.springbatch.model.Tache;
import com.ntica.springbatch.repository.TacheRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tache")
public class TacheController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private Job job;

    @GetMapping("/startJob")
    public BatchStatus load() throws Exception{
        Map<String, JobParameter> params = new HashMap<>();
        params.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(params);
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        while (jobExecution.isRunning()){
            System.out.println("......");
        }
        return jobExecution.getStatus();
    }

    @GetMapping("taches")
    public List<Tache> tacheList(){
        return tacheRepository.findAll();
    }
}
