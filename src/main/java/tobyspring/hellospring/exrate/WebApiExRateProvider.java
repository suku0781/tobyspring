package tobyspring.hellospring.exrate;

import tobyspring.hellospring.api.*;
import tobyspring.hellospring.payment.ExRateProvider;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class WebApiExRateProvider  implements ExRateProvider {
    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExRate(String currency) {

        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getExRate(url, uri -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();


            try(HttpClient client = HttpClient.newBuilder().build()){
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, new ErApiExRateExtractor());

        /*
        * 람다식으로 콜백을 만들어서 던지면 굳이 클래스를 만들지 않아도 됨.
        * 한번만 사용되어지고 말 것이라면 간단하니 람다식을 이용해서 작성할 수 있음.
        * 다른 쪽에서도 사용되어져야한다 라고하면 클래스를 만들어두는 것이 좋다.
        * */
    }



}
