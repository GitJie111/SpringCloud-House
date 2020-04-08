package com.xunqi.house.biz.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import com.xunqi.house.biz.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-31 11:39
 **/
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Value("${file.path:}")
    private String filePath;


    @Override
    public List<String> getImgPaths(List<MultipartFile> files) {
        if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        List<String> paths = Lists.newArrayList();
        files.forEach(file -> {
            File localFile = null;
            if (!file.isEmpty()) {
                try {
                    localFile =  saveToLocal(file, filePath);
                    String fileName = files.get(0).getOriginalFilename();
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
                    paths.add(fileName);
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return paths;
    }

    public static String getResourcePath(){
        File file = new File(".");
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }

    private File saveToLocal(MultipartFile file, String filePath2) throws IOException {
        File newFile = new File(filePath + "/" + Instant.now().getEpochSecond() +"/"+file.getOriginalFilename());
        if (!newFile.exists()) {
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(), newFile);
        return newFile;
    }

}
