package com.services.direct.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.services.direct.bean.City;

public class GeneratePdfReport {

	public static ByteArrayInputStream citiesReport(List<City> cities) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100.0f);
			// table.setWidthPercentage(60);
			table.setWidths(new float[] {3.0f, 2.0f, 2.0f, 3.0f});
			table.setSpacingBefore(10);

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			headFont.setColor(BaseColor.WHITE);

			// define table header cell
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(4);
	        
	        // write table header
	        cell.setPhrase(new Phrase("Book Title", headFont));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Author", headFont));
	        table.addCell(cell);
	 
	         
	        cell.setPhrase(new Phrase("Published Date", headFont));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Price", headFont));
	        
			table.addCell(cell);

			for (City city : cities) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(city.getId().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(city.getName()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(city.getPopulation())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				
				table.addCell("01/02/1979");
			}

			PdfWriter.getInstance(document, out);
			document.open();
			
			// add Table
			document.add(table);

			// add paragraph Left
            Paragraph paragraph = new Paragraph("This is right aligned text");
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            // add paragraph Centered
            paragraph = new Paragraph("This is centered text");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            // add paragraph  Left
            paragraph = new Paragraph("This is left aligned text");
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            // add paragraph Left with indentation
            paragraph = new Paragraph(
                    "This is left aligned text with indentation");
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setIndentationLeft(50);
            document.add(paragraph);

			// add Title
			document.addTitle("My first PDF");

			document.close();

		} catch (DocumentException ex) {

			Logger.getLogger(GeneratePdfReport.class.getName()).log(Level.SEVERE, null, ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}
