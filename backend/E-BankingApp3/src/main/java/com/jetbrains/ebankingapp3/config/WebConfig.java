package com.jetbrains.ebankingapp3.config;

import com.jetbrains.ebankingapp3.auth.SessionFilter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import java.util.List;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.jetbrains.ebankingapp3")
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SessionFactory sessionFactory;

    // Définir le ViewResolver pour JSP (exemple)
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/"); // dossier des JSP
        resolver.setSuffix(".jsp");             // extension des fichiers
        return resolver;
    }

    // Gérer les ressources statiques (CSS, JS, images)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")  // URL des ressources statiques
                .addResourceLocations("/resources/"); // chemin dans le projet
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // L'adresse du frontend Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
    @Bean // ouvrir/fermer session automatiquement et eviter erreur de lazyinitial
    public OpenSessionInViewInterceptor openSessionInViewInterceptor() {
        OpenSessionInViewInterceptor interceptor = new OpenSessionInViewInterceptor();
        interceptor.setSessionFactory(sessionFactory);
        return interceptor;
    }
    //BCryptPasswordEncoder est une classe qui implémente PasswordEncoder et qui permet de chiffrer (hasher) les mots de passe avec l’algorithme BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(openSessionInViewInterceptor());
    }

    //Il permet à Spring d’associer des informations sur la requête en cours au thread qui la traite
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
