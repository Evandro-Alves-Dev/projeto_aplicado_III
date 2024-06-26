
package com.senai.pa3.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.senai.pa3.entities.Production;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDTO implements Serializable {

    private Long idProduction;
    private Double planQuantity;
    private Double realQuantity;
    private String unit;
    private String startTime;
    private String finishTime;
    private LocalDateTime startDowntime;
    private LocalDateTime finishDowntime;
    private String packageType;
    private String labelType;
    private String equipment;
    private String workShift;// turno de produção
    private String productionBatch;// lote de produção
    private LocalDate bestBefore;
    private String notes;// observações

    public ProductionDTO(Production entity) {
        idProduction = entity.getIdProduction();
        planQuantity = entity.getPlanQuantity();
        realQuantity = entity.getRealQuantity();
        unit = entity.getUnit();
        startTime = entity.getStartTime();
        finishTime = entity.getFinishTime();
        startDowntime = entity.getStartDowntime();
        finishDowntime = entity.getFinishDowntime(); // tempo de parada
        packageType = entity.getPackageType();
        labelType = entity.getLabelType();
        equipment = entity.getEquipment();
        workShift = entity.getWorkShift(); // turno de trabalho
        productionBatch = entity.getProductionBatch(); // lote de produção
        bestBefore = entity.getBestBefore();
        notes = entity.getNotes(); // observações
    }
}