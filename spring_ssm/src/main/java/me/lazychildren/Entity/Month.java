package me.lazychildren.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Month {
    private int index;
    private double evaporation;
    private double precipitation;
}
