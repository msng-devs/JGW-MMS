package com.jaramgroupware.mms.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Event,Integer> {
    Event findEventById(Long id);
}
