package tech.omeganumeric.sfe.mecef;

public enum CompanyInfo {


    TRADENAME("I0"),
    ADRESSE("I1"),
    CITY("I2"),
    PHONE("I3"),
    EMAIL("I4");
    public final String value;


    CompanyInfo(String label) {
        this.value = label;
    }
}
