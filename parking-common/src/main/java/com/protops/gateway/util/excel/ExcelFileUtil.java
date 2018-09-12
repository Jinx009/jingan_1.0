package com.protops.gateway.util.excel;


import jxl.Cell;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.*;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ExcelFileUtil {

	/**
	 * 创建临时的文件
	 * @return File
	 */
	public static File createTempFile(String title,String dir) {

		// 创建目录
		boolean exists = (new File(dir)).exists();
		if (!exists) {
			new File(dir).mkdir();
		}

//		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		String datetime = df.format(new Date());

		String fileName = dir + File.separator + title + ".xls";
		File file = new File(fileName);
		return file;
	}

	/**
	 * 将List生成Excel文件,并返回Excel文件的输出流
	 * @param title 标题
	 * @param list  要成才excel对象
	 * @param c     需要生成的对象
	 * @return
	 * @throws WriteException
	 * @throws RowsExceededException
	 */
	@SuppressWarnings("unchecked")
	public  final File write(String title, String dir, List list,Class c) throws RowsExceededException, WriteException {

		if (c == null) {
			return null;
		}
		Refect refect = new RefectImpl();

		File file = ExcelFileUtil.createTempFile(title,dir);
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 字体设置
		WritableFont font = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.AUTOMATIC);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
		int sheetNum = 0;

		WritableSheet sheet = workbook.createSheet("Sheet" + sheetNum, 0);
		sheet.mergeCells(0,0,refect.getFieldAnnotation(c).length-1,0);
		Label titleLabel = new Label(0, 0, title, cellFormat);
		try {
			if (titleLabel != null)
				sheet.addCell(titleLabel);
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

		// 写文件
		String[] rowNames = refect.getFieldAnnotation(c);
		//首行显示
		for(int i = 0;i < rowNames.length;i++){
			Label label = null;
			label = new Label(i,1,rowNames[i],cellFormat);
			try {
				if (label != null)
					sheet.addCell(label);
				sheet.setColumnView(i,20);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}

		int row = 2;
		//数据显示
		for(int i = 0;i < list.size();i++){
			Object obj = list.get(i);
			List columnValues = refect.getFieldValue(obj);
			for(int j = 0;j < columnValues.size();j++){
				Label label = null;
				//超过5000条数据就再建一个sheet
				if (row > 5000) {
					sheetNum++;
					row = 1;
					WritableSheet newSheet = workbook.getSheet(0);

					Cell[] cells = newSheet.getRow(1);
					for (int k = 0; k < cells.length; k++) {
						Label newLabel = new Label(k, 0, cells[k].getContents(), cellFormat);
						try {
							sheet.addCell(newLabel);
						} catch (RowsExceededException e) {
							e.printStackTrace();
						} catch (WriteException e) {
							e.printStackTrace();
						}
					}
				}
				WritableFont font2 = new WritableFont(WritableFont.TIMES,10);
				WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
				cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
				cellFormat2.setAlignment(Alignment.CENTRE);
				cellFormat2.setWrap(true);
				cellFormat2.setBorder(Border.ALL, BorderLineStyle.MEDIUM);

				label = new Label(j, row, columnValues.get(j).toString(),cellFormat2);
				try {
					if (label != null)
						sheet.addCell(label);
					sheet.setColumnView(j,20);
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
			row++;
		}

		// 保存
		try {
			workbook.write();
			workbook.close();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
