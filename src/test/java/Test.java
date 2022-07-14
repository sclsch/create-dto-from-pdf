import com.alibaba.fastjson.JSON;
import com.pjd.PdfApplication;
import com.pjd.create.dto.from.pdf.dto.PdfClass;
import com.pjd.create.dto.from.pdf.dto.PdfFieldInfo;
import com.pjd.create.dto.from.pdf.util.FileUtil;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author suncl
 * @Date: 2022/7/1
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PdfApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Slf4j
public class Test {
    @org.junit.Test
    public void readPdfTable(){
        String path = "xxx.pdf";
        //传入pdf文件
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(path);
        //创建PdfTableExtractor
        PdfTableExtractor extractor = new PdfTableExtractor(pdf);
        List<PdfClass> pdfClasses = new ArrayList<>();
        //循环PDF页面
        for (int pageIndex = 0; pageIndex < pdf.getPages().getCount(); pageIndex++) {
            log.info("pageIndex:{}",pageIndex);
            //将数据表从当前页提取到PdfTable数组中
            PdfTable[] tableLists = extractor.extractTable(pageIndex);
            if (tableLists != null && tableLists.length > 0) {
                int k = 0;
                for (PdfTable table : tableLists) {
                    k ++;
                    log.info("tableIndex:{}",k);
                    PdfClass pdfClass = new PdfClass();
                    pdfClasses.add(pdfClass);
                    pdfClass.setClassName("page" + (pageIndex + 1) + "table" + k +  "Dto");
                    List<PdfFieldInfo> pdfFieldInfoList = new ArrayList<>();
                    pdfClass.setPdfFieldInfoList(pdfFieldInfoList);
                    //循环数据表的行
                    for (int i = 0; i < table.getRowCount(); i++) {
                        if(i == 0){
                            continue;
                        }
                        PdfFieldInfo pdfFieldInfo = new PdfFieldInfo();
                        pdfFieldInfoList.add(pdfFieldInfo);
                        //列
                        List<String> list = new ArrayList<String>();
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            //提取到数据
                            String text = table.getText(i, j);
                            if(j == 0){
                                pdfFieldInfo.setFieldName(text);
                            }else if(j == 1){
                                pdfFieldInfo.setFieldType(text);
                            }else if(j == 2){
                                pdfFieldInfo.setInfo(text);
                            }

                        }

                    }
                }
            }
        }
        for (PdfClass o : pdfClasses) {
            ArrayList<String> lines = new ArrayList<>();
            lines.add("public class " + o.getClassName() + "{");
            for (PdfFieldInfo pdfFieldInfo : o.getPdfFieldInfoList()) {
                String fieldType = pdfFieldInfo.getFieldType();
                String fieldLine = "private " + fieldType + " " + pdfFieldInfo.getFieldName() + ";";
                lines.add(fieldLine);
            }
            lines.add("}");
            FileUtil.write("D:\\工作\\业务中台\\dto\\" + o.getClassName() + ".java", lines);

        }
        log.info(">>>>>>>>>>>抽取出的dto集合为：{}>>>>>>>>>>>>>>", JSON.toJSONString(pdfClasses));
    }

    /**
     *规整数据
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
