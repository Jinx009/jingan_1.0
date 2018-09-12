package com.protops.gateway.service;

import com.protops.gateway.dao.AppInfoDao;
import com.protops.gateway.domain.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhouzhihao on 2015/4/1.
 */
@Service
@Transactional
public class AppInfoService {

    @Autowired
    AppInfoDao appInfoDao;

    /**
     * 创建Location时使用选择appinfo
     * @return
     */
    public List<AppInfo> getBaseList(){
        return appInfoDao.getBaseList();
    }

    /**
     * 新建一个appinfo
     * @param appInfo
     */
    public void save(AppInfo appInfo){
        appInfoDao.save(appInfo);
    }

    public AppInfo check(String appId,String secret){
        return appInfoDao.check(appId,secret);
    }

    /**
     * 通过id查找
     * @param id
     * @return
     */
    public AppInfo findById(Integer id){
        return appInfoDao.findById(id);
    }

    public List<AppInfo> refresh() {
        return appInfoDao.refresh();
    }

    public void refresh(String appId, String appSecret, String appKey){
        appInfoDao.updateAppInfo(appId, appSecret, appKey);
    }

    public AppInfo getByAppId(String appId) {
        return appInfoDao.getByAppId(appId);
    }

}