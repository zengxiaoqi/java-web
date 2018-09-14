package com.eastcom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件处理API
 * @author Administrator
 * 
 *         2013-1-17下午1:33:38
 */
public class FileUtil {


  /*
   * 检查文件是否存在
   **/
  public static boolean isExist(String filePath){
	  File file = new File(filePath);
	    if (!file.exists()) {
	      System.err.println("文件：" + filePath + "不存在");
	    }  
	    return file.exists();
  }
  /**
   * 复制某个源目录下的所有文件和文件夹到指定目录下
   * 
   * @param srcDirPath 源目录
   * @param destDirPath 目标目录
   * @throws IOException 
   */
  public static void copyToDir(String srcDirPath, String destDirPath) throws IOException {
    /*
     * 数据正确性检查
     */
    //源目录路径或目标目录路径为空
    if (isNullOrBlank(srcDirPath) || isNullOrBlank(destDirPath)) {
      return;
    }
    File srcDir = new File(srcDirPath);
    File destDir = new File(destDirPath);

    if (!srcDir.exists()) {//源目录不存在！
      return;
    }
    if (!destDir.exists()) {//目标目录不存在，创建目录
      destDir.mkdirs();
    }

    //执行目录复制
    String destAbsolutePath = destDir.getAbsolutePath();
    File[] files = srcDir.listFiles();
    for (int i = 0; i < files.length; i++) {
      File source = new File(files[i].getPath());
      File target = new File(destAbsolutePath, files[i].getName());
      if (files[i].isFile()) {
        copyFile(source, target);//复制文件
      } else {
        copyToDir(source.getAbsolutePath(), target.getAbsolutePath());//复制目录
      }
    }
  }

