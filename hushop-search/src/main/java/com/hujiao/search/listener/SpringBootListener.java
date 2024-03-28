package com.hujiao.search.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hujiao.clients.ProductClient;
import com.hujiao.doc.ProductDoc;
import com.hujiao.pojo.Product;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringBootListener implements ApplicationRunner {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ProductClient productClient;
    private String indexstr = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"productId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productName\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"categoryId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productTitle\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productIntro\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productPicture\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"productPrice\":{\n" +
            "        \"type\": \"double\",\n" +
            "        \"index\": true\n" +
            "      },\n" +
            "      \"productSellingPrice\":{\n" +
            "        \"type\": \"double\"\n" +
            "      },\n" +
            "      \"productNum\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productSales\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    /**
     * 同步Elasticsearch（ES）数据的方法。
     * 此方法首先检查“product”索引是否存在于ES中。如果存在，则删除该索引下的所有数据；
     * 如果不存在，则创建一个新的“product”索引。
     * 然后，从产品客户端获取所有产品信息，并将这些信息批量索引到ES的“product”索引中。
     *
     * @param args 应用启动参数，此处未使用
     * @throws Exception 可能抛出的异常，例如与Elasticsearch通信时出现的异常
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 检查"product"索引是否存在并相应地进行删除或创建操作
        GetIndexRequest request1 = new GetIndexRequest("product");
        boolean exists = restHighLevelClient.indices().exists(request1, RequestOptions.DEFAULT);
        if (exists) {
            // 索引存在时，删除所有文档
            DeleteByQueryRequest request2 = new DeleteByQueryRequest("product");
            request2.setQuery(QueryBuilders.matchAllQuery());
            restHighLevelClient.deleteByQuery(request2, RequestOptions.DEFAULT);
        } else {
            // 索引不存在时，创建新索引
            CreateIndexRequest request3 = new CreateIndexRequest("product");
            request3.source(indexstr, XContentType.JSON);
            restHighLevelClient.indices().create(request3, RequestOptions.DEFAULT);
        }

        // 从产品客户端获取所有产品，然后批量索引到Elasticsearch
        List<Product> products = productClient.allList();
        BulkRequest bulkRequest = new BulkRequest();
        ObjectMapper objectMapper = new ObjectMapper();

        // 遍历产品列表，将每个产品转换为ProductDoc，并批量添加到Elasticsearch
        for (Product product : products) {
            ProductDoc productDoc = new ProductDoc(product);
            IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
            String s = objectMapper.writeValueAsString(productDoc);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        // 执行批量索引操作
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

}
