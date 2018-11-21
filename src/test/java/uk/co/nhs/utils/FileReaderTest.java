package uk.co.nhs.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileReaderTest {

    public static String output = "appointment-letter-1.docx";

    @Test
    public void test() throws JSONException, IOException, InvalidFormatException {
        String textFileToString = FileReader.convertTextFileToString("appointment-letter-1.json");
        JSONObject jsonObject = new JSONObject(textFileToString);

        XWPFDocument document = new XWPFDocument();
        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
        XWPFParagraph headerParagraph = header.createParagraph();
        XWPFRun headerParagraphRun = headerParagraph.createRun();
        headerParagraphRun.setText(jsonObject.getString("note"));
        headerParagraphRun.setFontFamily("Courier");
        headerParagraphRun.setFontSize(10);
        headerParagraphRun.addBreak();

        XWPFParagraph address = document.createParagraph();
        address.setAlignment(ParagraphAlignment.LEFT);
        address.setWordWrapped(true);
        XWPFRun subTitleRun = address.createRun();
        subTitleRun.setText(jsonObject.getString("from"));
        subTitleRun.setFontFamily("Courier");
        subTitleRun.setFontSize(10);
        subTitleRun.addBreak();

        XWPFParagraph body = document.createParagraph();
        body.setAlignment(ParagraphAlignment.LEFT);
        body.setWordWrapped(true);
        XWPFRun addressRun = body.createRun();
        addressRun.setText(jsonObject.getString("body"));
        addressRun.setFontFamily("Courier");
        addressRun.setFontSize(10);


        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();
        readDates();

    }


    private void readDates() throws IOException, InvalidFormatException {

        FileInputStream fis = new FileInputStream(output);
        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
        List<XWPFParagraph> paragraphList = xdoc.getParagraphs();

        for (XWPFParagraph paragraph : paragraphList) {
            System.out.println(paragraph.getText());
        }
    }

}
