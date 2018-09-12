package com.protops.gateway.service;

import com.protops.gateway.dao.ErrorFlowDao;
import com.protops.gateway.domain.iot.ErrorFlow;
import com.protops.gateway.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhouzhihao on 2015/4/1.
 */
@Service
@Transactional
public class ErrorFlowService {

    @Autowired
    ErrorFlowDao errorFlowDao;

    public ErrorFlow isLost(String mac){
        return errorFlowDao.isLost(mac);
    }

    public List<ErrorFlow> findNoArea(){
        return errorFlowDao.findNoArea();
    }

    public void update(ErrorFlow errorFlow){
        errorFlowDao.update(errorFlow);
    }

    public Page<ErrorFlow> pagedList(Page<ErrorFlow> page) {

        page.setResult(errorFlowDao.pagedList(page).getResult());
        page.setTotalCount(errorFlowDao.getTotalCount());

        return page;
    }


    public Page<ErrorFlow> pagedMofangList(Page<ErrorFlow> page,Integer areaId) {
        page.setResult(errorFlowDao.pagedListWithAreaId(page,areaId).getResult());
        page.setTotalCount(errorFlowDao.pagedListWithAreaIdTotalCount(areaId));
        return page;
    }

    public Page<ErrorFlow> pagedList(Page<ErrorFlow> page,Integer areaId) {
        if(areaId!=0){
            page.setResult(errorFlowDao.pagedList(page,areaId).getResult());
            page.setTotalCount(errorFlowDao.getTotalCount(areaId));
        }else{
            page.setResult(errorFlowDao.pagedList(page).getResult());
            page.setTotalCount(errorFlowDao.getTotalCount());
        }

        return page;
    }


    public void save(ErrorFlow errorFlow) {
        errorFlowDao.save(errorFlow);
    }

    public ErrorFlow getBySerialNo(String serialNo) {
        return errorFlowDao.getBySerialNo(serialNo);
    }

    public ErrorFlow get(Integer id) {
        return errorFlowDao.get(id);
    }

    public Page<ErrorFlow> pagedListWithError(Page<ErrorFlow> page) {

        page.setResult(errorFlowDao.pagedListWithError(page).getResult());
        page.setTotalCount(errorFlowDao.getTotalCountWithError());

        return page;
    }

}