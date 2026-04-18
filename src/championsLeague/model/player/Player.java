package model.player;

abstract public class Player {
  private Integer playerId;
  private String name;
  private String countryOrigin;

  private Position position;

  protected Player(Integer id_, String name_, String countryOrigin_, Position position_){
    this.playerId = id_;
    this.name = name_;
    this.countryOrigin = countryOrigin_;
    this.position = position_;
  }

  public Integer getId() { return this.playerId; }
  public String getName() { return this.name; }
  public String getCountryOrigin() { return this.countryOrigin; }
  public Position getPosition() { return this.position; }

  public abstract double getOverall();
}