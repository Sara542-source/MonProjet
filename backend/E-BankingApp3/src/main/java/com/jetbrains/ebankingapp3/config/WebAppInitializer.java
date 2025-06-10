package com.jetbrains.ebankingapp3.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
@EnableTransactionManagement
@Configuration
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    // Configuration générale (services, datasource, sécurité, etc.)
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{DatabaseConfig.class} ;
    }

    // Configuration web spécifique (contrôleurs, vues, JSON, static file   ,etc .)
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    // Mapping des URL traitées par DispatcherServlet
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] { characterEncodingFilter };
    }

}