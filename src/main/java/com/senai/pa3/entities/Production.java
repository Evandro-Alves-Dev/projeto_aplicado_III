package com.senai.pa3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_production")
public class Production implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduction;
    private Float planQuantity;
    private Float realQuantity;
    private String unit;
    private String startTime;
    private String finishTime;
    private String downtime; // tempo de parada
    private String packageType;
    private String labelType;
    private String equipment;
    private String workShift;   // turno de produção
    private String productionBatch; // lote de produção
    private LocalDate bestBefore;  // validade do produto
    private String notes;   // observações
    private Long productId;
}
