package com.office.grabber.backend.model;

import com.office.grabber.frontend.configuration.annotation.ColumnDisplayGrid;
import com.office.grabber.frontend.configuration.annotation.ColumnEditForm;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteConfig {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ColumnEditForm(displayOrder = 0)
  @ColumnDisplayGrid(displayOrder = 0)
  private long id;

  @ColumnEditForm(displayOrder = 1)
  @ColumnDisplayGrid(displayOrder = 1)
  private String domain;


  @ColumnEditForm(displayOrder = 2)
  @ColumnDisplayGrid(displayOrder = 2)
  private String api;


  @ColumnEditForm(displayOrder = 3)
  @ColumnDisplayGrid(displayOrder = 3)
  private String accessToken;

  @ColumnEditForm(displayOrder = 4)
  @ColumnDisplayGrid(displayOrder = 4)
  private String shopId;


  @ColumnEditForm(displayOrder = 5)
  @ColumnDisplayGrid(displayOrder = 5)
  private String provider;

}
