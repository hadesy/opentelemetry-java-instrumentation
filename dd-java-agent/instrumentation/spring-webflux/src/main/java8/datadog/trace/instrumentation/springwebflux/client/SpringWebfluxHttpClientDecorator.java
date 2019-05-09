package datadog.trace.instrumentation.springwebflux.client;

import datadog.trace.agent.decorator.HttpClientDecorator;
import io.opentracing.Span;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;

@Slf4j
public class SpringWebfluxHttpClientDecorator
    extends HttpClientDecorator<ClientRequest, ClientResponse> {
  public static final SpringWebfluxHttpClientDecorator DECORATE =
      new SpringWebfluxHttpClientDecorator();

  public void onCancel(final Span span) {
    final Map<String, Object> logs = new HashMap<>(2);
    logs.put("event", "cancelled");
    logs.put("message", "The subscription was cancelled");
    span.log(logs);
  }

  @Override
  protected String[] instrumentationNames() {
    return new String[] {"spring-webflux-client"};
  }

  @Override
  protected String component() {
    return "spring-webflux-client";
  }

  @Override
  protected String method(final ClientRequest httpRequest) {
    return httpRequest.method().name();
  }

  @Override
  protected URI url(final ClientRequest httpRequest) {
    return httpRequest.url();
  }

  @Override
  protected String hostname(final ClientRequest httpRequest) {
    return httpRequest.url().getHost();
  }

  @Override
  protected Integer port(final ClientRequest httpRequest) {
    return httpRequest.url().getPort();
  }

  @Override
  protected Integer status(final ClientResponse httpResponse) {
    return httpResponse.statusCode().value();
  }
}
