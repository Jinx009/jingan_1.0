package com.protops.gateway.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by Mike on 2015/7/22.
 */
public class DownloadUtil {

    //浏览器下载方式
    public static void download(HttpServletResponse response,File f) throws IOException {

        response.reset(); //非常重要
        response.setCharacterEncoding("utf8");
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(f.getName(), "UTF-8"));

        //读取已生成的文件并写入response输出流中
        byte[] buf = new byte[1024];
        int len = 0;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
        OutputStream os = response.getOutputStream();
        try {
            while ((len = in.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//关闭流
            os.flush();
            if (in != null) {
                in.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    //删除一个文件夹及其所有子文件
    public static void deleteFile(File oldPath) {
        if (oldPath.isDirectory()) {
            File[] files = oldPath.listFiles();
            for (File file : files) {
                deleteFile(file);
            }
        }else{
            oldPath.delete();
        }
    }
}
