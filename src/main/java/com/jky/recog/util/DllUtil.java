package com.jky.recog.util;

import com.jky.recog.dll.lib.Lib;
import com.jky.recog.dll.model.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * dll的工具类
 * Created by DT人 on 2017/8/17 19:39.
 */
public class DllUtil {
    //绝对路径的地址获取，注意要去空格，特别坑，还要把把“/”转换成"\\"，replaceAll("/", "\\\\")
    /**
     * lib文件的基本路径
     */
    public static final String LIB_BASE_PATH = Lib.class.getResource("").getPath().replaceFirst("/","").replace("target/classes", "src/main/java").replaceAll("/", "\\\\").replaceAll("%20"," ");
    /**
     * model文件的基本路径
     */
    public static final String MODEL_BASE_PATH = Model.class.getResource("").getPath().replaceFirst("/","").replace("target/classes", "src/main/java").replaceAll("/", "\\\\").replaceAll("%20"," ");

    /**
     * 读取文件的方法
     * @param filePath
     * @return
     */
    public static byte[] readFile(String filePath){
        try {
            File file = new File(filePath);
            if(file.length() > Integer.MAX_VALUE){
                return null;
            }
            FileInputStream fi = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length && (numRead = fi.read(buffer, offset,buffer.length-offset))>=0) {
                offset += numRead;
            }
            if(offset != buffer.length){
                throw new IOException("Could not completely read file "+ file.getName());
            }
            fi.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取结果
     * @param file
     * @param array
     * @return
     */
    public static String getResult(String file,int[] array){
        byte[] data = readFile(file);
        String lable = new String(data);
        String[] lables = lable.split("\n");
        StringBuffer sb = new StringBuffer();
        for (int index : array) {
            sb.append(lables[index]);
        }
        return sb.toString();
    }
}
