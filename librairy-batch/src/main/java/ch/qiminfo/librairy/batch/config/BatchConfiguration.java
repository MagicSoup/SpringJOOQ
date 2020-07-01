package ch.qiminfo.librairy.batch.config;

import ch.qiminfo.librairy.batch.processor.AuthorBean;
import ch.qiminfo.librairy.batch.processor.AuthorCsv;
import ch.qiminfo.librairy.batch.processor.AuthorProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("classpath:sql/insert-into-author.sql")
    private Resource insertAuthorSQL;

    @Bean
    public FlatFileItemReader<AuthorCsv> reader() {
        return new FlatFileItemReaderBuilder<AuthorCsv>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data-author.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<AuthorCsv>() {{
                    setTargetType(AuthorCsv.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<AuthorCsv, AuthorBean> processor() {
        return new AuthorProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<AuthorBean> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<AuthorBean>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(asString(this.insertAuthorSQL))
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importAuthorJob(JobCompletionNotificationListener listener, Step step1) {
        return this.jobBuilderFactory.get("importAuthorJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<AuthorBean> writer) {
        return this.stepBuilderFactory.get("step1")
                .<AuthorCsv, AuthorBean>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    private static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}