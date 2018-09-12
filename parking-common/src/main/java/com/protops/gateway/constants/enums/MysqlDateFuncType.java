package com.protops.gateway.constants.enums;

import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

/**
 * Created by damen on 2014/11/29.
 */
public enum MysqlDateFuncType {

    TYPE_DAY(Projections.sqlGroupProjection("count(*) as pv, DATE_FORMAT(create_time, '%Y-%m-%d') as day", "day", new String[]{"pv", "day"}, new Type[]{LongType.INSTANCE, StringType.INSTANCE})),
    TYPE_WEEK(Projections.sqlGroupProjection("count(*) as pv, DATE_FORMAT(create_time, '%Y%u') as week", "week", new String[]{"pv", "week"}, new Type[]{LongType.INSTANCE, StringType.INSTANCE})),
    TYPE_MONTH(Projections.sqlGroupProjection("count(*) as pv, DATE_FORMAT(create_time, '%Y%c') as month", "month", new String[]{"pv", "month"}, new Type[]{LongType.INSTANCE, StringType.INSTANCE}));

    private Projection projection;

    MysqlDateFuncType(Projection projection){
        this.projection = projection;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }
}
