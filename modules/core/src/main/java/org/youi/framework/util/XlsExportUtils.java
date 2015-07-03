/**
 * 
 */
package org.youi.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.orm.PagerRecords;

/**
 * @author zhyi_12
 * 
 */
public class XlsExportUtils {
	/**
	 * xls导出
	 * 
	 * @param out
	 */
	public static void export(OutputStream out, PagerRecords pagerRecords) {

		HSSFWorkbook workbook = createWorkbook(pagerRecords);
		// xls
		try {
			workbook.write(out);
		} catch (FileNotFoundException e) {
			throw new BusException(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new BusException(" 写入Excel文件出错! ", e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					throw new BusException("excel导出异常", e);
				}
			}
		}
	}

	private static HSSFWorkbook createWorkbook(PagerRecords pagerRecords) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		
		
		// 创建表头
		String[] headers = pagerRecords.getPager().getExportHeaders();
		String[] exportProperties = pagerRecords.getPager().getExportProperties();
		
		//
		for (int i=0;i<headers.length;i++) {
			sheet.setColumnWidth(i, 380*13);
		}
		int rowIndex = 0;
		
		HSSFCellStyle[] styles = {createStyle(workbook,true),createStyle(workbook,false)};
		
		addRow(workbook, sheet, headers, rowIndex++,styles);
		
		
		// 创建内容
		if(pagerRecords.getRecords()!=null){
			for(Object record:pagerRecords.getRecords()){
				List<Object> values = new ArrayList<Object>();
				for(String exportProperty:exportProperties){
					Object value = PropertyUtils.getPropertyValue(record,exportProperty);
					values.add(value==null?"":value);
				}
				addRow(workbook, sheet, values.toArray(new Object[values.size()]), rowIndex++,styles);
				values = null;
			}
		}
		return workbook;
	}

	private static HSSFRow addRow(HSSFWorkbook workbook, HSSFSheet sheet,
			Object[] values, int rowIndex,HSSFCellStyle[] styles) {

		if (values == null) {
			return null;
		}

		HSSFRow row = sheet.createRow(rowIndex);
		int i = 0;
		for (Object value : values) {
			addCell(row, i++, value,styles[row.getRowNum()==0?0:1]);
		}
		return row;
	}

	private static HSSFCell addCell(HSSFRow row, int index, Object value,HSSFCellStyle cellStyle) {
		HSSFCell cell = row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value != null ? value.toString() : "");
		cell.setCellStyle(cellStyle);
		return cell;
	}
	
	private static HSSFCellStyle createStyle(HSSFWorkbook workbook,boolean isFirstRow){
		HSSFCellStyle cellStyle  =  workbook.createCellStyle();
		if(isFirstRow){
			cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderBottom((short) 1);
		
		return cellStyle;
	}
}
