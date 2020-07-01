package com.office.grabber.frontend.main;

import com.office.grabber.frontend.about.AboutView;
import com.office.grabber.frontend.product.ProductListView;
import com.office.grabber.frontend.siteconfig.SiteConfigListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;


//помоему эта страница использует дефолтный роутинг - localhost:8080/
@CssImport(value = "frontend://styles/shared-styles.css")
public class MainLayout extends AppLayout {


  public MainLayout() {
    createHeader();
    createDrawer();
  }

  private void createHeader() {
    var logo = new H1("Feo office grabber");
    logo.addClassName("logo");

    var header = new HorizontalLayout(new DrawerToggle(), logo);
    header.addClassName("header");
    header.setWidth("100%");
    header.setDefaultVerticalComponentAlignment(Alignment.CENTER);

    addToNavbar(header);
  }

  private void createDrawer() {

    final RouterLink products = new RouterLink("products", ProductListView.class);
    products.setHighlightCondition(HighlightConditions.sameLocation());
    final RouterLink routerLink = new RouterLink("site-config", SiteConfigListView.class);
    routerLink.setHighlightCondition(HighlightConditions.sameLocation());

    final RouterLink about = new RouterLink("about", AboutView.class);
    about.setHighlightCondition(HighlightConditions.sameLocation());

    addToDrawer(
        new VerticalLayout(
            products,
            routerLink,
            about
        )
    );
  }

}
