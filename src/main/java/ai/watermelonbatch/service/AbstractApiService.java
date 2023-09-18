package ai.watermelonbatch.service;

import ai.watermelonbatch.batch.domain.ApiInfo;
import ai.watermelonbatch.batch.domain.ApiRequestVo;
import ai.watermelonbatch.batch.domain.ApiResponseVo;
import java.io.IOException;
import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Service
public abstract class AbstractApiService {

    public ApiResponseVo service(List<? extends ApiRequestVo> apiRequest) {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(final ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(final ClientHttpResponse response) throws IOException {

            }
        }).build();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ApiInfo apiInfo = ApiInfo.builder().apiRequestList(apiRequest).build();
        HttpEntity<ApiInfo> reqEntity = new HttpEntity<>(apiInfo, headers);

        return doApiService(restTemplate, apiInfo);
    }

    protected abstract ApiResponseVo doApiService(final RestTemplate restTemplate,
        final ApiInfo apiInfo);
}
