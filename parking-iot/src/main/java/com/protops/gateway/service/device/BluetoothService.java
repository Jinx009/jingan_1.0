package com.protops.gateway.service.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.constants.common.SignConstant;
import com.protops.gateway.dao.log.BluetoothLogDao;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.BluetoothLog;
import com.protops.gateway.service.BaseService;
import com.protops.gateway.service.SensorService;
import com.protops.gateway.service.common.SignService;
import com.protops.gateway.vo.internal.BluetoothLogBo;
import com.protops.gateway.vo.internal.BluetoothLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinx on 6/23/17.
 */
@Service("bluetoothService")
public class BluetoothService extends BaseService{

    @Autowired
    private SignService signService;
    @Autowired
    private NewSensorService newSensorService;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private BluetoothLogDao bluetoothLogDao;


    public AjaxResponse status(String params){
        JSONObject jsonObject = JSONObject.parseObject(params);
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, SignConstant.SIGN_FAIL_MSG,null);
        if(signService.checkSign(jsonObject)){
            String baseId = jsonObject.getString(HttpConstant.BASE_ID);
            BluetoothLogVo bluetoothLogVo = JSON.parseObject(jsonObject.getString(HttpConstant.DATA),BluetoothLogVo.class);
            String listStr = jsonObject.getString(HttpConstant.DATA);
            JSONObject listObject = JSON.parseObject(listStr);
            List<BluetoothLogBo> list = JSONArray.parseArray(listObject.getString("bluetooths"),BluetoothLogBo.class);
            List<BluetoothLogBo> list2 = list;
            if(list!=null&&!list.isEmpty()){
                for(BluetoothLogBo bluetoothLog1:list){
                    BluetoothLog bluetoothLog2 = new BluetoothLog();
                    bluetoothLog2.setMac(bluetoothLogVo.getMac());
                    bluetoothLog2.setReportTime(bluetoothLogVo.getReportTime());
                    bluetoothLog2.setSignalValue(bluetoothLog1.getSignal());
                    bluetoothLog2.setStatus(bluetoothLogVo.getStatus());
                    bluetoothLog2.setUuid(bluetoothLog1.getUuid());
                    bluetoothLog2.setRouterMac(baseId);
                    bluetoothLogDao.save(bluetoothLog2);
                }
                Sensor sensor = sensorService.getByMac(bluetoothLogVo.getMac());
                sensor.setBluetoothArray(JSON.toJSONString(list));
                if("1".equals(listObject.getString("status"))){
                    if(list.size()==1){

                        BluetoothLogBo bluetoothLog3 = list.get(0);
                        sensor.setBluetooth(bluetoothLog3.getUuid());
                        sensorService.save(sensor);
                    }else{
                        List<Sensor> sensors = sensorService.getSensorsByArea(sensor.getAreaId());
                        for(Sensor sensor1:sensors){
                            for(BluetoothLogBo bluetoothLog:list){
                                if(bluetoothLog.getUuid().split("_")[0].equals(sensor1.getBluetooth())){
                                    list2.remove(bluetoothLog);
                                }
                            }
                        }
                        if(list2.size()==1){
                            sensor.setBluetoothArray(JSON.toJSONString(list));
                            BluetoothLogBo bluetoothLog3 = list2.get(0);
                            sensor.setBluetooth(bluetoothLog3.getUuid());
                            sensorService.save(sensor);
                        }
                    }
                }
            }
            ajaxResponse.setRespCode(HttpConstant.OK_CODE);
            ajaxResponse.setMsg(HttpConstant.OK_MSG);
        }
        return ajaxResponse;
    }

    public AjaxResponse bike(String params){
        JSONObject jsonObject = JSONObject.parseObject(params);
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, SignConstant.SIGN_FAIL_MSG,null);
        if(signService.checkSign(jsonObject)){
            String baseId = jsonObject.getString(HttpConstant.BASE_ID);
            BluetoothLogVo bluetoothLogVo = JSON.parseObject(jsonObject.getString(HttpConstant.DATA),BluetoothLogVo.class);
            String listStr = jsonObject.getString(HttpConstant.DATA);
            JSONObject listObject = JSON.parseObject(listStr);
            List<BluetoothLogBo> list = JSONArray.parseArray(listObject.getString("bluetooths"),BluetoothLogBo.class);
            if(list!=null&&!list.isEmpty()){
                for(BluetoothLogBo bluetoothLog1:list){
                    BluetoothLog bluetoothLog2 = new BluetoothLog();
                    bluetoothLog2.setMac(bluetoothLogVo.getMac());
                    bluetoothLog2.setReportTime(bluetoothLogVo.getReportTime());
                    bluetoothLog2.setSignalValue(bluetoothLog1.getSignal());
                    bluetoothLog2.setStatus(3);
                    bluetoothLog2.setUuid(bluetoothLog1.getUuid());
                    bluetoothLog2.setRouterMac(baseId);
                    bluetoothLogDao.save(bluetoothLog2);
                }
            }
            ajaxResponse.setRespCode(HttpConstant.OK_CODE);
            ajaxResponse.setMsg(HttpConstant.OK_MSG);
        }
        return ajaxResponse;
    }

}
