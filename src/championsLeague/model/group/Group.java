package model.group;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Match.Match;
import model.team.Team;

public class Group{
  public GroupType group;

  HashMap<Team, GroupStatistics> teams;
  TreeMap<Date, Match> matches;

  public Group(GroupType group_, List<Team> teams_, TreeMap<Date, Match> matches_){
    this.group = group_;
    this.teams = new HashMap<>();
    for ( Team team : teams_ ){
      teams.put(team, new GroupStatistics());
    }
    this.matches = matches_;
  }

  public Group(GroupType group_, List<Team> teams_){
    this(group_, teams_, new TreeMap<>());
  }

  public boolean hasTeam(Team team){
    return teams.containsKey(team);
  }

  public Set<Team> getTeams(){
    return teams.keySet();
  }

  public Team getWinner(){
    return teams.entrySet().stream()
        .min(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(null);
  }

  public void addMatch(Date date, Match match){
    this.matches.put(date, match);
  }

  public void recordResult(Team home, Team away, int homeGoals, int awayGoals) {
    teams.get(home).recordMatch(homeGoals, awayGoals);
    teams.get(away).recordMatch(awayGoals, homeGoals);
  }

  public void printGroupStatistics(){
    System.out.println(this.group + ":");
    System.out.printf("%3s | %-20s | %s%n", "Pos", "Team", GroupStatistics.getStatisticsHeader());
    System.out.println("----+----------------------+-----+-----+-----+-----+-----+-----+------+----");
    int pos = 1;
    for (Map.Entry<Team, GroupStatistics> e : teams.entrySet().stream()
        .sorted(Map.Entry.comparingByValue()).toList()) {
      System.out.printf("%3d | %-20s | %s%n", pos++, e.getKey().getName(), e.getValue().getStatisticsPrint());
    }
  }

  public TreeMap<Date, Match> getMatches(){ return this.matches; }
  public HashMap<Team, GroupStatistics> getTeamsStatistics() { return this.teams; }
}
