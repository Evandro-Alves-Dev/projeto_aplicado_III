package com.senai.pa3.services;

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
import com.senai.pa3.dto.ProductionDTO;
import com.senai.pa3.entities.Production;
import com.senai.pa3.enums.WorkShiftEnum;
import com.senai.pa3.exceptions.ResourceNotFoundException;
import com.senai.pa3.repository.ProductionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;

    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    public ByteArrayInputStream findAll2() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph paragraph = new Paragraph("Relatório de Produção");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(10);
            paragraph.setSpacingBefore(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 1, 1});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Quantidade Planejada", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Quantidade Real", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            List<Production> productions = productionRepository.findAll();

            for (Production production : productions) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(production.getIdProduction().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(production.getPlanQuantity().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingLeft(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(production.getRealQuantity().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            document.add(table);

            document.close();

        } catch (DocumentException ex) {
            System.out.println(ex.getMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Transactional(readOnly = true)
    public List<ProductionDTO> findAll() {
        List<Production> productions = productionRepository.findAll();
        return productions.stream().map(ProductionDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductionDTO findById(Long id) {
        Production production = productionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
        return new ProductionDTO(production);
    }

    @Transactional
    public ProductionDTO insert(ProductionDTO productionDTO) {
        Production production = new Production();
        copyDtoToEntity(productionDTO, production);
        production = productionRepository.save(production);
        return new ProductionDTO(production);
    }

    @Transactional
    public ProductionDTO update(Long id, ProductionDTO productionDTO) {
        try {
            var entity = productionRepository.getOne(id);
            copyDtoToEntityUpdate(productionDTO, entity);
            entity = productionRepository.save(entity);
            return new ProductionDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!productionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado");
        }
        productionRepository.deleteById(id);
    }

    private void copyDtoToEntity(ProductionDTO productionDTO, Production production) {
        production.setPlanQuantity(productionDTO.getPlanQuantity());
        production.setRealQuantity(productionDTO.getRealQuantity());
        production.setUnit(productionDTO.getUnit());
        production.setStartTime(buildFormartDate(productionDTO.getStartTime()));
        production.setFinishTime(buildFormartDate(productionDTO.getFinishTime()));
        production.setDowntime(buildFormartDate(productionDTO.getDowntime()));
        production.setPackageType(productionDTO.getPackageType());
        production.setLabelType(productionDTO.getLabelType());
        production.setEquipment(productionDTO.getEquipment());
        // Turno de trabalho definido automaticamente
        production.setWorkShift(buildWorkShift(productionDTO.getWorkShift()));
        // Lote de produção definido automaticamente
        production.setProductionBatch(buildBatch(production.getWorkShift()));
        production.setBestBefore(productionDTO.getBestBefore());
        production.setNotes(productionDTO.getNotes());
    }

    private void copyDtoToEntityUpdate(ProductionDTO productionDTO, Production production) {
        production.setPlanQuantity(productionDTO.getPlanQuantity());
        production.setRealQuantity(productionDTO.getRealQuantity());
        production.setUnit(productionDTO.getUnit());
        production.setFinishTime(productionDTO.getFinishTime()); //
        production.setDowntime(productionDTO.getDowntime());
        production.setPackageType(productionDTO.getPackageType());
        production.setLabelType(productionDTO.getLabelType());
        production.setBestBefore(productionDTO.getBestBefore());
        production.setNotes(productionDTO.getNotes());
    }

    private String buildWorkShift(String workShift) {
        if (workShift == null || workShift.isEmpty()) {
            return WorkShiftEnum.parseToString(LocalDateTime.now());
        } else {
            return WorkShiftEnum.parse(workShift.toUpperCase());
        }
    }

    private String buildBatch(String workShift) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(inputFormatter), inputFormatter);
        String output = dateTime.format(outputFormatter);

        var batch = "LT" + "*" + output + "*" + workShift;
        return batch;
    }

    private String buildFormartDate(String startTime) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        if (startTime == null || startTime.toString().isBlank()) {
            LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(inputFormatter), inputFormatter);
            String output = dateTime.format(outputFormatter);

            return output;
        } else {
            LocalDateTime dateTime = LocalDateTime.parse(startTime, inputFormatter);
            String output = dateTime.format(outputFormatter);
            return output;
        }
    }
}
