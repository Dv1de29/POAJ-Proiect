package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import model.Match.Match;
import model.group.Group;
import model.group.GroupType;
import model.team.Team;
import model.tournament.Tournament;

public class TournamentService {
  private static final long HOUR_MS = 60L * 60 * 1000;
  private static final long DAY_MS = 24L * HOUR_MS;
  private static final long HOURS_BETWEEN_MATCHES_IN_ROUND = 2;
  private static final long DEFAULT_DAYS_BETWEEN_ROUNDS = 7;

  private final MatchSimulator matchSimulator;

  public TournamentService() {
    this(new MatchSimulator());
  }

  public TournamentService(MatchSimulator matchSimulator) {
    this.matchSimulator = matchSimulator;
  }

  public void simulateGroupStage(Tournament tournament) {
    for (Map.Entry<GroupType, Group> entry : tournament.getGroups().entrySet()) {
      Group group = entry.getValue();
      for (Match match : group.getMatches().values()) {
        if (match.isPlayed()) continue;
        int[] score = matchSimulator.simulate(match);
        group.recordResult(match.getHomeTeam(), match.getAwayTeam(), score[0], score[1]);
      }
    }
  }

  public Map<GroupType, Team> getGroupWinners(Tournament tournament) {
    EnumMap<GroupType, Team> winners = new EnumMap<>(GroupType.class);
    for (Map.Entry<GroupType, Group> e : tournament.getGroups().entrySet()) {
      winners.put(e.getKey(), e.getValue().getWinner());
    }
    return winners;
  }

  public void generateGroupFixtures(Tournament tournament) {
    generateGroupFixtures(tournament, new Date(), DEFAULT_DAYS_BETWEEN_ROUNDS);
  }

  public void generateGroupFixtures(Tournament tournament, Date startDate, long daysBetweenRounds) {
    int matchId = 1;
    for (Map.Entry<GroupType, Group> entry : tournament.getGroups().entrySet()) {
      GroupType gt = entry.getKey();
      Group group = entry.getValue();
      List<Team> teams = new ArrayList<>(group.getTeams());
      if (teams.size() < 2) continue;

      if (teams.size() % 2 != 0) teams.add(null);
      int size = teams.size();
      int rounds = size - 1;
      int matchesPerRound = size / 2;

      long currentTime = startDate.getTime();

      for (int leg = 0; leg < 2; leg++) {
        List<Team> rot = new ArrayList<>(teams);
        for (int r = 0; r < rounds; r++) {
          for (int i = 0; i < matchesPerRound; i++) {
            Team a = rot.get(i);
            Team b = rot.get(size - 1 - i);
            if (a == null || b == null) continue;
            Team home = (leg == 0) ? a : b;
            Team away = (leg == 0) ? b : a;
            Date matchDate = new Date(currentTime);
            group.addMatch(matchDate, new Match(gt, matchId++, matchDate, home, away));
            currentTime += HOURS_BETWEEN_MATCHES_IN_ROUND * HOUR_MS;
          }
          currentTime += daysBetweenRounds * DAY_MS;
          rot.add(1, rot.remove(size - 1));
        }
      }
    }
  }
}
