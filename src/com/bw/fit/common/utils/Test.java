package com.bw.fit.common.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.Region;
import org.apache.struts2.ServletActionContext;

public class Test  {
    public static void main(String[] args) {  
        
        Calendar start = Calendar.getInstance();  
        start.set(2016, 3, 10);  
        Long startTIme = start.getTimeInMillis();  
      
        Calendar end = Calendar.getInstance();  
        end.set(2016, 4, 10);  
        Long endTime = end.getTimeInMillis();  
      
        Long oneDay = 1000 * 60 * 60 * 24l;  
      
        Long time = startTIme;  
        while (time <= endTime) {  
            Date d = new Date(time);  
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
            System.out.println(df.format(d));  
            time += oneDay;  
        }  
    }  
}
