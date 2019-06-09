package com.eops;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ArrayBasedStringTokenizer;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class PDFFormatter {

    public static void main(String[] args) throws IOException {


        Document doc = new Document();
        PdfWriter writer=null;
        String staticGK="Static GK Q & A – May 2019";
        ArrayList <String> topics = new ArrayList<String>();
        topics.add("INDIAN AFFAIR");
        topics.add("INTERNATIONAL AFFAIRS");
        topics.add("BANKING & FINANCE");
        topics.add("BUSINESS & ECONOMY");
        topics.add("AWARDS & RECOGNITIONS");
        topics.add("APPOINTMENTS & RESIGNS");
        topics.add("ACQUISITIONS & MERGERS");
        topics.add("SCIENCE & TECHNOLOGY");
        topics.add("ENVIRONMENT");
        topics.add("SPORTS");
        topics.add("OBITUARY");
        topics.add("BOOKS & AUTHORS");
        topics.add("IMPORTANT DAYS");
        topics.add(staticGK);
        
        
        
        String file="C:\\Users\\1600838\\Desktop\\PUTTU\\Current Affairs Q&A PDF - May 2019 by AffairsCloud";
        
        try (PDDocument document = PDDocument.load(new File(file+".pdf"))) {
        
        	writer = PdfWriter.getInstance(doc, new FileOutputStream(file+"_output.pdf"));
            doc.open();

        	document.getClass();

            if (!document.isEncrypted()) {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                //System.out.println("Text:" + st);
                Boolean isAns=false;
                Boolean isStaticGK=false;
                Boolean isExplanation=false;
                Boolean isNxt=false;
                int ansCount=1;
				// split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                	if(!isStaticGK){
                	if(line.trim().startsWith("Answer"))
                	{
                		isAns=true;
                		isNxt=false;
                		ansCount++;
                	}
                	if(line.trim().startsWith("Explanation"))
                	{
                		isAns=false;
                		isExplanation=true;
                		
                	}
                	if(ansCount==1)
                	{
                		System.out.println(line);	
                		doc.add(new Paragraph(line));
                         
                	}
                	else{
                		
                	if(!isAns && line.trim().startsWith(String.valueOf(ansCount)))
                	                	{
                		isNxt=true;
                		doc.add(new Paragraph("\n"));

                	}
                	if(!isNxt)
                	{
                		for (String topic : topics)
                		{
                			if(topic.equalsIgnoreCase(line.trim()))
                			{
                				ansCount=1;
                        		doc.add(new Paragraph("\n"+"\n"));
                        		doc.add(new Paragraph("                                                      	"+line));

                        		doc.add(new Paragraph("\n"));
                        		
                        		if(line.trim().equalsIgnoreCase(staticGK))
                        		{
                            		isStaticGK=true;                        			
                        		}

                			}
                		}
                	}
                	
                	if(!isAns&&isNxt){
                		System.out.println(line);	
                		doc.add(new Paragraph(line));
                		
                	}
                	}
                }
                else
                {
                	if(ansCount==1 && line.trim().startsWith(String.valueOf(ansCount)))
                	{
                		ansCount++;
                		doc.add(new Paragraph(line));
                		System.out.println(line);
                	}
                	if(line.trim().startsWith(String.valueOf(ansCount)))
                	{
                		doc.add(new Paragraph(line));
                		System.out.println(line);
                		ansCount++;

                	}
                	if(ansCount==83)
                	{
                	
                		/*if(line.trim().startsWith("3)"))
                		{

                    		System.out.println("sasa");
                    		doc.add(new Paragraph(line));
                    		System.out.println(line);
                    		ansCount++;
	
                		}*/
                	}
                }

            }
            }
        } catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        finally {
        	doc.close();
        	writer.close();	
		}
    	

    }
}
