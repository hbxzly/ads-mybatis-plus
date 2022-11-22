package com.hbx.ads.util;

import com.alibaba.fastjson.JSON;
import com.hbx.ads.domain.ExcelBean;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ExcelUtils {

        private final static String excel2003L =".xls";    //2003- 版本的excel
        private final static String excel2007U =".xlsx";   //2007+ 版本的excel


    /**
     * 表头字体大小
     */
    private static String headerFontSize = "13";
    /**
     * 表头字体样式
     */
    private static String headerFontName = FontStyle.MicrosoftYahei.name;
    /**
     * 数据字体大小
     */
    private static String otherFontSize = "10";
    /**
     * 数据字体样式
     */
    private static String otherFontName = FontStyle.MicrosoftYahei.name;
    /**
     * 单元格宽度
     */
    private static Integer width = 30;
    /**
     * sheet的名字
     */
    private static String sheetName = "sheetName";
    /**
     * 是否开启表头样式,默认为true,开启
     */
    private static Boolean isOpeanHeaderStyle = true;
    /**
     * ##############是否开始其他数据样式,默认为false,关闭(不建议开启,数据量大时影响性能)################
     */
    private static Boolean isOpeanOtherStyle = false;

    /**
     * @param keys        对象属性对应中文名
     * @param columnNames 对象的属性名
     * @param fileName    文件名
     * @param list        需要导出的json数据
     * @description 使用HSSFWorkBook导出数据, HSSF导出数据存在一些问题
     */
    public static void exportExcel(HttpServletResponse response, String[] keys, String[] columnNames, String fileName, List<Map<String, Object>> list) throws IOException {

        // 创建一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个sheet
        HSSFSheet sh = wb.createSheet(sheetName);
        // 创建Excel工作表第一行,设置表头信息
        HSSFRow row0 = sh.createRow(0);
        for (int i = 0; i < keys.length; i++) {
            // 设置单元格宽度
            sh.setColumnWidth(i, 256 * width + 184);
            HSSFCell cell = row0.createCell(i);
            cell.setCellValue(keys[i]);
            // 是否开启表头样式
            if (isOpeanHeaderStyle) {
                // 创建表头样式
                HSSFCellStyle headerStyle = setCellStyle(wb, headerFontSize, headerFontName, "header");
                cell.setCellStyle(headerStyle);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            // 循环创建行
            HSSFRow row = sh.createRow(i + 1);
            // 给这行的每列写入数据
            for (int j = 0; j < columnNames.length; j++) {
                HSSFCell cell = row.createCell(j);
                // 以这样的方式取值,过滤掉不需要的字段
                String value = String.valueOf(list.get(i).get(columnNames[j]));
                cell.setCellValue(value);
                // 是否开始其他数据样式
                if (isOpeanOtherStyle) {
                    // 设置数据样式
                    HSSFCellStyle otherStyle = setCellStyle(wb, otherFontSize, otherFontName, "other");
                    cell.setCellStyle(otherStyle);
                }
            }
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        // 这个操作也非常的耗时,暂时不知道和什么有关,应该该和文件的大小有关
        wb.write(response.getOutputStream());
    }

    /**
     * @param wb       工作簿
     * @param fontSize 字体大小
     * @param fontName 字体名称
     * @return 工作簿样式
     * @description 设置Excel样式
     */
    private static HSSFCellStyle setCellStyle(HSSFWorkbook wb, String fontSize, String fontName, String boo) {
        // 创建自定义样式类
        HSSFCellStyle style = wb.createCellStyle();
        // 创建自定义字体类
        HSSFFont font = wb.createFont();
        // 设置字体样式
        font.setFontName(fontName);
        // 设置字体大小
        font.setFontHeightInPoints(Short.parseShort(fontSize));
        // 我这个版本的POI没找到网上的HSSFCellStyle
        // 设置对齐方式
        style.setAlignment(HorizontalAlignment.CENTER);
        // 数据内容设置边框实在太丑,容易看瞎眼睛,我帮你们去掉了
        if ("header".equals(boo)) {
            // 设置边框
            style.setBorderBottom(BorderStyle.MEDIUM);
            style.setBorderLeft(BorderStyle.MEDIUM);
            style.setBorderRight(BorderStyle.MEDIUM);
            style.setBorderTop(BorderStyle.MEDIUM);
            // 表头字体加粗
            font.setBold(true);
        }
        style.setFont(font);
        return style;
    }

    /**
     * 格式化数据(我发现这个操作非常的消耗时间,尽量不要使用到这个数据转化,如果是List<Map>就直接传,如果是Json稍微改一下上面的工具类,List<Bean>好像没什么比较好的处理手段)
     *
     * @param s json数据
     * @return 装换成List集合的数据
     */
    public static List<Map<String, Object>> toList(String s) {
        List<Map<String, Object>> list = (List) JSON.parse(s);
        return list;
    }

    /**
     * 找了半天也没找到可以diy的类,我自己写个吧
     */
    enum FontStyle {
        // 微软雅黑
        MicrosoftYahei("微软雅黑"),
        // 宋体
        TimesNewRoman("宋体"),
        // 楷体
        Italics("楷体"),
        // 幼圆
        YoungRound("幼圆");

        private String name;

        FontStyle(String name) {
            this.name = name;
        }
    }


        /**
         * 描述：获取IO流中的数据，组装成List<List<Object>>对象
         * @param in,fileName
         * @return
         * @throws IOException
         */
        public  List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Exception{
            List<List<Object>> list = null;

            //创建Excel工作薄
            Workbook work = this.getWorkbook(in,fileName);
            if(null == work){
                throw new Exception("创建Excel工作薄为空！");
            }
            Sheet sheet = null;  //页数
            Row row = null;  //行数
            Cell cell = null;  //列数

            list = new ArrayList<List<Object>>();
            //遍历Excel中所有的sheet
            for (int i = 0; i < work.getNumberOfSheets(); i++) {
                sheet = work.getSheetAt(i);
                if(sheet==null){continue;}

                //遍历当前sheet中的所有行
                for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if(row==null||row.getFirstCellNum()==j){continue;}

                    //遍历所有的列
                    List<Object> li = new ArrayList<Object>();
                    for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        li.add(this.getValue(cell));
                    }
                    list.add(li);
                }
            }

            return list;

        }

        /**
         * 描述：根据文件后缀，自适应上传文件的版本
         * @param inStr,fileName
         * @return
         * @throws Exception
         */
        public  Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{
            Workbook wb = null;
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            if(excel2003L.equals(fileType)){
                wb = new HSSFWorkbook(inStr);  //2003-
            }else if(excel2007U.equals(fileType)){
                wb = new XSSFWorkbook(inStr);  //2007+
            }else{
                throw new Exception("解析的文件格式有误！");
            }
            return wb;
        }

        /**
         * 描述：对表格中数值进行格式化
         * @param cell
         * @return
         */
        //解决excel类型问题，获得数值
        public  String getValue(Cell cell) {
            String value = "";
            if(null==cell){
                return value;
            }
            switch (cell.getCellType()) {
                //数值型
                case NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        //如果是date类型则 ，获取该cell的date值
                        Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        value = format.format(date);;
                    }else {// 纯数字
                        BigDecimal big=new BigDecimal(cell.getNumericCellValue());
                        value = big.toString();
                        //解决1234.0  去掉后面的.0
                        if(null!=value&&!"".equals(value.trim())){
                            String[] item = value.split("[.]");
                            if(1<item.length&&"0".equals(item[1])){
                                value=item[0];
                            }
                        }
                    }
                    break;
                //字符串类型
                case STRING:
                    value = cell.getStringCellValue().toString();
                    break;
                // 公式类型
                case FORMULA:
                    //读公式计算值
                    value = String.valueOf(cell.getNumericCellValue());
                    if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
                        value = cell.getStringCellValue().toString();
                    }
                    break;
                // 布尔类型
                case BOOLEAN:
                    value = " "+ cell.getBooleanCellValue();
                    break;
                default:
                    value = cell.getStringCellValue().toString();
            }
            if("null".endsWith(value.trim())){
                value="";
            }
            return value;
        }

        /**
         * 导出Excel表
         * @param clazz 数据源model类型
         * @param objs excel标题以及对应的model字段
         * @param map 标题行数以及cell字体样式
         * @param sheetName 工作簿名称
         * @return
         * @throws IntrospectionException
         * @throws InvocationTargetException
         * @throws IllegalArgumentException
         * @throws IllegalAccessException
         */
        public static XSSFWorkbook createExcelFile(
                Class<?> clazz,
                List<Map<String,Object>> objs,
                Map<Integer,List<ExcelBean>> map,
                String sheetName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
            //创建新的工作簿
            XSSFWorkbook workbook = new XSSFWorkbook();
            //创建工作表
            XSSFSheet sheet = workbook.createSheet(sheetName);
            //设置excel的字体样式以及标题与内容的创建
            createFont(workbook);//字体样式
            createTableHeader(sheet,map);//创建标题
            createTableRows(sheet,map,objs,clazz);//创建内容
            System.out.println(workbook);
            return workbook;
        }
        private static XSSFCellStyle fontStyle;
        private static XSSFCellStyle fontStyle2;
        private static void createFont(XSSFWorkbook workbook) {
            //表头
            fontStyle = workbook.createCellStyle();
            XSSFFont font1 = workbook.createFont();
            font1.setBold(true);
            font1.setFontName("黑体");
            font1.setFontHeightInPoints((short) 12);//字体大小
            fontStyle.setFont(font1);
            fontStyle.setBorderBottom(BorderStyle.THIN);//下边框
            fontStyle.setBorderLeft(BorderStyle.THIN);//左边框
            fontStyle.setBorderTop(BorderStyle.THIN);//右边框
            fontStyle.setBorderRight(BorderStyle.THIN);//右边框
            fontStyle.setAlignment(HorizontalAlignment.CENTER);//居中
            //内容
            fontStyle2 = workbook.createCellStyle();
            XSSFFont font2 = workbook.createFont();
            font2.setFontName("宋体");
            font2.setFontHeightInPoints((short)10);
            fontStyle2.setFont(font2);
            fontStyle2.setBorderBottom(BorderStyle.THIN);//下边框
            fontStyle2.setBorderLeft(BorderStyle.THIN);//左边框
            fontStyle2.setBorderTop(BorderStyle.THIN);//右边框
            fontStyle2.setBorderRight(BorderStyle.THIN);//右边框
            fontStyle2.setAlignment(HorizontalAlignment.CENTER);//居中
        }
        /**
         * 根据ExcelMapping 生成列头(多行列头)
         * @param sheet 工作簿
         * @param map 每行每个单元格对应的列头信息
         */
        private static void createTableHeader(
                XSSFSheet sheet,
                Map<Integer, List<ExcelBean>> map) {
            int startIndex = 0;//cell起始位置
            int endIndex = 0;//cell终止位置
            for(Map.Entry<Integer,List<ExcelBean>> entry: map.entrySet()){
                XSSFRow row = sheet.createRow(entry.getKey()); //创建行
                List<ExcelBean> excels = entry.getValue();
                for(int x=0;x<excels.size();x++){
                    //合并单元格
                    if(excels.get(x).getCols()>1){
                        if(x==0){
                            endIndex += excels.get(x).getCols()-1;
                            //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                            sheet.addMergedRegion(new CellRangeAddress(0, 0, startIndex, endIndex));
                            startIndex += excels.get(x).getCols();
                        }else{
                            endIndex += excels.get(x).getCols();
                            sheet.addMergedRegion(new CellRangeAddress(0, 0, startIndex, endIndex));
                            startIndex += excels.get(x).getCols();
                        }
                        XSSFCell cell = row.createCell(startIndex-excels.get(x).getCols());
                        //设置内容
                        cell.setCellValue(excels.get(x).getHeadTextName());
                        if(excels.get(x).getCellStyle() != null){
                            //设置格式
                            cell.setCellStyle(excels.get(x).getCellStyle());
                        }
                        cell.setCellStyle(fontStyle);
                    }else{
                        XSSFCell cell = row.createCell(x);
                        //设置内容
                        cell.setCellValue(excels.get(x).getHeadTextName());
                        if(excels.get(x).getCellStyle() != null){
                            //设置格式
                            cell.setCellStyle(excels.get(x).getCellStyle());
                        }
                        cell.setCellStyle(fontStyle);
                    }
                }
            }
        }
        /**
         * 为excel表中循环添加数据
         * @param sheet
         * @param map  字段名
         * @param objs	查询的数据
         * @param clazz 无用
         */
        private static void createTableRows(
                XSSFSheet sheet,
                Map<Integer,List<ExcelBean>> map,
                List<Map<String,Object>> objs,
                Class<?> clazz)
                throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            int rowindex = map.size();
            int maxkey = 0;
            List<ExcelBean> ems = new ArrayList<ExcelBean>();
            for(Map.Entry<Integer,List<ExcelBean>> entry : map.entrySet()){
                if(entry.getKey() > maxkey){
                    maxkey = entry.getKey();
                }
            }
            ems = map.get(maxkey);
            List<Integer> widths = new ArrayList<Integer>(ems.size());
            for(Map<String,Object> obj : objs){
                XSSFRow row = sheet.createRow(rowindex);
                for(int i=0;i<ems.size();i++){
                    ExcelBean em = (ExcelBean)ems.get(i);
                    String propertyName = em.getPropertyName();
                    Object value = obj.get(propertyName);
                    XSSFCell cell = row.createCell(i);
                    String cellValue = "";
                    if("valid".equals(propertyName)){
                        cellValue = value.equals(1)?"启用":"禁用";
                    }else if(value==null){
                        cellValue = "";
                    }else if(value instanceof Date){
                        cellValue = new SimpleDateFormat("yyyy-MM-dd").format(value);
                    }else{
                        cellValue = value.toString();
                    }
                    cell.setCellValue(cellValue);
                    cell.setCellType(CellType.STRING);
                    cell.setCellStyle(fontStyle2);
                    sheet.autoSizeColumn(i);
                }
                rowindex++;
            }

            //设置列宽
            for(int index=0;index<widths.size();index++){
                Integer width = widths.get(index);
                width = width<2500?2500:width+300;
                width = width>10000?10000+300:width+300;
                sheet.setColumnWidth(index, width);
            }
        }



}


