package n.batch.newBatch.model;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;



@Component
public class NotificationListener implements JobExecutionListener {
    public NotificationListener() {

        super();
    }

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);



//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public NotificationListener() {
//        this.notificationListener = notificationListener;
//    }
//
//
//    @Autowired
//    public NotificationListener(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Autowired
//    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }



    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("before jobExecution = ===========");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("after jobExecution = ===========");
//        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
//           log.info("!!! JOB FINISHED! Time to verify the results");
//
//            jdbcTemplate.query("SELECT Id, first_name, last_name FROM user",
//                    (rs, row) -> new User(
//                            rs.getInt(1),
//                            rs.getString(2),
//                            rs.getString(3))
//
//            ).forEach(user -> log.info("Found <{{}}> in the database.", user));
//        }
        if(ExitStatus.FAILED.getExitCode()
                .equals(jobExecution.getExitStatus().getExitCode())){

            jobExecution.getAllFailureExceptions()
                    .forEach(throwable -> throwable.printStackTrace());
        }
    }
}
