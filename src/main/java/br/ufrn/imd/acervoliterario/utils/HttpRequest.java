package br.ufrn.imd.acervoliterario.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Classe para realizar requisições HTTP.
 * Esta classe encapsula os detalhes de uma requisição HTTP, incluindo a URL, método, cabeçalhos, corpo e tipo de resposta esperado.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 *
 * @param <T> Tipo de resposta esperada.
 */
public class HttpRequest<T> {
    private final String url;
    private final HttpMethod method;
    private final HttpHeaders headers;
    private final Object body;
    private final Class<T> responseType;

    /**
     * Construtor da classe HttpRequest.
     *
     * @param url          URL para a requisição HTTP.
     * @param method       Método HTTP (GET, POST, PUT, DELETE, etc.).
     * @param headers      Cabeçalhos HTTP a serem incluídos na requisição.
     * @param body         Corpo da requisição HTTP.
     * @param responseType Tipo de resposta esperada.
     */
    public HttpRequest(String url, HttpMethod method, HttpHeaders headers, Object body, Class<T> responseType) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = body;
        this.responseType = responseType;
    }

    /**
     * Envia a requisição HTTP e retorna a resposta.
     *
     * @return Corpo da resposta da requisição HTTP.
     */
    public T sendRequest() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);

        return response.getBody();
    }
}
