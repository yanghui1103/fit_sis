package com.bw.fit.common.utils;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadExcel {

    public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";  
    
    public static final String DBURL = "jdbc:oracle:thin:@110.18.60.194:10165:sis";  
      
    public static final String DBUSER = "fitsis";  
      
    public static final String DBPASS = "fit_sis2016yh";  
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String path = "d:/1.xls";

        Connection conn = null;//表示数据库连接的对象             
        Statement stmt = null;//表示数据库更新操作            
        ResultSet result = null;//表示接受数据库查询到的结果            
        Class.forName(DBDRIVER);//使用class类加载驱动程序  
        conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);//连接数据库 

        conn.setAutoCommit(true);
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow != null) { 
                String sql = "insert into attachment (fileid,foreign_id,filename,filesize,beforename,creator,create_time,state) "
                        + " values ( '"+hssfRow.getCell(0)+"', '"+hssfRow.getCell(1)+"','"+hssfRow.getCell(2)+"','"+hssfRow.getCell(3)+"', '"+hssfRow.getCell(4)+"','"+hssfRow.getCell(5)+"',to_date('"+hssfRow.getCell(6)+"','yyyy-MM-dd hh24:mi:ss'),'"+hssfRow.getCell(7)+"' )" ;
                System.out.println(sql); 
                int res = conn.prepareStatement(sql).executeUpdate();
                System.out.println("res:"+res);
                System.out.println("rowNum:"+rowNum);
            }
        } 

    }

}
