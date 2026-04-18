package model.Goal;

public class Goal {
  Integer matchId;
  Integer goalCount;
  Integer teamScorer;
  Integer playerId;

  public Goal(Integer matchId_, Integer goalCount_, Integer teamScorer_, Integer playerId_){
    this.matchId = matchId_;
    this.goalCount = goalCount_;
    this.teamScorer = teamScorer_;
    this.playerId = playerId_;
  }
}
