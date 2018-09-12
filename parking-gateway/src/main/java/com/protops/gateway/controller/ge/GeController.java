package com.protops.gateway.controller.ge;

import com.protops.gateway.Job.AreaJob;
import com.protops.gateway.constants.DeviceError;
import com.protops.gateway.constants.DeviceErrorMapping;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.enums.DeviceType;
import com.protops.gateway.domain.AreaVo;
import com.protops.gateway.domain.StatsResult;
import com.protops.gateway.domain.ajax.AjaxRequest;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.ErrorFlow;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.exception.GatewayException;
import com.protops.gateway.service.*;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.Page;
import com.protops.gateway.utils.HttpUtil;
import com.protops.gateway.utils.ResponseCodeHelper;
import com.protops.gateway.vo.ErrorFlowVo;
import com.protops.gateway.vo.SensorVo;
import com.protops.gateway.vo.ge.GeStatsResult;
import com.protops.gateway.vo.ge.LocationResponse;
import com.protops.gateway.vo.ge.LocationVo;
import com.protops.gateway.vo.ge.RushHour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by damen on 2016/4/9.
 * <p/>
 * 1，根据locationId获取片区列表
 * 2，根据areaId获取地磁列表
 */
@Controller
public class GeController {

    @Autowired
    LocationService locationService;

    @Autowired
    AreaService areaService;

    @Autowired
    SensorService sensorService;

    @Autowired
    AreaJob areaJob;

    @Autowired
    StatsService statsService;

    @Autowired
    ErrorFlowService errorFlowService;

    @Autowired
    SensorVoService sensorVoService;

