package com.hugov.fuyuki.sheets.batch.config

import org.springframework.context.annotation.Bean
import org.springframework.batch.core.Job
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.context.annotation.Configuration
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource
import org.springframework.transaction.TransactionManager
import com.hugov.fuyuki.sheets.batch.tasklet.CreateSpreadsheetTasklet
import com.hugov.fuyuki.sheets.batch.util.BatchConstantes
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.launch.support.RunIdIncrementer

@Configuration
class SheetGenerationJob extends DefaultBatchConfiguration {

    val mail: String = ???

    @Bean
    def generateAndUpdateSheet(
        getRowIds: Step,
        jobRepository: JobRepository,
        createSpreadSheet: Step
        // @Value("#[jobParameters['init']]") init: Boolean
    ): Job = {
        new JobBuilder("generateAndUpdateSheet", jobRepository)
            .incrementer(new RunIdIncrementer())
            // .start(getRowIds)
            .start(createSpreadSheet)
            .build()
    }


    // =======================================
    // Création et partage de la spreadsheet
    // =======================================
    @Bean
    def createSpreadSheet(createSpreadSheetTasklet: Tasklet,
        jobRepository: JobRepository, 
        transactionManager: PlatformTransactionManager
        ): Step = {
        new StepBuilder("createSpreadSheet", jobRepository)
            .tasklet(createSpreadSheetTasklet, transactionManager)
            .build
    }

    @Bean
    @JobScope
    def createSpreadSheetTasklet(): Tasklet = return new CreateSpreadsheetTasklet(mail, s"Test French")
    

    // =======================================
    // Création/Update des sheets
    // =======================================

    @Bean
    def dataSource(): DataSource = {
        val dataSource = new DriverManagerDataSource()
        dataSource.setDriverClassName("org.sqlite.JDBC")
        dataSource.setUrl("jdbc:sqlite:mydatabase.db")
        dataSource
    }

    @Bean
    def transactionManager(dataSource: DataSource): PlatformTransactionManager = new DataSourceTransactionManager(dataSource)

}