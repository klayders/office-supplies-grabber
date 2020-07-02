package com.office.grabber.backend.utils;

import com.office.grabber.backend.model.Product;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class XlsxUtils {

  private static final List<String> DATA_PRODUCTS_XLSX = List
      .of(
          "Id товара",
          "Наименование",
          "Код",
          "Штрихкод",
          "Цена карандаша",
          "Цена конкурентов",
          "Завышена цена у карандаша",
          "Завышенная цена у конкурентом"
      );


  public static void fillTicketsDataXLSX(XSSFSheet sheet, List<Product> seasonTicketReservationList) {
    fillTicketsFirstDefaultRows(sheet);
    fillTicketsReservationData(sheet, seasonTicketReservationList);
  }

  private static void fillTicketsFirstDefaultRows(XSSFSheet sheet) {
    var firstRow = sheet.createRow(1);
    var columnNumber = new AtomicInteger(0);
    DATA_PRODUCTS_XLSX.forEach(tableName -> {
      var cell = firstRow.createCell(columnNumber.incrementAndGet());
      cell.setCellValue(tableName);
    });
  }

  private static void fillTicketsReservationData(XSSFSheet sheet, List<Product> seasonTicketReservationList) {
    var rowNumber = new AtomicInteger(1);
    seasonTicketReservationList.forEach(seasonTicketReservation -> {
      var row = sheet.createRow(rowNumber.incrementAndGet());

      var cell = row.createCell(1);
      cell.setCellValue(seasonTicketReservation.getId());

      cell = row.createCell(2);
      cell.setCellValue(seasonTicketReservation.getName());

      cell = row.createCell(3);
      cell.setCellValue(seasonTicketReservation.getCode());

      cell = row.createCell(4);
      cell.setCellValue(seasonTicketReservation.getBarcode());

      cell = row.createCell(5);
      cell.setCellValue(seasonTicketReservation.getCurrentPrice());

      cell = row.createCell(6);
      cell.setCellValue(seasonTicketReservation.getConcurrentPrice());

      cell = row.createCell(7);
      cell.setCellValue(seasonTicketReservation.isCurrentInflatedPrice());

      cell = row.createCell(8);
      cell.setCellValue(seasonTicketReservation.isConcurrentInflatedPrice());
    });
  }


}
