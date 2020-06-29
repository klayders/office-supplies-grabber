package com.office.grabber.frontend.configuration;

import com.office.grabber.frontend.product.ProductListView;
import com.office.grabber.frontend.siteconfig.SiteConfigListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;


//помоему эта страница использует дефолтный роутинг - localhost:8080/
@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {


  public MainLayout() {
    createHeader();
    createDrawer();
  }

  private void createHeader() {
    var logo = new H1("Stoloto-Admin");
    logo.addClassName("logo");

    var header = new HorizontalLayout(new DrawerToggle(), logo);
    header.addClassName("header");
    header.setWidth("100%");
    header.setDefaultVerticalComponentAlignment(Alignment.CENTER);

    addToNavbar(header);
  }

  private void createDrawer() {

    addToDrawer(
        new VerticalLayout(
            new RouterLink("products", ProductListView.class),
            new RouterLink("site-config", SiteConfigListView.class)
        )
    );
  }
}
