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
         String s = "2016-09-21 09-11-40.bmp";
        String ss = PubFun.replaceNtStrToUid(s);
        System.out.println(ss);
    }  
}
