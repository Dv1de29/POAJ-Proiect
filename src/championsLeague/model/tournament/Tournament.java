package model.tournament;

import java.util.Date;
import java.util.EnumMap;
import java.util.TreeMap;

import model.Match.Match;
import model.group.Group;
import model.group.GroupType;
import model.team.Team;

public class Tournament {
  String name = "Champions League";
  String edition;
  EnumMap<GroupType, Group> groups;

  public Tournament(
    String edition_,
    EnumMap<GroupType, Group> groups_
  ){
    this.edition = edition_;
    this.groups = groups_;
  }

  public Team getWinnerByGroup(GroupType groupType){
    return groups.get(groupType).getWinner();
  }

  public String getFullName(){ return this.name + " " + this.edition + " edition"; }
  public EnumMap<GroupType, Group> getGroups(){ return this.groups; }
  public TreeMap<Date, Match> getMatchesByGroup(GroupType groupType){
    return groups.get(groupType).getMatches();
  }

  public Group getGroupOf(Team team) {
    return groups.values().stream()
        .filter(g -> g.hasTeam(team))
        .findFirst()
        .orElse(null);
  }
}
