package com.neuedu.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brief {
    private int totalnum;
    private int newnum;
    private int totalcure;
    private int newcure;
    private int totaldead;
    private int newdead;
}
