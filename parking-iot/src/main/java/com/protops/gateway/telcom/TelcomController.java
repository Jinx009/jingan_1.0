package com.protops.gateway.telcom;

import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.Constants;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.SensorOperationLog;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.service.device.NewSensorService;
import com.protops.gateway.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class TelcomController {


    private static final Logger log = LoggerFactory.getLogger(TelcomController.class);


    @Autowired
    private SensorService sensorService;
    @Autowired
    private NewSensorService newSensorService;

    /**
     * 点新地磁接入
     * @param bytes
     * @return
     */
    @RequestMapping(value = "/na/iocm/devNotify/v1.1.0/updateDeviceDatas")
    @ResponseBody
    public String pushs(@RequestBody byte[] bytes){
        try {
            String r = new String(bytes, Constants.Default_SysEncoding);
            log.warn("telcom datas:{}",r);
            if(StringUtils.isNotBlank(r)){
                TelcomPushDataModel telcomPushDataModel = JSONObject.parseObject(r, TelcomPushDataModel.class);
                if (telcomPushDataModel != null) {
                    List<PushModel> list = telcomPushDataModel.getServices();
                    PushModel pushModel2 = telcomPushDataModel.getService();
                    if (pushModel2 != null) {
                        list.add(pushModel2);
                    }
                    if (list != null && !list.isEmpty()) {
                        for(PushModel pushModel : list){
                            TModel tModel = pushModel.getData();
                            String data = tModel.getData();
                            String mac = data.substring(0, 16);
                            Sensor sensor = sensorService.getByMac(mac);
                            if(sensor==null){
                                sensor = new Sensor();
                                sensor.setAvailable(0);
                                sensor.setLastSeenTime(new Date());
                                sensor.setCreateTime(new Date());
                                sensor.setMac(mac);
                                sensor.setSensorStatus(0);
                                sensorService.save(sensor);
                            }else{
                                String cmd = data.substring(20, 22).toUpperCase();
                                if(cmd.equals("3D")){
                                    String av = data.substring(24, 26);
                                    Integer avalable = Integer.valueOf(av);
                                    String mode = data.substring(26, 28);
//                                    String dif = data.substring(30, 34);
//                                    String zdif = data.substring(34, 38);
                                    Date date = new Date();
                                    if((date.getTime()-sensor.getLastSeenTime().getTime())/1000>300||avalable!=sensor.getAvailable()){
                                        sensor.setAvailable(avalable);
                                        sensor.setLastSeenTime(date);
                                        sensor.setSensorStatus(avalable);
                                        sensor.setMode(String.valueOf(Long.parseLong(mode, 16)));
                                        sensorService.update(sensor);
                                        newSensorService.saveOperationLog(sensor);
                                    }
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    File f = new File("/apps/logs/sensor_status/"+sdf.format(date)+".txt");
                                    File fileParent = f.getParentFile();
                                    if (!fileParent.exists()) {
                                        fileParent.mkdirs();
                                        f.createNewFile();
                                    }
                                    FileWriter fw = new FileWriter(f, true);
                                    PrintWriter pw = new PrintWriter(fw);
                                    pw.println(mac+"    "+data+"    "+sdf1.format(date));
                                    pw.flush();
                                    fw.flush();
                                    pw.close();
                                    fw.close();
                                }
                                if(cmd.equals("3d")){

                                }
                            }
                        }
                    }
                }
            }
            log.warn("");
        }catch (Exception e){

        }
        return "{\"status\":\"ok\"}";
    }

    /**
     * 点新地磁接入
     * @param bytes
     * @return
     */
    @RequestMapping(value = "/na/iocm/devNotify/v1.1.0/updateDeviceData")
    @ResponseBody
    public String push(@RequestBody byte[] bytes){
        try {
            JSONObject json = JSONObject.parseObject(new String(bytes, Constants.Default_SysEncoding));
            log.warn("telcom data:{}",json);
        }catch (Exception e){
            log.error("e:{}",e);
        }
        return "{\"status\":\"ok\"}";
    }


    private String getDataBase(String index, String _d) throws Exception {
        int _index = Integer.parseInt(index, 16);
        Integer a = Integer.valueOf(_d, 16);
        String b = Integer.toBinaryString(a);
        String[] arrs = b.split("");
        String[] arr = new String[16];
        int i = 0;
        for (String s : arrs) {
            if (s != null && !"".equals(s)) {
                arr[i] = s;
                i++;
            }
        }
        String c = "";
        Integer e = Integer.parseInt(b, 2);
        if (_index > 8) {
            for (String d : arr) {
                if (d != null && !"".equals(d)) {
                    if (d.equals("1")) {
                        c += "0";
                    } else {
                        c += "1";
                    }
                }
            }
            e = (Integer.parseInt(c, 2) + 1) * -1;
        } else {
            e = Integer.parseInt(_d, 16);
        }

        return String.valueOf(e);
    }

}
