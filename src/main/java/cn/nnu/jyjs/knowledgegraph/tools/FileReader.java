package cn.nnu.jyjs.knowledgegraph.tools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.*;


/**
 * this java class is used to convert PDF/WORD/EXCEL/PPT to String
 */
public class FileReader {

    private static String EXCEL_LINE_DELIMITER = "\t";
    private static String EXCEL_LINE = "\n";

    /**
     * 读取pdf文件
     * @param file
     * @return
     * @throws Exception
     */
    public static String readPdf(String file) throws Exception {
        boolean sort=false;
        String encoding = "UTF-8";
        String text="";
        int startPage = 1;
        int endPage = Integer.MAX_VALUE;
        File pdfFile=new File(file);
        PDDocument document = null;
        try {
            document = PDDocument.load(pdfFile);
            PDFTextStripper stripper = null;
            stripper = new PDFTextStripper();
            stripper.setSortByPosition(sort);
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);
            text=stripper.getText(document);
            System.out.println(text);
            /* ----------------------------  */
            //String path="pdf.txt";
            //File myfile = new File(path);
            //myfile.createNewFile();
            //FileWriter fw = new FileWriter(myfile);
            //BufferedWriter bw = new BufferedWriter(fw);
            //bw.write(text);
            //bw.flush();
            //bw.close();
            //fw.close();
            /* ----------------------------  */
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(document!=null) {
                document.close();
            }
        }
        return text;
    }

    /**
     * 读取word文件
     * @param file
     * @return
     */
    public static String readWord(String file) {
        String buffer = "";
        try {
            if (file.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(file));
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
            } else if (file.endsWith("docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(file);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            } else {
                System.out.println("此文件不是word文件！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer;
    }


    /**
     * 读取excel文件
     *
     * @param file 源目录
     */
    public static String excelToTxt(String file) {
        File newFile = null;
        InputStream is = null;
        String buffer="";
        FileOutputStream out = null;
        XSSFWorkbook book = null;
        XSSFSheet sheet= null;
        try {

            out = new FileOutputStream(newFile);
            is = new FileInputStream(file);
            book =new XSSFWorkbook(new FileInputStream(new File(file)));
            sheet = book.getSheetAt(0);
            buffer = readSheet(sheet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (book != null)
                    book = null;
                if (is != null)
                    is.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }
    /**
     * 读excel.Sheet
     *
     * @param
     * @param
     */
    private static String readSheet(XSSFSheet xssfSheet) {
        int rowTotalNum = xssfSheet.getLastRowNum();//xssfSheet.getLastRowNum();//总行数  ~~总行数出错
        System.out.println(rowTotalNum);
        XSSFRow rowFirst=xssfSheet.getRow(0);//第一行
        int cols = rowFirst.getLastCellNum();//总列数
        StringBuffer sBuffer =new StringBuffer();

        //循环sheet中的所有行
        for (int rowNum = 0; rowNum < rowTotalNum; rowNum++) {
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow != null) {
                //循环一行中的所有列
                for(int j=0;j<cols;j++) {
                    XSSFCell cell1 = xssfRow.getCell(j);
                    sBuffer.append(cell1);//CUST_ACCT_NO
                    sBuffer.append(EXCEL_LINE_DELIMITER);
                }
                //一行结束后换行
                sBuffer.append(EXCEL_LINE);//UPLOAD_FILE
            }
        }
        System.out.println(sBuffer.toString());

        return sBuffer.toString();
    }

    /**
     *  读取字节流
     * @param path
     * @return
     */
    public static String readByte(String path) {
        File file = new File(path);
        Reader reader = null;
        String ret="";
        byte[] buffer = new byte[1024];
        try{
            reader = new InputStreamReader(new FileInputStream(file),"utf8");
            int tempchar;
            while((tempchar = reader.read()) != -1){

                ret += (char)tempchar;
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ret;
    }

}

