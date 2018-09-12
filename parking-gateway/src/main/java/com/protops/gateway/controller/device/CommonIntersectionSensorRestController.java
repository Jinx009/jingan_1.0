package com.protops.gateway.controller.device;

import com.protops.gateway.domain.ajax.AjaxResponse;
import com.protops.gateway.domain.device.CarNumViewVo;
import com.protops.gateway.domain.device.CarNumVo;
import com.protops.gateway.service.common.TokenFactoryService;
import com.protops.gateway.service.device.CarNumViewVoService;
import com.protops.gateway.service.device.CarNumVoService;
import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CommonIntersectionSensorRestController {
    private static final Logger logger = LoggerFactory.getLogger(CommonIntersectionSensorRestController.class);
    @Autowired
    private CarNumVoService carNumVoService;
    @Autowired
    private TokenFactoryService tokenFactoryService;
    @Autowired
    private CarNumViewVoService carNumViewVoService;

    @RequestMapping(value={"/rest/intersection/nums"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public AjaxResponse getCarNums(@RequestParam(value="token", required=false) String token, @RequestParam(value="area", required=false) Integer area, @RequestParam(value="location", required=false) Integer location, @RequestParam(value="lid", required=false) Integer lid, @RequestParam(value="type", required=false) Integer type, @RequestParam(value="dateStr", required=false) String dateStr) {
        AjaxResponse ajaxResponse = new AjaxResponse("300", "系统异常", null);
        logger.warn("CommonIntersectionsensorRestController.getCarNums [req:{},{}]", token, area);
        if (this.tokenFactoryService.checkToken(token)) {
            if (location != null) {
                if (!StringUtils.isNotBlank(dateStr)) {
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    dateStr = simpleDateFormat.format(date);
                }
                if (type.intValue() == 0) {
                    List<CarNumVo> list = this.carNumVoService.getCarNumOneDay(location, area, lid, dateStr);
                    ajaxResponse = new AjaxResponse("200", "访问成功", list);
                }
                else if (type.intValue() == 2) {
                    Date endDate = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
                    Date startDate = DateUtil.getAddedTime(endDate, 2, -1);
                    String endStr = DateUtil.getDate(startDate, "yyyy-MM-dd");
                    List<CarNumVo> list = this.carNumVoService.getCarNumManyDay(location, area, lid, dateStr, endStr);
                    ajaxResponse = new AjaxResponse("200", "访问成功", list);
                }
                else if (type.intValue() == 3) {
                    Date endDate = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
                    Date startDate = DateUtil.getAddedTime(endDate, 1, -1);
                    String endStr = DateUtil.getDate(startDate, "yyyy-MM-dd");
                    List<CarNumVo> list = this.carNumVoService.getCarNumManyDay(location, area, lid, dateStr, endStr);
                    ajaxResponse = new AjaxResponse("200", "访问成功", list);
                }
                else {
                    ajaxResponse.setMsg("接口不存在");
                }
            }
        }
        else {
            ajaxResponse.setMsg("token失效");
        }
        return ajaxResponse;
    }

    @RequestMapping(value={"/rest/intersection/numsView"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public AjaxResponse getCarNumView(@RequestParam(value="token", required=false) String token, @RequestParam(value="area", required=false) Integer area, @RequestParam(value="location", required=false) Integer location, @RequestParam(value="type", required=false) Integer type, @RequestParam(value="dateStr", required=false) String dateStr) {
        AjaxResponse ajaxResponse = new AjaxResponse("300", "系统异常", null);
        logger.warn("CommonIntersectionsensorRestController.getCarNumView [req:{},{}]", token, area);
        if (this.tokenFactoryService.checkToken(token)) {
            if (location != null) {
                if (type.intValue() == 0) {
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    dateStr = simpleDateFormat.format(date);
                    Date startDate = DateUtil.getAddedTime(date, 12, -5);
                    String startStr = DateUtil.getDate(startDate, "yyyy-MM-dd HH:mm:ss");
                    List<CarNumViewVo> list = this.carNumViewVoService.getData(location, area, startStr, dateStr);
                    ajaxResponse = new AjaxResponse("200", "访问成功", list);
                }
                else {
                    String[] str = getNewStringArray(type, dateStr);
                    List<CarNumViewVo> list = this.carNumViewVoService.getData(location, area, str[0], str[1]);
                    ajaxResponse = new AjaxResponse("200", "访问成功", list);
                }
            }
        }
        else {
            ajaxResponse.setMsg("token失效");
        }
        return ajaxResponse;
    }

    private String[] getNewStringArray(Integer type, String dateStr) {
        String[] arrs = { "00", "00", "00", "01", "01", "02", "02", "03", "03", "04", "04", "05", "05", "06", "06", "07", "07", "08", "08", "09", "09", "10", "10", "11", "11", "12", "12", "13", "13", "14", "14", "15", "15", "16", "16", "17", "17", "18", "18", "19", "19", "20", "20", "21", "21", "22", "22", "23", "23" };
        if (type.intValue() % 2 != 0) {
            return new String[] { dateStr + " " + arrs[type.intValue()] + ":00:00", dateStr + " " + arrs[type.intValue()] + ":29:59" };
        }
        return new String[] { dateStr + " " + arrs[type.intValue()] + ":30:00", dateStr + " " + arrs[type.intValue()] + ":59:59" };
    }

    private String[] getStringArray(Integer type, String dateStr) {
        if (1 == type.intValue()) {
            return new String[] { dateStr + " 06:00:00", dateStr + " 07:00:00" };
        }
        if (2 == type.intValue()) {
            return new String[] { dateStr + " 07:00:00", dateStr + " 08:00:00" };
        }
        if (3 == type.intValue()) {
            return new String[] { dateStr + " 08:00:00", dateStr + " 09:00:00" };
        }
        if (4 == type.intValue()) {
            return new String[] { dateStr + " 09:00:00", dateStr + " 10:00:00" };
        }
        if (5 == type.intValue()) {
            return new String[] { dateStr + " 10:00:00", dateStr + " 11:00:00" };
        }
        if (6 == type.intValue()) {
            return new String[] { dateStr + " 11:00:00", dateStr + " 12:00:00" };
        }
        if (7 == type.intValue()) {
            return new String[] { dateStr + " 12:00:00", dateStr + " 13:00:00" };
        }
        if (8 == type.intValue()) {
            return new String[] { dateStr + " 13:00:00", dateStr + " 14:00:00" };
        }
        if (9 == type.intValue()) {
            return new String[] { dateStr + " 14:00:00", dateStr + " 15:00:00" };
        }
        if (10 == type.intValue()) {
            return new String[] { dateStr + " 15:00:00", dateStr + " 16:00:00" };
        }
        if (11 == type.intValue()) {
            return new String[] { dateStr + " 16:00:00", dateStr + " 17:00:00" };
        }
        if (12 == type.intValue()) {
            return new String[] { dateStr + " 17:00:00", dateStr + " 18:00:00" };
        }
        if (13 == type.intValue()) {
            return new String[] { dateStr + " 18:00:00", dateStr + " 19:00:00" };
        }
        if (14 == type.intValue()) {
            return new String[] { dateStr + " 19:00:00", dateStr + " 20:00:00" };
        }
        if (15 == type.intValue()) {
            return new String[] { dateStr + " 20:00:00", dateStr + " 21:00:00" };
        }
        if (16 == type.intValue()) {
            return new String[] { dateStr + " 21:00:00", dateStr + " 22:00:00" };
        }
        if (17 == type.intValue()) {
            return new String[] { dateStr + " 22:00:00", dateStr + " 23:00:00" };
        }
        if (18 == type.intValue()) {
            return new String[] { dateStr + " 23:00:00", dateStr + " 24:00:00" };
        }
        if (19 == type.intValue()) {
            return new String[] { dateStr + " 24:00:00", dateStr + " 01:00:00" };
        }
        return null;
    }
}
