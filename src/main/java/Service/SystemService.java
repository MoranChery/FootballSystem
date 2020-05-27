package Service;

import Controller.SystemController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SystemService{

    private static SystemController systemController;

    public SystemService(){
        systemController = SystemController.getInstance();
        systemController.connectToAssociationAccountingSystem("AssociationAccountingSystemPort");
        systemController.connectToTaxSystem("TaxSystemPort");
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "isConnectionToExternalSystems")
    @ResponseStatus(HttpStatus.OK)
    public void isConnectionToExternalSystems(){
        try {
            systemController.isConnectionToExternalSystems();
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY , e.getMessage());

        }
    }

}
