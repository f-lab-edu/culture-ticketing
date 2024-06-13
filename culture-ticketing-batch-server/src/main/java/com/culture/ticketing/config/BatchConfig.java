package com.culture.ticketing.config;

import com.culture.ticketing.application.ProducerService;
import com.culture.ticketing.infra.ShowRepositoryItemReader;
import com.culture.ticketing.show.domain.Show;
import com.culture.ticketing.show.infra.ShowRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ProducerService producerService;
    private final ShowRepository showRepository;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ProducerService producerService, ShowRepository showRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.producerService = producerService;
        this.showRepository = showRepository;
    }

    @Bean
    public Job makeBookingStartAlarm() {

        return jobBuilderFactory.get("makeBookingStartAlarm")
                .start(step())
                .build();
    }

    @Bean
    public Step step() {

        return stepBuilderFactory.get("makeBookingStartAlarmStep")
                .<Show, Show>chunk(500)
                .reader(reader())
                .writer(writer())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ShowRepositoryItemReader reader() {

        return new ShowRepositoryItemReader(showRepository, LocalDateTime.now(), 500);
    }

    @Bean
    public ItemWriter<Show> writer() {

        return new ItemWriter<Show>() {
            @Override
            public void write(List<? extends Show> shows) {
                for (Show show : shows) {
                    producerService.publishShowBookingStartNotifications(show);
                }
            }
        };
    }
}
