package docmanipulationworkspace.DocumentManipulation.docs;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GeneratePdf {

    static Cell getHeaderTextCell(String textValue){
        return new Cell().add(new Paragraph(textValue)).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    static Cell getHeaderTextCellValue(String textValue){
        return new Cell().add(new Paragraph(textValue)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getBillingAndShippingCell(String textValue){
        return new Cell().add(new Paragraph(textValue)).setFontSize(12f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell formatInvoiceTableTwoAndThreeAndFourData(String textValue, Boolean isBold){
        Cell myCell = new Cell().add(new Paragraph(textValue)).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold? myCell.setBold():myCell;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String outputPath = "invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(outputPath);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);

        Document document = new Document(pdfDocument);

        float rightColumnWidth = 285f;
        float leftColumnWidth = rightColumnWidth + 150f;
        float threeColumn = 190f;
        float[] columnWidths = {leftColumnWidth, rightColumnWidth};
        float[] tableFourColumnWidth = {leftColumnWidth};
        float[] threeColumnWidth = {threeColumn, threeColumn, threeColumn};
        float[] horizontalLineWidthBelowTotal = {threeColumn + 125f, threeColumn * 2};

        //paragraph for spacing between tables
        Paragraph paragraph = new Paragraph("\n");

        // Column width for full-page divider
        float singleColumnWidth = 190f;
        float[] fullPageWidth = {singleColumnWidth * 3};
        float[] fullPageDashedWidth = fullPageWidth;

        Table invoiceTable = new Table(columnWidths);

        invoiceTable.addCell(new Cell().add(new Paragraph("Invoice")).setFontSize(20f).setBorder(Border.NO_BORDER).setBold());

        Table nestedtable = new Table(new float[]{rightColumnWidth/2, rightColumnWidth/2});
        nestedtable.addCell(getHeaderTextCell("Invoice"));
        nestedtable.addCell(getHeaderTextCellValue("JK222222"));
        nestedtable.addCell(getHeaderTextCell("Invoice Date"));
        nestedtable.addCell(getHeaderTextCellValue("24/09/2025"));

        invoiceTable.addCell(new Cell().add(nestedtable).setBorder(Border.NO_BORDER));

        //creating a custom border
        Border dividerBorder = new SolidBorder(ColorConstants.GRAY, 1f/2f);

        //divider table(thin horizontal line)
        Table horizontalDivider = new Table(fullPageWidth);
        horizontalDivider.setBorder(dividerBorder);

        //invoice table 2 headers
        Table invoiceTableTwoHeaders = new Table(columnWidths);
        invoiceTableTwoHeaders.addCell(getBillingAndShippingCell("Billing information"));
        invoiceTableTwoHeaders.addCell(getBillingAndShippingCell("Shipping information"));

        //invoice table 2 data
        Table invoiceTableTwoData = new Table(columnWidths);
        invoiceTableTwoData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Company", true));
        invoiceTableTwoData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Service", true));
        invoiceTableTwoData.addCell(formatInvoiceTableTwoAndThreeAndFourData("SEE GROUP", false));
        invoiceTableTwoData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Software development", false));

        //invoice table 3 data
        Table invoiceTableThreeData = new Table(columnWidths);
        invoiceTableThreeData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Name", true));
        invoiceTableThreeData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Address", true));
        invoiceTableThreeData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Ngugi Wathiogo", false));
        invoiceTableThreeData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Kyebando-Nsuuba,P.O.BOX 1738,Kawoomera street", false));

        //invoice table 4 data
        Table invoiceTableFourData = new Table(tableFourColumnWidth);
        invoiceTableFourData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Address", true));
        invoiceTableFourData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Kyebando-Nsuuba,P.O.BOX 1738,Kawoomera street", false));
        invoiceTableFourData.addCell(formatInvoiceTableTwoAndThreeAndFourData("Email", true));
        invoiceTableFourData.addCell(formatInvoiceTableTwoAndThreeAndFourData("wgugi@gmail.com", false));

        //dashed line
        Table horizontalDashTable = new Table(fullPageDashedWidth);
        Border horizontalDashBorder = new DashedBorder(ColorConstants.GRAY, 1f/2f);
        horizontalDashTable.setBorder(horizontalDashBorder);

        //products paragraph
        Paragraph productsParagraph = new Paragraph("Products");

        //table 4
        Table table4 = new Table(threeColumnWidth);
        table4.addCell(new Cell().add(new Paragraph("Description")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLACK).setBorder(Border.NO_BORDER));
        table4.addCell(new Cell().add(new Paragraph("Quantity")).setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLACK).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table4.addCell(new Cell().add(new Paragraph("Price")).setBold().setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLACK).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));

        //create a list of products
        List<Product> products = new ArrayList<>();
        products.add(new Product("Milk", 2, 2500f));
        products.add(new Product("Bread", 1, 6000f));
        products.add(new Product("Blueband", 1, 9000f));
        products.add(new Product("Coffee", 1, 32000f));
        products.add(new Product("Beef", 1, 17000f));

        //table 5

        float grandTotal = 0f;
        Table table5 = new Table(threeColumnWidth);
        for(Product product: products){
            float total = product.getQuantity() * product.getPricePerPiece();
            grandTotal += total;
            table5.addCell(new Cell().add(new Paragraph(product.getProductName())).setBorder(Border.NO_BORDER).setMarginLeft(10f));
            table5.addCell(new Cell().add(new Paragraph(String.valueOf(product.getQuantity()))).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
            table5.addCell(new Cell().add(new Paragraph(String.format("%.2f", total))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
        }
        table5.addCell(new Cell().add(new Paragraph(" ")).setBorder(Border.NO_BORDER).setMarginLeft(10f));
        table5.addCell(new Cell().add(new Paragraph("Total")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table5.addCell(new Cell().add(new Paragraph(String.format("%.2f", grandTotal))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));

        //table 6
        Table table6 = new Table(horizontalLineWidthBelowTotal);
        table6.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table6.addCell(new Cell().add(horizontalDashTable).setBorder(Border.NO_BORDER));






        document.add(invoiceTable);
        document.add(paragraph);
        document.add(horizontalDivider);
        document.add(paragraph);
        document.add(invoiceTableTwoHeaders.setMarginBottom(12f));
        document.add(invoiceTableTwoData);
        document.add(invoiceTableThreeData);
        document.add(invoiceTableFourData.setMarginBottom(12f));
        document.add(horizontalDashTable);
        document.add(productsParagraph.setBold());
        document.add(table4);
        document.add(table5.setMarginBottom(20f));
        document.add(table6.setMarginBottom(20f));
        document.add(horizontalDivider);
        document.add(paragraph);
        document.close();

        System.out.println("Pdf generated successfully in: " + outputPath);

    }
}
