package com.jaramgroupware.mms.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {
    Optional<Member> findMemberById(String id);
    Optional<List<Member>> findAllBy();
}
