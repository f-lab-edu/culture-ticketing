package com.culture.ticketing.config;

import com.culture.ticketing.application.ProducerService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ProducerService producerService;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ProducerService producerService) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.producerService = producerService;
    }

    @Bean
    public Job makeBookingStartAlarm() {

        return jobBuilderFactory.get("makeBookingStartAlarm")
                .start(firstStep())
                .build();
    }

    @Bean
    public Step firstStep() {

        return stepBuilderFactory.get("firstStep")
                .tasklet((contribution, chunkContext) -> {

                    producerService.createShowBookingStartNotifications();

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
