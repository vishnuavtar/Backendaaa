package com.example.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UtilDownload {

	private static String HEADER[] = { "ProductId", "productName", "productPrice" };

	public static String SHEET_NAME = "downloadProduct";

	public static ByteArrayInputStream dataToExcel(List<Product> productList) throws IOException {

		Workbook workbook = new XSSFWorkbook();

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try {
			Sheet sheet = workbook.createSheet(SHEET_NAME);

			Row row = sheet.createRow(0);

			for (int i = 0; i < HEADER.length; i++) {

				Cell cell = row.createCell(i);
				cell.setCellValue(HEADER[i]);
			}

			int rowIndex = 1;
			for (Product p : productList) {

				Row row1 = sheet.createRow(rowIndex);
				rowIndex++;

				row1.createCell(0).setCellValue(p.getPid());
				row1.createCell(1).setCellValue(p.getProductName());
				row1.createCell(2).setCellValue(p.getProductPrice());
			}

			workbook.write(byteArrayOutputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			workbook.close();
			byteArrayOutputStream.close();
		}
		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

	}

}
