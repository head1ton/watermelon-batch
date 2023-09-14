package ai.watermelonbatch.sample.job_batch.simplejob;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;

public class CustomJobParametersValidator implements
    org.springframework.batch.core.JobParametersValidator {

    @Override
    public void validate(final JobParameters parameters) throws JobParametersInvalidException {
        if (parameters.getString("name") == null) {
            throw new JobParametersInvalidException("name parameters is not found");
        }
    }
}
