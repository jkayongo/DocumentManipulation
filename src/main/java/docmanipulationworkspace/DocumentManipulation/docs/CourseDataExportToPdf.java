package docmanipulationworkspace.DocumentManipulation.docs;


import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import docmanipulationworkspace.DocumentManipulation.model.Course;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CourseDataExportToPdf {
    public ByteArrayInputStream exportCourseToPdf(Course course) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfWriter pdfWriter = new PdfWriter(outputStream);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);

        PdfFont pdfFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

        document.add(new Paragraph("COURSE DETAILS").setFont(pdfFont).setFontSize(16));
        document.add(new Paragraph("CourseID: " + course.getCourseId()));
        document.add(new Paragraph("Course name: " + course.getCourseName()));
        document.add(new Paragraph("Price: " + course.getPrice()));
        document.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
