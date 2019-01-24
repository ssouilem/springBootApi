package com.services.direct.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.stackextend.generatepdfdocument.model.OrderModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class InvoiceServicePdf {

    Logger log = LogManager.getLogger(InvoiceServicePdf.class);

    private static final String logo_path = "/jasper/images/logo3.png";
    private final String invoice_template = "/jasper/invoice_template.jrxml";
    private final String PDF_DIRECTORY = "C:\\Users\\ssouilem\\AppData\\Local\\Temp\\";

    public File generateInvoiceFor(OrderModel order, Locale locale) throws IOException {

        File pdfFile = File.createTempFile("my-invoice", ".pdf");

        log.info(String.format("Invoice pdf path : %s", pdfFile.getAbsolutePath()));

        try(FileOutputStream pos = new FileOutputStream(pdfFile))
        {
            // Load invoice jrxml template.
            final JasperReport report = loadTemplate();

            // Create parameters map.
            final Map<String, Object> parameters = parameters(order, locale);

            // Create an empty datasource.
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));

//            String output = "danfe.pdf"; 
//            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
//            JasperExportManager.exportReportToPdfFile(print, output);
            
            // Render as PDF.
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
            

        }
        catch (final Exception e)
        {
            log.error(String.format("An error occured during PDF creation: %s", e));
        }
        return pdfFile;
    }
    
    public File generatePdfFor(String pdfName, com.stackextend.generatepdfdocument.model.OrderModel invoice, Locale locale) throws IOException, Exception {

        // File pdfFile = File.createTempFile(invoiceUid, ".pdf");
        
        File file = new File(PDF_DIRECTORY + pdfName + ".pdf");

        if (! file.exists()) {
        	
        	Path pdf = Paths.get(PDF_DIRECTORY, pdfName + ".pdf");
        	Path newFilePath = Files.createFile(pdf);
        
	        File pdfFile = newFilePath.toFile();
	        
	        log.info(String.format("Invoice pdf path : %s", pdfFile.getAbsolutePath()));
        	
	        
	        try(FileOutputStream pos = new FileOutputStream(pdfFile))
	        {
	            // Load invoice jrxml template.
	        	// InputStream jrxmlInput = JRLoader.getResourceInputStream("/jasper/invoice_template.jrxml");
	            
	            final InputStream reportInputStream = getClass().getResourceAsStream("/jasper/invoice_template.jrxml");
	            // InputStream jrxmlInput = JRLoader.getResourceInputStream(jrxml);
	            final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
	            final JasperReport report = JasperCompileManager.compileReport(jasperDesign);

	            // Create parameters map.
	            final Map<String, Object> parameters = new HashMap<>();
	            
	            

	            parameters.put("logo", getClass().getResourceAsStream(logo_path));
	            parameters.put("order",  invoice);
	            parameters.put("REPORT_LOCALE", locale);

	            // Create an empty datasource.
	            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));
	  
	            // Render as PDF.
	            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
	            

	        }
	        catch (final Exception e)
	        {
	            log.error(String.format("An error occured during PDF creation: %s", e));
	            e.printStackTrace();
	        }
//	        
//	        
//	        
//	        
//	        FileOutputStream pos = new FileOutputStream(pdfFile);
//
//        	// File initialFile = new File("C:\\workspace\\jasper_template\\invoice_template.jrxml");
//        	// if (initialFile.exists()) {
//	        	//  final InputStream targetStream = new FileInputStream(initialFile);
//	        	    
//	            final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template);
//	            try {
//	            	final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
//					final JasperReport report = JasperCompileManager.compileReport(jasperDesign);
//				
//					// Create parameters map.
//		            final Map<String, Object> parameters = new HashMap<>();
//		            
//					parameters.put("logo", getClass().getResourceAsStream(logo_path));
//		            parameters.put("order",  invoice);
//		            parameters.put("REPORT_LOCALE", locale);
//		
//		            // Create an empty datasource.
//		            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));
//		  
//		            // Render as PDF.
//		            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
//					
//	            } catch (JRException e) {
//					log.info(String.format("JRException : %s", e));
//					e.printStackTrace();
//				}
//	            
//	            InputStream jasperStream = getClass().getResourceAsStream("/jasper/invoice_template.jasper");
//	            // File initialJasperFile = new File("C:\\workspace\\directAPI-spring\\direct\\src\\main\\resources\\jasper\\invoice_template.jasper");
//	        	// InputStream initJasperStream = new FileInputStream(initialFile);
//	            
////	        	JasperReport report = null;
////				try {
////					report = (JasperReport) JRLoader.loadObject(jasperStream);
////				} catch (JRException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//	           
////			} else {
////				 log.info(" =================== Pas de template invoice =================== ");
////				 throw new FileNotFoundException("Error Template : Pas des JRXML FILE Jasper", "INTERNAL_ERROR");
////			}
	            
        	return pdfFile;
        } else {
        	log.error(String.format("is exist PDF file : %s", file.getName()));
        }
        return file;
    }


    // Fill template order parametres
    private Map<String, Object> parameters(OrderModel order, Locale locale) {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("logo", getClass().getResourceAsStream(logo_path));
        parameters.put("order",  order);
        parameters.put("REPORT_LOCALE", locale);

        return parameters;
    }

    // Load invoice jrxml template
    private JasperReport loadTemplate() throws JRException {

        log.info(String.format("Invoice template path : %s", invoice_template));

        final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }

}
