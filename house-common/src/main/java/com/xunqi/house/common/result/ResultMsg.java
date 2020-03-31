package com.xunqi.house.common.result;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import lombok.Data;

/**
 * 错误信息提示
 * @Created with IntelliJ IDEA.
 * @author: 夏沫止水
 * @create: 2020-03-31 11:09
 **/
@Data
public class ResultMsg {

    public static final String errorMsgKey = "errorMsg";

    public static final String successMsgKey = "successMsg";

    private String errorMsg;

    private String successMsg;

    /**
     * 判断是否成功
     * @return
     */
    public boolean isSuccess() {
        return errorMsg == null;
    }

    /**
     * 成功
     * @param msg
     * @return
     */
    public static ResultMsg successMsg(String msg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    public static ResultMsg errorMsg(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }


    public Map<String,String> asMap() {
        Map<String,String> map = Maps.newHashMap();
        map.put("successMsgKey",successMsg);
        map.put(errorMsgKey,errorMsg);
        return map;
    }


    public String asUrlParams(){
        Map<String, String> map = asMap();
        Map<String, String> newMap = Maps.newHashMap();
        map.forEach((k,v) -> {if(v!=null) {
            try {
                newMap.put(k, URLEncoder.encode(v,"utf-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }
        });
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }
}
