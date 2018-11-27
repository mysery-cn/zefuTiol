package com.zefu.tiol.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONObject;
import org.json.XML;

import com.alibaba.fastjson.JSONArray;

public class Test {
	
	public static void main(String[] args) throws IOException {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结构
		JSONArray catalogJsonO = new JSONArray();//
		JSONObject datas = new JSONObject();//一级结果
		JSONObject data1 = new JSONObject();
		for (int i = 0; i < 2; i++) {
			JSONObject jsonTop = new JSONObject();
			JSONArray catalogJson1 = new JSONArray();//
			jsonTop.put("catalog_name", "重大决策事项");
			jsonTop.put("catalog_level", 1);
			jsonTop.put("order_number", i + 1);
			for (int j = 0; j < 2; j++) {
				JSONObject jsonSecond = new JSONObject();
				JSONObject data3 = new JSONObject();
				JSONArray catalogJson2 = new JSONArray();//
				jsonSecond.put("catalog_name", "重大决策事项");
				jsonSecond.put("catalog_level", 2);
				jsonSecond.put("order_number", j + 1);
				for (int m = 0; m < 4; m++) {
					JSONObject jsonThree = new JSONObject();
					jsonThree.put("catalog_name", "重大决策事项");
					jsonThree.put("catalog_level", 3);
					jsonThree.put("order_number", m + 1);
					catalogJson2.add(jsonThree);
				}
				data3.put("catalog", catalogJson2);
				jsonSecond.put("catalog_list", data3);
				catalogJson1.add(jsonSecond);
			}
			data1.put("catalog", catalogJson1);
			jsonTop.put("catalog_list", data1);
			catalogJsonO.add(jsonTop);
		} 
		datas.put("catalog", catalogJsonO);
		result.put("catalog_list", datas);
		message.put("tiol", result);
		System.out.println(message.toString());
		
		String content = "<?xml version='1.0' encoding='utf-8'?>" + XML.toString(message);
		String path = "D:\\企业下发材料";
		String fileName = "catalog.xml";
		File fileDir  = new File(path);
		File file = new File(path + "\\" + fileName);
		if(!fileDir.exists()){
			try {
				fileDir.mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writeContent(path + "\\" + fileName,content);
	}

	private static void writeContent(String path, String content) throws IOException {
		FileOutputStream fos = null;
		File file = new File(path);
		try {
			byte[] bs = content.getBytes("UTF-8");
			content = new String(bs,"UTF-8");
			fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private static final int BUFFER_SIZE = 2 * 1024;

	public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
			throws RuntimeException {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
            boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }
}
