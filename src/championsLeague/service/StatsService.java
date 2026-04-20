package service;

import java.util.List;

import model.player.Player;
import model.team.Team;

public class StatsService {
  private static final double DEFAULT_STRENGTH = 50.0;

  public double getTeamStrength(Team team) {
    List<Player> players = team.getPlayers();
    if (players.isEmpty()) return DEFAULT_STRENGTH;
    return players.stream().mapToDouble(Player::getOverall).average().orElse(DEFAULT_STRENGTH);
  }
}
