package org.techforumist.google.oauth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.techforumist.google.oauth.model.TherapyEvent;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<TherapyEvent, Integer> {
    @Query("select e from TherapyEvent e where e.patient.email = :email")
    List<TherapyEvent> findByPatientEmail(@Param("email") String email);
}
