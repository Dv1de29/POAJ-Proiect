package model.Match;

import java.util.Date;

import model.group.GroupType;
import model.team.Team;

public class Match {
  GroupType group;
  Integer id;
  Date matchDate;

  Team homeTeam;
  Team awayTeam;

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
  }
}
