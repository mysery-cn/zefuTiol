package com.zefu.tiol.util;

import java.io.*;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.conversion.util.ConversionFileUtil;
import com.yr.gap.conversion.vo.FileSystemVo;
import com.zefu.tiol.constant.CollectDataConstant;

import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件工具类
 * 
 * @author linch
 * 
 */

public class FileUtils {

	static final int buffer = 2048;

	/**
	 * 保存文件
	 * 
	 * @param file
	 *            要保存的文件
	 * @return fileId 文件ID
	 */
	public static String upload(File file) {
		String fileType = "";// 文件类型
		// 通过文件名判断文件类型
		String[] nameSplit = file.getName().split("\\.");
		fileType = nameSplit[nameSplit.length - 1];
		String fileName;
		try {
			fileName = URLEncoder.encode(file.getName(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			fileName=file.getName();
		}

		// 生成文件元数据对象
		FileSystemVo fsVo = new FileSystemVo();
		fsVo.setLocalFilePath(file.getAbsolutePath());
		fsVo.setFileId(CommonUtil.getUUID());
		fsVo.setFileName(fileName);
		fsVo.setFileType(fileType);
		fsVo.setFileVersion("1");

		// 保存文件
		try {
			String result = ConversionFileUtil.uploadFileToFs(CollectDataConstant.CREATER_ID, fsVo);
			JSONObject json = JSONObject.fromObject(result);
			if(json.get("code").equals("1")) {
				JSONObject finfo = json.getJSONObject("finfo");
				return finfo.getString("fid");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 解压Zip文件
	 * 
	 * @param path
	 *            文件目录
	 */
	public static void unZip(String path) {
		int count = -1;
		String savepath = "";

		File file = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		savepath = path.substring(0, path.lastIndexOf(".")) + File.separator; // 保存解压文件目录
		new File(savepath).mkdir(); // 创建保存目录
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(path, "gbk"); // 解决中文乱码问题
			Enumeration<?> entries = zipFile.getEntries();

			while (entries.hasMoreElements()) {
				byte buf[] = new byte[buffer];

				ZipEntry entry = (ZipEntry) entries.nextElement();

				String filename = entry.getName();
				boolean ismkdir = false;
				if (filename.lastIndexOf("/") != -1) { // 检查此文件是否带有文件夹
					ismkdir = true;
				}
				filename = savepath + filename;

				if (entry.isDirectory()) { // 如果是文件夹先创建
					file = new File(filename);
					file.mkdirs();
					continue;
				}
				file = new File(filename);
				if (!file.exists()) { // 如果是目录先创建
					if (ismkdir) {
						new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); // 目录先创建
					}
				}
				file.createNewFile(); // 创建文件

				is = zipFile.getInputStream(entry);
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, buffer);

				while ((count = is.read(buf)) > -1) {
					bos.write(buf, 0, count);
				}
				bos.flush();
				bos.close();
				fos.close();
				is.close();
			}

			zipFile.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private static final int BUFFER_SIZE = 2 * 1024;
	
	/**
     * 压缩成ZIP
     * @param srcDir 压缩文件夹路径 
     * @param out    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
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
     * 压缩成ZIP 不含根文件夹
     * @param srcDir 压缩文件夹路径 
     * @param out    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
	public static void toZipWithoutRoot(String srcDir, OutputStream out, boolean KeepDirStructure)
			throws RuntimeException {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile, zos, "", KeepDirStructure);
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils", e);
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
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
                    zos.putNextEntry(new ZipEntry(name + ("".equals(name)?"":"/")));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + ("".equals(name)?"":"/") + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }
	
	/**
	   * @功能描述: TODO 创建文件夹
	   * @参数: @param path
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午3:21:00
	 */
    public static void createFileUrl(String path) {
    	File fileDir  = new File(path);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
    }
	
    /**
       * @功能描述: TODO 创建文件
       * @参数: @param path
       * @创建人 ：林鹏
       * @创建时间：2018年11月6日 下午3:22:09
     */
    public static void createFile(File fileDir) {
		if(!fileDir.exists()){
			try {
				fileDir.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    /**
	   * @throws IOException 
	   * @功能描述: TODO 向文件中写入内容
	   * @参数: @param path
	   * @参数: @param content
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午2:16:09
	 */
    public static void writeContent(String path, String content) throws IOException{
    	FileOutputStream fos = null;
		File file = new File(path);
		try {
			fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(content);
			osw.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		FileOutputStream fos1 = new FileOutputStream(new File("D:\\tmp\\collect\\data\\2018-11-12\\91110000100009563N_0005_1000_20181112204112_D11D7B78EA9C427BBD4D277ECAA6052B.zip"));
//		File file = new File("D:\\tmp\\数据报送zip\\91310000132200821H_0004_1000_20181024.zip");
		
		toZipWithoutRoot("D:\\tmp\\collect\\data\\2018-11-12\\91110000100009563N_0005_1000_20181112204112_D11D7B78EA9C427BBD4D277ECAA6052B",fos1,true);
//		System.out.println(file.getAbsolutePath());
//		System.out.println(CommonUtil.getUUID());
//		File[] files = new File("D:\\tmp\\collect\\data\\2018-11-12\\91110000100009563N_0005_1000_20181112204112_D11D7B78EA9C427BBD4D277ECAA6052B").listFiles();
//		File zipFile = new File("D:\\tmp\\collect\\data\\2018-11-12\\91110000100009563N_0005_1000_20181112204112_D11D7B78EA9C427BBD4D277ECAA6052B.zip");
//		zipFiles(files,zipFile);
	}

	public static void zipFiles(File[] srcFiles, File zipFile) {
		// 判断压缩后的文件存在不，不存在则创建
		if (!zipFile.exists()) {
             try {
                 zipFile.createNewFile();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         // 创建 FileOutputStream 对象
         FileOutputStream fileOutputStream = null;
         // 创建 ZipOutputStream
         ZipOutputStream zipOutputStream = null;
         // 创建 FileInputStream 对象
         FileInputStream fileInputStream = null;
 
         try {
             // 实例化 FileOutputStream 对象
             fileOutputStream = new FileOutputStream(zipFile);
             // 实例化 ZipOutputStream 对象
             zipOutputStream = new ZipOutputStream(fileOutputStream);
             // 创建 ZipEntry 对象
             ZipEntry zipEntry = null;
             // 遍历源文件数组
             for (int i = 0; i < srcFiles.length; i++) {
                 // 将源文件数组中的当前文件读入 FileInputStream 流中
                 fileInputStream = new FileInputStream(srcFiles[i]);
                 // 实例化 ZipEntry 对象，源文件数组中的当前文件
                 zipEntry = new ZipEntry(srcFiles[i].getName());
                 zipOutputStream.putNextEntry(zipEntry);
                 // 该变量记录每次真正读的字节个数
                 int len;
                 // 定义每次读取的字节数组
                 byte[] buffer = new byte[1024];
                 while ((len = fileInputStream.read(buffer)) > 0) {
                     zipOutputStream.write(buffer, 0, len);
                 }
                 fileInputStream.close();
                 //删除文件
				 srcFiles[i].delete();
             }
             zipOutputStream.closeEntry();
             zipOutputStream.close();
             fileInputStream.close();
             fileOutputStream.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
	}
	
	/**
	   * @功能描述: TODO 删除文件
	   * @参数: @param srcFiles
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月9日 下午2:18:58
	 */
	public static void delFile(File[] srcFiles) {
		for (int i = 0; i < srcFiles.length; i++) {
			File file = srcFiles[i];
			if(file == null){
				System.out.println("文件删除失败:" + file);
			}else{
				System.out.println("删除文件：" + file.getName());
				boolean result = file.delete();
				System.out.println("删除文件结果：" + result);
			}
		}
	}

	/**
	 * multfile 上传文件类型转File 李缝兴
	 * @param multfile
	 * @return
	 * @throws IOException
	 */
	public static File multipartFileToFile(MultipartFile multfile) throws IOException {
		InputStream ins = new ByteArrayInputStream(multfile.getBytes());
		File file = new File(multfile.getOriginalFilename());
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 前端上传文件上传至FS文件服务
	 * @param multfile 上传文件
	 * @return
	 * @throws IOException
	 */
	public static String uploadMultipartFileToFs(MultipartFile multfile) throws IOException{
		if(multfile.getSize() == 0) return "";
		File file = multipartFileToFile(multfile);
		String fileId = upload(file);
		//上传成功删除服务器上的文件
		File delFile = new File(file.toURI());
		delFile.delete();
		return fileId;
	}
	
	/**
	   * @功能描述: TODO 从输入流中获取字节数组
	   * @参数: @param inputStream
	   * @参数: @return
	   * @参数: @throws IOException
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午2:54:07
	 */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
        }  
        bos.close();  
        return bos.toByteArray();  
    }
}
