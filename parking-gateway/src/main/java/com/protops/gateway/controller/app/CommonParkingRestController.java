package com.protops.gateway.controller.app;

import com.protops.gateway.constants.DeviceError;
import com.protops.gateway.constants.DeviceErrorMapping;
import com.protops.gateway.constants.common.HttpConstant;
import com.protops.gateway.constants.enums.DeviceType;
import com.protops.gateway.domain.AppInfo;
import com.protops.gateway.domain.StatsResult;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.common.TokenFactory;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.ErrorFlow;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.domain.log.BluetoothLog;
import com.protops.gateway.service.*;
import com.protops.gateway.service.common.TokenFactoryService;
import com.protops.gateway.service.device.BluetoothLogService;
import com.protops.gateway.service.device.GeCarInOutService;
import com.protops.gateway.service.log.SensorDeviceLogService;
import com.protops.gateway.service.log.SensorInOutMoneyLogService;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.Page;
import com.protops.gateway.util.StringUtils;
import com.protops.gateway.utils.MapUtils;
import com.protops.gateway.vo.ErrorFlowVo;
import com.protops.gateway.vo.SensorVo;
import com.protops.gateway.vo.ge.GeStatsResult;
import com.protops.gateway.vo.ge.RushHour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

/**
 * Created by jinx on 6/22/17.
 */
@Controller
public class CommonParkingRestController {

    private static final Logger logger = LoggerFactory.getLogger(CommonParkingRestController.class);

    @Autowired
    private LocationService locationService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private SensorVoService sensorVoService;
    @Autowired
    private TokenFactoryService tokenFactoryService;
    @Autowired
    private BluetoothLogService bluetoothLogService;
    @Autowired
    private ErrorFlowService errorFlowService;
    @Autowired
    private GeCarInOutService geCarInOutService;
    @Autowired
    private SensorDeviceLogService sensorDeviceLogService;
    @Autowired
    private SensorInOutMoneyLogService sensorInOutMoneyLogService;

