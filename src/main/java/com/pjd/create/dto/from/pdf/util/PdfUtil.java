package com.pjd.create.dto.from.pdf.util;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import java.io.FileOutputStream;
 
public class PdfUtil {
 
    /**
     * 合并pdf
     * @param files 需要合并的pdf路径
     * @param newfile 合并成新的文件的路径
     */
    public static boolean mergePdfFiles(String[] files, String newfile) {
        boolean retValue = false;
        Document document = null;
        PdfCopy copy = null;
        PdfReader reader = null;
        try {
            document = new Document(new PdfReader(files[0]).getPageSize(1));
            copy = new PdfCopy(document, new FileOutputStream(newfile));
            document.open();
            for (int i = 0; i < files.length; i++) {
                reader = new PdfReader(files[i]);
                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
				reader.close();
            }
            retValue = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (copy != null) {
                copy.close();
            }
            if (document != null) {
                document.close();
            }
        }
        return retValue;
    }
 
    public static void main(String[] args) {
        String[] files = {
                "D:\\practice\\zzz\\xxx\\目录清单V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\1测点管理中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\2yyy分析中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\3yyy环境中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\4yyy拓扑中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\5yyy图形中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\6yyy资产中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\7yyy资源中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\（备份）资源中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\8基础服务中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\9计量应用中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\10模型管理中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\11设备状态中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\12生产成本中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\13作业管理中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\14作业资源中心V1.25.0.pdf",
                "D:\\practice\\zzz\\xxx\\属性枚举V1.21.0.pdf",
                "D:\\practice\\zzz\\xxx\\数据字典-设备.pdf",
                "D:\\practice\\zzz\\xxx\\数据字典-业务.pdf"
        };
        String savepath = "D:\\practice\\zzz\\xxx\\hebing.pdf";
        boolean b = mergePdfFiles(files, savepath);
        System.out.println(b);
    }
}