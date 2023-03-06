package n.batch.newBatch.model;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
public class BatchConfig {

//    private NotificationListener notificationListener;
//
//    public BatchConfig(NotificationListener notificationListener) {
//        this.notificationListener = notificationListener;
//    }

    @Bean
    public FlatFileItemReader<User> reader1() {
        System.out.println("batch item reader===============");
        return new FlatFileItemReaderBuilder<User>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
                    setTargetType(User.class);
                }})
                .build();
    }

    @Bean
    public Processor processor() {
        return new Processor();
    }

    @Bean
    public JdbcBatchItemWriter<User> writer(DataSource dataSource) {
        System.out.println("batch item writer===============");
        return new JdbcBatchItemWriterBuilder<User>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO user (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .build();
    }
    @Bean
    public Job importUserJob(JobRepository jobRepository,
                             NotificationListener listener, Step step1) {
        System.out.println("importUserJob==============");
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new NotificationListener())
                .flow(step1)
                .end()
                .build();
    }

//    @Bean
//    public NotificationListener listener() {
//        return new NotificationListener();
//    }

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager, JdbcBatchItemWriter<User> writer) {
//        FlatFileItemReaderBuilder<User> reader1= reader1();
        System.out.println("step1===============");
        return new StepBuilder("step1", jobRepository)
                .<User, User> chunk(10, transactionManager)
                .reader(reader1())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
