package com.project.controller;

import com.project.util.CONSTANT;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by DongBaishun on 2017/7/11.
 */
@Controller
@RequestMapping("/file")
@SessionAttributes({"username"})
public class FileController {

    @RequestMapping(value = "/download")
    public void fileDownLoad(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String file_path = request.getParameter("path");//"C:\\Users\\JJR\\Desktop\\1.jpg";
        file_path = CONSTANT.FILE_PATH + file_path;
        File file = new File(file_path);
        if(!file.exists()) {
            return;
        }
        String fileName = file.getName();
        String headerValue = "attachment;";
        headerValue += " filename=\"" + encodeURIComponent(fileName) +"\";";
        headerValue += " filename*=utf-8''" + encodeURIComponent(fileName);

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", headerValue);

        try {
            long downloadedLength = 0;
            InputStream inputStream = new FileInputStream(file);
            OutputStream os = response.getOutputStream();

            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += b.length;
            }

            os.close();
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String encodeURIComponent(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
