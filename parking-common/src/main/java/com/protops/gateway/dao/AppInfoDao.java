package com.protops.gateway.dao;

import com.protops.gateway.domain.AppInfo;
import com.protops.gateway.util.HibernateBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhouzhihao on 2015/4/1.
 */
@Repository
public class AppInfoDao extends HibernateBaseDao<AppInfo, Integer> {

    /**
     * 创建location时使用公开接口
     * @return
     */
    public List<AppInfo> getBaseList(){
        String sql = " SELECT app_id AS appId,create_time AS createTime,id,description,notice_url AS noticeUrl,path,app_secret AS appSecret FROM tbl_app_info WHERE rec_st =1 AND description != '' ";
        return findBySql(sql,AppInfo.class);
    }

    public AppInfo check(String appId,String secret){
        String hql = " FROM AppInfo WHERE appId = ? AND appSecret = ? ";
        return findUnique(hql,appId,secret);
    }

    public AppInfo findById(Integer id){
        String hql = " FROM AppInfo WHERE id = ? ";
        return findUnique(hql,id);
    }

    public List<AppInfo> refresh() {
        String hql = "from AppInfo where recSt=1";
        return find(hql);

    }

    public void updateAppInfo(String appId, String appSecret, String appKey) {
        String hql = "update AppInfo set appSecret=?, appKey=? where appId=?";
        update(hql, appSecret, appKey, appId);
    }

    public AppInfo getByAppId(String appId) {
        String hql = "from AppInfo where appId = ?";
        return findUnique(hql, appId);
    }

}
