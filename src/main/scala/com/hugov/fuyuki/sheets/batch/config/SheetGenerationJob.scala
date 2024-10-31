package com.hugov.fuyuki.sheets.batch.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Value
import org.springframework.batch.core.Job
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import com.hugov.fuyuki.sheets.model.connecteur.sheets.SpreadSheetUtil
import com.hugov.fuyuki.sheets.model.connecteur.sheets.DriveUtil
import org.springframework.context.annotation.Configuration
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource
import org.springframework.transaction.TransactionManager
import com.hugov.fuyuki.sheets.batch.tasklet.CreateSpreadsheetTasklet
import batch.reader._
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.ItemProcessor
import com.hugov.fuyuki.sheets.batch.util.WarContext
import com.hugov.fuyuki.sheets.model.data.War
import com.hugov.fuyuki.sheets.batch.util.WarContext
import com.hugov.fuyuki.sheets.batch.tasklet.WarSaveTasklet
import com.hugov.fuyuki.sheets.model.data.IdRow
import com.hugov.fuyuki.sheets.model.data.Row
import com.hugov.fuyuki.sheets.batch.processor.RowItemProcessor
import com.hugov.fuyuki.sheets.batch.writer.RowItemWriter
import com.hugov.fuyuki.sheets.batch.util.BatchConstantes
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.context.annotation.Import

@Configuration
class SheetGenerationJob extends configuration.support.DefaultBatchConfiguration {

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

    //========================================
    // Mise en contexte de la war
    //========================================
    // @Bean
    // def warContext(): WarContext = new WarContext()

    // @Bean
    // def getRowIds(miseEnContexteWar: Tasklet,
    //     jobRepository: JobRepository, 
    //     transactionManager: PlatformTransactionManager): Step = {
    //         new StepBuilder("getRowIds", jobRepository)
    //         .tasklet(miseEnContexteWar)
    //         .transactionManager(transactionManager)
    //         .build
    // }

    // @Bean
    // def miseEnContexteWar(@Qualifier("warContext") warContext: WarContext): Tasklet = new WarSaveTasklet("101", warContext)


    // =======================================
    // Création et partage de la spreadsheet
    // =======================================
    @Bean
    def createSpreadSheet(createSpreadSheetTasklet: Tasklet,
        jobRepository: JobRepository, 
        transactionManager: PlatformTransactionManager
        ): Step = {
        new StepBuilder("createSpreadSheet", jobRepository)
            .tasklet(createSpreadSheetTasklet)
            .transactionManager(transactionManager)
            .build
    }

    @Bean
    def createSpreadSheetTasklet(): Tasklet = return new CreateSpreadsheetTasklet(mail, s"Test French")
    

    // =======================================
    // Création/Update des sheets
    // =======================================
    // @Bean
    // def updateSheet(updateSheetReader: ItemReader[IdRow],
    //     updateSheetProcessor: ItemProcessor[IdRow, Row],
    //     updateSheetWriter: ItemWriter[Row],
    //     jobRepository: JobRepository, 
    //     transactionManager: PlatformTransactionManager): Step = {
    //         new StepBuilder("updateSheet", jobRepository).chunk[IdRow, Row](10, transactionManager)
    //         .reader(updateSheetReader)
    //         .processor(updateSheetProcessor)
    //         .writer(updateSheetWriter)
    //         .build
    // }

    // @Bean
    // def updateSheetReader(miseEnContexteWar: WarContext) : RowIdReader = new RowIdReader(miseEnContexteWar.war.idRows)

    // @Bean
    // def updateSheetProcessor() : RowItemProcessor = new RowItemProcessor()

    // @Bean
    // @JobScope
    // def updateSheetWriter(@Value(s"#[jobContextExecution['idSpreadsheet']]") idSpreadSheet: String) : RowItemWriter = new RowItemWriter(idSpreadSheet)

    @Bean
    def dataSource(): DataSource = {
        val dataSource = new DriverManagerDataSource()
        dataSource.setDriverClassName("org.sqlite.JDBC")
        dataSource.setUrl("jdbc:sqlite:mydatabase.db")
        dataSource
    }

    @Bean
    def transactionManager(dataSource: DataSource): PlatformTransactionManager = new DataSourceTransactionManager(dataSource)

    // @Bean
    // def jobRegistry(): configuration.JobRegistry = new configuration.support.MapJobRegistry()

    // @Bean
    // def jobRepository(dataSource: DataSource, transactionManager: PlatformTransactionManager): JobRepository = {
    //     val factory = new JobRepositoryFactoryBean()
    //     factory.setDataSource(dataSource)
    //     factory.setTransactionManager(transactionManager)
    //     factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE") // Niveau d'isolation pour SQLite
    //     factory.afterPropertiesSet()
    //     factory.getObject
    // }
}