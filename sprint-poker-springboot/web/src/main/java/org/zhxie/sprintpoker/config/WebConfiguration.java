package org.zhxie.sprintpoker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  private String baseApiPath = "/api/";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // All resources go to where they should go
    registry
            .addResourceHandler("/**/*.css", "/**/*.html", "/**/*.js", "/**/*.jsx", "/**/*.png", "/**/*.ttf", "/**/*" +
                    ".woff", "/**/*.woff2")
            .setCachePeriod(0)
            .addResourceLocations("classpath:/static/");

    registry.addResourceHandler("/", "/**")
            .setCachePeriod(0)
            .addResourceLocations("classpath:/static/index.html")
            .resourceChain(true)
            .addResolver(new PathResourceResolver() {
              @Override
              protected Resource getResource(String resourcePath, Resource location) throws IOException {
                if (resourcePath.startsWith(baseApiPath) || resourcePath.startsWith(baseApiPath.substring(1))) {
                  return null;
                }

                return location.exists() && location.isReadable() ? location : null;
              }
            });
  }
}
