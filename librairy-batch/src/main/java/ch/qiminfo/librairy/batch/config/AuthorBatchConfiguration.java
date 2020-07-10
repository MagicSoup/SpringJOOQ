package ch.qiminfo.librairy.batch.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import ch.qiminfo.librairy.batch.processor.AuthorProcessor;
import ch.qiminfo.librairy.batch.processor.FilterAuthorProcessor;
import ch.qiminfo.librairy.batch.processor.bean.AuthorBean;
import ch.qiminfo.librairy.batch.processor.bean.AuthorCsvBean;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

/**
 * The type Batch configuration.
 */
@Configuration
@EnableBatchProcessing
public class AuthorBatchConfiguration {

    @Value("classpath:sql/insert-into-author.sql")
    private Resource insertAuthorSQL;

    private static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Reader flat file item reader.
     *
     * @return the flat file item reader
     */
    @Bean
    public FlatFileItemReader<AuthorCsvBean> reader() {
        BeanWrapperFieldSetMapper<AuthorCsvBean> authorCsvBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        authorCsvBeanWrapperFieldSetMapper.setTargetType(AuthorCsvBean.class);
        return new FlatFileItemReaderBuilder<AuthorCsvBean>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data-author.csv"))
                .delimited()
                .names("firstName", "lastName", "externalUid")
                .fieldSetMapper(authorCsvBeanWrapperFieldSetMapper)
                .build();
    }

    /**
     * Composite author processor item processor.
     *
     * @param authorProcessor       the author processor
     * @param filterAuthorProcessor the filter author processor
     * @return the item processor
     */
    @Bean
    public ItemProcessor<AuthorCsvBean, AuthorBean> compositeAuthorProcessor(AuthorProcessor authorProcessor,
                                                                             FilterAuthorProcessor filterAuthorProcessor) {
        CompositeItemProcessor<AuthorCsvBean, AuthorBean> processor = new CompositeItemProcessor<>();
        processor.setDelegates(Lists.newArrayList(authorProcessor, filterAuthorProcessor));
        return processor;
    }

    /**
     * Writer jdbc batch item writer.
     *
     * @param dataSource the data source
     * @return the jdbc batch item writer
     */
    @Bean
    public JdbcBatchItemWriter<AuthorBean> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<AuthorBean>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(asString(this.insertAuthorSQL))
                .dataSource(dataSource)
                .build();
    }

    /**
     * Step 1 step.
     *
     * @param stepBuilderFactory       the step builder factory
     * @param compositeAuthorProcessor the composite author processor
     * @param writer                   the writer
     * @return the step
     */
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory,
                      ItemProcessor<AuthorCsvBean, AuthorBean> compositeAuthorProcessor,
                      ItemWriter<AuthorBean> writer) {
        return stepBuilderFactory.get("step1")
                .<AuthorCsvBean, AuthorBean>chunk(10)
                .reader(reader())
                .processor(compositeAuthorProcessor)
                .writer(writer)
                .build();
    }

    /**
     * Import author job job.
     *
     * @param jobBuilderFactory the job builder factory
     * @param listener          the listener
     * @param step1             the step 1
     * @return the job
     */
    @Bean
    public Job importAuthorJob(JobBuilderFactory jobBuilderFactory,
                               JobCompletionNotificationListener listener,
                               Step step1) {
        return jobBuilderFactory.get("importAuthorJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }
}