package com.protops.gateway.Job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Lazy(value = false)
public class NbProcessTask {

    private static final Logger log = LoggerFactory.getLogger(NbProcessTask.class);

    /**
     * 测试环境
     */
    private static final String TIME_FILE_PATH = "/home/baoadmin/logs/heart_log_time.txt";
    private static final String RESTART_FILE_PATH = "/home/baoadmin/logs/nb_start_log.txt";
    private static final String CMD = "cd /home/baoadmin/nb;nohup ./nb_router &";

//    private static final String TIME_FILE_PATH = "/apps/logs/heart_log_time.txt";
//    private static final String RESTART_FILE_PATH = "/apps/logs/nb_start_log.txt";
//    private static final String CMD = "cd /apps/nb;nohup ./nb_router &";

    @Scheduled(fixedRate = 600 * 1000) // 每10分钟S执行一次
    public void sendNormal() {
        try {
            checkTime();
        } catch (Exception e) {
            log.error("error:{}", e);
        }
    }

    private static boolean checkTime() {
        try {
            File file = new File(TIME_FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(file));
                out.write(String.valueOf(new Date().getTime()));
                out.flush();
                out.close();
                return false;
            } else {
                StringBuilder result = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(file));
                String s = null;
                while ((s = br.readLine()) != null) {
                    result.append(s);
                }
                br.close();
                long timestamp = Long.valueOf(result.toString().replaceAll("\\s*", ""));
                Date date = new Date();
                if ((date.getTime() - timestamp) / 1000 / 60 > 3) {
                    log.warn("starttime:{},endtime:{}", timestamp, date.getTime());
                    kill();
                    start(timestamp, date.getTime());
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("error:{}", e);
        }
        return false;
    }

    private static void kill() {
        try {
            String[] cmd = { "sh", "-c", "killall nb_router" };
            ;
            log.warn("log:cmd:kill");
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            log.error("error:{}", e);
        }
    }

    private static void start(long start, long end) {
        try {
            String[] cmd = { "sh", "-c", CMD };
            log.warn("log:cmd:restart");
            Process p = Runtime.getRuntime().exec(cmd);
            p.destroy();
            File f = new File(RESTART_FILE_PATH);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(stampToDate(String.valueOf(start)) + "	" + stampToDate(String.valueOf(end)));
            pw.flush();
            fw.flush();
            pw.close();
            fw.close();
        } catch (Exception e) {
            log.error("error:{}", e);
        }
    }

    private static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


}