package com.pjd.create.dto.from.pdf.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

/**
 * @Author suncl
 * @Date: 2022/7/1
 * @Description:
 */
public class FileUtil {
    /**
     * 写入txt
     *
     * @param path 需要写入txt的路径
     * @param list 需要写入的字符串的list
     * @since 2021/1/8 19:37
     */
    public static void write(String path, List<String> list) {
        BufferedWriter bw = null;
        FileWriter fr = null;
        try {
            //将写入转化为流的形式
            fr = new FileWriter(path);
            bw = new BufferedWriter(fr);
            //一次写一行
            for (String s : list) {
                bw.write(s);
                bw.newLine();  //换行用
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
