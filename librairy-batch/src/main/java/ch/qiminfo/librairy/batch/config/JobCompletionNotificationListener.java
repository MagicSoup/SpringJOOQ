package ch.qiminfo.librairy.batch.config;

import ch.qiminfo.librairy.batch.processor.bean.AuthorBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * The type Job completion notification listener.
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Job completion notification listener.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("!!! JOB FINISHED! Time to verify the results");
            jdbcTemplate.query("SELECT UUID, FIRST_NAME, LAST_NAME, EXTERNAL_UUID FROM AUTHOR",
                    (rs, row) -> new AuthorBean(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4))
            ).forEach(author -> LOGGER.info("Found <{}> in the database.", author));
        }
    }
}
