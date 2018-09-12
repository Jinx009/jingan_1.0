package com.protops.gateway.controller.open;

import com.protops.gateway.Job.AreaJob;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.enums.RangeEnum;
import com.protops.gateway.domain.AccessToken;
import com.protops.gateway.domain.AreaVo;
import com.protops.gateway.domain.StatsResult;
import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.city.City;
import com.protops.gateway.domain.iot.Area;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.domain.iot.Sensor;
import com.protops.gateway.exception.GatewayException;
import com.protops.gateway.service.*;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.utils.ResponseCodeHelper;
import com.protops.gateway.vo.AreaAvailability;
import com.protops.gateway.vo.AreaDetail;
import com.protops.gateway.vo.AreaResponse;
import com.protops.gateway.vo.ge.GeStatsResult;
import com.protops.gateway.vo.ge.LocationResponse;
import com.protops.gateway.vo.ge.LocationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by damen on 2016/4/9.
 * <p/>
 * 1，根据locationId获取片区列表
 * 2，根据areaId获取地磁列表
 */
@Controller
@RequestMapping(value = "api")
public class OpenApiController {

    @Autowired
    LocationService locationService;

    @Autowired
    AreaService areaService;

    @Autowired
    SensorService sensorService;

    @Autowired
    StatsService statsService;

    @Autowired
    AuthTokenService authTokenService;

    @Autowired
    AreaJob areaJob;

    @Autowired
    CityService cityService;

    @RequestMapping(value = "location/city/{city}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<List<LocationResponse>> getByCity(@PathVariable("city") String city) throws IOException {

        AjaxResponse<List<LocationResponse>> ajaxResponse = new AjaxResponse<List<LocationResponse>>();

        try {

            City c = cityService.getCity("city", city);

            List<LocationResponse> locationVos = getResponse(c);

            ajaxResponse.setDomain(locationVos);

        } catch (GatewayException e) {

            ResponseCodeHelper.parseErrorResponse(ajaxResponse, e);

        }

        return ajaxResponse;


    }

