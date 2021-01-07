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

    private static final String SEPARATOR_ONE = "/";

    private static final String SEPARATOR_TWO = "\\";

    private static final String br = "\r\n";

    /**
     * 创建文件
     * @Date 2021/1/5 16:33
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @Author Qu.ZeHu
     * @return java.io.File
     **/
    public static File createFile(String filePath, String fileName) {
        // 创建文件
        File file = new File(getFilePath(filePath, fileName));
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
    /**
     * 获取文件全路径
     * @Date 2021/1/5 16:45
     * @param filePath 文件路径
     * @param fileName 件名称
     * @Author Qu.ZeHu
     * @return java.lang.String
     **/
    private static String getFilePath(String filePath, String fileName) {
        boolean endsWith = filePath.endsWith(SEPARATOR_ONE) || filePath.endsWith(SEPARATOR_TWO);
        String filePathAndName;
        if (endsWith) {
            filePathAndName = filePath + fileName;
        } else {
            filePathAndName = filePath + SEPARATOR_ONE + fileName;
        }
        return filePathAndName;
    }


    /**
     * 读文件
     * @Date 2021/1/5 16:33
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @Author Qu.ZeHu
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> readFile(String filePath, String fileName) {
        return readFile(getFilePath(filePath, fileName));
    }

    /**
     * 读文件
     * @Date 2021/1/5 16:33
     * @param filePathAndName 文件路径和名称
     * @Author Qu.ZeHu
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> readFile(String filePathAndName) {
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

    /**
     * 读文件中某一行内容
     * @Date 2021/1/5 16:46
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param lineNum 行号
     * @Author Qu.ZeHu
     * @return java.lang.String
     **/
    public static String readFileFromLine(String filePath, String fileName, int lineNum){
        String str = "";
        try (FileInputStream fis = new FileInputStream(getFilePath(filePath, fileName));
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
    /**
     * 在某一行上改写新内容
     * @Date 2021/1/5 16:50
     * @param file 文件
     * @param lineNum 行号
     * @param rowContent 行内容
     * @Author Qu.ZeHu
     * @return void
     **/
    public static void writeFileToLine(File file, int lineNum, String rowContent) {
        String str;
        StringBuilder bufAll = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            int startLine = 0;
            while ((str = bufferedReader.readLine()) != null) {
                startLine++;
                if (startLine == lineNum) {
                    bufAll.append(rowContent).append(br);
                } else {
                    bufAll.append(str).append(br);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufAll.delete(bufAll.length() - 2, bufAll.length());
        writeFile(file, bufAll.toString(), false);
    }

    /**
     * 在文件的末尾追加写内容
     * @Date 2021/1/5 16:51
     * @param file 文件
     * @param rowContent 行内容
     * @Author Qu.ZeHu
     * @return void
     **/
    public static void writeFileEndAppend(File file, String rowContent) {
        writeFile(file, rowContent, true);
    }

    /**
     * 写文件
     * @Date 2021/1/5 16:52
     * @param file 文件
     * @param rowContent 行内容
     * @param endFlag 是否追加添加内容
     * @Author Qu.ZeHu
     * @return void
     **/
    public static void writeFile(File file, String rowContent, boolean endFlag) {
        try (FileOutputStream fos = new FileOutputStream(file,endFlag);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            osw.write(rowContent);
            osw.write(br);
            osw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
