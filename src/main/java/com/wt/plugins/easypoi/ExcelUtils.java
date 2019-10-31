package com.wt.plugins.easypoi;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Excel 导入导出 工具类
 *
 * @author wtao
 * @date 2019/10/30 17:06
 **/
public class ExcelUtils {

    /**
     * 导出Excel
     *
     * @param list           导入数据列表
     * @param title          标题
     * @param sheetName      工作簿名称
     * @param pojoClass      导入列表 POJO 类
     * @param fileName       文件名称
     * @param isCreateHeader 是否创建 表头
     * @param response       Response对象
     * @author wtao
     * @date 2019/10/30 17:09
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,
                                   String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 导出Excel
     *
     * @param list      导出数据列表
     * @param title     标题
     * @param sheetName 工作簿名称
     * @param pojoClass 导出列表 POJO 类
     * @param fileName  文件名称
     * @param response  Response对象
     * @author wtao
     * @date 2019/10/30 17:09
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 导出Excel
     *
     * @param list     导出数据列表
     * @param fileName 文件名称
     * @param response Response对象
     * @author wtao
     * @date 2019/10/30 17:09
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }


    /**
     * 导出Excel
     *
     * @param list         导出数据列表
     * @param exportParams 其他属性 参数
     * @param pojoClass    导出列表 POJO 类
     * @param fileName     文件名称
     * @param response     Response对象
     * @author wtao
     * @date 2019/10/30 17:09
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName,
                                      HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 导出Excel
     *
     * @param list     导出数据列表
     * @param fileName 文件名称
     * @param response Response对象
     * @author wtao
     * @date 2019/10/30 17:09
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }


    /**
     * 执行导出文件
     *
     * @param fileName 文件名称
     * @param response Response对象
     * @author wtao
     * @date 2019/10/30 17:09
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            //throw new NormalException(e.getMessage());
        }
    }

    /**
     * 导入Excel
     *
     * @param filePath   文件路径（绝对路径）
     * @param titleRows  标题行数
     * @param headerRows 表头行数
     * @param pojoClass  导入的对象类型
     * @author wtao
     * @date 2019/10/30 17:09
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            //throw new NormalException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            //throw new NormalException(e.getMessage());
        }
        return list;
    }

    /**
     * 导入Excel
     *
     * @param file       文件
     * @param titleRows  标题行数
     * @param headerRows 表头行数
     * @param pojoClass  导入的对象类型
     * @author wtao
     * @date 2019/10/30 17:09
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            // throw new NormalException("excel文件不能为空");
        } catch (Exception e) {
            //throw new NormalException(e.getMessage());
            System.out.println(e.getMessage());
        }
        return list;
    }
}
