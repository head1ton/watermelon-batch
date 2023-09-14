package ai.watermelonbatch.sample.step;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(final StepContribution contribution,
        final ChunkContext chunkContext)
        throws Exception {

        System.out.println("Step2 was executed");

        return RepeatStatus.FINISHED;
    }
}