    /**
     * location首页数据图（上）
     *
     * @param token
     * @param locationId
     * @param op
     * @param dateStr
     * @return
     */
    @RequestMapping(value = "/rest/parking/locationStatus")
    @ResponseBody
    public AjaxResponse<?> locationStatus(@RequestParam(value = "token") String token,
                                          @RequestParam(value = "locationId") Integer locationId,
                                          @RequestParam(value = "op") Integer op,
                                          @RequestParam(value = "date") String dateStr) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("CommonLocationRestController.locationStatus [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                if (locationId == null || 0 == locationId) {
                    return ajaxResponse;
                } else {
                    ajaxResponse = new AjaxResponse<List<GeStatsResult>>();
                    List<Area> areas = areaService.getAreasByLocationId(locationId);
                    List<StatsResult> results = new ArrayList<StatsResult>();
                    String startStr;
                    if (op == 0) {
                        results = statsService.getByHourByLocationAll(areas, dateStr);
                    } else if (op == 1) {
                        Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);
                        Date startDate = DateUtil.getAddedTime(endDate, Calendar.DAY_OF_MONTH, -7);
                        startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);
                        results = statsService.getByDayByLocationAll(areas, startStr, dateStr);
                    } else if (op == 2) {
                        Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);
                        Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);
                        startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);
                        results = statsService.getByDayByLocationAll(areas, startStr, dateStr);
                    } else if (op == 3) {
                        Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY4);
                        Date startDate = DateUtil.getAddedTime(endDate, Calendar.YEAR, -1);
                        startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY4);
                        results = statsService.getMonthByLocationAll(areas, startStr, dateStr);
                    }

                    List<GeStatsResult> geStatsResults = new ArrayList<GeStatsResult>();
                    for (StatsResult statsResult : results) {
                        GeStatsResult geStatsResult = new GeStatsResult();
                        geStatsResult.setPercent(statsResult.getPercent().toString());
                        geStatsResult.setUnit(statsResult.getUnit());
                        if (statsResult != null) {
                        }
                        geStatsResults.add(geStatsResult);
                    }
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, geStatsResults);
                    return ajaxResponse;
                }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("CommonParkingRestController.locationStatus.error:{}", e);
        }
        return ajaxResponse;
    }

    /**
     * location首页数据图（下）
     *
     * @param token
     * @param locationId
     * @param dateStr
     * @return
     */
    @RequestMapping(value = "/rest/parking/locationRush")
    @ResponseBody
    public AjaxResponse<?> locationRush(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "locationId") Integer locationId,
                                        @RequestParam(value = "date") String dateStr) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("CommonLocationRestController.locationRush [req:{},{}]", token, locationId);
        try {
            if (tokenFactoryService.checkToken(token)) {
                if (locationId == null || 0 == locationId) {
                    return ajaxResponse;
                } else {
                    ajaxResponse = new AjaxResponse<Map<Integer, List<RushHour>>>();
                    Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);
                    Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);
                    String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);
                    List<Area> areas = areaService.getAreasByLocationId(locationId);
                    List<StatsResult> results = statsService.getRushHourByLocation(areas, startStr, dateStr);
                    Map<Integer, List<RushHour>> rushHourMap = new HashMap<Integer, List<RushHour>>();
                    for (Integer week : RushHour.weekDay) {
                        for (StatsResult result : results) {
                            if (result.getWeek() == week) {
                                if (rushHourMap.containsKey(result.getWeek())) {
                                    List<RushHour> rushHours = rushHourMap.get(result.getWeek());
                                    rushHours.add(new RushHour(result.getPercent(), result.getUnit()));
                                } else {
                                    List<RushHour> rushHours = new ArrayList<RushHour>();
                                    rushHours.add(new RushHour(result.getPercent(), result.getUnit()));
                                    if (rushHours != null) {
                                    }
                                    rushHourMap.put(result.getWeek(), rushHours);
                                }
                            }
                        }
                    }
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, rushHourMap);
                    return ajaxResponse;
                }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("CommonParkingRestController.locationRush.error:{}", e);
        }
        return ajaxResponse;
    }

    /**
     * area首页数据图（下）
     *
     * @param token
     * @param areaId
     * @param dateStr
     * @return
     */
    @RequestMapping(value = "/rest/parking/rush")
    @ResponseBody
    public AjaxResponse<?> rush(@RequestParam(value = "token") String token,
                                @RequestParam(value = "areaId") Integer areaId,
                                @RequestParam(value = "date") String dateStr) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("CommonLocationRestController.rush [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                if (areaId == null || 0 == areaId) {
                    return ajaxResponse;
                } else {
                    ajaxResponse = new AjaxResponse<Map<Integer, List<RushHour>>>();
                    Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);
                    Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);
                    String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);
                    List<StatsResult> results = statsService.getRushHour(areaId, startStr, dateStr);
                    Map<Integer, List<RushHour>> rushHourMap = new HashMap<Integer, List<RushHour>>();
                    for (Integer week : RushHour.weekDay) {
                        for (StatsResult result : results) {
                            if (result.getWeek() == week) {
                                if (rushHourMap.containsKey(result.getWeek())) {
                                    List<RushHour> rushHours = rushHourMap.get(result.getWeek());
                                    rushHours.add(new RushHour(result.getPercent(), result.getUnit()));
                                } else {
                                    List<RushHour> rushHours = new ArrayList<RushHour>();
                                    if (rushHours != null) {
                                    }
                                    rushHours.add(new RushHour(result.getPercent(), result.getUnit()));
                                    rushHourMap.put(result.getWeek(), rushHours);
                                }
                            }
                        }
                    }
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, rushHourMap);
                    return ajaxResponse;
                }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("CommonParkingRestController.rush.error:{}", e);
        }
        return ajaxResponse;
    }

    /**
     * area首页数据图（上）
     *
     * @param token
     * @param areaId
     * @param dateStr
     * @return
     */
    @RequestMapping(value = "/rest/parking/areaStatus")
    @ResponseBody
    public AjaxResponse<?> areaStatus(@RequestParam(value = "token") String token,
                                      @RequestParam(value = "areaId") Integer areaId,
                                      @RequestParam(value = "op") Integer op,
                                      @RequestParam(value = "date") String dateStr) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("areaStatus [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                if (areaId == null || 0 == areaId) {
                    return ajaxResponse;
                } else {
                    ajaxResponse = new AjaxResponse<List<GeStatsResult>>();
                    List<StatsResult> results = new ArrayList<StatsResult>();
                    String startStr;
                    if (op == 0) {
                        results = statsService.getByHour(areaId, dateStr);
                    } else if (op == 1) {
                        Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);
                        Date startDate = DateUtil.getAddedTime(endDate, Calendar.DAY_OF_MONTH, -7);
                        startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);
                        results = statsService.getByDay(areaId, startStr, dateStr);
                    } else if (op == 2) {
                        Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);
                        Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);
                        startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);
                        results = statsService.getByDay(areaId, startStr, dateStr);
                    } else if (op == 3) {
                        Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY4);
                        Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);
                        startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY4);
                        results = statsService.getMonth(areaId, startStr, dateStr);
                    }

                    List<GeStatsResult> geStatsResults = new ArrayList<GeStatsResult>();

                    for (StatsResult statsResult : results) {
                        GeStatsResult geStatsResult = new GeStatsResult();
                         geStatsResult.setPercent(statsResult.getPercent().toString());
                        geStatsResult.setUnit(statsResult.getUnit());
                        geStatsResults.add(geStatsResult);
                        if (statsResult != null) {
                        }
                    }

                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, geStatsResults);
                    return ajaxResponse;
                }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("areaStatus.error:{}", e);
        }
        return ajaxResponse;
    }

    /**
     * 具体区域详情
     *
     * @param token
     * @param areaId
     * @return
     */
    @RequestMapping(value = "/rest/parking/detail")
    @ResponseBody
    public AjaxResponse<?> detail(@RequestParam(value = "token") String token,
                                  @RequestParam(value = "areaId") Integer areaId) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("detail [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                if (areaId == null || 0 == areaId) {
                    return ajaxResponse;
                } else {
                    List<Sensor> sensorList = sensorService.getSensorsByArea(areaId);
                    if (sensorList == null)
                        return ajaxResponse;

                    List<SensorVo> sensorVos = new ArrayList<SensorVo>();

                    for (Sensor sensor : sensorList) {
                        SensorVo sensorVo = new SensorVo();
                        sensorVo.setId(sensor.getId());
                        sensorVo.setAddr(sensor.getDesc());
                        sensorVo.setAvailable(sensor.getAvailable());
                        sensorVo.setBluetooth(sensor.getBluetooth());
                        sensorVos.add(sensorVo);
                    }
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, sensorVos);
                    return ajaxResponse;
                }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("detail error:{}", e);
        }
        return ajaxResponse;
    }

    /**
     * 设备数据
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/rest/parking/device")
    @ResponseBody
    public AjaxResponse<?> device(@RequestParam(value = "token") String token,
                                  @RequestParam(value = "p", required = false) Integer pageNo) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("device [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                TokenFactory tokenFactory = tokenFactoryService.getToken(token);
                AppInfo appInfo = appInfoService.getByAppId(tokenFactory.getBaseId());
                List<Location> list = locationService.findOpenByAppInfoId(appInfo.getId());
                ajaxResponse = new AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>>();
                Page<com.protops.gateway.vo.ge.SensorVo> page = new Page<com.protops.gateway.vo.ge.SensorVo>();
                Integer pageSize = 100;
                page.setPageSize(pageSize);
                if (pageNo == null) {
                    page.setPageNo(1);
                } else {
                    page.setPageNo(pageNo);
                }
                Map<String, String> searchMap = new HashMap<String, String>();
                if (list != null && !list.isEmpty()) {
                    Location location = list.get(0);
                    searchMap.put("location", String.valueOf(location.getId()));
                    sensorVoService.pagedList(page, searchMap);
//                    List<com.protops.gateway.vo.ge.SensorVo> sensors = page.getResult();
//                    List<com.protops.gateway.vo.ge.SensorVo> vos = new ArrayList<com.protops.gateway.vo.ge.SensorVo>();
//                    if(sensors!=null&&!sensors.isEmpty()){
//                        for(com.protops.gateway.vo.ge.SensorVo sensorVo: sensors){
//                            Date date = sensorVo.getLastSeenTime();
//                            if(date!=null){
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.setTime(sensorVo.getLastSeenTime());
//                                calendar.add(Calendar.DATE,-1);
//                                Date startTime = calendar.getTime();
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                String dateStr = sdf.format(startTime);
//                                SensorDeviceLog sensorDeviceLog = sensorDeviceLogService.getNearMax(sensorVo.getMac(),dateStr);
//                                if(sensorDeviceLog!=null){
//                                    sensorVo.setBatteryVoltage(sensorDeviceLog.getBatteryVoltage());
//                                }
//                            }
//                            vos.add(sensorVo);
//                        }
//                    }
//                    page.setResult(se);
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, page);
                    return ajaxResponse;
                }
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("device.error:{}", e);
        }
        return ajaxResponse;
    }

    /**
     * 共享单车
     *
     * @param token
     * @param mac
     * @return
     */
    @RequestMapping(value = "/rest/parking/bikes")
    @ResponseBody
    public AjaxResponse<?> bikes(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "mac") String mac) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("bikes [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                List<BluetoothLog> list = bluetoothLogService.findByRouterMac(mac);
                ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, list);
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("bikes.error:{}", e);
        }
        return ajaxResponse;
    }

    /**
     * 错误列表（中继，地磁）
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/rest/parking/errors", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<List<ErrorFlowVo>> errors(@RequestParam(value = "token", required = false) String token,
                                                  @RequestParam(value = "areaId", required = false) Integer areaId) throws IOException {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("errors [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                Page<ErrorFlow> page = new Page<ErrorFlow>();
                Integer pageSize = 1000;
                page.setPageSize(pageSize); //初始化每页条数
                page.setPageNo(1);//初始化页码
                Page<ErrorFlow> errorFlowPage = errorFlowService.pagedListWithError(page);
                List<ErrorFlowVo> errorFlowVos = new ArrayList<ErrorFlowVo>();
                for (ErrorFlow errorFlow : errorFlowPage.getResult()) {
                    ErrorFlowVo errorFlowVo = new ErrorFlowVo(errorFlow);
                    DeviceError deviceError = DeviceErrorMapping.getMappingCode(errorFlow.getErrorBitMap());
                    if (deviceError == null || !deviceError.isVisible()) {
                        continue;
                    }
                    errorFlowVo.setDeviceError(deviceError);
                    Sensor sensor = null;
                    if (errorFlowVo.getType() == DeviceType.Sensor) {
                        sensor = sensorService.getByMac(errorFlow.getMac());
                    } else if (errorFlowVo.getType() == DeviceType.Repeater) {//中继错误
                        sensor = sensorService.getByParentMac(errorFlow.getMac());
                    }
                    if (sensor != null && sensor.getAreaId() != null) {
                        if (sensor.getAreaId() == areaId) {
                            Area area = areaService.get(sensor.getAreaId());
                            errorFlowVo.setArea(area.getName());
                            errorFlowVos.add(errorFlowVo);
                        }
                    }
                }
                ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, errorFlowVos);
                logger.warn("error data:{}", ajaxResponse);
                return ajaxResponse;
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("errors.error:{}", e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "/rest/parking/inOutLog")
    @ResponseBody
    public AjaxResponse inOutLog(@RequestParam(value = "token", required = false) String token,
                                  @RequestParam(value = "areaId", required = false) Integer areaId,
                                  @RequestParam(value = "dateStr", required = false) String dateStr,
                                  @RequestParam(value = "type", required = false) Integer type) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("errors [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                if (!StringUtils.isNotBlank(dateStr)) {
                    dateStr = DateUtil.getDate(new Date(), DateUtil.DATE_FMT_DISPLAY2);
                }
                if (type == 1) {
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, geCarInOutService.getByArea(dateStr, areaId));
                } else {
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, geCarInOutService.getByAreaMonth(dateStr, areaId));
                }
                logger.warn("inOutLog.data:{}", ajaxResponse);
                return ajaxResponse;
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("inOutLog.error:{}", e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "/rest/parking/money")
    @ResponseBody
    public AjaxResponse money(@RequestParam(value = "token", required = false) String token,
                                 @RequestParam(value = "areaId", required = false) Integer areaId,
                                 @RequestParam(value = "dateStr", required = false) String dateStr,
                                 @RequestParam(value = "type", required = false) Integer type) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn(" [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                if (!StringUtils.isNotBlank(dateStr)) {
                    dateStr = DateUtil.getDate(new Date(), DateUtil.DATE_FMT_DISPLAY2);
                }
                if (type == 1) {
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, sensorInOutMoneyLogService.getMoney(areaId,dateStr));
                } else {
                    ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, sensorInOutMoneyLogService.getMoneyMonth(areaId,dateStr));
                }
                logger.warn("inOutLog.data:{}", ajaxResponse);
                return ajaxResponse;
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("inOutLog.error:{}", e);
        }
        return ajaxResponse;
    }


    @RequestMapping(value = "/rest/parking/areas")
    @ResponseBody
    public AjaxResponse<?> areas(@RequestParam(value = "token") String token,
                                 @RequestParam(value = "lng") Double lng,
                                 @RequestParam(value = "lat") Double lat) {
        AjaxResponse ajaxResponse = new AjaxResponse(HttpConstant.ERROR_CODE, HttpConstant.ERROE_MSG, null);
        logger.warn("areas [req:{}]", token);
        try {
            if (tokenFactoryService.checkToken(token)) {
                TokenFactory tokenFactory = tokenFactoryService.getToken(token);
                AppInfo appInfo = appInfoService.getByAppId(tokenFactory.getBaseId());
                List<Location> list = locationService.findOpenByAppInfoId(appInfo.getId());
                List<Area> areas = new ArrayList<Area>();
                if(list!=null&&!list.isEmpty()){
                    for(Location location : list){
                        List<Area> areas1 = areaService.getAreasByLocationId(location.getId());
                        if(areas1!=null&&!areas1.isEmpty()){
                            for(Area area : areas1){
                                if(StringUtils.isNotBlank(area.getLongitude())&&StringUtils.isNotBlank(area.getLatitude())){
                                    Double lng1 = Double.valueOf(area.getLongitude());
                                    Double lat1 = Double.valueOf(area.getLatitude());
                                    double distance = MapUtils.getDistance(lng,lat,lng1,lat1);
                                    if(distance<=5.0){
                                        areas.add(area);
                                    }
                                }
                            }
                        }
                    }
                }

                ajaxResponse = new AjaxResponse(HttpConstant.OK_CODE, HttpConstant.OK_MSG, areas);
                return ajaxResponse;
            } else {
                ajaxResponse.setMsg(HttpConstant.TOKEN_FAIL);
            }
        } catch (Exception e) {
            logger.error("device.error:{}", e);
        }
        return ajaxResponse;
    }


}