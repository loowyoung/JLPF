package com.anxin.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.anxin.applicants.domain.model.Applicants;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 孙兴华
 * 文件导入导出Util(easypoi)
 */
public class EasyPoiUtil {

    /**
     * (常用)
     * 复杂导出Excel，包括文件名以及表名。创建表头
     * @param list 导出的实体类
     * @param title 表头名称
     * @param sheetName sheet表名
     * @param pojoClass 映射的实体类
     * @param isCreateHeader 是否创建表头
     * @param fileName
     * @param response
     * @return
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }


    /**
     * 复杂导出Excel，包括文件名以及表名,不创建表头
     * @param list 导出的实体类
     * @param title 表头名称
     * @param sheetName sheet表名
     * @param pojoClass 映射的实体类
     * @param fileName
     * @param response
     * @return
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * Map 集合导出
     * @param list 实体集合
     * @param fileName 导出的文件名称
     * @param response
     * @return
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     *默认导出方法
     * @param list 导出的实体集合
     * @param fileName 导出的文件名
     * @param pojoClass pojo实体
     * @param exportParams ExportParams封装实体
     * @param response
     * @return
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 动态列导出excel（常用）
     * @param list
     * @param title
     * @param sheetName
     * @param fileName
     * @param response
     * @param maplist
     */
    public static void dtExport(List<ExcelExportEntity> list, String title, String sheetName, String fileName, boolean isCreateHeader,List<Map<String, Object>> maplist, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, list,maplist);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * Excel导出
     * @param fileName 文件名称
     * @param response
     * @param workbook Excel对象
     * @return
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
//            response.reset(); // 必要地清除response中的缓存信息
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new  RuntimeException(e);
        }
    }

    /**
     * 默认导出方法
     * @param list 导出的实体集合
     * @param fileName 导出的文件名
     * @param response
     * @return
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }


    /**
     *根据文件路径来导入Excel
     * @param filePath 文件路径
     * @param titleRows 表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        //判断文件是否存在
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setNeedVerfiy(true);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    /**
     * （常用）
     * 根据接收的Excel文件来导入Excel,并封装成实体类
     * @param file 上传的文件
     * @param titleRows 表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setNeedVerfiy(true);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
        return list;
    }


    public static void main(String[] args) {




    }


}
