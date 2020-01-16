package com.itxwl.elasticsearchserver.controller;

//import com.itxwl.elasticsearchserver.entity.MyNameList;
import com.itxwl.elasticsearchserver.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@CrossOrigin
public class ElasticSearchController {
    @Autowired
    private ElasticSearchService elasticSearchService;

//    /**
//     * 全文检索查询
//     *
//     * @param current
//     * @param size
//     * @param nameCode
//     * @return
//     */
//    @GetMapping("findSearchByName")
//    public Page<MyNameList> findUserNameByCode(Integer current, Integer size, String nameCode) {
//        Page<MyNameList> pageUtil = elasticSearchService.findUserNameByCode(current, size, nameCode);
//        return pageUtil;
//    }
    @GetMapping("/GetElasticSearch")
    public String GetElasticSearch(){
        elasticSearchService.saves();
        return "OK";
    }
}
