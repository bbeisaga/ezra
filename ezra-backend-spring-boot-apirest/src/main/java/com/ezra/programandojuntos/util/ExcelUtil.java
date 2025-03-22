package com.ezra.programandojuntos.util;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	public static void generarCabecera(XSSFWorkbook libro, XSSFSheet hoja, String[] cabeceraExcel) {
		short columna = 0;
		XSSFRow fila = hoja.createRow(0);
		XSSFCellStyle style = crearStyle(libro, 
								HorizontalAlignment.CENTER,
								HSSFColorPredefined.SEA_GREEN.getIndex(), 
								(short) 10, 
								HSSFColorPredefined.WHITE.getIndex());
		
		for (String cabecera : cabeceraExcel) {
//			XSSFCell celda = fila.createCell(columna++);
//			celda.setCellValue(cabecera);
//			celda.setCellStyle(style);
			insertarDataCelda(fila,columna++, cabecera,style);
		}
	}
	
	public static XSSFCellStyle crearStyle(XSSFWorkbook libro, 
										HorizontalAlignment celdaAlineada, 
										short celdaColor,
										short fuenteTamanio, 
										short colorFuente) {
		XSSFCellStyle style = libro.createCellStyle();
		XSSFFont font = crearFuente(libro, 
									fuenteTamanio, 
									true, 
									colorFuente);
		style.setFont(font);
		style.setAlignment(celdaAlineada);
		style.setFillForegroundColor(celdaColor);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}
	
	private static XSSFFont crearFuente(XSSFWorkbook libro, 
										short fuenteTamanio, 
										boolean fuenteNegrita, 
										short colorFuente) {

		XSSFFont font = libro.createFont();
		font.setBold(fuenteNegrita);
		font.setFontName("Arial");
		font.setColor(colorFuente);
		font.setFontHeightInPoints(fuenteTamanio);
		return font;
	}
	
	public static void insertarDataCelda(XSSFRow fila, Short columna, String data, XSSFCellStyle style) {
		XSSFCell contentCell = fila.createCell(columna);
		contentCell.setCellValue(data);
		contentCell.setCellStyle(style);
	}

}
