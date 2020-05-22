package Service;

import Controller.RepresentativeAssociationController;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.QualificationJudge;
import Model.LoggerHandler;
import Service.OutSystems.ProxyAssociationAccountingSystem;
import Service.OutSystems.ProxyTaxSystem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.logging.Level;

@RestController
public class RepresentativeAssociationService {
    private RepresentativeAssociationController representativeAssociationController;
    private LoggerHandler loggerHandler;
    private ProxyTaxSystem proxyTaxSystem;
    private ProxyAssociationAccountingSystem proxyAssociationAccountingSystem;

    public RepresentativeAssociationService() {
        this.representativeAssociationController = new RepresentativeAssociationController();
        this.loggerHandler = new LoggerHandler(TeamOwnerService.class.getName());
        this.proxyTaxSystem=new ProxyTaxSystem();
        this.proxyAssociationAccountingSystem=new ProxyAssociationAccountingSystem();
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
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: League \"" + leagueName + "\" was created");
        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: League \"" + leagueName + "\" wasn't created because: " + e.getMessage());
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
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: Season \"" + seasonName + "\" was created");
        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: Season \"" + seasonName + "\" wasn't created because: " + e.getMessage());
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
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: SeasonLeague \"" + seasonName + "\" \"" + leagueName + "\" was created");
        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: SeasonLeague \"" + seasonName + "\" \"" + leagueName + "\" wasn't created because: " + e.getMessage());
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
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + username + "\" was created");
        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + username + "\" wasn't created because: " + e.getMessage());
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
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + judgeEmailAddress + "\" was removed");
        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + judgeEmailAddress + "\" wasn't removed because: " + e.getMessage());
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
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + judgeEmailAddress + "\" was associated with SeasonLeague \"" + seasonLeagueName + "\"");
        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: Judge \"" + judgeEmailAddress + "\" was associated with SeasonLeague \"" + seasonLeagueName + "\" because: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Will receive from the UI the seasonLeague's name, policy-calculateLeaguePoints,
     * want to set Policy CalculateLeaguePoints of the SeasonLeague.
     * Will continue to Controller.
     *
     * @param representativeAssociationEmailAddress-username/emailAddress of the online RepresentativeAssociation.
     * @param leagueName-name                                       of SeasonLeague.
     * @param calculateLeaguePoints-Policy                                CalculateLeaguePoints.
     * @throws Exception
     */
    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "representativeAssociationService/{representativeAssociationEmailAddress}/{leagueName}/{season}/{calculateLeaguePoints}")
    @ResponseStatus(HttpStatus.OK)
    public void changeCalculateLeaguePointsPolicy(@PathVariable String representativeAssociationEmailAddress, @PathVariable String leagueName, @PathVariable String calculateLeaguePoints, @PathVariable String season) throws Exception {
        try {
            //todo
            CalculateLeaguePoints calculateLeaguePointsToMake= CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0;
            String seasonLeagueName="";
            if (representativeAssociationEmailAddress == null) {
                throw new Exception("Only RepresentativeAssociation has permissions to this action!");
            }
            representativeAssociationController.changeCalculateLeaguePointsPolicy(representativeAssociationEmailAddress, seasonLeagueName, calculateLeaguePointsToMake);
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + representativeAssociationEmailAddress + " Description: CalculateLeaguePoints \"" + calculateLeaguePoints + "\"  was changed to SeasonLeague \"" + seasonLeagueName + "\"");
        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + representativeAssociationEmailAddress + " Description: CalculateLeaguePoints \"" + calculateLeaguePoints + "\"  wasn't changed to SeasonLeague \"" + "seasonLeagueName" + "\" because: " + e.getMessage()); // todo - seasonLeagueName put as String
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

    public void changeGameDate(String repMail, Date newDate, String gameID) throws Exception {
        if(repMail.isEmpty() || gameID.isEmpty()){
            throw new Exception("The value is empty");
        }
        if (newDate == null){
            throw new NullPointerException("bad input");
        }
        representativeAssociationController.changeGameDate(repMail, newDate, gameID);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + repMail + " Description: Game Date \"" + newDate + "\"  was changed to the game \"" + gameID + "\"");
    }

    public void changeGameLocation(String repMail, String newLocation, String gameID) throws Exception {
        if(repMail.isEmpty() || gameID.isEmpty() || newLocation.isEmpty()){
            throw new Exception("The value is empty");
        }
        representativeAssociationController.changeGameLocation(repMail, newLocation, gameID);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + repMail + " Description: Game Location \"" + newLocation + "\"  was changed to the game \"" + gameID + "\"");
    }

    /**
     * connection to the Tax system with the Proxy
     * @param serverhost
     */
    public void connectToTaxSystem(String serverhost){
        try {
            proxyTaxSystem.connectTo(serverhost);
        } catch (Exception e) {
            System.out.println("something was wrong with tax system connecting");
        }
    }

    /**
     * connection to the Association Accounting system with the Proxy
     * @param serverhost
     */
    public void connectToAssociationAccountingSystem(String serverhost){
        try {
            proxyAssociationAccountingSystem.connectTo(serverhost);
        } catch (Exception e) {
            System.out.println("something was wrong with Association Accounting System connecting");
        }
    }

    /**
     *
     * @param revenueAmount
     * @return the tax rate from the Tax system proxy
     */
    public double getTaxRate(double revenueAmount){
        return proxyTaxSystem.getTaxRate(revenueAmount);
    }

    /**
     *
     * @param teamName
     * @param date
     * @param amount
     * @return if the payment operation has done successfully
     */
    public boolean addPayment(String teamName, String date, double amount){
        if(teamName==null||date==null)
            return false;
        return proxyAssociationAccountingSystem.addPayment(teamName,date,amount);
    }
}

