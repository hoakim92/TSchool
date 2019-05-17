package com.tsystems.lims.dao.interfaces;

import java.util.List;
import java.util.Map;

public interface IStatisticDao {
    public List<Map.Entry> execDoctorsQuery();
    public List<Map.Entry> execEventsQuery();
}
