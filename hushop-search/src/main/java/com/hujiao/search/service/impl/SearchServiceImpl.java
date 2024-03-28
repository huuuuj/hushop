package com.hujiao.search.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujiao.param.ProductSearchParam;
import com.hujiao.pojo.Product;
import com.hujiao.search.service.SearchService;
import com.hujiao.utils.R;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 根据提供的产品搜索参数进行产品搜索。
     *
     * @param param 包含搜索条件、分页信息等的ProductSearchParam对象。
     * @return 返回一个包含搜索结果信息的R对象，其中包含搜索到的产品列表和总数量。
     */
    @Override
    public R search(ProductSearchParam param) {
        // 创建一个针对"product"索引的搜索请求
        SearchRequest searchRequest = new SearchRequest("product");
        String search = param.getSearch();
        // 如果搜索条件为空，则查询所有文档；否则，根据搜索条件进行匹配查询
        if (search.isEmpty()) {
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        } else {
            searchRequest.source().query(QueryBuilders.matchQuery("all", search));
        }
        // 设置分页参数
        searchRequest.source().from((param.getCurrentPage() - 1) * param.getPageSize());
        searchRequest.source().size(param.getPageSize());
        SearchResponse searchResponse = null;
        try {
            // 执行搜索请求
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            // 处理搜索过程中的IO异常
            throw new RuntimeException("查询错误");
        }
        // 获取搜索结果的基本信息
        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits().value;
        SearchHit[] hits1 = hits.getHits();
        List<Product> productList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        // 遍历搜索结果，将JSON格式的搜索结果转换为Product对象，并添加到产品列表中
        for (SearchHit hit : hits1) {
            String sourceAsString = hit.getSourceAsString();
            Product product = null;
            try {
                product = objectMapper.readValue(sourceAsString, Product.class);
            } catch (JsonProcessingException e) {
                // 处理JSON解析异常
                throw new RuntimeException(e);
            }
            productList.add(product);

        }

        // 构建并返回搜索结果
        return R.ok(null,productList,total);
    }
}
