package com.techstore.techstore_backend.modules.campaign.repository;

import com.techstore.techstore_backend.modules.campaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign,Integer> {
}
