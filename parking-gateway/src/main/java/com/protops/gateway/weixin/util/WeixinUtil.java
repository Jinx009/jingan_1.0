package com.protops.gateway.weixin.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.protops.gateway.weixin.vo.Signature;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by damen on 2014/10/26.
 */
public class WeixinUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

    public static boolean checkSignature(Signature signature) throws IllegalAccessException, InvocationTargetException {
        String sign = Hashing.sha1().hashString(buildSignatureText(signature), Charsets.UTF_8).toString();

        logger.warn("computed signature : {}", sign);

        return sign.equalsIgnoreCase(signature.getSignature());
    }

    public static String buildSignatureText(Signature signature) {
        List<String> list = new ArrayList<String>();
        list.add(Validate.notNull(signature.getToken(), "missing-token"));
        list.add(Validate.notNull(signature.getTimestamp(), "missing-timestamp"));
        list.add(Validate.notNull(signature.getNonce(), "missing-nonce"));
        Collections.sort(list);

        return StringUtils.join(list, "");
    }

    public static String getMsgType(String message) {
        return StringUtils.substringBetween(message, "<MsgType><![CDATA[", "]]></MsgType>");
    }

}
