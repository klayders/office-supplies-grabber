package com.office.grabber.backend.task;

import com.office.grabber.backend.repository.SiteConfigRepository;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class GrabberTask {

  private final SiteConfigRepository siteConfigRepository;
  private final GrabberService grabberService;


  @Scheduled(cron = "0 0 */3 * * *")
  public void grab() {
    final var polunin = siteConfigRepository.findByProvider("polunin");
    final var concurrent = siteConfigRepository.findByProvider("concurrent");

    log.info("grab: start");

    grabberService.startingGrabbing(polunin, concurrent);

    log.info("grab: done");
  }


}
