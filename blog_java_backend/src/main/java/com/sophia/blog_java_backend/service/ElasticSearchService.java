package com.sophia.blog_java_backend.service;

import com.sophia.blog_java_backend.entity.DiscussPost;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

public class ElasticSearchService {
    @Resource
    private ElasticsearchRepository elasticsearchRepository;

    public void save(DiscussPost discussPost) {

    }

}
