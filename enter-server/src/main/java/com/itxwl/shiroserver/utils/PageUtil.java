package com.itxwl.shiroserver.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class PageUtil<T> {
    private Integer current;
    private Integer size;
    private Integer totol;
    private List<T> dataList;
    private Integer pagetotal;

    public PageUtil (Integer current,Integer size ,List<T> dataList){
        this.current=current;
        this.size=size;
        this.totol=dataList.size();
        /**
         * 12
         * 1 10
         * 0,10
         */
        this.dataList =dataList.subList ((current-1)*size,((current-1)*size+size)<dataList.size()?((current-1)*size+size):dataList.size());
        this.pagetotal=dataList.size()%size==0?dataList.size()/size:(dataList.size()/size)+1;
    }

}
