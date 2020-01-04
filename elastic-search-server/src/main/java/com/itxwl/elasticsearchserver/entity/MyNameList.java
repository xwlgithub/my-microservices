package com.itxwl.elasticsearchserver.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 索引对应属性映射关系
 */
@Document( indexName = "indexname",type = "myindex")
@Data
public class MyNameList {


    @Id
    @Field(value = "id",store = true)
    private Long id;

    @Field(value = "name", type = FieldType.Text, analyzer = "ik_max_word",store = true)
    private String name;

    @Field(value = "soudu", type = FieldType.Text, analyzer = "ik_max_word",store = true)
    private String soudu;

}
