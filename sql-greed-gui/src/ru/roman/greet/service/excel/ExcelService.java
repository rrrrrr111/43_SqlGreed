/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roman.greet.service.excel;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.Number;
import ru.roman.greet.service.excel.dto.CreateReportRequest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Roman
 */
public class ExcelService {

    private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-dd-MM");

    public ExcelService() {
    }

    public void write(String inputFile) throws IOException, WriteException {
        File file = new File(inputFile);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Report", 0);
        WritableSheet excelSheet = workbook.getSheet(0);

        createLabel(excelSheet);
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        WritableCellFormat cellFormat = new WritableCellFormat(times10pt);
        createContent(excelSheet, cellFormat);

        workbook.write();
        workbook.close();
    }

    public void createExcelTable(File someF, CreateReportRequest req) {

        try {
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(someF, wbSettings);
            workbook.createSheet("Report", 0);
            WritableSheet excelSheet = workbook.getSheet(0);

            feelExcelTable(excelSheet, req);

            workbook.write();
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception wile Excel creation", e);
        }

    }

    private void createLabel(WritableSheet sheet) throws WriteException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        WritableCellFormat cellFormat1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        cellFormat1.setWrap(true);

        // Create create a bold font with underlines
        WritableFont font = new WritableFont(
                WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        WritableCellFormat cellFormat2 = new WritableCellFormat(font);
        // Lets automatically wrap the cells
        cellFormat2.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(cellFormat1);
        cv.setFormat(cellFormat2);
        cv.setAutosize(true);

        // Write a few headers
        addCaption(sheet, 0, 0, "Header 1");
        addCaption(sheet, 1, 0, "This is another header");


    }

    private void createContent(WritableSheet sheet, CellFormat cellFormat) throws WriteException {
        // Write a few number
        for (int i = 1; i < 10; i++) {
            // First column
            addNumber(sheet, 0, i, i + 10, cellFormat);
            // Second column
            addNumber(sheet, 1, i, i * i, cellFormat);
        }
        // Lets calculate the sum of it
        StringBuffer buf = new StringBuffer();
        buf.append("SUM(A2:A10)");
        Formula f = new Formula(0, 10, buf.toString());
        sheet.addCell(f);
        buf = new StringBuffer();
        buf.append("SUM(B2:B10)");
        f = new Formula(1, 10, buf.toString());
        sheet.addCell(f);

        // Now a bit of text
        for (int i = 12; i < 20; i++) {
            // First column
            addLabel(sheet, 0, i, "Boring text " + i, cellFormat);
            // Second column
            addLabel(sheet, 1, i, "Another text", cellFormat);
        }
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s) throws WriteException {
        WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        WritableCellFormat cellFormat = new WritableCellFormat(font);
        cellFormat.setWrap(true);
        Label label = new Label(column, row, s, cellFormat);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row, Integer integer, CellFormat cellFormat) throws WriteException {
        Number number;
        number = new Number(column, row, integer, cellFormat);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s, CellFormat cellFormat) throws WriteException {
        sheet.addCell(new Label(column, row, s, cellFormat));
    }

    private void feelExcelTable(WritableSheet someSheet, CreateReportRequest req) throws WriteException, ParseException {

        final List<Object[]> columnInfo = req.getColumnInfo();
        final List<Object[]> data = req.getData();

        WritableFont times10ptB = new WritableFont(WritableFont.TAHOMA);
        times10ptB.setColour(Colour.BLUE2);
        times10ptB.setBoldStyle(WritableFont.BOLD);
        WritableCellFormat formatHeaders = new WritableCellFormat(times10ptB);
        formatHeaders.setWrap(true);
        //CellView cvHeaders = new CellView();
        //cvHeaders.setFormat(formatHeaders);
        //cvHeaders.setAutoSize(true);

        //someSheet.setRowView(1, cvHeaders);


        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        WritableCellFormat formatData = new WritableCellFormat(times10pt);
        formatData.setWrap(true);
        CellView cvData = new CellView();
        cvData.setFormat(formatData);
        cvData.setAutosize(true);

        for (int i = 0; i < columnInfo.size(); i++) {
            someSheet.setColumnView(i, cvData);
            someSheet.addCell(new Label(i, 0, columnInfo.get(i)[0] + "", formatHeaders));
        }

        Date currDate;
        final ParsePosition pos = new ParsePosition(0);

        for (int i = 0; i < data.size(); i++) {
            Object[] dataRow = data.get(i);
            for (int j = 0; j < dataRow.length; j++) {
                String val = (String) dataRow[j];
                if (val != null) {
                    final int someInt = Integer.parseInt(columnInfo.get(j)[1] + "");
                    if (someInt == 91) {
                        pos.setIndex(0);
                        currDate = DATE_PARSER.parse(val, pos);
                        someSheet.addCell(new DateTime(j, i + 1, currDate));
                    }
                    if ((someInt > 1 && someInt < 9) || someInt == -5) {

                        someSheet.addCell(new Number(j, i + 1, Double.parseDouble(val)));
                    } else {
                        someSheet.addCell(new Label(j, i + 1, val));
                    }
                }
            }
        }
    }
}
