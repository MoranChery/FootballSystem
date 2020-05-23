package Data;

import Model.Court;
import Model.Enums.FinancialActivityType;
import Model.Enums.PermissionType;
import Model.Enums.TeamStatus;
import Model.FinancialActivity;
import Model.Team;
import Model.TeamPage;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamDbInMemory implements TeamDb {
    /*structure like the DB of teams*/
    private Map<String, Team> teams;

    public TeamDbInMemory() {
        teams = new HashMap<>();
    }

    private static TeamDbInMemory ourInstance = new TeamDbInMemory();

    public static TeamDbInMemory getInstance() {
        return ourInstance;
    }

    @Override
    public void insertTeam(String teamName) throws Exception {

    }

    /**
     * create team in DB
     * @param teamName
     * @param budget
     * @param active
     * @throws Exception
     */
    @Override
    public void insertTeam(String teamName, Double budget, TeamStatus active) throws Exception {
        if(teamName == null) {
            throw new NullPointerException("bad input");
        }
        if(teams.containsKey(teamName)) {
            throw new Exception("Team already exist in the system");
        }
        Team team = new Team();
        team.setTeamName(teamName);
        teams.put(teamName, team);
    }

    /**
     * "pull" the team from DB
     * @param teamName
     * @return
     * @throws Exception
     */
    @Override
    public Team getTeam(String teamName) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new NotFoundException("Team not found");
        }
        return team;
    }

    /**
     * add the player to the team in DB
     * @param teamName
     * @param player
     * @throws Exception
     */
    @Override
    public void addPlayer(String teamName, Player player) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<String, Player> players = team.getPlayers();
        String emailAddress = player.getEmailAddress();

        if(players.containsKey(emailAddress)) {
            throw new Exception("Player already part of the team");
        }
        players.put(emailAddress, player);
        player.setTeam(teamName);
    }

    @Override
    public void addTeamManager(String teamName, TeamManager teamManager, List<PermissionType> permissionTypes, String ownedByEmail) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        String emailAddress = teamManager.getEmailAddress();

        if(teamManagers.containsKey(emailAddress)) {
            throw new Exception("TeamManager already part of the team");
        }
        teamManagers.put(emailAddress, teamManager);
        teamManager.setTeam(team.getTeamName());
        teamManager.setOwnedByEmail(ownedByEmail);
        teamManager.setPermissionTypes(permissionTypes);
    }

    @Override
    public void addCoach(String teamName, Coach coach) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<String, Coach> coaches = team.getCoaches();
        String emailAddress = coach.getEmailAddress();

        if(coaches.containsKey(emailAddress)) {
            throw new Exception("Coach already part of the team");
        }
        coaches.put(emailAddress, coach);
        coach.setTeam(teamName);
    }

    @Override
    public void addCourt(String teamName, Court court) throws Exception {
        Team team = teams.get(teamName);
        //add the court to the team
        team.setCourt(court);
        //add the team to the teams's court
        List<String> teams = court.getTeams();
        teams.add(teamName);
    }

    /**
     * remove the player from the team in DB
     * @param teamName
     * @param playerEmailAddress
     * @throws Exception
     */
    @Override
    public void removePlayer(String teamName, String playerEmailAddress) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<String, Player> players = team.getPlayers();
        if(!players.containsKey(playerEmailAddress)) {
            throw new Exception("Player not part of the team");
        }
        Player player = players.remove(playerEmailAddress);
        //TODO check if needed
        player.setTeam(null);
    }



    @Override
    public void removeTeamManager(String teamName, String teamManagerEmailAddress) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        if(!teamManagers.containsKey(teamManagerEmailAddress)) {
            throw new Exception("TeamManager not part of the team");
        }
        TeamManager teamManager = teamManagers.remove(teamManagerEmailAddress);
        //TODO check if needed
        teamManager.setTeam(null);
        teamManager.setPermissionTypes(new ArrayList<>());
    }

    @Override
    public void removeCoach(String teamName, String coachEmailAddress) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<String, Coach> coaches = team.getCoaches();
        if(!coaches.containsKey(coachEmailAddress)) {
            throw new Exception("Coach not part of the team");
        }
        Coach coach = coaches.remove(coachEmailAddress);
        //TODO check if needed
        coach.setTeam(null);
    }

    @Override
    public void removeCourtFromTeam(String teamName, String courtName) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

       Court court = team.getCourt();
        if(!court.getCourtName().equals(courtName)) {
            throw new Exception("court not part of the team");
        }
        team.setCourt(null);
        //TODO check if needed
        List<String> teamOfCourt = court.getTeams();
        teamOfCourt.remove(teamName);
    }

    @Override
    public void addFinancialActivity(String teamName, FinancialActivity financialActivity) throws Exception {
        if(teamName == null || !teams.containsKey(teamName)) {
            throw new Exception("Team not found");
        }
        Team team = teams.get(teamName);
        Map<String, FinancialActivity> financialActivities = team.getFinancialActivities();
        financialActivities.put(financialActivity.getFinancialActivityId(),financialActivity);
        team.setBudget(transferTheAmountPerType(team.getBudget(),financialActivity));
    }

    public double transferTheAmountPerType(Double teamBudget, FinancialActivity financialActivity){
        if (financialActivity.getFinancialActivityType().equals(FinancialActivityType.OUTCOME)) {
            return teamBudget - financialActivity.getFinancialActivityAmount();
        }else{
            return teamBudget + financialActivity.getFinancialActivityAmount();
        }
    }
    @Override
    public void changeStatus(String teamName, TeamStatus teamStatus) throws Exception {
        if(teamName == null || !teams.containsKey(teamName)) {
            throw new Exception("Team not found");
        }
        Team team = teams.get(teamName);
       if(!teamStatus.equals(team.getTeamStatus())){
            team.setTeamStatus(teamStatus);
        }else{
           throw new Exception("The team already " + teamStatus.toString());
       }
   }



    @Override
    public void closeTeamForever(String teamName) throws Exception {

    }

//
//   @Override
//   public void addTeamPage(TeamPage teamPage) throws Exception {
//       Team teamName = teamPage.getTeam();
//       if(teamPage == null || !teams.containsKey(teamName)) {
//           throw new Exception("Team not found");
//       }
//       Team team = teams.get(teamName);
//        team.setTeamPage(teamPage);
//   }


    @Override
    public void deleteAll() {
        teams.clear();
    }



}
