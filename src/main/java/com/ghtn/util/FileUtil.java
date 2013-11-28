package com.ghtn.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * created by lh
 * 文件操作相关
 */
public class FileUtil {

    private static Log log = LogFactory.getLog(FileUtil.class);

    /**
     * 得到文件扩展名
     *
     * @param fileName 文件全名
     * @return
     */
    public static String getFileExtension(String fileName) {
        if (!StringUtil.isNullStr(fileName)) {
            fileName = fileName.trim();
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * 得到文件内容
     *
     * @param path
     * @return
     */
    public static String getFileContent(String path) {
        try {
            if (path == null || path.trim().equals("")) {
                System.out.println("路径为空！");
                return null;
            }
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("文件不存在！");
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(path)));
            String line = new String();
            StringBuffer temp = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                temp.append(line);
            }
            reader.close();
            return temp.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件没有找到！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件读取错误！");
        }

        return "";
    }

    /**
     * 读取03版word文件的内容
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String Word_03_Reader(String fileName) throws Exception {
        StringBuffer temp = new StringBuffer();

        FileInputStream fis = new FileInputStream(fileName);
        HWPFDocument doc = new HWPFDocument(fis);
        Range range = doc.getRange();
        int paragraphCount = range.numParagraphs();// 段落
        for (int i = 0; i < paragraphCount; i++) {
            // 遍历段落读取数据
            Paragraph pp = range.getParagraph(i);
            temp.append(pp.text());
        }

        fis.close();
        return temp.toString();
    }

    /**
     * 读取07版word文件的内容
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String Word_07_Reader(String fileName) throws Exception {
        OPCPackage opcPackage = POIXMLDocument.openPackage(fileName);
        POIXMLTextExtractor ex = new XWPFWordExtractor(opcPackage);
        opcPackage.close();
        return ex.getText();
    }

    /**
     * 读取excel文件的内容
     *
     * @param fileName 文件全名
     * @param fileType 文件类型，2003或2007
     * @return
     * @throws Exception
     */
    public static String ExcelReader(String fileName, String fileType) throws Exception {
        StringBuffer temp = new StringBuffer();
        FileInputStream fis = new FileInputStream(fileName);
        Workbook wb;
        if (fileType.trim().equals("2003")) {
            wb = new HSSFWorkbook(fis);
        } else if (fileType.trim().equals("2007")) {
            wb = new XSSFWorkbook(fis);
        } else {
            throw new FileNotFoundException("文件类型必须为'2003'或'2007'！");
        }

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext(); ) {
                Row r = rowIt.next();
                for (Iterator<Cell> cellIt = r.iterator(); cellIt.hasNext(); ) {
                    Cell cell = cellIt.next();
                    String cellContent = "";

                    //如果是数字类型
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        cellContent = String
                                .valueOf(cell.getNumericCellValue());
                    }
                    //如果是字符串类型
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        cellContent = cell.getStringCellValue();
                    }

                    temp.append(cellContent);
                }
            }
        }
        fis.close();
        return getPureText(temp.toString());
    }

    /**
     * 读取excel文件的内容，把返回的内容用list封装起来
     *
     * @param fileName  文件全名
     * @param fileType  文件类型，2003或2007
     * @param startLine 起始行,从1开始
     * @return list封装之后的内容
     * @throws Exception
     */
    public static List<Object[]> ExcelReader(String fileName, String fileType, int startLine) throws Exception {
        if (exists(fileName) && !StringUtil.isNullStr(fileType)) {
            List<Object[]> list = new ArrayList<>();

            FileInputStream fis = new FileInputStream(fileName);
            Workbook wb;
            if (fileType.trim().equals("2003")) {
                wb = new HSSFWorkbook(fis);
            } else if (fileType.trim().equals("2007")) {
                wb = new XSSFWorkbook(fis);
            } else {
                throw new FileNotFoundException("文件类型必须为'2003'或'2007'！");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if (wb.getNumberOfSheets() > 1) {
                // 不止一个sheet
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    Sheet sheet = wb.getSheetAt(i);
                    loopExcelSheet(list, sheet, startLine, sdf);
                }
            } else {
                Sheet sheet = wb.getSheetAt(0);
                loopExcelSheet(list, sheet, startLine, sdf);
            }

            return list;
        } else {
            throw new FileNotFoundException("文件不存在！");
        }

    }

    /**
     * 循环sheet
     *
     * @param list
     * @param sheet
     * @param startLine
     * @param sdf
     */
    private static void loopExcelSheet(List<Object[]> list, Sheet sheet, int startLine, SimpleDateFormat sdf) {
        int line = 1; //当前行

        for (Iterator<Row> rowIt = sheet.iterator(); rowIt.hasNext(); ) {
            Row r = rowIt.next();
            if (line < startLine) {
                line++;
                continue;
            }
            int n = 0;
            Object[] obj = new Object[r.getLastCellNum()];

            for (Iterator<Cell> cellIt = r.iterator(); cellIt.hasNext(); ) {
                Cell cell = cellIt.next();
                String cellContent = "";

                if (cell != null) {
                    switch (cell.getCellType()) {
                        // 数字
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            //如果是日期
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                cellContent = sdf.format(cell.getDateCellValue());
                            } else {
                                cellContent = String
                                        .valueOf(cell.getNumericCellValue());
                            }
                            break;
                        // 字符串
                        case HSSFCell.CELL_TYPE_STRING:
                            cellContent = cell.getStringCellValue();
                            break;
                        // boolean
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            cellContent = cell.getBooleanCellValue() + "";
                            break;
                        // 公式
                        case HSSFCell.CELL_TYPE_FORMULA:
                            cellContent = cell.getCellFormula();
                            break;
                        // 空值
                        case HSSFCell.CELL_TYPE_BLANK:
                            break;
                        // 错误
                        case HSSFCell.CELL_TYPE_ERROR:
                            break;
                        default:
                            break;
                    }
                }

                obj[n] = cellContent;
                n++;
            }

            list.add(obj);

            line++;
        }

    }

    /**
     * 向文件中写入内容
     *
     * @param text
     * @param path
     */
    public static void writeToFile(String text, String path) {
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(text);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件写入错误！");
        }
    }

    public static void writeToFile(String text, File file) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件写入错误！");
        }
    }

    /**
     * 得到路径path下的所有文件
     *
     * @param path
     * @return
     */
    public static List<File> getAllFiles(File path, List<File> fileList) throws Exception {
        if (path.isFile()) {
            fileList.add(path);
            return fileList;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            getAllFiles(new File(files[i].getCanonicalPath()), fileList);
        }
        return fileList;
    }

    /**
     * 得到path路径下的所有文件夹
     *
     * @param path
     * @param fileList
     * @return
     * @throws Exception
     */
    public static List<File> getAllDirectories(File path, List<File> fileList) throws Exception {
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                fileList.add(files[i]);
                getAllDirectories(new File(files[i].getCanonicalPath()), fileList);
            }
        }
        return fileList;
    }

    /**
     * 得到纯文本
     *
     * @param text
     * @return
     */
    public static String getPureText(String text) {
        return text.replaceAll("<([^>]*)>", "").replaceAll("\\s*|\t|\r|\n", "").replaceAll("&nbsp;", "");
    }

    /**
     * 删除文件
     *
     * @param list
     */
    public static void deleteFile(List<File> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                File f = list.get(i);
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param path
     * @throws Exception
     */
    public static void deleteDirectory(File path) {
        if (path.isFile()) {
            path.delete();
        } else {
            File[] files = path.listFiles();
            if (files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    deleteDirectory(files[i]);
                }
            }
            path.delete();
        }
    }

    /**
     * 清空文件夹
     *
     * @param path
     */
    public static void clearDirectory(File path) {
        if (path.isFile()) {
            return;
        }
        File[] files = path.listFiles();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                deleteDirectory(files[i]);
            }
        }

    }

    /**
     * 根据文件名判断文件是否存在
     *
     * @param fileName 文件全名
     * @return
     */
    public static boolean exists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * spring mvc 上传文件
     *
     * @param file 需要上传的文件
     * @return 上传结果
     */
    public static ResultMessage uploadFile(CommonsMultipartFile file) {
        if (!file.isEmpty()) {
            // 文件扩展名
            String fileExtension = FileUtil.getFileExtension(file.getOriginalFilename());
            // 上传到服务器上保存的文件名
            String newFileName = Calendar.getInstance().getTimeInMillis() + "." + fileExtension;
            // 文件在服务器硬盘上的全路径
            String path = ConstantUtil.UPLOAD_TEMP_PATH + "/" + newFileName;
            File localFile = new File(path);
            try {
                file.transferTo(localFile);
                return new ResultMessage(1, newFileName);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return new ResultMessage(-1, e.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return new ResultMessage(-1, e.toString());
            }
        } else {
            return new ResultMessage(-1, "文件为空！");
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param response HttpServletResponse对象
     * @return 下载结果
     */
    public static ResultMessage downloadFile(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // log.debug(ConstantUtil.CONTACTS_TEMPLATE_PATH);
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));

        // TODO : 发布到正式服务器需要修改文件路径
        File file = new File(ConstantUtil.UPLOAD_TEMP_PATH + "/" + fileName);

        try (InputStream is = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] b = new byte[1024];
            int length;
            while ((length = is.read(b)) > 0) {
                os.write(b, 0, length);
            }
            return new ResultMessage(1, ConstantUtil.SUCCESS);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ResultMessage(-1, "文件没有找到！");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultMessage(-1, "输入输出错误！");
        }
    }

}

