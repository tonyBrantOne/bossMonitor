package com.quartz.monitor.nosql.es.util;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ESHelper implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ESHelper.class);

    private TransportClient client;
    private BulkProcessor bulkProcessor;
    @Value("${clustername.es}")
    private String clustername;
    @Value("${host.es}")
    private String hosts;
    @Value("${bulkactions.es}")
    private String bulkactions;
    @Value("${bulksize.es}")
    private String bulksize;
    @Value("${es.flushinterval}")
    private String flushinterval;

    public TransportClient getClient() {
        return client;
    }

    public void setClient(TransportClient client) {
        this.client = client;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //System.setProperty("es.set.netty.runtime.available.processors", "false");
        logger.info("es.clustername:" + clustername);
        logger.info("es.hosts:" + hosts);
        logger.info("es.bulkactions:" + bulkactions);
        logger.info("es.bulksize:" + bulksize);
        logger.info("es.flushinterval:" + flushinterval);
        Settings settings = Settings.builder()
                .put("cluster.name", clustername)
//                .put("xpack.security.transport.ssl.enabled", false)
//                .put("xpack.security.user", username + ":" + password)
                //.put("client.transport.sniff", true)// 自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
                .build();

        client = new PreBuiltTransportClient(settings);
      //  client = new PreBuiltXPackTransportClient(settings);

        String[] nodes = hosts.split(",");
        for (String node : nodes) {
            if (node.length() > 0) {// 跳过为空的node（当开头、结尾有逗号或多个连续逗号时会出现空node）
                String[] hostPort = node.split(":");
                try {
                    client.addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName(hostPort[0]), Integer.parseInt(hostPort[1])));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                logger.info("---尝试插入{}条数据---", bulkRequest.numberOfActions());
            }

            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                logger.info("---尝试插入{}条数据成功---", bulkRequest.numberOfActions());
            }

            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                logger.error("[es错误]---尝试插入数据失败---", throwable);
            }
        })
                // 2w次请求执行一次bulk
                .setBulkActions(Integer.parseInt(bulkactions))
                // 1gb的数据刷新一次bulk
                .setBulkSize(new ByteSizeValue(Integer.parseInt(bulksize), ByteSizeUnit.MB))
                // 固定5s必须刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(Integer.parseInt(flushinterval)))
                // 并发请求数量, 0不并发, 1并发允许执行
                .setConcurrentRequests(1)
                // 设置退避, 100ms后执行, 最大请求3次
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
    }

    @Override
    public void destroy() throws Exception {
        logger.info("---bulkProcessor awaitClose---");
        if (bulkProcessor != null)
            bulkProcessor.awaitClose(5, TimeUnit.SECONDS);

        if (client != null) {
            client.close();
        }
    }

    /**
     * 获取集群的状态
     */
    public List<DiscoveryNode> getClusterInfo() {
        List<DiscoveryNode> nodes = client.connectedNodes();
        return nodes;
    }

    /**
     * 功能描述：验证索引是否存在
     *
     * @param index 索引名
     */
    public boolean indexExist(String index) {
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(index);
        IndicesExistsResponse inExistsResponse = client.admin().indices()
                .exists(inExistsRequest).actionGet();
        return inExistsResponse.isExists();
    }

    /**
     * 创建索引
     */
    private void createIndex(String index) {
        if (!indexExist(index)) {
            CreateIndexRequest request = new CreateIndexRequest(index);
            client.admin().indices().create(request);
        }
    }

    /**
     * 批量插入数据
     */
    public void BulkProcessor(String indexname, String type, Map<String, Object> map) {
        bulkProcessor.add(new IndexRequest(indexname, type).source(map));
    }

    /**
     * 批量插入数据
     */
    public void BulkProcessor(String indexname, String type, String json) {
        bulkProcessor.add(new IndexRequest(indexname, type).source(json, XContentType.JSON));
    }

    /**
     * 批量插入数据
     */
    public void BulkProcessor(String indexname, String type, String id, Map<String, Object> map) throws Exception {
        bulkProcessor.add(new IndexRequest(indexname, type, id).source(map));
    }

    public void putDoc(String indexname, String type, String json) throws Exception {
        IndexResponse response = client.prepareIndex(indexname, type).setSource(json, XContentType.JSON).get();
    }

    public int putDoc(String indexname, String type, Map<String, Object> map) throws Exception {
        IndexResponse response = client.prepareIndex(indexname, type).setSource(map).get();
        return response.status().getStatus();
    }

    public int putDoc(String indexname, String type, String id, Map<String, Object> map) throws Exception {
        IndexResponse response = client.prepareIndex(indexname, type, id).setSource(map).get();
        return response.status().getStatus();
    }

    public int UpsetDoc(String indexname, String type, String id, Map<String, Object> map) throws Exception {
        createIndex(indexname);

        IndexRequest indexRequest = new IndexRequest(indexname, type, id);
        indexRequest.source(map);

        UpdateRequest updateRequest = new UpdateRequest(indexname, type, id);
        updateRequest.doc(map).upsert(indexRequest);

        return client.update(updateRequest).get().status().getStatus();
    }

    /**
     * 根据id获取文档
     */
    public GetResponse getDoc(String index, String type, String id) {
        return client.prepareGet(index, type, id).get();
    }


    /**
     * 根据id删除文档
     */
    public void deleteDocById(String index, String type, String id) {
        DeleteResponse response = client.prepareDelete(index, type, id).get();
    }

    /**
     * 同步批量删除文档
     */
    public long deleteDocByQuery(String index, QueryBuilder query) {
        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(query)
                .source(index)
                .get();
        return response.getDeleted();
    }

    /**
     * 异步批量删除文档
     */
    public void deleteAsyncDocByQuery(String index, QueryBuilder query) {
        DeleteByQueryAction.INSTANCE
                .newRequestBuilder(client)
                .filter(query)
                .source(index)
                .execute(new ActionListener<BulkByScrollResponse>() {
                    /**
                     * 删除成功回调方法
                     * */
                    @Override
                    public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
                        long deleted = bulkByScrollResponse.getDeleted();
                    }

                    /**
                     * 删除失败回调方法
                     * */
                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }


    /**
     * 根据id更新文档
     */
//    public boolean updateDoc(String index, String type, String id, String json) {
//        IndexRequest indexRequest = new IndexRequest(index, type, id);
//        indexRequest.source(json);
//    }
}
