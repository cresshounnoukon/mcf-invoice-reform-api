/*
package tech.omeganumeric.sfe.mecef;

import com.fazecast.jSerialComm.SerialPort;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tech.omeganumeric.sfe.mecef.requests.OpenSellInvoiceRequest;
import tech.omeganumeric.sfe.mecef.responses.*;
import tech.omeganumeric.sfe.model.*;
import tech.omeganumeric.sfe.repository.TouristTaxRepository;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Component
public class MecefServiceOld extends MecefServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(MecefServiceOld.class);
    private Company company;
    private List<TaxGroup> taxGroups;

    private CommandRunnner commandRunnner;

    private StatusResponse statusResponse;


    private String serialPortName;
    @Autowired
    private TouristTaxRepository touristTaxRepository;


    @Value("${sfe.ifu}")
    public String ifu;
    @Value("${sfe.time}")
    public String timer;
    private Customer customer;
    private InvoiceOperation invoiceOperation;

    public MecefServiceOld() {

    }


    public StatusResponse checkStatus() {
        if (checkIsExpired() == false) {
        try {

                if (this.statusResponse == null) {


                    System.out.println("******************** CHECK STATUS**************");


                    String response = commandRunnner.executeCommand((byte) 0xC1, null);
                    StatusResponse statusResponse = new StatusResponse();
                    statusResponse.fromString((response));


                    System.out.println("STATUS=>" + statusResponse.toString());


                    if (statusResponse.getCompany().getTaxPayerIdentifier().equals(ifu)) {
                        return statusResponse;
                    } else {
                        this.statusResponse = null;
                    }

                    System.out.println("******************** END CHECK STATUS**************");
                    return this.statusResponse;
                }

        } catch (Exception e) {

        }
    }

        return statusResponse;
    }


    public boolean checkIsExpired() {

        try {
            Date expiration = new SimpleDateFormat("yyyy-MM-dd").parse(timer);
            if (!expiration.after(new Date())) {
                System.out.println("*********************************");
                System.out.println("VOUS AVEZ EPUISÉ LA PERIODE D'ÉVELUATION");
                System.out.println("*********************************");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public StatusResponse checkWithCompanyInfoStatus() {
        if (checkIsExpired() == false) {


            System.out.println("******************** CHECK STATUS**************");
            String response = commandRunnner.executeCommand((byte) 0xC1, null);
            StatusResponse statusResponse = new StatusResponse();
            statusResponse.fromString((response));

            company = statusResponse.getCompany();

            company.setTradeName(getCompanyInfo(CompanyInfo.TRADENAME));
            company.setAdresse(getCompanyInfo(CompanyInfo.ADRESSE));
            company.setCity(getCompanyInfo(CompanyInfo.CITY));
            company.setPhone(getCompanyInfo(CompanyInfo.PHONE));
            company.setEmail(getCompanyInfo(CompanyInfo.EMAIL));
            System.out.println("STATUS=>" + statusResponse.toString());


            if (statusResponse.getCompany().getTaxPayerIdentifier().equals(ifu)) {
                return statusResponse;
            } else {

                this.statusResponse = null;
            }

            System.out.println("******************** END CHECK STATUS**************");
            return this.statusResponse;


        }
        return null;
    }


    private String getCompanyInfo(CompanyInfo companyInfo) {
        String response = commandRunnner.executeCommand((byte) 0x2B, companyInfo.value);
        return (response);
    }

    public void getFiscaleGroupTax() {
    }

    public MecefReference storeInvoice(Invoice invoice) {
        this.customer = invoice.getCustomer();
        this.invoiceOperation = invoice.getInvoiceOperation();
        company = invoice.getCompany();
        OpenInvoiceResponse openInvoiceResponse = startInvoice(invoice);
        if (openInvoiceResponse != null) {

            if (invoice.getInvoiceItems() != null) {
                System.out.println("invoice = " + invoice.getInvoiceItems().size());
                final int[] i = {0};
                final InvoiceItem[] p = new InvoiceItem[1];
                invoice.getInvoiceItems().forEach(invoiceItem -> {

                    if ( invoiceItem.getPriceRepertory().getTouristTax()!=null) {
                       // p[0] = invoiceItem;
                       */
