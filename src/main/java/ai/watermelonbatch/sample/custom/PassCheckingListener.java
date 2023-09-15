package ai.watermelonbatch.sample.custom;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class PassCheckingListener implements
    StepExecutionListener {

    @Override
    public void beforeStep(final StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {

        String exitCode = stepExecution.getExitStatus().getExitCode();

        if (!exitCode.equals(ExitStatus.FAILED.getExitCode())) {
            return new ExitStatus("PASS");
        }
        return null;
    }
}