    @RequestMapping(value = "device/sensors", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>> getSensorlist(@RequestParam(value = "p", required = false) Integer pageNo) throws IOException {

        AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>> ajaxResponse = new AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>>();

        Page<com.protops.gateway.vo.ge.SensorVo> page = new Page<com.protops.gateway.vo.ge.SensorVo>();

        Integer pageSize = 15;

        page.setPageSize(pageSize); //初始化每页条数

        if (pageNo == null) {
            page.setPageNo(1);//初始化页码
        } else {
            page.setPageNo(pageNo);
        }
        Map<String, String> searchMap = new HashMap<String, String>();
        searchMap.put("location", "4");
        sensorVoService.pagedList(page, searchMap);

        ajaxResponse.setDomain(page);

        return ajaxResponse;
    }

    @RequestMapping(value = "device/ty/sensors", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>> getSensorTylist(@RequestParam(value = "p", required = false) Integer pageNo) throws IOException {

        AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>> ajaxResponse = new AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>>();

        Page<com.protops.gateway.vo.ge.SensorVo> page = new Page<com.protops.gateway.vo.ge.SensorVo>();

        Integer pageSize = 15;

        page.setPageSize(pageSize); //初始化每页条数

        if (pageNo == null) {
            page.setPageNo(1);//初始化页码
        } else {
            page.setPageNo(pageNo);
        }
        Map<String, String> searchMap = new HashMap<String, String>();
        searchMap.put("location", "11");
        sensorVoService.pagedList(page, searchMap);

        ajaxResponse.setDomain(page);

        return ajaxResponse;
    }

    @RequestMapping(value = "device/boken/sensors", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>> getSensorBokenlist(@RequestParam(value = "p", required = false) Integer pageNo) throws IOException {

        AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>> ajaxResponse = new AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>>();

        Page<com.protops.gateway.vo.ge.SensorVo> page = new Page<com.protops.gateway.vo.ge.SensorVo>();

        Integer pageSize = 100;

        page.setPageSize(pageSize); //初始化每页条数

        if (pageNo == null) {
            page.setPageNo(1);//初始化页码
        } else {
            page.setPageNo(pageNo);
        }
        Map<String, String> searchMap = new HashMap<String, String>();
        searchMap.put("location", "19");
        sensorVoService.pagedList(page, searchMap);

        ajaxResponse.setDomain(page);

        return ajaxResponse;
    }

    @RequestMapping(value = "device/gd/sensors", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>> getSensorGdlist(@RequestParam(value = "p", required = false) Integer pageNo) throws IOException {

        AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>> ajaxResponse = new AjaxResponse<Page<com.protops.gateway.vo.ge.SensorVo>>();

        Page<com.protops.gateway.vo.ge.SensorVo> page = new Page<com.protops.gateway.vo.ge.SensorVo>();

        Integer pageSize = 300;

        page.setPageSize(pageSize); //初始化每页条数

        if (pageNo == null) {
            page.setPageNo(1);//初始化页码
        } else {
            page.setPageNo(pageNo);
        }
        Map<String, String> searchMap = new HashMap<String, String>();
        searchMap.put("location", "14");
        sensorVoService.pagedList(page, searchMap);

        ajaxResponse.setDomain(page);

        return ajaxResponse;
    }


    @RequestMapping(value = "device/errorFlow", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<List<ErrorFlowVo>> getLocationList(@RequestParam(value = "p", required = false) Integer pageNo) throws IOException {

        AjaxResponse<List<ErrorFlowVo>> ajaxResponse = new AjaxResponse<List<ErrorFlowVo>>();

        Page<ErrorFlow> page = new Page<ErrorFlow>();

        Integer pageSize = 15;

        page.setPageSize(pageSize); //初始化每页条数

        if (pageNo == null) {
            page.setPageNo(1);//初始化页码
        } else {
            page.setPageNo(pageNo);
        }

        Page<ErrorFlow> errorFlowPage = errorFlowService.pagedListWithError(page);
        List<ErrorFlowVo> errorFlowVos = new ArrayList<ErrorFlowVo>();
        for (ErrorFlow errorFlow : errorFlowPage.getResult()) {
            ErrorFlowVo errorFlowVo = new ErrorFlowVo(errorFlow);
            DeviceError deviceError = DeviceErrorMapping.getMappingCode(errorFlow.getErrorBitMap());
           if (deviceError == null || !deviceError.isVisible()) {
                continue;
            }
            errorFlowVo.setDeviceError(deviceError);
            if (errorFlowVo.getType() == DeviceType.Sensor) {
                Sensor sensor = sensorService.getByMac(errorFlow.getMac());
                if (sensor != null && sensor.getAreaId() != null) {
                    if (sensor.getAreaId() == 4 || sensor.getAreaId() == 5 || sensor.getAreaId() == 6 || sensor.getAreaId() == 7 || sensor.getAreaId() == 8 || sensor.getAreaId() == 9) {
                        Area area = areaService.get(sensor.getAreaId());
                        errorFlowVo.setArea(area.getName());
                        errorFlowVos.add(errorFlowVo);
                    }
                }
            } else if (errorFlowVo.getType() == DeviceType.Repeater) {//中继错误
                Sensor sensor = sensorService.getByMac(errorFlow.getMac());
                if (sensor != null && sensor.getAreaId() != null) {
                    if (sensor.getAreaId() == 4 || sensor.getAreaId() == 5 || sensor.getAreaId() == 6 || sensor.getAreaId() == 7 || sensor.getAreaId() == 8 || sensor.getAreaId() == 9) {
                        Area area = areaService.get(sensor.getAreaId());
                        errorFlowVo.setArea(area.getName());
                        errorFlowVos.add(errorFlowVo);
                    }
                }
            }
        }

        ajaxResponse.setDomain(errorFlowVos);
        System.out.println(errorFlowVos);
        return ajaxResponse;
    }


    /**
     * 外部接口
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "lbs/remains", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<String> getRemaining() throws IOException {

        AjaxResponse<String> ajaxResponse = new AjaxResponse<String>();

        Map<String, String> req = new HashMap<String, String>();
        req.put("key", "63ea0ebede2448298c61eb71acd2be42");
        req.put("parkCode", "wju5bhs8");
        req.put("parkLever", "1");

        String reqStr = JsonUtil.toJson(req);

        try {
            String ret = HttpUtil.post("http://cloud.bluecardsoft.com.cn/main/open/api/parkSpaceNumBypark", reqStr);

            ajaxResponse.setDomain(ret);

        } catch (Exception e) {

        }

        return ajaxResponse;
    }


    @RequestMapping(value = "lbs/locations", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRequest<List<Location>> getLocationList() throws IOException {

        areaJob.process();

        return null;
    }

    @RequestMapping(value = "lbs/get", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse<Location> getLocation(@RequestParam(value = "locationId", required = true) Integer locationId) throws IOException {

        AjaxResponse<Location> ajaxResponse = new AjaxResponse<Location>();

        try {

            if (locationId == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

            Location location = locationService.get(locationId);

            if (location == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

            ajaxResponse.setDomain(location);

        } catch (GatewayException e) {

            ResponseCodeHelper.parseErrorResponse(ajaxResponse, e);

        }

        return ajaxResponse;
    }


    /**
     * 该接口根据locationId获取所有area的list
     * 每个area的list需要获取某一个总数的结果
     *
     * @param locationId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "lbs/areas", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<LocationResponse> getAreaList(@RequestParam(value = "locationId", required = true) Integer locationId) throws IOException {

        AjaxResponse<LocationResponse> ajaxResponse = new AjaxResponse<LocationResponse>();

        try {

            if (locationId == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

            Location location = locationService.get(locationId);
            if (location == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

            List<Area> areaList = areaService.getAreasByLocationId(locationId);

            if (areaList == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

            // 对于每一个area，都要获取router的总和，即
            // group by areaId 并且 cnt
            List<AreaVo> areaCntList = sensorService.getByAreaId(areaList);

            LocationVo locationVo = new LocationVo();

            locationVo.setId(location.getId());
            locationVo.setName(location.getName());

            for (AreaVo areaVo : areaCntList) {

                BigInteger totalCnt = areaJob.getCnt(areaVo.getId());

                areaVo.setTotalCnt(totalCnt);

                locationVo.setAvailableCnt(locationVo.getAvailableCnt() + areaVo.getAvailableCnt().intValue());
                locationVo.setTotalCnt(locationVo.getTotalCnt() + totalCnt.intValue());

            }

            boolean in = false;
            for (Area area : areaList) {

                // 遍历每个areaVo，如果找到就设置为true，并且break掉。如果没有找到，就是false，是false，就是满了
                for (AreaVo areaVo : areaCntList) {

                    if (areaVo.getId() == area.getId()) {
                        in = true;
                        break;
                    }

                }

                if (!in) {
                    AreaVo areaVo = new AreaVo(area);
                    BigInteger totalCnt = areaJob.getCnt(area.getId());
                    areaVo.setTotalCnt(totalCnt);
                    areaVo.setAvailableCnt(new BigInteger("0"));
                    areaCntList.add(areaVo);
                    locationVo.setTotalCnt(locationVo.getTotalCnt() + totalCnt.intValue());
                }

                in = false;

            }

            LocationResponse locationResponse = LocationResponse.parse(locationVo, areaCntList);


            ajaxResponse.setDomain(locationResponse);

        } catch (GatewayException e) {

            ResponseCodeHelper.parseErrorResponse(ajaxResponse, e);

        }

        return ajaxResponse;
    }

    @RequestMapping(value = "lbs/detail", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<List<SensorVo>> getAvailableSensors(@RequestParam(value = "areaId", required = true) Integer areaId) throws IOException {

        AjaxResponse<List<SensorVo>> ajaxResponse = new AjaxResponse<List<SensorVo>>();

        try {

            if (areaId == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

            List<Sensor> sensorList = sensorService.getSensorsByArea(areaId);

            if (sensorList == null) {
                return ajaxResponse;
            }

            // 对于每一个area，都要获取router的总和，即
            // group by areaId 并且 cnt
            List<SensorVo> sensorVos = new ArrayList<SensorVo>();

            for (Sensor sensor : sensorList) {

                SensorVo sensorVo = new SensorVo();

                sensorVo.setId(sensor.getId());
                sensorVo.setAddr(sensor.getDesc());
                sensorVo.setAvailable(sensor.getAvailable());

                sensorVos.add(sensorVo);

            }

            ajaxResponse.setDomain(sensorVos);

        } catch (GatewayException e) {

            ResponseCodeHelper.parseErrorResponse(ajaxResponse, e);

        }

        return ajaxResponse;
    }

    /**
     * op - day-1&#xff0c;week-2&#xff0c;year-3
     * data - startDate
     *
     * @param op
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "lbs/stats", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<List<GeStatsResult>> getStats(@RequestParam(value = "areaId", required = true) Integer areaId, @RequestParam(value = "op", required = true) Integer op, @RequestParam(value = "date", required = true) String dateStr) throws IOException {

        AjaxResponse<List<GeStatsResult>> ajaxResponse = new AjaxResponse<List<GeStatsResult>>();
        // 获取拆解后的日期，这是截至日期
        List<StatsResult> results = null;
        if (op == 0) {

            // 如果是时，那就是当天24小时
            results = statsService.getByHour(areaId, dateStr);

        } else if (op == 1) {

            // 如果是天，那就是最近一周
            Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);

            Date startDate = DateUtil.getAddedTime(endDate, Calendar.DAY_OF_MONTH, -7);

            String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);

            results = statsService.getByDay(areaId, startStr, dateStr);

        } else if (op == 2) {

            // 如果是月，那就是最近30天

            Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);

            Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);

            String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);

            results = statsService.getByDay(areaId, startStr, dateStr);


        } else if (op == 3) {

            // 如果是年，最近12个月
            Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY4);

            Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);

            String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY4);

            results = statsService.getMonth(areaId, startStr, dateStr);

        }

        List<GeStatsResult> geStatsResults = new ArrayList<GeStatsResult>();

        for (StatsResult statsResult : results) {

            GeStatsResult geStatsResult = new GeStatsResult();
            geStatsResult.setPercent(statsResult.getPercent().toString());
            geStatsResult.setUnit(statsResult.getUnit());

            geStatsResults.add(geStatsResult);

        }

        ajaxResponse.setDomain(geStatsResults);

        return ajaxResponse;


    }

    @RequestMapping(value = "lbs/rushHour", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<Map<Integer, List<RushHour>>> rushHour(@RequestParam(value = "areaId", required = true) Integer areaId, @RequestParam(value = "date", required = true) String dateStr) throws IOException {

        AjaxResponse<Map<Integer, List<RushHour>>> ajaxResponse = new AjaxResponse<Map<Integer, List<RushHour>>>();

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

                        rushHours.add(new RushHour(result.getPercent(), result.getUnit()));

                        rushHourMap.put(result.getWeek(), rushHours);

                    }

                }

            }

        }

        ajaxResponse.setDomain(rushHourMap);

        return ajaxResponse;

    }


    @RequestMapping(value = "lbs/statsLocation", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<List<GeStatsResult>> getStatsByLocation(@RequestParam(value = "locationId", required = true) Integer areaId, @RequestParam(value = "op", required = true) Integer op, @RequestParam(value = "date", required = true) String dateStr) throws IOException {

        AjaxResponse<List<GeStatsResult>> ajaxResponse = new AjaxResponse<List<GeStatsResult>>();


        List<Area> areas = areaService.getAreasByLocationId(areaId);

        // 获取拆解后的日期，这是截至日期
        List<StatsResult> results = null;
        if (op == 0) {

            // 如果是时，那就是当天24小时
            results = statsService.getByHourByLocation(areas, dateStr);

        } else if (op == 1) {

            // 如果是天，那就是最近一周
            Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);

            Date startDate = DateUtil.getAddedTime(endDate, Calendar.DAY_OF_MONTH, -7);

            String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);

            results = statsService.getByDayByLocation(areas, startStr, dateStr);

        } else if (op == 2) {

            // 如果是月，那就是最近30天

            Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);

            Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);

            String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);

            results = statsService.getByDayByLocation(areas, startStr, dateStr);


        } else if (op == 3) {

            // 如果是年，最近12个月
            Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY4);

            Date startDate = DateUtil.getAddedTime(endDate, Calendar.YEAR, -1);

            String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY4);

            results = statsService.getMonthByLocation(areas, startStr, dateStr);

        }

        List<GeStatsResult> geStatsResults = new ArrayList<GeStatsResult>();

        for (StatsResult statsResult : results) {

            GeStatsResult geStatsResult = new GeStatsResult();
            geStatsResult.setPercent(statsResult.getPercent().toString());
            geStatsResult.setUnit(statsResult.getUnit());

            geStatsResults.add(geStatsResult);

        }

        ajaxResponse.setDomain(geStatsResults);

        return ajaxResponse;


    }

    @RequestMapping(value = "lbs/rushHourLocation", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<Map<Integer, List<RushHour>>> rushHourByLocation(@RequestParam(value = "locationId", required = true) Integer areaId, @RequestParam(value = "date", required = true) String dateStr) throws IOException {

        AjaxResponse<Map<Integer, List<RushHour>>> ajaxResponse = new AjaxResponse<Map<Integer, List<RushHour>>>();

        Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);

        Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);

        String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);

        List<Area> areas = areaService.getAreasByLocationId(areaId);

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

                        rushHourMap.put(result.getWeek(), rushHours);

                    }

                }

            }

        }

        ajaxResponse.setDomain(rushHourMap);

        return ajaxResponse;

    }
}
