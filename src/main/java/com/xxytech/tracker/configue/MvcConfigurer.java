package com.xxytech.tracker.configue;

import static com.alibaba.fastjson.serializer.SerializerFeature.QuoteFieldNames;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import freemarker.template.utility.XmlEscape;

@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {
    public static final String         DEFAULT_TEMPLATE_LOADER_PATH = "classpath:/templates/";
    
    @Autowired
    private FreeMarkerProperties       properties;
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON));
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(WriteMapNullValue, QuoteFieldNames);
        messageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(messageConverter);
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {

        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        Properties settings = new Properties();
        settings.putAll(this.properties.getSettings());
        configurer.setFreemarkerSettings(settings);

        configurer.setTemplateLoaderPath(DEFAULT_TEMPLATE_LOADER_PATH);

        Map<String, Object> variables = Maps.newHashMap();
        variables.put("xml_escape", new XmlEscape());

        configurer.setFreemarkerVariables(variables);
        return configurer;
    }
}
