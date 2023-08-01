package tech.omeganumeric.sfe.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.omeganumeric.sfe.exception.MethodNotAllowedException;
import tech.omeganumeric.sfe.mecef.Action;
import tech.omeganumeric.sfe.mecef.MecefService;
import tech.omeganumeric.sfe.mecef.responses.MecefReference;
import tech.omeganumeric.sfe.payload.request.Invoice;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/invoice")
@Api(value = "Factures", description = " API pour la gestion des factures.", tags = {"Factures"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private MecefService mecefService;


    /**
     * @param invoice
     * @return l'AIB enregistré
     * @throws MethodNotAllowedException
     */
    @ApiOperation(value = "Enregistrement d'une facture.")
    @PostMapping("/save/{action}")

    public ResponseEntity<MecefReference> save(@PathVariable(name = "action") Action action, @Valid @RequestBody Invoice invoice) throws MethodNotAllowedException {
       try {
           MecefReference mecefReference = mecefService.storeInvoice(invoice, action);
           return ResponseEntity.ok(mecefReference);

       }catch (Exception exception){
           exception.printStackTrace();
           return ResponseEntity.badRequest().body(null);

       }

    }


}
