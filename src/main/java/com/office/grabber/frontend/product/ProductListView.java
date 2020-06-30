package com.office.grabber.frontend.product;

import com.office.grabber.backend.model.Product;
import com.office.grabber.backend.repository.ProductRepository;
import com.office.grabber.frontend.configuration.AbstractEditEntityForm;
import com.office.grabber.frontend.configuration.EntityAnnotationParser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import org.springframework.data.domain.PageRequest;


@Route("products")
@PageTitle("Каталог продуктов")
public class ProductListView extends VerticalLayout {

  private final ApplicationContext applicationContext;
  private final AbstractEditEntityForm<Product> entityForm;
  private final ProductRepository repository;
  private final EntityAnnotationParser entityAnnotationParser;

  private final Grid<Product> entityGrid = new Grid<>(Product.class);

  private final IntegerField countRowsPerPage = new IntegerField("Число записей на странице", 10, event -> updateList());
  private final IntegerField pageRows = new IntegerField("Текущая страница", 0, event -> updateList());
  private final IntegerField totalPage = new IntegerField("Всего страниц");

  private final Checkbox pencilExpensivePrice = new Checkbox("Дорогие товары карандаша", event -> updateList());
  private final Checkbox kancmirExpensivePrice = new Checkbox("Дорогие товары канцмир", event -> updateList());


  public ProductListView(ApplicationContext applicationContext,
                         ProductRepository repository,
                         EntityAnnotationParser entityAnnotationParser) {
    this.applicationContext = applicationContext;
    this.repository = repository;
    this.entityAnnotationParser = entityAnnotationParser;

    this.entityForm = new AbstractEditEntityForm<>(
        this.applicationContext,
        Product.class,
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
    pageRows.setMaxWidth("150px");
    pageRows.setClearButtonVisible(true);
    pageRows.setValueChangeMode(ValueChangeMode.LAZY);

    totalPage.setMaxWidth("150px");
    totalPage.setReadOnly(true);

    if (countRowsPerPage.getValue() == null || countRowsPerPage.getValue() <= 0) {
      countRowsPerPage.setValue(50);
    }
    countRowsPerPage.setMaxWidth("150px");
    countRowsPerPage.setClearButtonVisible(true);
    countRowsPerPage.setValueChangeMode(ValueChangeMode.LAZY);

    var createEntityButton = new Button("Создать", click -> createEntity());

    var toolBar = new HorizontalLayout(
        pageRows,
        countRowsPerPage,
        totalPage,
        pencilExpensivePrice,
        kancmirExpensivePrice,
        createEntityButton
    );

    toolBar.setClassName("toolbar");

    return toolBar;

  }

  private void configureGrid() {
    entityGrid.addClassName("main-grid");
    entityGrid.setSizeFull();

       var gridFields = Stream.of(Product.class.getDeclaredFields())
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
          "admin." + Product.class.getSimpleName().toLowerCase() + "." + fieldName,
          null,
          fieldName,
          Locale.getDefault()
      );
      column.setHeader(i18nFieldName);
    });

    entityGrid.asSingleSelect().addValueChangeListener(event -> entityForm.editEntity(event.getValue()));
  }

  private void updateList() {
    var all = repository.findAllByCurrentInflatedPriceAndAndConcurrentInflatedPrice(
        PageRequest.of(pageRows.getValue(), countRowsPerPage.getValue()),
        pencilExpensivePrice.getValue(),
        kancmirExpensivePrice.getValue()
    );

    totalPage.setValue(all.getTotalPages());

    entityGrid.setItems(all.getContent());
  }

  private void createEntity() {
    entityGrid.asSingleSelect().clear();
    Product entity = null;
    try {
      entity = Product.class.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    entityForm.editEntity(entity);
  }
}
