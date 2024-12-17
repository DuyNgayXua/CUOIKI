package iuh.fit.se.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfiguration {
	public OpenAPI defineOpenApi() {
		io.swagger.v3.oas.models.servers.Server server = new io.swagger.v3.oas.models.servers.Server();
		server.setUrl("http://localhost:9998");
		server.setDescription("Employee Management REST API Documentation");
		Info information = new Info().title("Employee Management REST API Documentation").version("1.0")
				.description("This API exposes endpoints to manage Products.");
		return new OpenAPI().info(information).servers(List.of(server));
	}

}
