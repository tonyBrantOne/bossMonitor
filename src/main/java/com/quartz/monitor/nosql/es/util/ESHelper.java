package com.quartz.monitor.nosql.es.util;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
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

@Component
public class ESHelper implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ESHelper.class);

    private TransportClient client;

    @Value("${es.clustername}")
    private String clustername;
    @Value("${es.host}")
    private String hosts;
    @Value("${es.user}")
    private String username;
    @Value("${es.password}")
    private String password;

    @Override
    public void destroy() throws Exception {
        logger.info("---bulkProcessor awaitClose---");
        if (client != null) {
            client.close();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        logger.info("es.cluster.name:" + clustername);
        logger.info("es.hosts:" + hosts);
        logger.info("es.username:" + username);
        logger.info("es.password:" + password);
        Settings settings = Settings.builder()
                .put("cluster.name", clustername)
                //.put("xpack.security.transport.ssl.enabled", false)
                //.put("xpack.security.user", username + ":" + password)
                //.put("client.transport.sniff", true)// 自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
                //.put("transport.type", "security4")
                //.put("http.type", "security4")
                .build();

        client = new PreBuiltTransportClient(settings);
        //client = new PreBuiltXPackTransportClient(settings);

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

    }


    public TransportClient getClient() {
        return client;
    }

    public void setClient(TransportClient client) {
        this.client = client;
    }

    public String getClustername() {
        return clustername;
    }

    public void setClustername(String clustername) {
        this.clustername = clustername;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取集群的状态
     */
    public List<DiscoveryNode> getClusterInfo() {
        List<DiscoveryNode> nodes = client.connectedNodes();
        return nodes;
    }

    /**
     * 新建索引
     *
     * @param indexName 索引名
     */
    public boolean createIndex(String indexName) {
        boolean isExist = false;

        isExist = indexExist(indexName);

        if (isExist)
            return false;

        CreateIndexResponse cIndexResponse = client.admin().indices()
                .create(new CreateIndexRequest(indexName))
                .actionGet();
        return cIndexResponse.isAcknowledged();
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
     * 获取所有的索引
     */
    public String[] getAllIndex() {
        ClusterStateResponse response = client.admin().cluster().prepareState().execute().actionGet();
        //获取所有索引
        return response.getState().getMetaData().getConcreteAllIndices();
    }

    /**
     * 添加文档
     * */
    public void putDoc(String indexname, String type, String json) throws Exception {
        IndexResponse response = client.prepareIndex(indexname, type).setSource(json, XContentType.JSON).get();
    }

    /**
     * 添加文档
     * */
    public int putDoc(String indexname, String type, Map<String, Object> map) throws Exception {
        return client.prepareIndex(indexname, type).setSource(map).get().status().getStatus();
    }

    /**
     * 添加或更新文档
     * */
    public void UpsetDoc(String indexname, String type, String id, Map<String, Object> map) throws Exception {
        IndexRequest indexRequest = new IndexRequest(indexname, type, id);
        indexRequest.source(map);

        UpdateRequest updateRequest = new UpdateRequest(indexname, type, id);
        updateRequest.doc(map).upsert(indexRequest);

        client.update(updateRequest).get();
    }

    /**
     * 根据id获取文档
     */
    public GetResponse getDoc(String index, String type, String id) {
        return client.prepareGet(index, type, id).get();
    }

    /**
     * 查询工具类
     * */
    public SearchResponse searchDoc(String index, String type, QueryBuilder query, AggregationBuilder agg, Integer size, Integer from, Map<String,SortOrder> sortMap) {

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);

        if(query != null) {
            searchRequestBuilder.setQuery(query);
        }

        if(agg != null) {
            searchRequestBuilder.addAggregation(agg);
        }

        if(size != null)
            searchRequestBuilder.setSize(size);

        if(from != null)
            searchRequestBuilder.setFrom(from);


        if(sortMap!=null && sortMap.size() > 0) {
            for (String key : sortMap.keySet()) {
                searchRequestBuilder.addSort(key,sortMap.get(key));
            }
        }

        SearchResponse response = searchRequestBuilder.execute().actionGet();
        return  response;
    }
}
