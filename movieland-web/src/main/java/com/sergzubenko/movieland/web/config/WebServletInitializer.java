package com.sergzubenko.movieland.web.config;

import com.sergzubenko.movieland.service.impl.config.ServiceConfig;
import com.sergzubenko.movieland.web.security.SecurityFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ServiceConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebAppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/v1/*"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new SecurityFilter()};
    }
}
