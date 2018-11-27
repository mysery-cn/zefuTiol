package com.zefu.tiol.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jcifs.UniAddress;
import jcifs.smb.*;

public class SambaUtil {
		
	private static final Logger logger = LoggerFactory.getLogger(SambaUtil.class);
	
    /**
     * 从samba服务器上下载指定的文件到本地目录
     * @param remoteSmbFile   Samba服务器远程文件
     * @param localDir        本地目录的路径
     * @throws SmbException
     */

    public static void downloadFileFromSamba(SmbFile remoteSmbFile, String localDir) throws SmbException {
        if (!remoteSmbFile.exists()) {
        	logger.error("Samba服务器远程文件不存在");
            return;
        }
        if((localDir == null) || ("".equals(localDir.trim()))) {
        	logger.error("本地目录路径不可以为空");
            return;
        }
        InputStream in = null;
        OutputStream out = null;
        try{
            //获取远程文件的文件名,这个的目的是为了在本地的目录下创建一个同名文件
            String remoteSmbFileName = remoteSmbFile.getName();

            //本地文件由本地目录，路径分隔符，文件名拼接而成
            File localFile = new File(localDir + File.separator + remoteSmbFileName);

            // 如果路径不存在,则创建
            File parentFile = localFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            //打开文件输入流，指向远程的smb服务器上的文件，特别注意，这里流包装器包装了SmbFileInputStream
            in = new BufferedInputStream(new SmbFileInputStream(remoteSmbFile));
            //打开文件输出流，指向新创建的本地文件，作为最终复制到的目的地
            out = new BufferedOutputStream(new FileOutputStream(localFile));

            //缓冲内存
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1){
                out.write(buffer);
                buffer = new byte[1024];
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try  {
                out.close();
                in.close();
            } catch  (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传本地文件到Samba服务器指定目录
     * @param url
     * @param auth
     * @param localFilePath
     * @throws MalformedURLException
     * @throws SmbException
     */
    public static boolean  uploadFileToSamba(String url, NtlmPasswordAuthentication auth, String localFilePath) throws MalformedURLException, SmbException{
        boolean ret=false;
    	if ((localFilePath == null) || ("".equals(localFilePath.trim()))) {
        	logger.error("本地文件路径不可以为空");
            return false;
        }
        //检查远程父路径，不存在则创建
        SmbFile remoteSmbFile = new SmbFile(url, auth);
        String parent = remoteSmbFile.getParent();
        SmbFile parentSmbFile = new SmbFile(parent, auth);
        if (!parentSmbFile.exists()) {
            parentSmbFile.mkdirs();
        }
        InputStream in = null;
        OutputStream out = null;
        try{
            File localFile = new File(localFilePath);

            //打开一个文件输入流执行本地文件，要从它读取内容
            in = new BufferedInputStream(new FileInputStream(localFile));

            //打开一个远程Samba文件输出流，作为复制到的目的地
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteSmbFile));

            //缓冲内存
            byte [] buffer =  new   byte [1024];
            while  (in.read(buffer) != - 1 ) {
                out.write(buffer);
                buffer = new byte[1024];
            }
            ret=true;
        } catch  (Exception e) {
            e.printStackTrace();
            ret=false;
        } finally  {
            try  {
            	if(out!=null){
            		out.close();
            	}
            	if(in!=null){
            		in.close();
            	}
            } catch  (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 获取文件列表
     * @param host
     * @param username
     * @param password
     * @param filePath
     * @throws MalformedURLException
     * @throws SmbException
     * @throws UnknownHostException 
     */
    public static SmbFile[] getSmbFiles(String host,String username,String password,String filePath) throws SmbException, MalformedURLException, UnknownHostException {
    	 UniAddress ua = UniAddress.getByName(host);
         NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(host, username, password);
         SmbSession.logon(ua, auth);//验证是否能够成功登录
         return new SmbFile("smb://" + host + filePath, auth).listFiles();
    	
    } 
    public static void main(String[] args) throws UnknownHostException, SmbException, MalformedURLException {
//        String host = "192.168.137.45";
//        String username = "zefu";
//        String password = "zefusoft@2018";
    	 String host = "192.168.137.121";
         String username = "gzw";
         String password = "gzw";

//        //samba服务器上的文件
//        String filePath = "/a/b/xxx.pdf";
//        String demo1LocalDir = "D:\\Data\\潇公子-沙漠骆驼.lrc";
//        UniAddress ua = UniAddress.getByName(host);
//        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(host, username, password);
//        SmbSession.logon(ua, auth);//验证是否能够成功登录
//        //创建Smb文件,地址一定要使用smb://
//        SmbFile remoteSmbFile = new SmbFile("smb://" + host + filePath, auth);
//        SambaUtil.downloadFileFromSamba(remoteSmbFile, demo1LocalDir);
//        System.out.println("download success");
    	String filePath = "/share/"; // 共享文件夹路径
        String localPath = "d:\\tmp\\collect\\data"; // 共享文件夹路径
        List<File> fileList = new ArrayList<File>();
        SmbFile[] smbFiles = SambaUtil.getSmbFiles(host, username, password, filePath);
		for (SmbFile sf : smbFiles) {
			SambaUtil.downloadFilesFromSamba(sf, localPath);	
			fileList.add(new File(localPath + "\\" + sf.getName()));
		}
		
        //upload
//        String demo2LocalFile= "D:\\Data\\展展与罗罗-沙漠骆驼.lrc";
//        String sambaDir = "/share";
//        String filePathUpload = sambaDir +"/2018-10-30/"+ new File(demo2LocalFile).getName();
//        String url = "smb://" + host + filePathUpload;
//        SambaUtil.uploadFileToSamba(url, auth, demo2LocalFile);
    }

	public static void downloadFilesFromSamba(SmbFile smbFile, String localPath) throws SmbException {
		// TODO Auto-generated method stub
		if(smbFile.isDirectory()) {
			File file = new File(localPath+"\\"+smbFile.getName());
			if(!file.exists()) {
				file.mkdirs();
			}
			SmbFile[] smbFiles =  smbFile.listFiles();
			for(SmbFile sf:smbFiles) {
				downloadFilesFromSamba(sf,file.getPath());
			}
		}else {
			downloadFileFromSamba(smbFile,localPath);
		}
	}
}