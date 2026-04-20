package service;

import java.util.Random;

import model.Match.Match;

public class MatchSimulator {
  private static final double BASE_EXPECTED_GOALS = 3.0;
  private static final double HOME_ADVANTAGE = 0.2;

  private final Random random;
  private final StatsService statsService;

  public MatchSimulator() {
    this(new StatsService(), new Random());
  }

  public MatchSimulator(long seed) {
    this(new StatsService(), new Random(seed));
  }

  public MatchSimulator(StatsService statsService, Random random) {
    this.statsService = statsService;
    this.random = random;
  }

  public int[] simulate(Match match) {
    double homeStrength = statsService.getTeamStrength(match.getHomeTeam());
    double awayStrength = statsService.getTeamStrength(match.getAwayTeam());
    double total = homeStrength + awayStrength;

    double homeExpected = (homeStrength / total) * BASE_EXPECTED_GOALS + HOME_ADVANTAGE;
    double awayExpected = (awayStrength / total) * BASE_EXPECTED_GOALS;

    int homeGoals = samplePoisson(homeExpected);
    int awayGoals = samplePoisson(awayExpected);

    match.recordScore(homeGoals, awayGoals);
    return new int[]{ homeGoals, awayGoals };
  }

  private int samplePoisson(double lambda) {
    double L = Math.exp(-lambda);
    int k = 0;
    double p = 1.0;
    do {
      k++;
      p *= random.nextDouble();
    } while (p > L);
    return k - 1;
  }
}
