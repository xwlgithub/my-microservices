package com.itxwl.elasticsearchserver.service;

//import com.itxwl.elasticsearchserver.dao.XwLElasticDao;
//import com.itxwl.elasticsearchserver.entity.MyNameList;
//import org.apache.commons.lang.StringUtils;
//import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ElasticSearchService {
    //查询检索支持类
//    @Autowired
//    private ElasticsearchTemplate template;
//    @Autowired
//    private XwLElasticDao xwLElasticDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    /**
//     * 全文检索姓名逻辑处理
//     *
//     * @param current
//     * @param size
//     * @param nameCode
//     * @return
//     */
//    public Page<MyNameList> findUserNameByCode(Integer current, Integer size, String nameCode) {
//
//        //封装查询条件
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        //分页
//        queryBuilder.withPageable(PageRequest.of(current<=1?0:current-1, size));
//        //过滤条件
//        MatchQueryBuilder matchQueryBuilder = null;
//        if (!StringUtils.isEmpty(nameCode)&& nameCode.length()>1) {
//            //matchQueryBuilder = QueryBuilders.matchQuery("name",nameCode);
//            matchQueryBuilder = QueryBuilders.matchQuery("name",nameCode);
//            queryBuilder.withQuery(matchQueryBuilder);
//        } else {
//            queryBuilder.withQuery(QueryBuilders.queryStringQuery(nameCode));
//        }
//        //只显示有关信息-姓名
//        //queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"name"}, null));
//        Page<MyNameList> search = xwLElasticDao.search(queryBuilder.build());
//        return search;
//    }
    @Transactional
    public void saves() {
        //对当前服务一个数据库表操作
        String sql="insert into d(val) VALUES (50)";
        jdbcTemplate.update(sql);

    }
}
