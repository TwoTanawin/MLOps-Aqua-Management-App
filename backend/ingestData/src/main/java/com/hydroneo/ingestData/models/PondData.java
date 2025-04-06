package com.hydroneo.ingestData.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PondData {
    private double do_c;
    private double temp;
    private double salinity;
    private double pH;
    private boolean state;
}
