package com.protops.gateway.domain;

import com.protops.gateway.constants.IOTContext;
import com.protops.gateway.constants.ResponseCodes;
import com.protops.gateway.constants.enums.Version;
import com.protops.gateway.domain.base.IOTDomain;
import com.protops.gateway.exception.IOTException;
import com.protops.gateway.vo.internal.SensorVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by damen on 2016/4/4.
 */
public class SensorRegisterRequest extends IOTDomain {


    private List<SensorVo> ssr;

    private String cnt;


    @Override
    public void validateDomain() throws IOTException {

        if (ssr == null) {
            throw new IOTException(ResponseCodes.Sys.PARAM_ILLEGAL);
        }

        if (!StringUtils.isNumeric(cnt)) {
            throw new IOTException(ResponseCodes.Sys.PARAM_ILLEGAL);
        }

        Integer cntInt = Integer.valueOf(cnt);
        if (cntInt != ssr.size() || ssr.size() > 5) {
            throw new IOTException(ResponseCodes.Sys.PARAM_ILLEGAL);
        }

    }

    @Override
    public String parseSignStr(IOTContext iotContext) {

        StringBuilder sb = new StringBuilder();

        for (SensorVo sensorVo : getSsr()) {

            genKeyValuePairWithAnd(sb, "avlb", sensorVo.getAvailable() == null ? "" : sensorVo.getAvailable(), iotContext);
            genKeyValuePairWithAnd(sb, "be", sensorVo.getBaseEnergy() == null ? "" : sensorVo.getBaseEnergy(), iotContext);
            genKeyValuePairWithAnd(sb, "bv", sensorVo.getBatteryVoltage() == null ? "" : sensorVo.getBatteryVoltage(), iotContext); // 不参与签名
            genKeyValuePairWithAnd(sb, "cid", sensorVo.getChannelId() == null ? "" : sensorVo.getChannelId(), iotContext);
            genKeyValuePairWithAnd(sb, "cnct", sensorVo.getConnected() == null ? "" : sensorVo.getConnected(), iotContext); // 不参与签名
            genKeyValuePairWithAnd(sb, "dc", sensorVo.getDescription() == null ? "" : sensorVo.getDescription(), iotContext); // 不参与签名
            genKeyValuePairWithAnd(sb, "fc", sensorVo.getFrequency() == null ? "" : sensorVo.getFrequency(), iotContext);
            genKeyValuePairWithAnd(sb, "fv", sensorVo.getFirmwareVersion() == null ? "" : sensorVo.getFirmwareVersion(), iotContext);
            genKeyValuePairWithAnd(sb, "hbi", sensorVo.getHeartBeatInterval() == null ? "" : sensorVo.getHeartBeatInterval(), iotContext);

            if(iotContext.getVersion() != Version.v1_0) {
                genKeyValuePairWithAnd(sb, "hv", sensorVo.getHardwareVersion() == null ? "" : sensorVo.getHardwareVersion(), iotContext);
            }

            genKeyValuePairWithAnd(sb, "mc", sensorVo.getMac() == null ? "" : sensorVo.getMac(), iotContext);
            genKeyValuePairWithAnd(sb, "pid", sensorVo.getPanId() == null ? "" : sensorVo.getPanId(), iotContext);
            genKeyValuePairWithAnd(sb, "pm", sensorVo.getParentMac() == null ? "" : sensorVo.getParentMac(), iotContext);
            genKeyValuePairWithAnd(sb, "rm", sensorVo.getRouterMac() == null ? "" : sensorVo.getRouterMac(), iotContext);
            genKeyValuePairWithAnd(sb, "tp", sensorVo.getType() == null ? "" : sensorVo.getType(), iotContext);
            genKeyValuePairWithAnd(sb, "uid", sensorVo.getUid() == null ? "" : sensorVo.getUid(), iotContext);
            genKeyValuePairWithAnd(sb, "x", sensorVo.getxMag() == null ? "" : sensorVo.getxMag(), iotContext);
            genKeyValuePairWithAnd(sb, "y", sensorVo.getyMag() == null ? "" : sensorVo.getyMag(), iotContext);
            genKeyValuePairWithAnd(sb, "z", sensorVo.getzMag() == null ? "" : sensorVo.getzMag(), iotContext);

        }

        genKeyValuePair(sb, "cnt", getCnt(), iotContext);

        return sb.toString();

    }

    public List<SensorVo> getSsr() {
        return ssr;
    }

    public void setSsr(List<SensorVo> ssr) {
        this.ssr = ssr;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
