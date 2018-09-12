/**
 * 版权所有(C)，中国银联股份有限公司，2002-2014，所有权利保留。
 * 
 * 项目名：	web-service
 * 文件名：	IPUtils.java
 * 模块说明：	
 * 修改历史：
 * 2014-8-20 - linhui - 创建。
 */
package com.protops.gateway.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Pattern;


/**
 * @author linhui
 *
 */
public final class IPUtils {
    protected static final Logger log = LoggerFactory.getLogger(IPUtils.class);
    
    public static final String IP_REGEX = "(((\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]))";
    private static final Pattern ip_pattern = Pattern.compile(IP_REGEX);
    
    public static final String LOCAL_IP = getHostAddress();
    
    public static final String LOCAL_IP_TRIMMED = getTrimmedHostAddress();

    public static String getHostAddress() {
        final Collection<String> ips = getAllAddress();
        log.debug("get ips{} from NetworkInterface", ips);

        if (ips.size() == 1) {
            final String ip = ips.toArray(new String[ips.size()])[0];
            return ip;
        }
        
        return ips.isEmpty() ? "127.0.0.1" : new ArrayList<String>(ips).toString();
    }
    
    
    public static String getTrimmedHostAddress() {
        final Collection<String> ips = getAllAddress();
        log.debug("get ips{} from NetworkInterface", ips);

        if (ips.size() == 1) {
            final String ip = ips.toArray(new String[ips.size()])[0];
            return trimIpAddress(ip);
        }
        
        if(ips.isEmpty()) {
            return "127.0.0.1";
        } else {
            List<String> ipList = new ArrayList<String>();
            for(String ip : ips) {
                ipList.add(trimIpAddress(ip));
            }
            return ipList.toString();
        }
    }
    
    private static String trimIpAddress(String ipAddress) {
        if(StringUtils.isBlank(ipAddress)) {
            return "";
        }
        return ipAddress.replaceFirst("^\\d{1,3}\\.\\d{1,3}\\.", "");
    }
    
    /**
     * 从网卡信息中获取IPV4格式的ip地址
     *
     * @return
     */
    private static Collection<String> getAllAddress() {
        final Set<String> ips = new HashSet<String>();
        try {
            final InetAddress localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                ips.add(localAddress.getHostAddress());
            }
        } catch (UnknownHostException e) {
            //ignore
        }

        final Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            log.error("cannot get network interfaces", e);
            return ips;
        }
        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();
            final Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                final InetAddress inetAddress = inetAddresses.nextElement();
                if (isValidAddress(inetAddress)) {
                    ips.add(inetAddress.getHostAddress());
                }
            }
        }
        return ips;
    }
    
    private static boolean isValidAddress(final InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        final String name = address.getHostAddress();
        return name != null
                && !"0.0.0.0".equals(name)
                && !"127.0.0.1".equals(name)
                && ip_pattern.matcher(name).matches();
    }
}
