package com.jaramgroupware.mms.domain.penalty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Penalty,Integer> {
    Penalty findPenaltyById(Long id);
}
