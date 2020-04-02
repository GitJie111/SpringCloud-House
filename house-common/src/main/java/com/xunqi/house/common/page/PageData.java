package com.xunqi.house.common.page;
import lombok.Data;

import	java.util.List;

/**
 * 分页属性
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-02 09:34
 **/
@Data
public class PageData<T> {

    /**
     * 结果列表
     */
    private List<T> list;

    private Pagination pagination;

    public PageData(Pagination pagination,List<T> list){
        this.pagination = pagination;
        this.list = list;
    }

    public static  <T> PageData<T> buildPage(List<T> list,Long count,Integer pageSize,Integer pageNum){
        Pagination _pagination = new Pagination(pageSize, pageNum, count);
        return new PageData<>(_pagination, list);
    }
}
