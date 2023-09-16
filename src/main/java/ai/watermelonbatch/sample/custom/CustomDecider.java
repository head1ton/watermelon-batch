package ai.watermelonbatch.sample.custom;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class CustomDecider implements JobExecutionDecider {

    //    private int count = 0;
    private int count = 1;

    @Override
    public FlowExecutionStatus decide(final JobExecution jobExecution,
        final StepExecution stepExecution) {

        count++;

        if (count % 2 == 0) {
            return new FlowExecutionStatus("EVEN");
        } else {
            return new FlowExecutionStatus("ODD");
        }

    }
}
