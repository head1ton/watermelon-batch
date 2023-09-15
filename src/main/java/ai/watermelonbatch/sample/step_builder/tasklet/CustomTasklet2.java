package ai.watermelonbatch.sample.step_builder.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class CustomTasklet2 implements Tasklet {

    @Override
    public RepeatStatus execute(final StepContribution contribution,
        final ChunkContext chunkContext)
        throws Exception {

        String stepName = contribution.getStepExecution().getStepName();
        String jobName = chunkContext.getStepContext().getJobName();
        System.out.println("stepName = " + stepName);
        System.out.println("jobName = " + jobName);

        return RepeatStatus.FINISHED;
    }
}
