package com.bw.fit.common.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.Region;
import org.apache.struts2.ServletActionContext;

public class Test implements Cloneable {
    public String k ;
    public String getK() {
        return k;
    }
    public void setK(String k) {
        this.k = k;
    }
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
	public static void main(String[] args) throws Exception {
	    Test t = new Test();
	    t.setK("99");
	    Test t2= (Test) t.clone();
	    System.out.println(t2.getK());
		  
	}
}