/* System.out.println("p[0].getRealPrice() = " + p[0].getRealPrice());

                        int realPrice = p[0].getRealPrice();

                        int realPriceHt = determineHorTax(realPrice);
                        System.out.println("realPriceHt = " + realPriceHt);


                        //Optional<TouristTax> priceOptional = touristTaxRepository.obtainRealTourisTax(realPriceHt);

                        Optional<TouristTax> touristTax1 = touristTaxRepository.obtainRealTourisTax(realPriceHt);
                        if (touristTax1.isPresent()) {
                            System.out.println("touristTax1.get() = " + touristTax1.get());*//*



                            System.out.println("ADDING INVOICE PRODUCT ITEM =>" +invoiceItem.convertToString(detecTaxGroupDependOfCompanyOrCustomer(), String.valueOf(  invoiceItem.getPriceRepertory().getTouristTax()),true));

                            String response = commandRunnner.executeCommand((byte) 0x31, p[0].convertToString(detecTaxGroupDependOfCompanyOrCustomer(), String.valueOf(  invoiceItem.getPriceRepertory().getTouristTax()),true));
                            AddProductSellResponse addProductSellResponse = new AddProductSellResponse();
                            addProductSellResponse.fromString((response));
                            System.out.println("addProductSellResponse=>" + addProductSellResponse.toString());

                            // invoiceItem.setOtherPrice((int) (invoiceItem.getPriceRepertory().getSalePrice()-touristTax1.get().getTaxAmount()));
                            // invoiceItem.setOtherPrice(realPriceHt);
                        }

                      //  System.out.println("invoiceItem = " + invoiceItem.getOtherPrice());

                  //      invoiceItem.getPriceRepertory().getTaxGroup().setGroupeCode('B');

                       */
