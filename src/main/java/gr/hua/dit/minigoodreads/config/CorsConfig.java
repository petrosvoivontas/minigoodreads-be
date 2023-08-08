package gr.hua.dit.minigoodreads.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@Configuration("corsConfig")
public class CorsConfig implements CorsConfigurationSource {

	@Override
	public CorsConfiguration getCorsConfiguration(@NotNull HttpServletRequest request) {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOriginPattern("*");
		configuration.setAllowCredentials(true);
		return configuration;
	}
}
