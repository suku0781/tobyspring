package tobyspring.hellospring.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Collectors;

public class SimpleApiExecutor implements ApiExecutor{
    @Override
    public String excute(URI uri) throws IOException {
        String response;
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            response = br.lines().collect(Collectors.joining());
        }

        /*
         * try, catch가 실행하고 close를 보장하기 위해서
         * 자바에서 try-with-resource로 finally 블록을 사용하지 않고도
         * 블록안에서 어떤 일이 일어나건 관계없이 끝나면 리소스를 close()해줄 수 있는 기능을 제공함.
         *
         * try안에 변수 선언부를 모두 ()에 집어넣고 실행되는 코드를 {}안에 넣어주면 close()를 선언하지 않고도
         * try() 에 선언해 놓은 것을 블록 안에서 처리되고 내려가든 예외가 발생해서 exception이 던져지든 관계없이
         * close()를 해줌.
         * */

        return response;
    }
}
