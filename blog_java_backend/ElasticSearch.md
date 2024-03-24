#### Elasticsearch6
```
// 插入单条数据
@Test
public void testInsert() {
    discussRepository.save(discussMapper.selectDiscussPostById(241));
    discussRepository.save(discussMapper.selectDiscussPostById(242));
    discussRepository.save(discussMapper.selectDiscussPostById(243));   
}

// 插入多条数据
@Test
public void testInsertList() {
    discussRepository.saveAll(discussMapper.selectDiscussPosts(101,0,100));
}

// 对数据进行修改（.../_doc/231)
@Test
public void testUpdate() {
    DiscussPost post = discussMapper.selectDiscussPostById(231);
    post.setContent("new content");
    discussRepository.save(post);
}

// 对数据进行删除
@Test
public void testDelete() {
    discussRepository.deleteById(231);
    discussRepository.deleteAll();
}

// 对数据进行搜索（关键词？排序？分页？高亮显示？在词的前后加上标签）
// type: 置顶 score: 帖子的价值 time: 时间
// 底层: elasticTemplate.queryForPage(searchQuery, class, SearchResultMapper)
// 底层获取到了高亮现实的值，但是没有返回。
@Test
public void testSearchByRepository() {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
                            .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬","title","content"))
                            .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                            .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                            .withSort(SortBuilders.fieldSort("craeteTime").order(SortOrder.DESC))
                            .withPageable(PageRequest.of(0,10))
                            .withHighlightFields(
                                    new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                                    new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                            ).build();
    
    Page<DiscussPost> page = discussRepository.search(searchQuery);
    System.out.println(page.getTotalElements()); // 总共多少条数据
    System.out.println(page.getTotalPages()); // 多少页
    System.out.println(page.getNumber()); // 目前在第几页
    System.out.println(page.getSize()); // 每一页显示多少条数据
    
    for(DiscussPost post:page) {
        System.out.println(post);
    }  
}

@Test
public void testSearchByTemplate() {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
                            .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬","title","content"))
                            .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                            .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                            .withSort(SortBuilders.fieldSort("craeteTime").order(SortOrder.DESC))
                            .withPageable(PageRequest.of(0,10))
                            .withHighlightFields(
                                    new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                                    new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                            ).build();
    
    Page<DiscussPost> page = elasticTemplate.queryForPage(searchQuery, DiscussPost.class, new searchResultMapper() {
        @Override
        public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable, pageable) {
            SearchHits hits = response.getHits();
            if(hits.getTotalHits() <= 0) {
                return null;
            }
            
            List<DiscussPost> list = new ArrayList<>();
            for (SearchHit hit: hits) {
                DiscussPost post = new DiscussPost();
                
                String id = hit.getSourceAsMap().get("id").toString(); // get得到的是object
                post.setId(Integer.valueOf(id));
                
                String userId = hit.getSourceAsMap().get("userId").toString(); // get得到的是object
                post.setUserId(Integer.valueOf(userId));
                
                String title = hit.getSourceAsMap().get("title").toString(); // get得到的是object
                post.setTitle(title);
                
                String content = hit.getSourceAsMap().get("content").toString(); // get得到的是object
                post.setContent(content);
                
                String status = hit.getSourceAsMap().get("status").toString(); // get得到的是object
                post.setStatus(Integer.valueOf(status));
                
                String createTime = hit.getSourceAsMap().get("createTime").toString(); // get得到的是object
                post.setCreateTime(new Date(Long.valueOf(createTime)); // ES存类型的时候是转成了Long
                
                String commentCount = hit.getSourceAsMap().get("commentCount").toString(); // get得到的是object
                post.setCommentCount(Integer.valueOf(commentCount));
                
                // 处理高亮显示的结果
                HighlightField titleField = hit.getHighlightFields().get("title");
                if (titleField != null) {
                    post.setTitle(titleField.getFragments()[0].toString());
                }
                
                HighlightField contentField = hit.getHighlightFields().get("content");
                if (contentField != null) {
                    post.setTitle(contentField.getFragments()[0].toString());
                }
                
                list.add(post);
            }
            return new AggregatedPageImpl(list, pageable,
                                    hits.getTotalHits(),response.getAggregations(), response.getScrollId(), hits.getMaxScore());
        }
    });
    
    System.out.println(page.getTotalElements()); // 总共多少条数据
    System.out.println(page.getTotalPages()); // 多少页
    System.out.println(page.getNumber()); // 目前在第几页
    System.out.println(page.getSize()); // 每一页显示多少条数据
    
    for(DiscussPost post:page) {
        System.out.println(post);
    }  
}

```