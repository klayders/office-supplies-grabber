package com.office.grabber.backend.task;


import static java.util.stream.Collectors.toMap;

import com.office.grabber.backend.model.Product;
import com.office.grabber.backend.model.SiteConfig;
import com.office.grabber.backend.pojo.MainResponse;
import com.office.grabber.backend.pojo.ProductPojo;
import com.office.grabber.backend.repository.ProductRepository;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class GrabberService {

  private final RestTemplate restTemplate;
  private final ProductRepository productRepository;

  private static int section = 600;
  private static int offset = 0;
  private static int limit = 1000;

  public void startingGrabbing(SiteConfig polunin, SiteConfig concurrent) {
    while (section <= 100_000) {
      try {

        var poluninUrl = getGrabUrl(
            polunin.getDomain(),
            polunin.getApi(),
            polunin.getAccessToken(),
            polunin.getShopId()
        );
        var concurrentUrl = getGrabUrl(
            concurrent.getDomain(),
            concurrent.getApi(),
            concurrent.getAccessToken(),
            concurrent.getShopId()
        );

        final var poluninBody = restTemplate.getForEntity(poluninUrl, MainResponse.class).getBody();
        final var concurrentBody = restTemplate.getForEntity(concurrentUrl, MainResponse.class).getBody();

        var poluninMap = covertToMap(poluninBody, false);
        var concurrentMap = covertToMap(concurrentBody, true);

        if (!CollectionUtils.isEmpty(poluninMap) || !CollectionUtils.isEmpty(concurrentMap)) {
          offset += limit;

          final var listProducts = Stream.of(poluninMap, concurrentMap)
              .flatMap(map -> map.entrySet().stream())
              .collect(
                  toMap(
                      Entry::getKey,
                      Entry::getValue,
                      Product::fromTwoProduct
                  )
              )
              .values();

          productRepository.saveAll(listProducts);

        } else {
          increment();
          log.info("startingGrabbing: increment section={}", section);
        }
      } catch (Exception e) {
        increment();
        if (e instanceof BadRequest) {
          log.warn("startingGrabbing: increment section=" + section);
        }else {
          log.error("startingGrabbing: increment section=" + section, e);
        }
      }
    }
    section = 0;
  }


  private Map<String, Product> covertToMap(MainResponse mainResponse, boolean concurrent) {
    if (mainResponse != null &&
        mainResponse.getResponse() != null &&
        !CollectionUtils.isEmpty(mainResponse.getResponse().getProducts())) {

      return mainResponse.getResponse().getProducts()
          .stream()
          .collect(
              toMap(
                  ProductPojo::getId,
                  productPojo -> Product.fromPojo(productPojo, concurrent)
              )
          );

    } else {
      return Map.of();
    }
  }

  private String getGrabUrl(String domain, String api, String accessToken, String shopId) {
    return domain + api + section +
        "/?offset=" + offset +
        "&limit=" + limit +
        "&access_token=" + accessToken +
        "&shop_id=" + shopId;
  }

  private void increment() {
    section++;
    offset = 0;
    limit = 1000;
  }


}
