package com.quzehu.learn.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.utils.FileUtils
 * @Author Qu.ZeHu
 * @Date 2021/1/4 13:55
 * @Version 1.0
 */
public class FileUtils {

    private static String separator = "/";


    public static File createFile(String filePath, String fileName) {
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(separator)) {
            filePath = filePath + separator;
        }
        // 创建文件
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            if ("".equals(fileName)) {
                file.mkdirs();
            }else {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }



    public static List<String> readFile(String filePath, String fileName) {
        boolean endsWith = filePath.endsWith("/") || filePath.endsWith("\\");
        String filePathAndName;
        if (endsWith) {
            filePathAndName = filePath + fileName;
        } else {
            filePathAndName = filePath + "/" + fileName;
        }
        String str;
        List<String> results = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePathAndName);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            while ((str = br.readLine()) != null) {
                results.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


    public static String readFileLine(String filePath, String fileName, int lineNum){
        boolean endsWith = filePath.endsWith("/") || filePath.endsWith("\\");
        String filePathAndName;
        if (endsWith) {
            filePathAndName = filePath + fileName;
        } else {
            filePathAndName = filePath + "/" + fileName;
        }
        String str = "";
        try (FileInputStream fis = new FileInputStream(filePathAndName);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            int startLine = 0;
            while ((str = br.readLine()) != null) {
                startLine++;
                if (startLine == lineNum) {
                    return str;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void writeFile(File file, int lineNum, String rowContent) {
        String str;
        // 保存修改过后的所有内容
        StringBuilder bufAll = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            int startLine = 0;
            while ((str = br.readLine()) != null) {
                startLine++;
                bufAll.append(str).append("\r\n");
                if (startLine == lineNum) {
                    bufAll.append(rowContent).append("\r\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufAll = bufAll.delete(bufAll.length() - 2, bufAll.length());
        writeFile(file, bufAll.toString(), false);
    }

    public static void writeFileEnd(File file, String rowContent) {
        writeFile(file, rowContent, true);
    }

    public static void writeFile(File file, String rowContent, boolean endFlag) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, endFlag))) {
            bufferedWriter.write(rowContent);
            bufferedWriter.write("\r\n");
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
