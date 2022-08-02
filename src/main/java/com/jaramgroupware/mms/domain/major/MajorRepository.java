package com.jaramgroupware.mms.domain.major;

import com.jaramgroupware.mms.domain.major.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major,Integer> {
    Major findMajorById (Integer id);
}
