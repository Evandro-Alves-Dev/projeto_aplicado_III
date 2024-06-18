package com.senai.pa3.exceptions.handle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardError implements Serializable {

    private Instant timestamp;
    private Integer status;
    private String message;
    private String path;
}
