package model.tournament;

import java.util.ArrayList;
import java.util.EnumMap;

import model.group.Group;
import model.group.GroupType;
import model.team.Team;

public class Tournament {
  String name = "Champions League";
  String edition;
  EnumMap<GroupType, Group> groups;

  public Tournament(
    String edition_,
    EnumMap groups_
  ){
    this.edition = edition_;
    this.groups = groups_;
  }


  public Team getWinnerByGroup(GroupType groupType){
    return groups.get(groupType).getWinner();
  }
}
