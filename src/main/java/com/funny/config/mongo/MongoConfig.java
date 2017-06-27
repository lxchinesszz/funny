package com.funny.config.mongo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Created by liuxin on 17/1/9.
 * mongo配置类，去掉默认生成_class多余字段
 */
@Configuration
@EnableAutoConfiguration
public class MongoConfig {
    @SuppressWarnings("deprecation")
    public @Bean
    MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) throws Exception {
        //remove _class
        MappingMongoConverter converter =
                new MappingMongoConverter(mongoDbFactory, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        return mongoTemplate;

    }
}
