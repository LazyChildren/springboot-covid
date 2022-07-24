package me.lazychildren.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detail {
    private int year;
    private int month;
    private int date;
    private int newnum;
    private int totalnum;
}
