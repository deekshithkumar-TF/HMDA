package com.HMDA.helper;

import com.HMDA.constants.CPSSDBConstants;
import com.HMDA.constants.ConfigConstants;
import com.HMDA.reports.Logs;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtils {

    static Workbook wb;
    // An output stream accepts output bytes and sends them to sink.
    static OutputStream fileOut;

    public static Map<String, String> order = new LinkedHashMap<>();
    public static Map<String, String> subProp = new LinkedHashMap<>();
    public static Map<String, String> borrower = new LinkedHashMap<>();

    static {
        try {
            fileOut = new FileOutputStream( ConfigConstants.outputDir + "database_records.xlsx" );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ExcelUtils() {
    }

    private static void createExcelDynamically() {

        // Creating Workbook instances
        wb = new XSSFWorkbook();

        // Creating Sheets using sheet object
        wb.createSheet( CPSSDBConstants.orderTable);
        wb.createSheet( CPSSDBConstants.orderSubPropTable );
        wb.createSheet( CPSSDBConstants.orderBorrowerTable );
        wb.createSheet( CPSSDBConstants.headerTable );
        wb.createSheet( CPSSDBConstants.borrowerTable );
        wb.createSheet( CPSSDBConstants.borrowerAddressTable );
        wb.createSheet( CPSSDBConstants.inquiryTable );
        wb.createSheet( CPSSDBConstants.empTable );
        wb.createSheet( CPSSDBConstants.bankruptcyTable );
        wb.createSheet( CPSSDBConstants.tradeLineTable );
        wb.createSheet( CPSSDBConstants.akaTable );
        wb.createSheet( CPSSDBConstants.alertsTable );
        wb.createSheet( CPSSDBConstants.ficoScoreTable );
        wb.createSheet( CPSSDBConstants.tradeLineDimTable );

        Logs.info( "Sheets Has been Created successfully" );

    }

    public static void storeDBRecordsToExcel(String orderId) throws IOException {

        createExcelDynamically();

        Map<String, String> recordsFromDB;

        for (int i = 0; i < wb.getNumberOfSheets() - 1; i++) {

            recordsFromDB = DataBaseUtils.fetchRecord( wb.getSheetName( i ), orderId );

            switch (wb.getSheetName( i )) {

                case CPSSDBConstants.orderTable:
                    order = DataBaseUtils.fetchRecord( wb.getSheetName( i ), orderId );
                case CPSSDBConstants.orderSubPropTable:
                    subProp = DataBaseUtils.fetchRecord( wb.getSheetName( i ), orderId );
                case CPSSDBConstants.orderBorrowerTable:
                    borrower = DataBaseUtils.fetchRecord( wb.getSheetName( i ), orderId );

            }

            int rowNum = 0;

            for (String key : recordsFromDB.keySet()) {

                Row row = wb.getSheetAt( i ).createRow( rowNum++ );
                String value = recordsFromDB.get( key );

                int cellNum = 0;
                Cell cell = row.createCell( cellNum++ );
                cell.setCellValue( key );

                cell = row.createCell( cellNum );
                cell.setCellValue( value );
            }
            Logs.info( "Data from DB written to excel sheet: " + wb.getSheetName( i ) );
        }

        Sheet sheet = wb.getSheet( CPSSDBConstants.tradeLineDimTable );
        recordsFromDB = DataBaseUtils.fetchTopRecordBasedOnJoin(
                CPSSDBConstants.tradeLineTable, CPSSDBConstants.tradeLineDimTable);

        int rowNum = 0;

        for (String key : recordsFromDB.keySet()) {

            Row row = sheet.createRow( rowNum++ );
            String value = recordsFromDB.get( key );

            int cellNum = 0;
            Cell cell = row.createCell( cellNum++ );
            cell.setCellValue( key );

            cell = row.createCell( cellNum );
            cell.setCellValue( value );
        }
        wb.write( fileOut );
    }

    private static int getRowCount(XSSFSheet sheet) {
        return sheet.getPhysicalNumberOfRows();
    }

    private static List<String> readRowData(XSSFSheet sheet, int row) {
        Row row1 = sheet.getRow(row);
        int colCount = row1.getPhysicalNumberOfCells();
        List<String> rowValues = new ArrayList<>();
        for (int i = 0; i < colCount; i++) {
            Cell cell = row1.getCell(i);

            DataFormatter df = new DataFormatter();
            String data = df.formatCellValue(cell);

            if (!"".equals(data) && null != data) {
                rowValues.add( data );
            } else {
                break;
            }
        }
        return rowValues;
    }

    public static List<Map<String, String>> fetchData(String dataPath, String sheetName) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(dataPath)));
        XSSFSheet sheet = workbook.getSheet( sheetName );

        List<String> header = readRowData(sheet, 0);
        List<Map<String, String> > excelData = new ArrayList<>();
        List<String> currentRow;
        for (int i = 1; i < getRowCount( sheet ); i++) {

            Map<String, String> data = new HashMap<>();
            currentRow = readRowData(sheet, i);
            for (int j = 0; j < header.size(); j++) {
                data.put( header.get( j ), currentRow.get( j ) );
            }
            if (data.get( "Flag" ).equals( "Y" ))
                excelData.add( data );
        }
        return excelData;
    }
}
