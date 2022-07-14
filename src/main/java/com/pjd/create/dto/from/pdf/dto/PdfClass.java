package com.pjd.create.dto.from.pdf.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author suncl
 * @Date: 2022/7/1
 * @Description: 根据pdf生成的dto类
 */
@Data
public class PdfClass {
    private String className;
    private List<PdfFieldInfo> pdfFieldInfoList;
}
