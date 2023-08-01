package tech.omeganumeric.sfe.payload.request;

import lombok.Data;

@Data
public class Payment {
    private String name;
    private int amount;
}
