package com.example.demo.controller;

import com.example.demo.service.NotificationService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public Map<String, Object> send(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String title = body.get("title");
        String message = body.get("message");

        String result = notificationService.sendNotification(token, title, message);
        return Map.of("status", "ok", "result", result);
    }


    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generateInvoicePdf() throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 40, 40, 40); // margins
        PdfWriter.getInstance(document, baos);
        document.open();

        // --------------------------------------------------------
        // HEADER
        // --------------------------------------------------------
        Paragraph header = new Paragraph("INVOICE", 
                new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 26, com.lowagie.text.Font.BOLD));
        header.setAlignment(Element.ALIGN_CENTER);
        header.setSpacingAfter(20);
        document.add(header);

        // --------------------------------------------------------
        // INVOICE + CUSTOMER INFO TABLE
        // --------------------------------------------------------
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingAfter(20);
        infoTable.setWidths(new float[]{1, 1});

        PdfPCell invoiceCell = new PdfPCell();
        invoiceCell.setBorder(Rectangle.NO_BORDER);
        invoiceCell.addElement(new Paragraph("Invoice No: INV-01234"));
        invoiceCell.addElement(new Paragraph("Date: 2025-11-27"));
        invoiceCell.addElement(new Paragraph("Due Date: 2025-12-05"));

        PdfPCell customerCell = new PdfPCell();
        customerCell.setBorder(Rectangle.NO_BORDER);
        customerCell.addElement(new Paragraph("Customer Details"));
        customerCell.addElement(new Paragraph("Bhola Kumar"));
        customerCell.addElement(new Paragraph("bk@hcltech.com"));
        customerCell.addElement(new Paragraph("+1 234 567 890"));

        infoTable.addCell(invoiceCell);
        infoTable.addCell(customerCell);

        document.add(infoTable);

        // --------------------------------------------------------
        // BILLING ITEMS TABLE
        // --------------------------------------------------------
        PdfPTable itemTable = new PdfPTable(4);
        itemTable.setWidthPercentage(100);
        itemTable.setWidths(new float[]{4, 1, 2, 2});

        // Header row
        String[] headers = {"Item Description", "Qty", "Unit Price", "Total"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Paragraph(h));
            cell.setBackgroundColor(new java.awt.Color(200, 200, 200));
            cell.setPadding(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            itemTable.addCell(cell);
        }

        // Data rows
        itemTable.addCell("Premium Hosting Package");
        itemTable.addCell("1");
        itemTable.addCell("$100");
        itemTable.addCell("$100");

        itemTable.addCell("Monthly Maintenance Service");
        itemTable.addCell("2");
        itemTable.addCell("$50");
        itemTable.addCell("$100");

        itemTable.addCell("Domain Renewal");
        itemTable.addCell("1");
        itemTable.addCell("$20");
        itemTable.addCell("$20");

        // Add spacing after table
        itemTable.setSpacingAfter(20);
        document.add(itemTable);

        // --------------------------------------------------------
        // SUMMARY TABLE
        // --------------------------------------------------------
        PdfPTable summaryTable = new PdfPTable(2);
        summaryTable.setWidthPercentage(40);
        summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        summaryTable.addCell(getSummaryCell("Subtotal:", true));
        summaryTable.addCell(getSummaryCell("$220", false));

        summaryTable.addCell(getSummaryCell("Tax (10%):", true));
        summaryTable.addCell(getSummaryCell("$22", false));

        summaryTable.addCell(getSummaryCell("Grand Total:", true));
        summaryTable.addCell(getSummaryCell("$242", false));

        summaryTable.setSpacingAfter(30);
        document.add(summaryTable);

        // --------------------------------------------------------
        // FOOTER
        // --------------------------------------------------------
        Paragraph footer = new Paragraph(
                "Thank you for your business!",
                new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.ITALIC)
        );
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }

    // Helper method for summary cells
    private PdfPCell getSummaryCell(String text, boolean bold) {
        com.lowagie.text.Font font = new com.lowagie.text.Font(
                com.lowagie.text.Font.HELVETICA,
                12,
                bold ? com.lowagie.text.Font.BOLD : com.lowagie.text.Font.NORMAL
        );
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(bold ? Element.ALIGN_LEFT : Element.ALIGN_RIGHT);
        cell.setPadding(5);
        return cell;
    }
}