package Service;

import Controller.NotificationController;
import Controller.RepresentativeAssociationController;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.QualificationJudge;
import Model.Game;
import Model.LoggerHandler;
import Model.SeasonLeague;
import Service.OutSystems.IAssociationAccountingSystem;
import Service.OutSystems.ITaxSystem;
import Service.OutSystems.ProxyAssociationAccountingSystem;
import Service.OutSystems.ProxyTaxSystem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class RepresentativeAssociationService {
    private Logger logger = Logger.getLogger(RepresentativeAssociationService.class.getName());
    private RepresentativeAssociationController representativeAssociationController;
//    private LoggerHandler loggerHandler;
    private LoggerHandler loggerHandler;
    private ITaxSystem proxyTaxSystem;
    private IAssociationAccountingSystem proxyAssociationAccountingSystem;
    private NotificationController notificationController = NotificationController.getInstance();

    public RepresentativeAssociationService() {
        this.representativeAssociationController = new RepresentativeAssociationController();
        notificationController.setRepControll(this.representativeAssociationController);
//        this.loggerHandler = new LoggerHandler(TeamOwnerService.class.getName());
        logger.addHandler(LoggerHandler.loggerErrorFileHandler);
        logger.addHandler(LoggerHandler.loggerEventFileHandler);
        this.proxyTaxSystem=ProxyTaxSystem.getInstance();
        this.proxyAssociationAccountingSystem=ProxyAssociationAccountingSystem.getInstance();
    }

    /**
     * Will receive from the UI the league's name, want to create League.
     * Will continue to Controller.
     *
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param leagueName-name                                             of the new League.
     * @throws Exception-if details are incorrect.
     */
    public void createLeague(String representativeAssociationEmailAddress, String leagueName) throws Exception {
        try {
            if (representativeAssociationEmailAddress == null) {
                throw new Exception("Only RepresentativeAssociation has permissions to this action!");
            }
            if (leagueName == null) {
                throw new NullPointerException("One or more of the League details incorrect");
            }
            representativeAssociationController.createLeague(representativeAssociationEmailAddress, leagueName);
            logger.log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: League \"" + leagueName + "\" was created");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: League \"" + leagueName + "\" wasn't created because: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Will receive from the UI the season's name, want to create Season.
     * Will continue to Controller.
     *
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param seasonName-name                                             of the new Season.
     * @throws Exception-if details are incorrect.
     */
    public void createSeason(String representativeAssociationEmailAddress, String seasonName) throws Exception {
        try {
            if (representativeAssociationEmailAddress == null) {
                throw new Exception("Only RepresentativeAssociation has permissions to this action!");
            }
            if (seasonName == null) {
                throw new NullPointerException("One or more of the Season details incorrect");
            }
            representativeAssociationController.createSeason(representativeAssociationEmailAddress, seasonName);
            logger.log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: Season \"" + seasonName + "\" was created");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: Season \"" + seasonName + "\" wasn't created because: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Will receive from the UI the league's name the season's name the policy-calculateLeaguePoints and the policy-inlayGames, create SeasonLeague.
     * Will continue to Data.
     *
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param leagueName-name                                             of the League.
     * @param seasonName-name                                             of the Season.
     * @param calculateLeaguePoints-Policy                                CalculateLeaguePoints.
     * @param inlayGames-Policy                                           InlayGames.
     * @throws Exception-if details are incorrect.
     */
    public void createSeasonLeague(String representativeAssociationEmailAddress, String leagueName, String seasonName, CalculateLeaguePoints calculateLeaguePoints, InlayGames inlayGames) throws Exception {
        try {
            if (representativeAssociationEmailAddress == null) {
                throw new Exception("Only RepresentativeAssociation has permissions to this action!");
            }
            if (leagueName == null || seasonName == null || calculateLeaguePoints == null || inlayGames == null) {
                throw new NullPointerException("One or more of the SeasonLeague details incorrect");
            }
            representativeAssociationController.createSeasonLeague(representativeAssociationEmailAddress, leagueName, seasonName, calculateLeaguePoints, inlayGames);
            logger.log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: SeasonLeague \"" + seasonName + "\" \"" + leagueName + "\" was created");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: SeasonLeague \"" + seasonName + "\" \"" + leagueName + "\" wasn't created because: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Will receive from the Service the judge's details, want to create Judge.
     * Will continue to Data.
     *
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param username-username                                           of the new Judge.
     * @param password-password                                           of the new Judge.
     * @param id-id                                                       of the new Judge.
     * @param firstName-firstName                                         of the new Judge.
     * @param lastName-lastName                                           of the new Judge.
     * @param qualificationJudge-qualification                            of the new Judge.
     *                                                                    //     * @param judgeType-type                                              of the new Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudge(String representativeAssociationEmailAddress, String username, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) throws Exception {
        try {
            if (representativeAssociationEmailAddress == null) {
                throw new Exception("Only RepresentativeAssociation has permissions to this action!");
            }
            if (username == null || password == null || id == null || firstName == null || lastName == null || qualificationJudge == null) {
                throw new NullPointerException("One or more of the Judge details incorrect");
            }
            representativeAssociationController.createJudge(representativeAssociationEmailAddress, username, password, id, firstName, lastName, qualificationJudge);
            logger.log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + username + "\" was created");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + username + "\" wasn't created because: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Will receive from the UI the judge's id, want to remove Judge.
     * Will continue to Controller.
     *
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param judgeEmailAddress-emailAddress                              of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void removeJudge(String representativeAssociationEmailAddress, String judgeEmailAddress) throws Exception {
        try {
            if (representativeAssociationEmailAddress == null) {
                throw new Exception("Only RepresentativeAssociation has permissions to this action!");
            }
            if (judgeEmailAddress == null) {
                throw new NullPointerException("One or more of the Judge details incorrect");
            }
            representativeAssociationController.removeJudge(representativeAssociationEmailAddress, judgeEmailAddress);
            logger.log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + judgeEmailAddress + "\" was removed");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + judgeEmailAddress + "\" wasn't removed because: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Will receive from the UI the seasonLeague's name and the judge's emailAddress, want to create JudgeSeasonLeague.
     * Will continue to Controller.
     *
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param seasonLeagueName-name                                       of the SeasonLeague.
     * @param judgeEmailAddress-emailAddress                              of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(String representativeAssociationEmailAddress, String seasonLeagueName, String judgeEmailAddress) throws Exception {
        try {
            if (representativeAssociationEmailAddress == null) {
                throw new Exception("Only RepresentativeAssociation has permissions to this action!");
            }
            if (seasonLeagueName == null || judgeEmailAddress == null) {
                throw new NullPointerException("One or more of the JudgeSeasonLeague details incorrect");
            }
            representativeAssociationController.createJudgeSeasonLeague(representativeAssociationEmailAddress, seasonLeagueName, judgeEmailAddress);
            logger.log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + judgeEmailAddress + "\" was associated with SeasonLeague \"" + seasonLeagueName + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + judgeEmailAddress + "\" was not associated with SeasonLeague \"" + seasonLeagueName + "\" because: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Will receive from the UI the seasonLeague's name, policy-calculateLeaguePoints,
     * want to set Policy CalculateLeaguePoints of the SeasonLeague.
     * Will continue to Controller.
     *
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param changeLeagueSeason-name                                       of SeasonLeague.
     * @param policyChosen-Policy                                CalculateLeaguePoints.
     * @throws Exception
     */
    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping(value = "changeCalculateLeaguePointsPolicy/{representativeAssociationEmailAddress}/{changeLeagueSeason}/{policyChosen}")
    @ResponseStatus(HttpStatus.OK)
    public void changeCalculateLeaguePointsPolicy(@PathVariable String representativeAssociationEmailAddress, @PathVariable String changeLeagueSeason, @PathVariable String policyChosen) throws Exception {

            String seasonLeagueName = changeLeagueSeason;
            try {
                CalculateLeaguePoints calculateLeaguePointsToMake= CalculateLeaguePoints.valueOf(policyChosen);
                if (representativeAssociationEmailAddress == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only RepresentativeAssociation has permissions to this action!");
                }
                representativeAssociationController.changeCalculateLeaguePointsPolicy(representativeAssociationEmailAddress, seasonLeagueName, calculateLeaguePointsToMake);
                logger.log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: CalculateLeaguePoints \"" + policyChosen + "\"  was changed to SeasonLeague \"" + seasonLeagueName + "\"");
            } catch (Exception e) {
                logger.log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: CalculateLeaguePoints \"" + policyChosen + "\"  wasn't changed to SeasonLeague \"" + seasonLeagueName + "\" because: " + e.getMessage()); // todo - seasonLeagueName put as String
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
    }

    /**
     * Getter for the RepresentativeAssociationController.
     *
     * @return the RepresentativeAssociationController.
     */
    public RepresentativeAssociationController getRepresentativeAssociationController() {
        return representativeAssociationController;
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "changeGameDate/{repMail}/{changeGameDate}/{gameID}")
    @ResponseStatus(HttpStatus.OK)
    public void changeGameDate(@PathVariable String repMail, @PathVariable String changeGameDate, @PathVariable String gameID) throws Exception {
        if(repMail.isEmpty() || gameID.isEmpty() || changeGameDate.isEmpty()){
            throw new Exception("The value is empty");
        }
        try {
            Date newDate;
            try {
//                newDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(changeGameDate);
                newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(changeGameDate);
            }
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "put valid input date");
            }
            representativeAssociationController.changeGameDate(repMail, newDate, gameID);

            logger.log(Level.INFO, "Created by: " + repMail + " Description: Game Date \"" + newDate + "\"  was changed to the game \"" + gameID + "\"");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "changeGameLocation/{repMail}/{newLocation}/{gameID}")
    @ResponseStatus(HttpStatus.OK)
    public void changeGameLocation(@PathVariable String repMail, @PathVariable String newLocation, @PathVariable String gameID) throws Exception {
        try {
            if (repMail.isEmpty() || gameID.isEmpty() || newLocation.isEmpty()) {
                throw new Exception("The value is empty");
            }
            representativeAssociationController.changeGameLocation(repMail, newLocation, gameID);
            logger.log(Level.INFO, "Created by: " + repMail + " Description: Game Location \"" + newLocation + "\"  was changed to the game \"" + gameID + "\"");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     *
     * @param revenueAmount
     * @return the tax rate from the Tax system proxy
     */
    public double getTaxRate(double revenueAmount,String repMail){
        logger.log(Level.INFO, "Created by: " + repMail + " Description: Get Tax Rate");
        return proxyTaxSystem.getTaxRate(revenueAmount);
    }

    /**
     *
     * @param teamName
     * @param date
     * @param amount
     * @return if the payment operation has done successfully
     */
    public boolean addPayment(String teamName, String date, double amount,String repMail){
        if(teamName==null||date==null) {
            logger.log(Level.WARNING,"Created by: " + repMail + " Description: the payment not added!");
            return false;
        }
        logger.log(Level.INFO, "Created by: " + repMail + " Description: Add Payment has done successfully");
        return proxyAssociationAccountingSystem.addPayment(teamName,date,amount);
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "getSeasonLeague/")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, String> getSeasonLeague(){
        try{
            List<SeasonLeague> seasonLeagueList = representativeAssociationController.getAllSeasonLeague();
            Map <Integer, String> seasonLeagueListNames= new HashMap<>();
            for(int i = 0; i<seasonLeagueList.size() ;i++){
                seasonLeagueListNames.put(i,seasonLeagueList.get(i).getSeasonLeagueName());
            }
            return seasonLeagueListNames;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "getAllPolicy/")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, String> getAllPolicy(){
        try{
            Map <Integer, String> policyList= new HashMap<>();
            List<String> policyNames = Stream.of(CalculateLeaguePoints.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            for(int i = 0; i<policyNames.size() ;i++){
                policyList.put(i,policyNames.get(i));
            }
            return policyList;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "getAllGames/")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer , Game> getAllGames(){
        try{
            List<Game> gamesList = representativeAssociationController.getAllGames();
            Map <Integer, Game> games= new HashMap<>();
            for(int i = 0; i<gamesList.size() ;i++){
                games.put(i,gamesList.get(i));
            }
            return games;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "getAllLocation/")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer , String> getAllLocation(){
        try{
            List<String> locationsList = representativeAssociationController.getAllLocation();
            Map <Integer, String> locations= new HashMap<>();
            for(int i = 0; i<locationsList.size() ;i++){
                locations.put(i,locationsList.get(i));
            }
            return locations;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

