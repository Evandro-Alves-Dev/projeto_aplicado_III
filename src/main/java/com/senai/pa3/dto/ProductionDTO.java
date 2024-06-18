
package com.senai.pa3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.senai.pa3.config.CustomLocalDateDeserializer;
import com.senai.pa3.config.CustomLocalDateSerializer;
import com.senai.pa3.entities.Production;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductionDTO implements Serializable {

    private Long idProduction;
    //private Long productIdProduct;
    //private Long userIdUser;

    private Float planQuantity;

    private Float realQuantity;

    private String unit;

    private String startTime;

    private String finishTime;

    private String downtime; // tempo de parada

    private String packageType;

    private String labelType;

    private String equipment;

    private String workShift;// turno de produção

    private String productionBatch;// lote de produção

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate bestBefore;// validade do produto em formato dd-MM-yyyy

    private String notes;// observações

    private Long productId;

    public ProductionDTO(Production entity) {
        idProduction = entity.getIdProduction();
        planQuantity = entity.getPlanQuantity();
        realQuantity = entity.getRealQuantity();
        unit = entity.getUnit();
        startTime = entity.getStartTime();
        finishTime = entity.getFinishTime();
        downtime = entity.getDowntime();  // tempo de parada
        packageType = entity.getPackageType();
        labelType = entity.getLabelType();
        equipment = entity.getEquipment();
        workShift = entity.getWorkShift(); // turno de trabalho
        productionBatch = entity.getProductionBatch(); // lote de produção
        bestBefore = entity.getBestBefore();
        notes = entity.getNotes(); // observações
        productId = entity.getProductId();
    }
}
