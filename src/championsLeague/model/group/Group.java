package model.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.team.Team;

class GroupStatistics implements Comparable<GroupStatistics>{
  Integer points;
  Integer goalsScored;
  Integer goalsConceded;
  Integer wins;
  Integer draws;
  Integer loses;

  public GroupStatistics(
    Integer points_,
    Integer goalsScored_,
    Integer goalsConceded_,
    Integer wins_,
    Integer draws_,
    Integer loses_
  ){
    this.points = points_;
    this.goalsScored = goalsScored_;
    this.goalsConceded = goalsConceded_;
    this.wins = wins_;
    this.draws = draws_;
    this.loses = loses_;
  }

  public GroupStatistics(){
    this(0,0,0,0,0,0);
  }

  public int getGoalDifference(){ return this.goalsScored - this.goalsConceded; }

  @Override
  public int compareTo(GroupStatistics other) {
    if (this.points != other.points)
      return other.points - this.points;
    int gdDiff = other.getGoalDifference() - this.getGoalDifference();
    if (gdDiff != 0) return gdDiff;
    return other.goalsScored - this.goalsScored;
  }
}


public class Group{
  public GroupType group;

  HashMap<Team, GroupStatistics> teams;

  public Group(GroupType group_, List<Team> teams_){
    this.group = group_;
    this.teams = new HashMap<>();
    for ( Team team : teams_ ){
      teams.put(team, new GroupStatistics());
    }
  }

  public Team getWinner(){
    return teams.entrySet().stream()
        .min(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(null);
  }
}
