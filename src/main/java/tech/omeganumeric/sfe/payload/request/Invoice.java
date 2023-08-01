package tech.omeganumeric.sfe.payload.request;

import lombok.Data;
import tech.omeganumeric.sfe.payload.request.Client;
import tech.omeganumeric.sfe.payload.request.Item;
import tech.omeganumeric.sfe.payload.request.Operator;
import tech.omeganumeric.sfe.payload.request.Payment;

import java.util.HashSet;
import java.util.Set;

@Data
public class Invoice{
    private String ifu;
    private String aib;
    private String type;
    private Set<Item> items= new HashSet<>();
    private Client client;
    private Operator operator;
    private Set<Payment> payments= new HashSet<>();
    private String reference;
}

