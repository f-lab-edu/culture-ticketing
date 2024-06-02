package com.culture.ticketing.config;

import com.culture.ticketing.application.ProducerService;
import com.culture.ticketing.show.domain.Show;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ProducerService producerService;
    private final EntityManagerFactory entityManagerFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ProducerService producerService, EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.producerService = producerService;
        this.entityManagerFactory = entityManagerFactory;
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
    public JpaPagingItemReader<Show> reader() {

        LocalDateTime now = LocalDateTime.now();
        HashMap<String, Object> paramValues = new HashMap<>();
        paramValues.put("dateTimeStartLimit", now.plusHours(1).withSecond(0).withNano(0));
        paramValues.put("dateTimeEndLimit", now.plusHours(1).plusMinutes(1).withSecond(0).withNano(0));

        return new JpaPagingItemReaderBuilder<Show>()
                .name("jpaBookingStartDateTimeLeftAnHour")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(500)
                .queryString("select s from Show s " +
                        "where s.bookingStartDateTime >= :dateTimeStartLimit " +
                        "and s.bookingStartDateTime < :dateTimeEndLimit")
                .parameterValues(paramValues)
                .build();
    }

    @Bean
    public ItemWriter<Show> writer() {

        return new ItemWriter<Show>() {
            @Override
            public void write(List<? extends Show> shows) throws Exception {
                for (Show show : shows) {
                    producerService.createShowBookingStartNotifications(show);
                }
            }
        };
    }
}
