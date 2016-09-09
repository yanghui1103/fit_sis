package com.bw.fit.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Tans {

    public static final String DBDRIVERm = "com.mysql.jdbc.Driver";  
    
    public static final String DBURLm = "jdbc:mysql://218.21.213.76:7202/sis";  
      
    public static final String DBUSERm = "root";  
      
    public static final String DBPASSm= "root123456";  
    //---------------
    public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";  
    
    public static final String DBURL = "jdbc:oracle:thin:@110.18.60.194:10165:sis";  
      
    public static final String DBUSER = "fitsis";  
      
    public static final String DBPASS = "fit_sis2016yh";  
      
    public static void main(String[] args) throws Exception{            
        try {
            Connection connm = null;//表示数据库连接的对象             
            Statement stmtm = null;//表示数据库更新操作            
            ResultSet resultm = null;//表示接受数据库查询到的结果            
            Class.forName(DBDRIVERm);//使用class类加载驱动程序  
            Connection conn = null;//表示数据库连接的对象             
            Statement stmt = null;//表示数据库更新操作            
            ResultSet result = null;//表示接受数据库查询到的结果            
            Class.forName(DBDRIVER);//使用class类加载驱动程序  
              
            connm = DriverManager.getConnection(DBURLm, DBUSERm, DBPASSm);//连接数据库  
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);//连接数据库 
              
            stmtm = connm.createStatement();//tatement接口需要通过connection接口进行实例化操作  
              
              
            resultm = stmtm.executeQuery("select * from co_report a where a.audit_state ='BTT' ");//执行sql语句，结果集放在result中  
            int i=0;
            conn.setAutoCommit(false);
            while(resultm.next()){//判断是否还有下一行  
                String co_id = resultm.getString("co_id"); 
                String name = resultm.getString("name"); 
                String card_id = resultm.getString("card_id"); 
                String rpt_unit = resultm.getString("rpt_unit"); 
                String rpt_date = resultm.getString("rpt_date"); 
                String sub_start = resultm.getString("sub_start"); 
                String sub_end = resultm.getString("sub_end"); 
                String creator = resultm.getString("creator");  
                String sql = "insert into rpt_reconds (fdid,person_name,card_id,unit_name,remark,pay_start, pay_end,creator) "
                        + " values ( '"+co_id+"', '"+name+"','"+card_id+"','"+rpt_unit+"', '"+rpt_date+"','"+sub_start+"','"+sub_end+"','"+creator+"' )" ;
                System.out.println(sql);
                int res = conn.prepareStatement(sql).executeUpdate();
                if(res<1){
                    conn.rollback();
                    System.out.println("异常："+card_id);                    
                }
                i++;
                System.out.println(i);
                conn.commit();
            }  
            resultm.close();  
            stmtm.close();  
            connm.close();  
            result.close();  
            stmt.close();  
            conn.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
          
    }  
}
