package com.xunqi.house.biz.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-31 11:51
 **/
public interface FileService {

    /**
     * 保存图片到本地路径
     * @param files
     * @return
     */
    public List<String> getImgPaths(List<MultipartFile> files);

}
