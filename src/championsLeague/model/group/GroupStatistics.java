package model.group;

public class GroupStatistics implements Comparable<GroupStatistics>{
  private Integer points;
  private Integer goalsScored;
  private Integer goalsConceded;
  private Integer wins;
  private Integer draws;
  private Integer loses;

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
  public int getPlayedCount(){ return this.wins + this.draws + this.loses; }

  public void recordMatch(int scored, int conceded) {
    this.goalsScored += scored;
    this.goalsConceded += conceded;
    if (scored > conceded) {
      this.wins += 1;
      this.points += 3;
    } else if (scored == conceded) {
      this.draws += 1;
      this.points += 1;
    } else {
      this.loses += 1;
    }
  }

  public static String getStatisticsHeader(){
    return String.format("%3s | %3s | %3s | %3s | %3s | %3s | %4s | %3s",
        "P", "W", "D", "L", "GF", "GA", "GD", "Pts");
  }

  public String getStatisticsPrint(){
    return String.format("%3d | %3d | %3d | %3d | %3d | %3d | %+4d | %3d",
        getPlayedCount(), wins, draws, loses,
        goalsScored, goalsConceded, getGoalDifference(), points);
  }

  public Integer getPoints() { return this.points; }
  public Integer getGoalsScored() { return this.goalsScored; }
  public Integer getGoalsConceded() { return this.goalsConceded; }
  public Integer getWins() { return this.wins; }
  public Integer getDraws() { return this.draws; }
  public Integer getLoses() { return this.loses; }

  @Override
  public int compareTo(GroupStatistics other) {
    if (this.points != other.points)
      return other.points - this.points;
    int gdDiff = other.getGoalDifference() - this.getGoalDifference();
    if (gdDiff != 0) return gdDiff;
    return other.goalsScored - this.goalsScored;
  }
}
