package com.itxwl.elasticsearchserver.dao;

import com.itxwl.elasticsearchserver.entity.MyNameList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface XwLElasticDao extends ElasticsearchRepository<MyNameList,Long> {
}
