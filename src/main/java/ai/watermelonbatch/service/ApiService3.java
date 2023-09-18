package ai.watermelonbatch.service;

import ai.watermelonbatch.batch.domain.ApiInfo;
import ai.watermelonbatch.batch.domain.ApiResponseVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiService3 extends AbstractApiService {

    @Override
    protected ApiResponseVo doApiService(final RestTemplate restTemplate, final ApiInfo apiInfo) {
        ResponseEntity<String> response = restTemplate.postForEntity(
            "http://localhost:8083/api/product/3", apiInfo, String.class);

        int statusCodeValue = response.getStatusCodeValue();
        ApiResponseVo apiResponseVo = new ApiResponseVo(statusCodeValue + "", response.getBody());

        return apiResponseVo;
    }
}
