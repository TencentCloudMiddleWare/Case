package com.tencent.tsf.gateway;

import org.apache.http.HttpHost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.tsf.core.TsfContext;
import org.springframework.tsf.core.context.TsfCoreContext;
import org.springframework.tsf.core.context.TsfCoreContextHolder;
import org.springframework.tsf.core.entity.Tag;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.cloud.gateway.filter.LoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author seanlxliu
 */
@Component
public class TestFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(TestFilter.class);

    @Override
    public int getOrder() {
        return LOAD_BALANCER_CLIENT_FILTER_ORDER - 2;
    }

    public Mono<Void> doFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("hello world");
        TsfCoreContext tsfCoreContext = TsfCoreContextHolder.get();
        List<Tag> oldTagList = tsfCoreContext.getTags();
        oldTagList.forEach(e -> System.out.println("aabb is : " + e.getKey() + " : " + e.getValue()));
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, URI.create("http://127.0.0.1:18081/echo/23"));
        //TsfContext.putTag("aabb","111", Tag.ControlFlag.TRANSITIVE);
        logger.info("hello world");
        //exchange.getAttributes().put("aabb","111");
        return chain.filter(exchange);
//        ServerHttpResponse originalResponse = exchange.getResponse();
//        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                System.out.println("TestFilter : " + getStatusCode().value());
//                return super.writeWith(body);
//            }
//        };
//        // replace response with decorator
//        ServerWebExchange build = exchange.mutate().response(decoratedResponse).build();
//        return chain.filter(build).doFinally((type)->{
//            System.out.println("type is" + type);
//        });
//        return chain.filter(build).onErrorMap((t) -> {
//            System.out.println("========11" + t);
//            System.out.println("error bbb is " + exchange.getAttribute("bbb"));
//            return t;
//        }).doOnError(((t) -> System.out.println("========222" + t)));
//
//            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//            return exchange.getResponse().writeWith(Mono.empty());

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("hello world");
        TsfContext.putTag("aa", "11" , Tag.ControlFlag.TRANSITIVE);
//        URI uri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
//        try {
//            uri = URIUtils.rewriteURI(uri, HttpHost.create("http://127.0.0.1:18081"));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
//
//        //exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, URI.create("http://127.0.0.1:18081/echo/23"));
//        //TsfContext.putTag("aabb","111", Tag.ControlFlag.TRANSITIVE);
//        logger.info("hello world");
//        //exchange.getAttributes().put("aabb","111");
        return chain.filter(exchange);
    }

}
