package tech.omeganumeric.sfe.mecef.responses;

import lombok.Data;


@Data


public class Company   {

    private  Long id;

    private String taxPayerIdentifier;
    private String tradeName;

    private String deviceNo;
    private String adresse;
    private String city;
    private String phone;
    private String email;



    public Company(String deviceNo, String taxPayerIdentifier) {
        this.taxPayerIdentifier = taxPayerIdentifier;
        this.deviceNo = deviceNo;
    }

    public Company() {
    }


}