    private List<LocationResponse> getResponse(City c) throws GatewayException {

// 获取到所有的location，获取所有id，找到所有area

        List<LocationResponse> locationVos = new ArrayList<LocationResponse>();

        if (c.getIds().size() == 0) {

            return locationVos;

        }

        List<Location> locations = locationService.getByIds(c.getIds());

        for (Location location : locations) {

            List<Area> areaList = areaService.getAreasByLocationId(location.getId());

            if (areaList == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

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

            LocationResponse locationResponse = LocationResponse.parse(locationVo, areaCntList);

            locationVos.add(locationResponse);

        }


        return locationVos;

    }

    @RequestMapping(value = "location/province/{province}/{city}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<List<LocationResponse>> getByProvince(@PathVariable("province") String province, @PathVariable("city") String city) throws IOException {

        AjaxResponse<List<LocationResponse>> ajaxResponse = new AjaxResponse<List<LocationResponse>>();

        try {

            City c = cityService.getCity("province", province, city);

            List<LocationResponse> locationVos = getResponse(c);

            ajaxResponse.setDomain(locationVos);

        } catch (GatewayException e) {

            ResponseCodeHelper.parseErrorResponse(ajaxResponse, e);

        }

        return ajaxResponse;
    }

    @RequestMapping(value = "areas/overview/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<AreaResponse> getLocationListBrief(@PathVariable("id") Integer areaId) throws IOException {

        AjaxResponse<AreaResponse> ajaxResponse = new AjaxResponse<AreaResponse>();

        try {

            if (areaId == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

            AreaResponse areaResponse = buildAreaResponse(areaId, true);

            ajaxResponse.setDomain(areaResponse);

        } catch (GatewayException e) {

            ResponseCodeHelper.parseErrorResponse(ajaxResponse, e);

        }

        return ajaxResponse;
    }

    /**
     * {
     * "respCode": "00",
     * "msg": "",
     * "params": {
     * "totalCnt": 20,
     * "availableCnt": 10,
     * "sensors": [{
     * "id": 1,
     * "available": 0,
     * "addr": "74"
     * }, {
     * "id": 2,
     * "available": 1,
     * "addr": "75"
     * }]
     * }
     * }
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "areas/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<AreaResponse> getLocationList(@PathVariable("id") Integer areaId) throws IOException {

        AjaxResponse<AreaResponse> ajaxResponse = new AjaxResponse<AreaResponse>();

        try {

            if (areaId == null) {
                throw new GatewayException(ResponseCodes.Sys.PARAM_ILLEGAL);
            }

            AreaResponse areaResponse = buildAreaResponse(areaId, false);

            ajaxResponse.setDomain(areaResponse);

        } catch (GatewayException e) {

            ResponseCodeHelper.parseErrorResponse(ajaxResponse, e);

        }

        return ajaxResponse;
    }

    private AreaResponse buildAreaResponse(Integer areaId, boolean isOverview) {
        AreaResponse areaResponse = new AreaResponse();


        List<Sensor> sensorList = sensorService.getSensorsByArea(areaId);

        if (sensorList == null) {
            return areaResponse;
        }

        // 对于每一个area，都要获取router的总和，即
        // group by areaId 并且 cnt
        List<AreaDetail> sensors = new ArrayList<AreaDetail>();

        Integer availableCnt = 0;
        for (Sensor sensor : sensorList) {

            if (sensor.getAvailable() == 0) {
                availableCnt++;
            }

            if (isOverview) {
                continue;
            }

            AreaDetail areaDetail = new AreaDetail();

            areaDetail.setId(sensor.getId());
            areaDetail.setAddr(sensor.getAddr());
            areaDetail.setAvailable(sensor.getAvailable());

            sensors.add(areaDetail);

        }


        Integer totalCnt = areaJob.getTotalCnt(areaId);
        areaResponse.setTotalCnt(totalCnt);
        areaResponse.setAvailableCnt(getSubCnt(areaId, availableCnt));

        areaResponse.setSensors(sensors);

        return areaResponse;
    }

    /**
     * 2017-04-21 新增避错车位状态
     *
     * @param areaId
     * @param availableCnt
     * @return
     */
    private int getSubCnt(Integer areaId, Integer availableCnt) {
        if (areaId == 4) {
            availableCnt = availableCnt - 1;
        } else if (areaId == 5) {
            availableCnt = availableCnt - 5;
        } else if (areaId == 6) {
            availableCnt = availableCnt - 3;
        } else if (areaId == 7) {
            availableCnt = availableCnt - 2;
        } else if (areaId == 8) {
            availableCnt = availableCnt - 1;
        } else if (areaId == 9) {
            availableCnt = availableCnt - 2;
        }
        if (availableCnt > 0) {
            return availableCnt;
        }
        return 0;
    }

    @RequestMapping(value = "areas/{id}/availability/{range}/{date}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<List<AreaAvailability>> getStats(@PathVariable("id") Integer areaId, @PathVariable(value = "range") RangeEnum range, @PathVariable(value = "date") String dateStr) throws IOException {

        AjaxResponse<List<AreaAvailability>> ajaxResponse = new AjaxResponse<List<AreaAvailability>>();

        Date date = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY1);
        dateStr = DateUtil.getDate(date, DateUtil.DATE_FMT_DISPLAY2);

        // 获取拆解后的日期，这是截至日期
        List<StatsResult> results = null;

        if (range == RangeEnum.day) {

            // 如果是时，那就是当天24小时
            results = statsService.getByHour(areaId, dateStr);

        } else if (range == RangeEnum.week) {

            // 如果是天，那就是最近一周
            Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);

            Date startDate = DateUtil.getAddedTime(endDate, Calendar.DAY_OF_MONTH, -7);

            String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);

            results = statsService.getByDay(areaId, startStr, dateStr);

        } else if (range == RangeEnum.month) {

            // 如果是月，那就是最近30天

            Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY2);

            Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);

            String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY2);

            results = statsService.getByDay(areaId, startStr, dateStr);

        } else if (range == RangeEnum.year) {

            // 如果是年，最近12个月
            Date endDate = DateUtil.parseDate(dateStr, DateUtil.DATE_FMT_DISPLAY4);

            Date startDate = DateUtil.getAddedTime(endDate, Calendar.MONTH, -1);

            String startStr = DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY4);

            results = statsService.getMonth(areaId, startStr, dateStr);

        }

        List<AreaAvailability> areaAvailabilities = new ArrayList<AreaAvailability>();

        for (StatsResult statsResult : results) {

            AreaAvailability areaAvailability = new AreaAvailability();
            areaAvailability.setPercent(statsResult.getPercent().toString());
            areaAvailability.setUnit(statsResult.getUnit());

            areaAvailabilities.add(areaAvailability);

        }

        ajaxResponse.setDomain(areaAvailabilities);

        return ajaxResponse;


    }


}
