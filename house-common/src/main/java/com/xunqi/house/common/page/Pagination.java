package com.xunqi.house.common.page;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-04-02 09:36
 **/
@Data
public class Pagination {

    private int pageNum;

    private int pageSize;

    private long totalCount;

    private List<Integer> pages = Lists.newArrayList();

    public Pagination(Integer pageSize,Integer pageNum,Long totalCount) {

        this.totalCount = totalCount;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        for(int i=1;i<=pageNum;i++){
            pages.add(i);
        }

        Long pageCount = totalCount/pageSize + ((totalCount % pageSize == 0 ) ? 0: 1);
        if (pageCount > pageNum) {
            for(int i= pageNum + 1; i<= pageCount ;i ++){
                pages.add(i);
            }
        }
    }
}
