//package com.itxwl.elasticsearchserver;
//
////import com.itxwl.elasticsearchserver.dao.XwLElasticDao;
////import com.itxwl.elasticsearchserver.entity.MyNameList;
//import com.itxwl.elasticsearchserver.service.ElasticSearchService;
//import org.apache.commons.lang.StringUtils;
//import org.elasticsearch.index.query.MatchQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ElasticSearchServerApplication.class)
//class ElasticSearchServerApplicationTests {
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//    @Autowired
//    private XwLElasticDao xwLElasticDao;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//
//    @Test
//    void saveNameWithHead() {
//        //封装查询条件
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        //分页
//        queryBuilder.withPageable(PageRequest.of(0, 5));
//        //过滤条件
//        MatchQueryBuilder matchQueryBuilder = null;
//        matchQueryBuilder = QueryBuilders.matchQuery("name", "张");
//        queryBuilder.withQuery(matchQueryBuilder);
//        //只显示有关信息-姓名
//        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"name"}, null));
//        Page<MyNameList> search = xwLElasticDao.search(queryBuilder.build());
//        System.out.println(search.getContent().toString());
//    }
//
//    @Test
//    void save() {
//        elasticsearchTemplate.createIndex(MyNameList.class);
//        elasticsearchTemplate.putMapping(MyNameList.class);
//        String sql = "select name from user";
//        jdbcTemplate.query(sql, new RowMapper<String>() {
//            int index = 0;
//
//            @Override
//            public String mapRow(ResultSet resultSet, int i) throws SQLException {
//                MyNameList myNameList = new MyNameList();
//                myNameList.setId(Long.decode(String.valueOf(index++)));
//                myNameList.setName(resultSet.getString("name"));
//                int x = (int) (Math.random() * 50) + 1;
//                myNameList.setSoudu(String.valueOf(x));
//                xwLElasticDao.save(myNameList);
//                return resultSet.getString("name");
//            }
//        });
//    }
//    @Test
//    void saveTwo() {
//
//                MyNameList myNameList = new MyNameList();
//                myNameList.setId(21l);
//                myNameList.setName("张");
//                int x = (int) (Math.random() * 50) + 1;
//                myNameList.setSoudu(String.valueOf(x));
//                xwLElasticDao.save(myNameList);
//        };
//
//    @Test
//    void savedd() {
//        boolean b = elasticsearchTemplate.deleteIndex(MyNameList.class);
//    }
//
//    @Test
//    public void testStringLength() {
//        String name = "张";
//        System.out.println(name.length());
//    }
//    @Test
//    public void demoRandom(){
//        int i = (int) (Math.random() * 2) + 1;
//
//    }
//
//}
