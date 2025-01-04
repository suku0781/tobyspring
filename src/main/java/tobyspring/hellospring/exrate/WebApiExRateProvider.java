package tobyspring.hellospring.exrate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.api.ApiExecutor;
import tobyspring.hellospring.api.ErApiExRateExtractor;
import tobyspring.hellospring.api.ExRateExtractor;
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

        return runApiForExRate(url, new SimpleApiExecutor(), new ErApiExRateExtractor());

        /*
        * 람다식으로 콜백을 만들어서 던지면 굳이 클래스를 만들지 않아도 됨.
        * 한번만 사용되어지고 말 것이라면 간단하니 람다식을 이용해서 작성할 수 있음.
        * 다른 쪽에서도 사용되어져야한다 라고하면 클래스를 만들어두는 것이 좋다.
        * */
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
    private static BigDecimal runApiForExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
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


}
