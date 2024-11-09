package com.stackbytes.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuerryingUtils<T,V> {

    //TODO: Error handling

    public List<T> getKeyValueList(String key, V value, MongoTemplate mongoTemplate, Class<T> clazz) {
        List<T> results = mongoTemplate.find(Query.query(Criteria.where(key).is(value)), clazz);
        return results;
    }

    public T getKeyValue(String key, V value, MongoTemplate mongoTemplate, Class<T> clazz) {
        T result = mongoTemplate.findOne(Query.query(Criteria.where(key).is(value)), clazz);
        return result;
    }

}
