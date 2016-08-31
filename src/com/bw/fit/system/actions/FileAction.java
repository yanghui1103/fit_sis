package com.bw.fit.system.actions;
import static com.bw.fit.common.utils.PubFun.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FileAction extends ActionSupport {

	private  HttpServletResponse response = ServletActionContext.getResponse() ;
	private  HttpServletRequest request = ServletActionContext.getRequest();
	private static Log log = LogFactory.getLog(FileAction.class);
	private File file; 
	private String fileFileName;
	private String fileFileContentType;
 

	private String message = "-9";
	private String beforeName = "-9";
	private String error ="";
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getBeforeName() {
		return beforeName;
	}

	public void setBeforeName(String beforeName) {
		this.beforeName = beforeName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileFileContentType() {
		return fileFileContentType;
	}

	public void setFileFileContentType(String fileFileContentType) {
		this.fileFileContentType = fileFileContentType;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String execute() throws Exception {   
		 String path = ServletActionContext.getServletContext()   
	                .getRealPath("/uploadfiles"); 
		 String beforeFileName=this.getFileFileName();
		 if("".equals(beforeFileName)||beforeFileName==null){
			 	message="1";
				error="请选择文件！";
				return ERROR;
		 }
			beforeName = beforeFileName ;
		 String afterFileName = getFixLenthString(16)+getFileTypeName(beforeFileName);
		try {
			File f = this.getFile();
			if(!this.getFileFileName().endsWith(".bmp")&&
			        !this.getFileFileName().endsWith(".BMP")&&
                    !this.getFileFileName().endsWith(".GIF")&&
                    !this.getFileFileName().endsWith(".gif")&&
                    !this.getFileFileName().endsWith(".jpg")&&
                    !this.getFileFileName().endsWith(".JPG")&&
                    !this.getFileFileName().endsWith(".jpeg")&&
                    !this.getFileFileName().endsWith(".JPEG")){
				message="1";
				error="文件被禁止上传！,格式不符，只容许传图片文件";
				return ERROR;
			}
			FileInputStream inputStream = new FileInputStream(f);
			String toPathFile = path +"/"+ afterFileName;
			
			FileOutputStream outputStream = new FileOutputStream(toPathFile);
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, length);
			}

            inputStream.close();
            outputStream.flush();
            // 与此同时写入一个绝对路径的文件夹下
//            createOtherPath("/opt/app/uploadfiles_bak",afterFileName,f);
			createOtherPath("d://uploadfiles_bak",afterFileName,f);					
		} catch (Exception e) {
			e.printStackTrace();
			message = "1";
			error="上传异常";
			return ERROR;
		}
		message = afterFileName; // 将上传成功后的文件名传出前台
		return SUCCESS;
	}

	/***
	 * 把进入系统的附件  放入另一个绝对路径的另一个
	 * 文件夹下
	 * @throws Exception 
	 */
	public void createOtherPath(String newPath ,String afterFileName,File f ) throws Exception{    
        String toPathFile = newPath +"/"+ afterFileName; 
        FileInputStream inputStream = new FileInputStream(f);
        FileOutputStream outputStream = new FileOutputStream(toPathFile);
        byte[] buf = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buf)) != -1) {
            outputStream.write(buf, 0, length);
        } 
        inputStream.close();
        outputStream.flush();
	}
}
