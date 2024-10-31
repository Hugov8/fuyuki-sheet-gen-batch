package com.hugov.fuyuki.sheets


import com.hugov.fuyuki.sheets.batch.config.SheetGenerationJob
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import com.hugov.fuyuki.sheets.batch.config.SheetGenerationJob

@SpringBootApplication
class SheetGenerationApplication

object SheetGenerationApplication extends App {
    SpringApplication.run(classOf[SheetGenerationApplication])
}