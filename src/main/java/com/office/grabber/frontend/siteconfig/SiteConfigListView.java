package com.office.grabber.frontend.siteconfig;

import com.office.grabber.backend.model.SiteConfig;
import com.office.grabber.backend.repository.SiteConfigRepository;
import com.office.grabber.frontend.configuration.AbstractEditEntityForm;
import com.office.grabber.frontend.configuration.EntityAnnotationParser;
import com.office.grabber.frontend.main.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.stream.Stream;
import org.springframework.context.ApplicationContext;


@Route(value = "site-config", layout = MainLayout.class)
@PageTitle("Католог продуктов")
public class SiteConfigListView extends VerticalLayout {

  private final ApplicationContext applicationContext;
  private final AbstractEditEntityForm<SiteConfig> entityForm;
  private final SiteConfigRepository repository;
  private final Grid<SiteConfig> entityGrid;
  private final EntityAnnotationParser entityAnnotationParser;
  private final IntegerField versionFilter = new IntegerField("Фильтр по версии", null, event -> updateList());
  private final IntegerField countSelectedRows = new IntegerField("Число записей на странице:", 50, event -> updateList());


  public SiteConfigListView(ApplicationContext applicationContext,
                            SiteConfigRepository repository,
                            EntityAnnotationParser entityAnnotationParser) {
    this.applicationContext = applicationContext;
    this.repository = repository;
    this.entityAnnotationParser = entityAnnotationParser;
    this.entityGrid = new Grid<>(SiteConfig.class);

    this.entityForm = new AbstractEditEntityForm<SiteConfig>(
        this.applicationContext,
        SiteConfig.class,
        this.repository,
        this.entityGrid,
        entityAnnotationParser
    ) {};

    //    entityForm = new TestForm(imageService.findAll(), applicationContext);

    var content = new Div(entityGrid, entityForm);
    content.addClassName("content");
    content.setSizeFull();

    addClassName("list-view");
    setSizeFull();

    configureGrid();
    getToolbar();

    add(getToolbar(), content);

    updateList();

    entityForm.closeEditor();
  }


  private HorizontalLayout getToolbar() {
    versionFilter.setMaxWidth("150px");
    versionFilter.setClearButtonVisible(true);
    versionFilter.setValueChangeMode(ValueChangeMode.LAZY);

    if (countSelectedRows.getValue() == null || countSelectedRows.getValue() <= 0) {
      countSelectedRows.setValue(50);
    }
    countSelectedRows.setMaxWidth("150px");
    countSelectedRows.setClearButtonVisible(true);
    countSelectedRows.setValueChangeMode(ValueChangeMode.LAZY);

    var createEntityButton = new Button("Создать", click -> createEntity());

    var toolBar = new HorizontalLayout(versionFilter, countSelectedRows, createEntityButton);
    toolBar.setClassName("toolbar");

    return toolBar;

  }

  private void configureGrid() {
    entityGrid.addClassName("main-grid");
    entityGrid.setSizeFull();
    //      grid.setColumns("type", "shortDescription", "row", "position", "description", "fullDescription", "smallPicId");

    var gridFields = Stream.of(SiteConfig.class.getDeclaredFields())
        .filter(field -> field.getAnnotation(entityAnnotationParser.annotationGridColumn()) != null)
        .sorted((field1, field2) -> {
          var annotation1 = field1.getAnnotation(entityAnnotationParser.annotationGridColumn());
          var order1 = (Integer) entityAnnotationParser.getGridOrderByAnnotation(annotation1);

          var annotation2 = field2.getAnnotation(entityAnnotationParser.annotationGridColumn());
          var order2 = (Integer) entityAnnotationParser.getGridOrderByAnnotation(annotation2);

          return order1.compareTo(order2);
        })
        .map(Field::getName)
        .toArray(String[]::new);

    entityGrid.setColumns(gridFields);

    entityGrid.getColumns().forEach(column -> {
      column.setAutoWidth(true);
      var fieldName = column.getKey();
      final var i18nFieldName = applicationContext.getMessage(
          "admin." + SiteConfig.class.getSimpleName().toLowerCase() + "." + fieldName,
          null,
          fieldName,
          Locale.getDefault()
      );
      column.setHeader(i18nFieldName);
    });

    entityGrid.asSingleSelect().addValueChangeListener(event -> entityForm.editEntity(event.getValue()));
  }

  private void updateList() {
    var all = repository.findAll();
    entityGrid.setItems(all);
  }

  private void createEntity() {
    entityGrid.asSingleSelect().clear();
    SiteConfig entity = null;
    try {
      entity = SiteConfig.class.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    entityForm.editEntity(entity);
  }
}
