package com.tsystems.lims.service.interfaces;

import java.util.List;
import java.util.Map;

public interface IStatisticService {
    public List<Map.Entry> getDoctorsStatistic();
    public List<Map.Entry> getEventsStatistic();
}
