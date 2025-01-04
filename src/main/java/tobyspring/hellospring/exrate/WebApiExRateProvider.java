package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.api.ApiExecutor;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.payment.ExRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;


public class WebApiExRateProvider  implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return runApiForExRate(url, new SimpleApiExecutor());
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
    private static BigDecimal runApiForExRate(String url, ApiExecutor apiExecutor) {
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
            return extractExRate(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static BigDecimal extractExRate(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);

        return data.rates().get("KRW");
    }

}
