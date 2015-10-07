package microservices.projectcomposite.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    @Autowired
    private LoadBalancerClient loadBalancer;

    public URI getServiceUrl(final String serviceId, final String fallbackUri) {
        URI uri = null;
        try {
            final ServiceInstance instance = loadBalancer.choose(serviceId);
            uri = instance.getUri();
            LOGGER.debug("Resolved serviceId '{}' to URL '{}'.", serviceId, uri);
        } catch (RuntimeException e) {
            // Eureka not available, use fallback
            uri = URI.create(fallbackUri);
            LOGGER.warn("Failed to resolve serviceId '{}'. Fallback to URL '{}'.", serviceId, uri);
        }
        return uri;
    }

    public <T> ResponseEntity<T> createOkResponse(T body) {
        return createResponse(body, HttpStatus.OK);
    }

    public <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }
}
