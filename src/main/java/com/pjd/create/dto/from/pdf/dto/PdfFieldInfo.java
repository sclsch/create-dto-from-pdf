package com.pjd.create.dto.from.pdf.dto;

import lombok.Data;

/**
 * @Author suncl
 * @Date: 2022/7/1
 * @Description:
 */
@Data
public class PdfFieldInfo {
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段类型  String,Integer,Date ...
     */
    private String fieldType;
    /**
     * 字段含义
     */
    private String info;
    /**
     * 字段格式
     */
    private String format;

}
