package com.tsystems.lims.dao.impl;

import com.tsystems.lims.dao.interfaces.IStatisticDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class StatisticDao implements IStatisticDao {

    @Autowired
    private DataSource dataSource;

    public List<Map.Entry> execDoctorsQuery(){
        List<Map.Entry> result = new ArrayList<>();
        String sql = "select  doctor.name, scoreTable.idsCount\n" +
                "from\n" +
                " (SELECT COUNT(id) as idsCount, doctor_id FROM patient GROUP BY doctor_id) scoreTable\n" +
                " LEFT JOIN\n" +
                "\tdoctor on doctor.id = scoreTable.doctor_id\n" +
                "    order by scoreTable.idsCount DESC\n" +
                "    LIMIT 5";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new AbstractMap.SimpleEntry<>(rs.getString("NAME"), rs.getInt("idsCount")));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        return result;
    }

    public List<Map.Entry> execEventsQuery(){
        List<Map.Entry> result = new ArrayList<>();
        String sql = "SELECT COUNT(id) as idsCount, cause FROM medicalcrm.therapyevent  WHERE therapyevent.status='CANCELED' group by cause\n" +
                "order by idsCount DESC";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new AbstractMap.SimpleEntry<>(rs.getString("cause"), rs.getInt("idsCount")));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        return result;
    }
}
