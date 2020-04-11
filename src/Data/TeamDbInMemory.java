package Data;

import Model.Court;
import Model.Enums.FinancialActivityType;
import Model.Enums.TeamStatus;
import Model.FinancialActivity;
import Model.Team;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;
import com.sun.xml.internal.bind.v2.TODO;
import org.omg.PortableInterceptor.ACTIVE;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashMap;
import java.util.Map;

public class TeamDbInMemory implements TeamDb {
    /*structure like the DB of teams*/
    private Map<String, Team> teams;

    public TeamDbInMemory() {
        teams = new HashMap<>();
    }

    /**
     * create team in DB
     * @param teamName
     * @throws Exception
     */
    @Override
    public void createTeam(String teamName) throws Exception {
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

        Map<Integer, Player> players = team.getPlayers();
        Integer id = player.getId();

        if(players.containsKey(id)) {
            throw new Exception("Player already part of the team");
        }
        players.put(id, player);
        player.setTeam(team);
    }

    @Override
    public void addTeamManager(String teamName, TeamManager teamManager) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<Integer, TeamManager> teamManagers = team.getTeamManagers();
        Integer id = teamManager.getId();

        if(teamManagers.containsKey(id)) {
            throw new Exception("Player already part of the team");
        }
        teamManagers.put(id, teamManager);
        teamManager.setTeam(team);
    }

    @Override
    public void addCoach(String teamName, Coach coach) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<Integer, Coach> coaches = team.getCoaches();
        Integer id = coach.getId();

        if(coaches.containsKey(id)) {
            throw new Exception("Coach already part of the team");
        }
        coaches.put(id, coach);
    }

    @Override
    public void addCourt(String teamName, Court court) throws Exception {
        Team team = teams.get(teamName);
        //add the court to the team
        team.setCourt(court);
        //add the team to the teams's court
        HashMap<String, Team> teams = court.getTeams();
        teams.put(teamName,team);
    }

    /**
     * remove the player from the team in DB
     * @param teamName
     * @param playerId
     * @throws Exception
     */
    @Override
    public void removePlayer(String teamName, Integer playerId) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<Integer, Player> players = team.getPlayers();
        if(!players.containsKey(playerId)) {
            throw new Exception("Player not part of the team");
        }
        Player player = players.remove(playerId);
        //TODO check if needed
        player.setTeam(null);
    }



    @Override
    public void removeTeamManager(String teamName, Integer teamManagerId) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<Integer, TeamManager> teamManagers = team.getTeamManagers();
        if(!teamManagers.containsKey(teamManagerId)) {
            throw new Exception("TeamManager not part of the team");
        }
        TeamManager teamManager = teamManagers.remove(teamManagerId);
        //TODO check if needed
        teamManager.setTeam(null);
    }

    @Override
    public void removeCoach(String teamName, Integer coachId) throws Exception {
        Team team = teams.get(teamName);
        if(team == null) {
            throw new Exception("Team not found");
        }

        Map<Integer, Coach> coaches = team.getCoaches();
        if(!coaches.containsKey(coachId)) {
            throw new Exception("Coach not part of the team");
        }
        Coach coach = coaches.remove(coachId);
        //TODO check if needed
        coach.setTeam(null);
    }

    @Override
    public void removeCourt(String teamName, String courtName) throws Exception {
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
        Map<String,Team> teamOfCourt = court.getTeams();
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

    public double transferTheAmountPerType(Double teamBudget,FinancialActivity financialActivity){
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



}
