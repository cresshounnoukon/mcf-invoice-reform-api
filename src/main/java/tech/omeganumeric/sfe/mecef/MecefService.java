package tech.omeganumeric.sfe.mecef;

import com.fazecast.jSerialComm.SerialPort;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tech.omeganumeric.sfe.mecef.requests.OpenSellInvoiceRequest;
import tech.omeganumeric.sfe.mecef.responses.*;
import tech.omeganumeric.sfe.payload.request.Client;
import tech.omeganumeric.sfe.payload.request.Invoice;
import tech.omeganumeric.sfe.payload.request.Item;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Component
public class MecefService {

    private static final Logger logger = LoggerFactory.getLogger(MecefService.class);
    private Company company;
    @Value("${mcf.tax.values}")
    private String taxValues;
    @Value("${mcf.tax.labels}")
    private String taxLabels;
    @Value("${mcf.aib.label}")
    private String aibLabels;
    @Value("${mcf.aib.values}")
    private String aibValues;

    private CommandRunnner commandRunnner;

    private StatusResponse statusResponse;


    private String serialPortName;


    @Value("${sfe.ifu}")
    public String ifu;
    @Value("${sfe.time}")
    public String timer;
    private Client customer;


    public MecefService() {

    }


    public StatusResponse checkStatus() {

            try {

                if (this.statusResponse == null) {
                    System.out.println("******************** CHECK STATUS**************");
                    String response = commandRunnner.executeCommand((byte) 0xC1, null);
                    StatusResponse statusResponse = new StatusResponse();
                    statusResponse.fromString((response));
                    System.out.println("STATUS=>" + statusResponse);
                    if (statusResponse.getCompany().getTaxPayerIdentifier().equals(ifu)) {
                        return statusResponse;
                    } else {
                        this.statusResponse = null;
                    }
                    System.out.println("******************** END CHECK STATUS**************");
                    return this.statusResponse;
                }
            } catch (Exception ignored) {

            }


        return statusResponse;
    }


    public boolean checkIsExpired() {

        try {
            Date expiration = new SimpleDateFormat("yyyy-MM-dd").parse(timer);
            if (new Date().after(expiration)) {
                System.out.println("*********************************");
                System.out.println("VOUS AVEZ EPUISÉ LA PERIODE D'ÉVELUATION");
                System.out.println("*********************************");
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }

    public StatusResponse checkWithCompanyInfoStatus() {



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
            System.out.println("STATUS=>" + statusResponse);


            if (statusResponse.getCompany().getTaxPayerIdentifier().equals(ifu)) {
                return statusResponse;
            } else {

                this.statusResponse = null;
            }

            System.out.println("******************** END CHECK STATUS**************");
            return this.statusResponse;


    }


    private String getCompanyInfo(CompanyInfo companyInfo) {
        return (commandRunnner.executeCommand((byte) 0x2B, companyInfo.value));
    }


    public MecefReference storeInvoice(Invoice invoice, Action action) {
        this.customer = invoice.getClient();
        OpenInvoiceResponse openInvoiceResponse = startInvoice(invoice);
        if (openInvoiceResponse != null) {

            if (invoice.getItems().size() > 0) {
                try {

                for (Item item : invoice.getItems()) {
                    System.out.println("******************** ADD PRODUCT ITEM  **************");


                      System.out.println("ADDING INVOICE PRODUCT ITEM =>" + item.convertToString());
                      String response = commandRunnner.executeCommand((byte) 0x31, (item.convertToString()));
                      AddProductSellResponse addProductSellResponse = new AddProductSellResponse();
                      addProductSellResponse.fromString((response));
                      System.out.println("addProductSellResponse=>" + addProductSellResponse);


                      System.out.println("*******************************************************");

                    // }


                }
            }catch (Exception exception){
                exception.printStackTrace();
            }


            }

            System.out.println("******************** GETTING  SUBTOTAL **************");
            String stb = commandRunnner.executeCommand((byte) 0x33, null);
            SubTotalResponse subTotalResponse = new SubTotalResponse();
            subTotalResponse.fromString((stb));
            System.out.println("before CONVERTING=>" + (stb));
            System.out.println("subTotalResponse=>" + subTotalResponse);

            System.out.println("*******************************************************");

            if (action.equals(Action.CONFIRM)) {
                System.out.println("******************** CONFIRM TOTAL **************");
                String total = commandRunnner.executeCommand((byte) 0x35, null);
                //  MecefReference closeInvoiceResponse = new MecefReference();
                //closeInvoiceResponse.fromString((total));
                //    System.out.println("closeInvoiceResponse=>" +  closeInvoiceResponse.ToString());
                System.out.println("*******************************************************");
            }

            System.out.println("******************** CLOSE INVOICE  TOTAL **************");
            String endInvoice = commandRunnner.executeCommand((byte) 0x38, null);
            MecefReference closeInvoiceResponse = new MecefReference();
            closeInvoiceResponse.fromString(endInvoice, subTotalResponse);
            System.out.println("closeInvoiceResponse=>" + closeInvoiceResponse);

            System.out.println("*******************************************************");
            return closeInvoiceResponse;
        }
        return null;
    }


    public OpenInvoiceResponse startInvoice(Invoice invoice) {
        statusResponse = statusResponse == null ? checkStatus() : statusResponse;
        if (statusResponse != null) {
            try {
                System.out.println("******************** OEPN INVOICE **************");
                OpenSellInvoiceRequest openSellInvoiceRequest = new OpenSellInvoiceRequest();
                openSellInvoiceRequest.setIfu(ifu);
                System.out.println("******************** OK **************");

                openSellInvoiceRequest.setOperator(invoice.getOperator());
                openSellInvoiceRequest.setCustomer(invoice.getClient());
                openSellInvoiceRequest.setTaxGroupString(taxValues.replaceAll("\\|", ","));
                openSellInvoiceRequest.setTypeInvoice(invoice.getType());

                openSellInvoiceRequest.setMecefReference(invoice.getReference());

                System.out.println("******************** OK 2 **************");
                //  String[] split = aibValues.split("\\|");
                //String aib = null;
            /*if (invoice.aib.equals("")) {
                for (String s : split) {
                    if (s.equals(invoice.aib)) {
                        aib = s;
                        break;
                    }

                }
            }
*/
            openSellInvoiceRequest.setAib(invoice.getAib());

                System.out.println("********************" + openSellInvoiceRequest + " **************");
                System.out.println("OPEN INVOICE ASCII DATA =>" + openSellInvoiceRequest.convertToString());
                System.out.println("******************** OK 3 **************");
                String response = commandRunnner.executeCommand((byte) 0xC0, openSellInvoiceRequest.convertToString());
                OpenInvoiceResponse openInvoiceResponse = new OpenInvoiceResponse();
                System.out.println("openInvoiceResponseS String=>" + (response).trim());

                openInvoiceResponse.fromString((response).trim());
                System.out.println("openInvoiceResponse=>" + openInvoiceResponse);

                System.out.println("********************  END OPEN INVOICE **************");

                return openInvoiceResponse;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("statusResponse=> is null");

            return null;
        }


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




 


}
