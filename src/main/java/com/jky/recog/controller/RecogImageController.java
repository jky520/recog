package com.jky.recog.controller;

import com.jky.recog.service.RecogImageService;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片识别控制器
 *
 * Created by DT人 on 2017/8/16 16:07.
 */
@RestController
@RequestMapping("/image")
public class RecogImageController {
    @Value("${web.upload-path}")
    private String uploadPath;

    @Resource
    private RecogImageService recogImageService;

    /**
     * 支持多文件上传
     * @param request
     * @param files
     * @return
     */
    @PostMapping("/recogImage")
    public String recogImage(HttpServletRequest request, @RequestParam("file") MultipartFile[] files) {
        // 可以获取从页面传来的参数
        String name = request.getParameter("name");
        String result = null; // 识别结果
        // 支持多文件上传
        if(files != null && files.length >= 1) {
            BufferedOutputStream bos = null;
            try {
                for (MultipartFile file : files ) {
                    String fileName = file.getOriginalFilename();
                    // 判断文件是否是位图片文件
                    if(fileName != null && !"".equalsIgnoreCase(fileName.trim()) && isImageFile(fileName)) {
                        // 根据上传文件的临时文件夹判断该文件夹是否存在
                        File tempDir = new File(uploadPath);
                        if(!tempDir.exists()) {
                            tempDir.mkdirs(); // 创建文件夹
                        }
                        String filePath = uploadPath + "/" + UUID.randomUUID().toString().replaceAll("-","") + getFileType(fileName);
                        // 创建输出对象
                        File outFile = new File(filePath);
                        // 拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(file.getInputStream(), outFile);
                        // 调用service中的方法orc进行图片识别
                        result = recogImageService.orc(filePath);
                        if(outFile.exists()) {
                            Boolean rs = outFile.delete();
                            System.out.println("是否删除了？" + rs);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try { // 快捷键 ctrl + alt + t
                    if (bos != null) { bos.close(); }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            result = "不是图片";
        }
        return result;
    }

    /**
     * 判断文件是否为图片文件
     * @param fileName
     * @return
     */
    private Boolean isImageFile(String fileName) {
        String [] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if(fileName == null) { return false; }
        fileName = fileName.toLowerCase();
        for(String type : img_type) {
            if(fileName.endsWith(type)) { return true; }
        }
        return false;
    }

    /**
     * 获取文件的后缀名
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        if(fileName != null && fileName.indexOf(".") >= 0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }
}
