package com.brilliantreform.sc.utils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.brilliantreform.sc.distri.po.DistriOrderBean;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class ExcelUtil {
	
	/** 
     * @功能：手工构建一个简单格式的Excel 
     */  
    private static List<DistriOrderBean> getStudent() throws Exception  
    {  
        List list = new ArrayList();  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");  
  
        DistriOrderBean user1 = new DistriOrderBean();  
        user1.setDistri_num("0004111231231331");
        list.add(user1);  
  
        return list;  
    }  
	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("学生表一");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
  
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("配送单号");  
        cell.setCellStyle(style);  
     // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        try {
			List list = ExcelUtil.getStudent();
			for (int i = 0; i < list.size(); i++)  
	        {  
	            row = sheet.createRow((int) i + 1);  
	            DistriOrderBean stu = (DistriOrderBean) list.get(i);  
	            // 第四步，创建单元格，并设置值  
	            row.createCell((short) 0).setCellValue(stu.getDistri_num());  
	        }  
			FileOutputStream fout = new FileOutputStream("E:/students.xls");
            wb.write(fout);  
            fout.close();  
            //PrintUtil.print("E:/students.xls");
            
		} catch (Exception e) {
			e.printStackTrace();
		}  
        

	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static OutputStream exportExcel(OutputStream outputStream,List list){
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("配送单");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
  
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("昵称");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);  
        cell.setCellValue("手机号");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);  
        cell.setCellValue("地址");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);  
        cell.setCellValue("收款信息");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);  
        cell.setCellValue("商品信息");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);  
        cell.setCellValue("状态");  
        cell.setCellStyle(style);
     // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
        try {
			for (int i = 0; i < list.size(); i++)  
	        {  
	            row = sheet.createRow((int) i + 1);  
	            DistriOrderBean distribean = (DistriOrderBean) list.get(i);  
	            // 第四步，创建单元格，并设置值  
	            row.createCell((short) 0).setCellValue(distribean.getNickName());  
	            row.createCell((short) 1).setCellValue(distribean.getPhone());  
	            row.createCell((short) 2).setCellValue(distribean.getAddr());  
	            row.createCell((short) 3).setCellValue((JsonUtil.obj2Str(distribean.getPay_staus()).equals("1")?"未支付商品总价":JsonUtil.obj2Str(distribean.getPay_staus()).equals("2")?"已支付商品总价":"")+(JsonUtil.obj2Str(distribean.getOrder_price())));  
	            row.createCell((short) 4).setCellValue(JsonUtil.obj2Str(distribean.getProduct_name())+"*"+JsonUtil.obj2Str(distribean.getProduct_amount())+"  "+JsonUtil.obj2Str(distribean.getProduct_price())+"元");  
	            row.createCell((short) 5).setCellValue(JsonUtil.obj2Str(distribean.getDistri_staus()).equals("0")
				           ?"待配送":JsonUtil.obj2Str(distribean.getDistri_staus()).equals("1")
						           ?"配送中":JsonUtil.obj2Str(distribean.getDistri_staus()).equals("2")
						           ?"完成":JsonUtil.obj2Str(distribean.getDistri_staus()).equals("3")
						           ?"暂时删除":"");  
	        }  
            wb.write(outputStream);  
            //PrintUtil.print("E:/students.xls");
            return outputStream;
            
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return null;
	}
	

}
