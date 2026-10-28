package com.techstore.techstore_backend.modules.rule.repository;

import com.techstore.techstore_backend.modules.rule.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule,Integer> {
}