  /*
   * copy单一文件
   */
  private static boolean copyFile(File source, File target) throws IOException {
    if (target.exists()) {
      System.err.println("文件：" + target.getAbsolutePath() + "已存在，不允许覆盖更新！");
      return false;
    }
    FileChannel in = null;
    FileChannel out = null;
    FileInputStream inStream = null;
    FileOutputStream outStream = null;
    try {
      inStream = new FileInputStream(source);
      outStream = new FileOutputStream(target);
      in = inStream.getChannel();
      out = outStream.getChannel();
      in.transferTo(0, in.size(), out);
    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (inStream != null)
          inStream.close();
        if (outStream != null)
          outStream.close();
        if (in != null)
          in.close();
        if (out != null)
          out.close();
      } catch (IOException e1) {
        throw e1;
      }
    }
    return true;
  }

  /**
   * 复制一个目录到指定的目录下
   * @param srcDirPath  一个目录路径
   * @param destDirPath 目标目录路径
   * @throws IOException 
   */
  public static void copyDirToDir(String srcDirPath, String destDirPath) throws IOException {
    //源目录路径或目标目录路径为空
    if (isNullOrBlank(srcDirPath) || isNullOrBlank(destDirPath)) {
      return;
    }
    File srcDir = new File(srcDirPath);
    File destDir = new File(destDirPath, srcDir.getName());
    copyToDir(srcDir.getPath(), destDir.getPath());
  }

  /**
   * 复制文件到指定文件夹下
   * @param srcFilePath   源文件路径
   * @param destFilePath  目标文件夹路径
   * @return 复制成功，返回true，复制错误或不成功，返回false 
   * @throws IOException 
   */
  public static boolean copyFile(String srcFilePath, String destFilePath) throws IOException {
    File source = new File(srcFilePath);
    File target = new File(destFilePath);
    boolean result = false;
    result = copyFile(source, target);
    return result;
  }

  /**
   * 删除指定路径的一个文件或目录
   * @param path 文件路径或目录路径
   * @return     删除成功，true，删除失败，返回false
   */
  public static boolean delete(String path) {
    if (isNullOrBlank(path)) {
      return false;
    }
    File file = new File(path);
    if (!file.exists()) {
      return false;
    }
    if (file.isDirectory()) {//如果是目录，遍历删除
      File[] files = file.listFiles();
      for (int i = 0; i < files.length; i++) {
        delete(files[i].getPath());
      }
    }
    file.delete();//如果是文件或已遍历删除完目录（即当前目录为空）
    return true;
  }

  /**
   * 读取整个文本文件内容
   * @param filePath  全路径文件名
   * @return          文件内容串
   * @throws IOException 
   */
  public static String readFileFully(String filePath) throws IOException {
    return readFileFully(filePath, null);
  }

  /**
   * 指定编码集读取整个文本文件内容
   * 
   * @param filePath  全路径文件名
   * @param encoding  字符编码
   * @return          文件内容串
   * @throws IOException 
   */
  public static String readFileFully(String filePath, String encoding) throws IOException {
    if (isNullOrBlank(filePath)) {
      return null;
    }
    File file = new File(filePath);
    if (!file.exists()) {
      System.err.println("文件：" + filePath + "不存在");
      return null;
    }

    StringBuffer sb = new StringBuffer(2000);
    BufferedReader reader = null;
    try {
      String temp = null;
      //根据encoding是否为空采用不同的生成方式
      if (isNullOrBlank(encoding)) {
        reader = new BufferedReader(new FileReader(file));
      } else {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
      }
      while ((temp = reader.readLine()) != null) {
        sb.append(temp);
      }
    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (reader != null)
          reader.close();
      } catch (IOException e1) {
        throw e1;
      }
    }
    return sb.toString();
  }

  /**
   * 随机读取文件内容
   * 
   * @param filePath
   * @param post      读取位置
   * @param length    读取长度
   * @param encoding  编码集
   * @return          文件内容串
   * @throws IOException 
   */
  public static String readFileRandom(String filePath, int post, int length, String encoding) throws IOException {

    /*
     * 数据正确性检查
     */
    if (isNullOrBlank(filePath)) {
      return null;
    }

    File file = new File(filePath);
    if (!file.exists()) {
      System.err.println("文件：" + filePath + "不存在");
      return null;
    }
    char[] charArray = new char[4096];
    BufferedReader reader = null;
    String str = null;
    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
      reader.skip(post);
      int i = reader.read(charArray, post, length);
      if (i > 0) {
        str = new String(charArray);
      }

    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (reader != null)
          reader.close();
      } catch (IOException e) {
        throw e;
      }
    }
    return str;
  }

  /**
   * 按行读文件  
   * @param filePath  全路径文件名
   * @return          该文件的内容集list
   * @throws IOException 
   */
  public static List<?> readFile(String filePath) throws IOException {
    return readFile(filePath, null);
  }

  /**
   * 根据指定的编码按行读文件
   * 
   * @param filePath 源文件所在的路径
   * @param encoding 编码
   * @return
   * @throws IOException 
   */
  public static List<?> readFile(String filePath, String encoding) throws IOException {
    /*
     * 数据正确性检查
     */
    if (isNullOrBlank(filePath)) {
      return null;
    }
    File file = new File(filePath);
    if (!file.exists()) {
      System.err.println("文件：" + filePath + "不存在");
      return null;
    }

    List<String> result = new ArrayList<String>();
    String temp = null;
    BufferedReader reader = null;
    try {
      //根据encoding是否为空采用不同的生成方式
      if (isNullOrBlank(encoding)) {
        reader = new BufferedReader(new FileReader(file));
      } else {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
      }
      while ((temp = reader.readLine()) != null) {
        result.add(temp);
      }
    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (reader != null)
          reader.close();
      } catch (IOException e1) {
        throw e1;
      }
    }
    return result;
  }

  /**
   * 把内容字符串写到指定文件的中
   * 
   * @param filePath    文件所在路径
   * @param text        写进去的内容字符串 
   * @throws IOException 
   */
  public static void writeFile(String filePath, String text) throws IOException {
    writeFile(filePath, text, null);
  }

  /**
   * 根据指定的编码把内容字符串写进指定的文件中
   * 如果文件不存在，则创建filePath对应的文件保存内容字符串
   * 
   * @param filePath  文件所在路径
   * @param text      内容字符串
   * @param encoding  编码
   * @throws IOException 
   */
  public static void writeFile(String filePath, String text, String encoding) throws IOException {
    write(filePath, text, encoding, false);
  }

  /*
   * 文件写入
   * filePath 写入文件路径
   * textContent 写入内容
   * encoding 写入内容编码
   * isAppend 是否追加
   */
  private static void write(String filePath, String textContent, String encoding, boolean isAppend) throws IOException {

    //数据正确性检查  
    if (isNullOrBlank(filePath)) {
      return;
    }

    File file = new File(filePath);
    Writer outWriter = null;
    try {
      if (isNullOrBlank(encoding)) {
        outWriter = new FileWriter(filePath, isAppend);
      } else {
        outWriter = new OutputStreamWriter(new FileOutputStream(file, isAppend), encoding);
      }
      outWriter.write(textContent);
    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (outWriter != null)
          outWriter.close();
      } catch (IOException e1) {
        throw e1;
      }
    }
  }

  /**
   * 
   * 根据指定的编码把内容字符串附加到指定的文件后
   * 
   * @param filePath  文件所在路径
   * @param text      内容字符串
   * @param encoding  编码
   * @throws IOException 
   */
  public static void writeFileAppend(String filePath, String text, String encoding) throws IOException {
    write(filePath, text, encoding, true);
  }
  
  public static boolean isNullOrBlank(String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}
}
