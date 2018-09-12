package com.protops.gateway.service.log;

import com.protops.gateway.dao.log.SensorInOutLogDao;
import com.protops.gateway.dao.log.SensorInOutMoneyLogDao;
import com.protops.gateway.domain.log.SensorInOutLog;
import com.protops.gateway.domain.log.SensorInOutMoneyLog;
import com.protops.gateway.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SensorInOutMoneyLogService {

    private static final Logger log = LoggerFactory.getLogger(SensorInOutMoneyLogService.class);

    @Autowired
    private SensorInOutMoneyLogDao sensorInOutMoneyLogDao;
    @Autowired
    private SensorInOutLogDao sensorInOutLogDao;

    public SensorInOutMoneyLog getMoney(Integer areaId,String dateStr){
        try {
            List<SensorInOutMoneyLog> list = sensorInOutMoneyLogDao.getMoney(areaId,dateStr);
            if(list!=null&&!list.isEmpty()){
                return list.get(0);
            }else{
                String endTime = dateStr+" 07:30:00";
                Date date =  DateUtil.parseDate(dateStr,DateUtil.DATE_FMT_DISPLAY2);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE,-1);
                String beforeDayStr = DateUtil.getDate(calendar.getTime(),DateUtil.DATE_FMT_DISPLAY2);
                String startTime = beforeDayStr+" 07:30:00";
                long startDate = DateUtil.parseDate(startTime,DateUtil.DATE_FMT_DISPLAY).getTime();
                String minTime = beforeDayStr+" 19:30:00";
                long minDate = DateUtil.parseDate(minTime,DateUtil.DATE_FMT_DISPLAY).getTime();
                List<SensorInOutLog> logs = sensorInOutLogDao.findUse(areaId,startTime,endTime);
                Double money = 0.00;
                for(SensorInOutLog sensorInOutLog : logs){
                    Double m = 0.00;
                    if(sensorInOutLog.getInTime().getTime()>minDate){
                        m =10.00;//晚上19点半之后开进的
                    }
                    if(sensorInOutLog.getInTime().getTime()<startDate&&sensorInOutLog.getInTime().getTime()==sensorInOutLog.getOutTime().getTime()){
                        m =235.00;//前一天早上7点半之前停进
                    }
                    if((sensorInOutLog.getInTime().getTime()>startDate&&sensorInOutLog.getInTime().getTime()<minDate&&sensorInOutLog.getInTime().getTime()==sensorInOutLog.getOutTime().getTime())||(sensorInOutLog.getInTime().getTime()>startDate&&sensorInOutLog.getOutTime().getTime()>minDate&&sensorInOutLog.getInTime().getTime()!=sensorInOutLog.getOutTime().getTime())){
                        long diff = minDate - sensorInOutLog.getInTime().getTime();//早上7点半之后停进未开出的
                        double d = diff/(60*30*1000);
                        double dd  = diff%(60*30*1000);
                        if(dd>0.00){
                            d++;
                        }
                        if(d<2){
                            d = 2;
                        }
                        m = 25.00 + 10*(d-2);
                    }
                    if(sensorInOutLog.getInTime().getTime()<startDate&&sensorInOutLog.getOutTime().getTime()<minDate&&sensorInOutLog.getInTime().getTime()!=sensorInOutLog.getOutTime().getTime()){
                        long diff = sensorInOutLog.getOutTime().getTime() - startDate;//前一天7点半之前开进 19点半之前驶出
                        double d = diff/(60*30*1000);
                        double dd  = diff%(60*30*1000);
                        if(dd>0.00){
                           d++;
                        }
                        if(d<2){
                            d = 2;
                        }
                        m = 15.00 + 10*(d-2);
                    }
                    if(sensorInOutLog.getInTime().getTime()<startDate&&sensorInOutLog.getOutTime().getTime()>minDate&&sensorInOutLog.getInTime().getTime()!=sensorInOutLog.getOutTime().getTime()){
                        m =235.00;
                    }
                    if(sensorInOutLog.getInTime().getTime()>startDate&&sensorInOutLog.getOutTime().getTime()<minDate&&sensorInOutLog.getInTime().getTime()!=sensorInOutLog.getOutTime().getTime()){
                        long diff = sensorInOutLog.getOutTime().getTime() - sensorInOutLog.getInTime().getTime();//正常收费区间停车
                        double d = diff/(60*30*1000);
                        double dd  = diff%(60*30*1000);
                        if(dd>0.00){
                            d++;
                        }
                        if(d<2){
                            d = 2;
                        }
                        m = 15.00 + 10*(d-2);
                    }
                    long diff = sensorInOutLog.getOutTime().getTime() - sensorInOutLog.getInTime().getTime();//正常收费区间停车
                    if(diff/(60*30*1000)<1){
                        m= 0.00;
                    }
                    money+= m;
                }
                SensorInOutMoneyLog sensorInOutMoneyLog = new SensorInOutMoneyLog();
                sensorInOutMoneyLog.setAreaId(areaId);
                sensorInOutMoneyLog.setCreateTime(new Date());
                sensorInOutMoneyLog.setHappenTime(date);
                sensorInOutMoneyLog.setMoney(money);
                sensorInOutMoneyLogDao.save(sensorInOutMoneyLog);
                return  sensorInOutMoneyLog;
            }
        }catch (Exception e){
            log.error("error:{}",e);
        }
        return null;
    }

    public List<SensorInOutMoneyLog> getMoneyMonth(Integer areaId,String dateStr){
        try {
            Date date = DateUtil.parseDate(dateStr,DateUtil.DATE_FMT_DISPLAY2);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH,-1);
            Date endDate = calendar.getTime();
            String startDate = DateUtil.getDate(endDate,DateUtil.DATE_FMT_DISPLAY2);
            List<SensorInOutMoneyLog> list = sensorInOutMoneyLogDao.getMoneyMonth(areaId,startDate,dateStr);
            return list;
        }catch (Exception e){
            log.error("error:{}",e);
        }
        return null;
    }

    public void save(SensorInOutMoneyLog sensorInOutMoneyLog){
        sensorInOutMoneyLogDao.save(sensorInOutMoneyLog);
    }

}
