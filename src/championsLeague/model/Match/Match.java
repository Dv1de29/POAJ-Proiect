package model.Match;

import java.util.Date;

import model.group.GroupType;
import model.team.Team;

public class Match {
  private GroupType group;
  private Integer id;
  private Date matchDate;

  private Team homeTeam;
  private Team awayTeam;

  private Integer homeGoals;
  private Integer awayGoals;
  private boolean played;

  public Match(
    GroupType group_,
    Integer id_,
    Date matchDate_,
    Team homeTeam_,
    Team awayTeam_
  ){
    this.group = group_;
    this.id = id_;
    this.matchDate = matchDate_;
    this.homeTeam = homeTeam_;
    this.awayTeam = awayTeam_;
    this.played = false;
  }

  public GroupType getGroup() { return group; }
  public Integer getId() { return id; }
  public Date getMatchDate() { return matchDate; }
  public Team getHomeTeam() { return homeTeam; }
  public Team getAwayTeam() { return awayTeam; }
  public Integer getHomeGoals() { return homeGoals; }
  public Integer getAwayGoals() { return awayGoals; }
  public boolean isPlayed() { return played; }

  public void recordScore(int home, int away) {
    this.homeGoals = home;
    this.awayGoals = away;
    this.played = true;
  }
}
