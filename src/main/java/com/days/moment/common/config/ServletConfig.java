package com.days.moment.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import com.days.moment.board.config.BoardServletConfig;
import com.days.moment.common.converter.StringToLocalDateTimeConverter;

@EnableWebMvc
@Import(BoardServletConfig.class)
@ComponentScan(basePackages = {"com.days.moment.common.exception", "com.days.moment.common.controller"} )
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServletConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateTimeConverter());
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        registry.viewResolver(bean);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //webapp resources folder
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
