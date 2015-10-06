package microservices.projectcomposite.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import microservices.project.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

import static java.text.MessageFormat.format;

@Component
public class ProjectCompositeIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectCompositeIntegration.class);
    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    Util util;
    private ObjectReader productReader = null;

    @HystrixCommand(fallbackMethod = "defaultProject")
    public ResponseEntity<Project> getProduct(final Long projectId) {
        final URI uri = util.getServiceUrl("project", "http://localhost:8081/project");
        final String url = format("{0}/project/{1}", uri.toString(), projectId);
        LOGGER.info("GetProject from URL: {}", url);

        final ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
        LOGGER.info("GetProject http-status: {}", resultStr.getStatusCode());
        LOGGER.info("GetProject body: {}", resultStr.getBody());

        final Project project = response2Project(resultStr);
        LOGGER.info("GetProject.id: {}", project.getId());

        return util.createOkResponse(project);
    }

    public Project response2Project(final ResponseEntity<String> response) {
        try {
            return getProjectReader().readValue(response.getBody());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private ObjectReader getProjectReader() {
        if (productReader != null) {
            return productReader;
        }
        final ObjectMapper mapper = new ObjectMapper();
        return productReader = mapper.reader(Project.class);
    }
}
