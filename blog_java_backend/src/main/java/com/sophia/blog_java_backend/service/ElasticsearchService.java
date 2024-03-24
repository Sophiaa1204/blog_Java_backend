package com.sophia.blog_java_backend.service;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.sophia.blog_java_backend.dao.elasticsearch.DiscussPostRepository;
import com.sophia.blog_java_backend.entity.DiscussPost;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElasticsearchService {
//    @Resource
//    private DiscussPostRepository discussPostRepository;

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    public void saveDiscussPost(DiscussPost discussPost) {
//          discussRepository.save(post);
//    }

//    public void deleteDiscussPost(int id) {
//        discussRepository.deleteById(id);
//    }

//    public Page<DiscussPost> searchDiscussPost(String keyword, int current, int limit) {
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.multiMatchQuery(keyword,"title","content"))
//                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
//                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
//                .withSort(SortBuilders.fieldSort("craeteTime").order(SortOrder.DESC))
//                .withPageable(PageRequest.of(current,limit))
//                .withHighlightFields(
//                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
//                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
//                ).build();
//
//        return elasticTemplate.queryForPage(searchQuery, DiscussPost.class, new searchResultMapper() {
//            @Override
//            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable, pageable) {
//                SearchHits hits = response.getHits();
//                if(hits.getTotalHits() <= 0) {
//                    return null;
//                }
//
//                List<DiscussPost> list = new ArrayList<>();
//                for (SearchHit hit: hits) {
//                    DiscussPost post = new DiscussPost();
//
//                    String id = hit.getSourceAsMap().get("id").toString(); // get得到的是object
//                    post.setId(Integer.valueOf(id));
//
//                    String userId = hit.getSourceAsMap().get("userId").toString(); // get得到的是object
//                    post.setUserId(Integer.valueOf(userId));
//
//                    String title = hit.getSourceAsMap().get("title").toString(); // get得到的是object
//                    post.setTitle(title);
//
//                    String content = hit.getSourceAsMap().get("content").toString(); // get得到的是object
//                    post.setContent(content);
//
//                    String status = hit.getSourceAsMap().get("status").toString(); // get得到的是object
//                    post.setStatus(Integer.valueOf(status));
//
//                    String createTime = hit.getSourceAsMap().get("createTime").toString(); // get得到的是object
//                    post.setCreateTime(new Date(Long.valueOf(createTime))); // ES存类型的时候是转成了Long
//
//                    String commentCount = hit.getSourceAsMap().get("commentCount").toString(); // get得到的是object
//                    post.setCommentCount(Integer.valueOf(commentCount));
//
//                    // 处理高亮显示的结果
//                    HighlightField titleField = hit.getHighlightFields().get("title");
//                    if (titleField != null) {
//                        post.setTitle(titleField.getFragments()[0].toString());
//                    }
//
//                    HighlightField contentField = hit.getHighlightFields().get("content");
//                    if (contentField != null) {
//                        post.setTitle(contentField.getFragments()[0].toString());
//                    }
//
//                    list.add(post);
//                }
//                return new AggregatedPageImpl(list, pageable,
//                        hits.getTotalHits(),response.getAggregations(), response.getScrollId(), hits.getMaxScore());
//            }
//        });
//    }

}
