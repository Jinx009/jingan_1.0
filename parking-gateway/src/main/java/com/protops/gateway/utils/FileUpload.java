package com.protops.gateway.utils;

import com.protops.gateway.util.Base64;
import com.protops.gateway.util.FileUtil;

/**
 * Created by zhouzhihao on 2015/5/4.
 */
public class FileUpload {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void uploadToFile(String picName, String picContent) {

        byte[] decodedPicBytes = Base64.decode(picContent);

        FileUtil.saveBytesAsFile(getUrl() + picName, decodedPicBytes);
    }
//
//    public static void main(String[] args){
//
//        String picContent = FileUtil.loadFileAsString(new File("E:\\c.txt"), "utf-8");
//
//
//        byte[] decodedPicBytes = Base64.decode(picContent);
//
//        FileUtil.saveBytesAsFile("E:\\e.jpg", decodedPicBytes);
//
//
//    }
}
