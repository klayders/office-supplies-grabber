package com.office.grabber.backend.file;

import static com.office.grabber.backend.utils.XlsxUtils.fillTicketsDataXLSX;

import com.office.grabber.backend.model.Product;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileLoaderXlsx {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-hh");


  public File generatedProductFile(List<Product> products){
    var fileName = "product_data_" + DATE_FORMAT.format(new Date()) + ".xlsx";
    var file = new File(fileName);

    InputStreamResource resource = null;
    if (file.exists()) {
      try {
        resource = new InputStreamResource(new FileInputStream(file));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      var xssfWorkbook = new XSSFWorkbook();

      var xssfWorkbookSheet = xssfWorkbook.createSheet("product");

      fillTicketsDataXLSX(xssfWorkbookSheet, products);

      try (FileOutputStream out = new FileOutputStream(file)) {
        xssfWorkbook.write(out);
        out.close();
        resource = new InputStreamResource(new FileInputStream(file));
        log.info("generatedProductFile: successfully on disk, fileName={}, filePath={}", file.getName(), file.getAbsolutePath());
      } catch (Exception e) {
        log.error("generatedProductFile: exception={}", e.getMessage());
      }
    }
    return file;
  }

}
