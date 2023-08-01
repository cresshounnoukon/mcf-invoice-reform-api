package tech.omeganumeric.sfe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.omeganumeric.sfe.exception.MethodNotAllowedException;
import tech.omeganumeric.sfe.mecef.MecefService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mecef")
@Api(value = "MECEF", description = " API pour la gestion des  Mecef.", tags = {"MECEF"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class MecefController {

    @Autowired
    private MecefService mecefService;



    private static final Logger logger = LoggerFactory.getLogger(MecefController.class);

    /**
     * @return la liste des aib
     */
    @ApiOperation(value = "Renvoie les données d'activation sur la machine")
    @GetMapping("")
    public ResponseEntity<?> activationData() throws MethodNotAllowedException {
        try {
            return ResponseEntity.ok(mecefService.checkWithCompanyInfoStatus());
        } catch (Exception e) {
            Map<String, String>  map=new HashMap<>();
            map.put("error", e.getMessage() );
            //logger.error(e.getLocalizedMessage());
            return  ResponseEntity.ok(map);
        }
    }


    /**
     * @return
     * @throws MethodNotAllowedException
     */
    @ApiOperation(value = "Récupère les stats sur l'envoie des fichies")
    @GetMapping("/status-stats")
    public ResponseEntity<?> getStatus() throws MethodNotAllowedException {
        try {
            return ResponseEntity.ok(mecefService.checkMCFServerInfo());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
