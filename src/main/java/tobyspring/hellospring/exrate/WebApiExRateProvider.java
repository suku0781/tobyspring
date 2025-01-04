package tobyspring.hellospring.exrate;

import tobyspring.hellospring.api.*;
import tobyspring.hellospring.payment.ExRateProvider;

import java.math.BigDecimal;


public class WebApiExRateProvider  implements ExRateProvider {
    private final ApiTemplate apiTemplate;

    public WebApiExRateProvider(ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
    }

    @Override
    public BigDecimal getExRate(String currency) {

        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getExRate(url);

        /*
        * 람다식으로 콜백을 만들어서 던지면 굳이 클래스를 만들지 않아도 됨.
        * 한번만 사용되어지고 말 것이라면 간단하니 람다식을 이용해서 작성할 수 있음.
        * 다른 쪽에서도 사용되어져야한다 라고하면 클래스를 만들어두는 것이 좋다.
        * */
    }



}
