package com.hugov.fuyuki.sheets.batch.config

import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.annotation.Bean
import org.springframework.boot.CommandLineRunner
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.stereotype.Component
import org.springframework.batch.core.Job

@Component
class BatchJobLauncher(jobLauncher: JobLauncher, generateAndUpdateSheet: Job) {
    @Bean
    def runJob: CommandLineRunner = (args: Array[String]) => {
        val jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis()) // Ajout d'un paramètre unique pour chaque exécution
            .toJobParameters()
        val jobExecution = jobLauncher.run(generateAndUpdateSheet, jobParameters)
    }
}

