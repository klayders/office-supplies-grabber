package com.office.grabber.backend.repository;

import com.office.grabber.backend.model.SiteConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteConfigRepository extends JpaRepository<SiteConfig, Long> {
  SiteConfig findByProvider(String provider);
}
