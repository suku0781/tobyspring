package tobyspring.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiTemplate {
    private final ApiExecutor apiExecutor;
    private final ExRateExtractor exRateExtractor;

    // 디폴트 콜백
    public ApiTemplate() {
        this.apiExecutor = new HttpClientApiExecutor();
        this.exRateExtractor = new ErApiExRateExtractor();
    }

    public ApiTemplate(ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        this.apiExecutor = apiExecutor;
        this.exRateExtractor = exRateExtractor;
    }

    public BigDecimal getExRate(String url){
        return this.getExRate(url, this.apiExecutor, this.exRateExtractor);
    }

    public BigDecimal getExRate(String url, ApiExecutor apiExecutor) {
        return this.getExRate(url, apiExecutor, this.exRateExtractor);
    }

    public BigDecimal getExRate(String url, ExRateExtractor exRateExtractor) {
        return this.getExRate(url, this.apiExecutor, exRateExtractor);
    }

    public BigDecimal getExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        String response;
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        try{
            response = apiExecutor.excute(uri);

        } catch (IOException e) {
            throw new RuntimeException();
        }

        try {
            return exRateExtractor.extract(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * 메서드로 구분해놓은 runApiForExRate (템플릿이라 부름) 잘변하지 않는 속성을 가진 코드를 넣어놓음.
     * 변하는 속성을 가진 코드는 콜백 형태로 만들어서 템플릿에 메서드 파라미터 형태로 전달함.
     * 콜백이 있고, 템플릿이 있고, 이 템플릿의 메서드를 호출해 주는 코드가 getExRate인데 이것을 클라이언트라고 부름.
     *
     * 클라이언트가 콜백을 만들어서 템플릿을 호출함.
     * 클라이언트가 콜백을 직접 만들면서 템플릿의 메서드를 호출한다.
     *
     * 클라이언트, 콜백, 템플릿 세가지가 협력해서 일을 하는 구조가 됨.
     * */

}
