package com.protops.gateway.service;

import com.protops.gateway.dao.RouterDao;
import com.protops.gateway.domain.iot.Location;
import com.protops.gateway.domain.iot.Router;
import com.protops.gateway.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by damen on 2016/3/23.
 */
@Service
@Transactional
public class RouterService {

    @Autowired
    RouterDao routerDao;

    public Router getByMac(String mac) {
        return routerDao.getByMac(mac);
    }

    public List<Router> getNeedNotice(Location location){
        return routerDao.getNeedNotice(location);
    }

    public void save(Router router) {

        routerDao.save(router);

    }

    public Page<Router> pagedList(Page<Router> page) {

        page.setResult(routerDao.pagedList(page).getResult());
        page.setTotalCount(routerDao.getTotalCount());

        return page;
    }

    public List<Router> getByAreaId(Integer id) {
        return null;
    }


    public Router getPreview(Integer id) {

        return routerDao.get(id);
    }

    public Page<Router> pagedList(Page<Router> page, Map<String, String> searchMap) {

        if(!searchMap.isEmpty()) {

            page.setResult(routerDao.pagedList(page, searchMap).getResult());
            page.setTotalCount(routerDao.getTotalCount(searchMap));
        } else {
            page.setResult(routerDao.pagedList(page).getResult());
            page.setTotalCount(routerDao.getTotalCount());
        }
        return page;
    }

    public void updateTime(String mac, Date date) {

        routerDao.updateTime(mac, date);

    }

    public Router get(Integer id) {
        return routerDao.get(id);
    }

    public void updateNote(Integer id, String note) {
        routerDao.updateDesc(id, note);

    }

    public List<Router> getAll() {
        return routerDao.getAll();
    }

    public void pagedIllList(Page<Router> page, Set<Integer> allKeys) {
        List<Integer> list = new ArrayList<Integer>(allKeys);
        page.setResult(routerDao.pagedList(page, list).getResult());
        page.setTotalCount(routerDao.getTotalCount(list));
    }
}
