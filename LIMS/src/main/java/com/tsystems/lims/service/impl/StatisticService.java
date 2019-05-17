package com.tsystems.lims.service.impl;

import com.tsystems.lims.dao.interfaces.IStatisticDao;
import com.tsystems.lims.service.interfaces.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;


@Service
public class StatisticService implements IStatisticService {
    @Autowired
    IStatisticDao statisticDao;

    @Override
    public List<Map.Entry> getDoctorsStatistic() {
        List<Map.Entry> result = statisticDao.execDoctorsQuery();
        if (result.size() == 0)
            result.add(new AbstractMap.SimpleEntry<>("PATIENTS",0));
        return result;

    }

    @Override
    public List<Map.Entry> getEventsStatistic() {
        List<Map.Entry> result = statisticDao.execEventsQuery();
        if (result.size() == 0)
            result.add(new AbstractMap.SimpleEntry<>("EVENTS",0));
        return result;
    }
}