/* System.out.println("******************** ADD PRODUCT ITEM " + i[0] + "**************");

                        System.out.println("ADDING INVOICE PRODUCT ITEM =>" + invoiceItem.convertToString(detecTaxGroupDependOfCompanyOrCustomer(), null,false));
                        String response = commandRunnner.executeCommand((byte) 0x31, invoiceItem.convertToString(detecTaxGroupDependOfCompanyOrCustomer(), null,false));
                        AddProductSellResponse addProductSellResponse = new AddProductSellResponse();
                        addProductSellResponse.fromString((response));
                        System.out.println("addProductSellResponse=>" + addProductSellResponse.toString());

                        invoiceItem.getPriceRepertory().getTaxGroup().setGroupeCode('F');
                        System.out.println("*******************************************************");*//*


                   // } else {

                        System.out.println("******************** ADD PRODUCT ITEM " + i[0] + "**************");

                        System.out.println("ADDING INVOICE PRODUCT ITEM =>" + invoiceItem.convertToString(detecTaxGroupDependOfCompanyOrCustomer(), null,false));
                        String response = commandRunnner.executeCommand((byte) 0x31, invoiceItem.convertToString(detecTaxGroupDependOfCompanyOrCustomer(), null,false));
                        AddProductSellResponse addProductSellResponse = new AddProductSellResponse();
                        addProductSellResponse.fromString((response));
                        System.out.println("addProductSellResponse=>" + addProductSellResponse.toString());


                        System.out.println("*******************************************************");

                   // }


                   // i[0]++;

                });


            }

            System.out.println("******************** GETTING  SUBTOTAL **************");
            String stb = commandRunnner.executeCommand((byte) 0x33, null);
            SubTotalResponse subTotalResponse = new SubTotalResponse();
            subTotalResponse.fromString((stb));
            System.out.println("before CONVERTING=>" + (stb));
            System.out.println("subTotalResponse=>" + subTotalResponse.toString());

            System.out.println("*******************************************************");


            System.out.println("******************** CONFIRM TOTAL **************");
            String total = commandRunnner.executeCommand((byte) 0x35, null);
            //  MecefReference closeInvoiceResponse = new MecefReference();
            //closeInvoiceResponse.fromString((total));
            //    System.out.println("closeInvoiceResponse=>" +  closeInvoiceResponse.ToString());
            System.out.println("*******************************************************");

            System.out.println("******************** CLOSE INVOICE  TOTAL **************");
            String endInvoice = commandRunnner.executeCommand((byte) 0x38, null);
            MecefReference closeInvoiceResponse = new MecefReference();
            closeInvoiceResponse.fromString(endInvoice, subTotalResponse);
            System.out.println("closeInvoiceResponse=>" + closeInvoiceResponse.toString());

            System.out.println("*******************************************************");
            return closeInvoiceResponse;
        }
        return null;
    }


    public OpenInvoiceResponse startInvoice(Invoice invoice) {
        statusResponse = statusResponse == null ? checkStatus() : statusResponse;
        if (statusResponse != null) {
            System.out.println("******************** OEPN INVOICE **************");
            OpenSellInvoiceRequest openSellInvoiceRequest = new OpenSellInvoiceRequest();
            openSellInvoiceRequest.setUser(invoice.getUser());
            openSellInvoiceRequest.setCompany(statusResponse.getCompany());
            openSellInvoiceRequest.setTaxGroups(statusResponse.getTaxGroups());
            openSellInvoiceRequest.setInvoiceOperation(invoice.getInvoiceOperation());
            if (invoice.getParent() != null) {
                openSellInvoiceRequest.setMecefReference(invoice.getParent().getMecefReference());
            }
            openSellInvoiceRequest.setCustomer(invoice.getCustomer());

            System.out.println("OPEN INVOICE ASCII DATA =>" + openSellInvoiceRequest.convertToString());
            String response = commandRunnner.executeCommand((byte) 0xC0, openSellInvoiceRequest.convertToString());
            OpenInvoiceResponse openInvoiceResponse = new OpenInvoiceResponse();
            System.out.println("openInvoiceResponseS String=>" + (response).trim());

            openInvoiceResponse.fromString((response).trim());
            System.out.println("openInvoiceResponse=>" + openInvoiceResponse.toString());

            System.out.println("********************  END OPEN INVOICE **************");
            return openInvoiceResponse;
        } else {
            System.out.println("statusResponse=> is null");

        }

        return null;

    }

    public MCFServerResponse checkMCFServerInfo() {
        String response = commandRunnner.executeCommand((byte) 0xc2, null);
        MCFServerResponse mcfServerResponse = new MCFServerResponse();
        mcfServerResponse.fromString((response).trim());
        System.out.println("mcfServerResponse=>" + (response).trim());
        return mcfServerResponse;
    }

    public void confirmInvoiceSubtotal() {
    }

    public void getInvoiceQrCode() {
    }


    private String decodeResponse(byte[] response) {
        return null;
    }

    @PostConstruct
    public void init() {

        if (checkRightSerialPort()) {
            commandRunnner = new CommandRunnner(serialPortName);
        }

    }


    public boolean checkRightSerialPort() {
        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length == 0) {
            logger.warn("No serial ports available!");
            return false;
        }
        logger.debug("Got {} serial ports available", ports.length);
        int portToUse = -1;

        for (int i = 0; i < ports.length; i++) {
            SerialPort sp = ports[i];
            System.out.println("sp.getSystemPortName() = " + sp.getSystemPortName());
            logger.debug("\t- {}, {}", sp.getSystemPortName(), sp.getDescriptivePortName());
            if (isSerialPort(sp)) {
                portToUse = i;
            }
        }
        if (portToUse < 0) {
            logger.warn("No relevant serial usb found on this system!");
            return false;
        }
        serialPortName = ports[portToUse].getSystemPortName();
        logger.info("Going to use the following port: {}", ports[portToUse].getSystemPortName());

        //     comPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        //   comPort.setComPortParameters(BAUD_RATE, 8,This port appears to have been shutdown or disconnected
        //     SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
//        comPort.setComPortTimeouts(
//                SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING,
//                TIMEOUT_READ,
//                TIMEOUT_WRITE
//        );

        logger.debug("Going to open the port...");
        boolean result = true;
        logger.debug("Port opened? {}", result);
        return result;
    }


    private boolean isSerialPort(SerialPort sp) {
        String portName = sp.getSystemPortName().toLowerCase();
        System.out.println("portName = " + portName);
        String portDesc = sp.getDescriptivePortName().toLowerCase();
        System.out.println("portDesc = " + portDesc);
        return (SystemUtils.IS_OS_MAC_OSX && portName.startsWith("cu") && portName.contains("usbserial") ||
                SystemUtils.IS_OS_MAC_OSX && portName.startsWith("tty") && portName.contains("usbmodem") ||
                SystemUtils.IS_OS_MAC_OSX && portName.startsWith("cu.hc-0") ||  // Bluetooth uart on Mac
                SystemUtils.IS_OS_WINDOWS && portDesc.contains("serial") ||
                SystemUtils.IS_OS_WINDOWS && portName.startsWith("com") ||
                SystemUtils.IS_OS_WINDOWS && portDesc.contains("hc-0") || // Bluetooth uart on Win
                SystemUtils.IS_OS_LINUX && portDesc.contains("usb") && portDesc.contains("serial") ||
                SystemUtils.IS_OS_LINUX && portDesc.contains("hc-0") || // Bluetooth uart on Linux?
                SystemUtils.IS_OS_LINUX && portName.startsWith("tty") || //  on Linux?
                portDesc.contains("pmsensor")   // TODO make the name configurable (custom name for BT HC-05/HC-06 or even normal serial)
        );
    }


    public String detecTaxGroupDependOfCompanyOrCustomer() {


        if (company.getRegimeFiscal().equals(RegimeFiscal.TPS)) {
            return "E";
        } else {
            if (customer != null) {
                if (customer.getRegimeFiscal() != null) {
                    if (customer.getRegimeFiscal().equals(RegimeFiscal.EXCEPTION)) {
                        return "D";
                    }
                }
            }
        }


        if (invoiceOperation.getValue().equals("EV") || invoiceOperation.getValue().equals("EA")) {
            return "C";
        }

        return null;
    }


}
*/
