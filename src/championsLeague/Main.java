import java.util.List;
import java.util.Map;

import model.Match.Match;
import model.group.Group;
import model.group.GroupType;
import model.team.Team;
import model.tournament.Tournament;
import service.GenerateTeamsService;
import service.TournamentService;

public class Main {
    private static final String TEAMS_JSON = "src/data/team_players.json";

    public static void main(String[] args) {
        GenerateTeamsService teamLoader = new GenerateTeamsService();
        TournamentService tournamentService = new TournamentService();

        try {
            List<Team> teams = teamLoader.loadTeams(TEAMS_JSON);
            Tournament tournament = teamLoader.buildTournament("2025/26", teams);
            tournamentService.generateGroupFixtures(tournament);
            tournamentService.simulateGroupStage(tournament);

            System.out.println(tournament.getFullName());
            System.out.println("Loaded " + teams.size() + " teams");
            System.out.println();

            for (Map.Entry<GroupType, Group> entry : tournament.getGroups().entrySet()) {
                Group group = entry.getValue();
                System.out.println(entry.getKey());
                for (Team team : group.getTeams()) {
                    System.out.println("  - " + team.getName());
                }
                System.out.println("  results:");
                for (Match match : group.getMatches().values()) {
                    System.out.printf("    %s %d - %d %s%n",
                        match.getHomeTeam().getName(),
                        match.getHomeGoals(),
                        match.getAwayGoals(),
                        match.getAwayTeam().getName());
                }
                System.out.println();
            }

            System.out.println("Group winners:");
            Map<GroupType, Team> winners = tournamentService.getGroupWinners(tournament);
            for (Map.Entry<GroupType, Team> e : winners.entrySet()) {
                System.out.println("  " + e.getKey() + ": " + e.getValue().getName());
            }
            System.out.println();

            System.out.println("Each group stats:");
            for (Map.Entry<GroupType, Group> entry : tournament.getGroups().entrySet()) {
              entry.getValue().printGroupStatistics();
              System.out.println();
            }
        } catch (Exception e) {
            System.err.println("Failed to simulate tournament: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
