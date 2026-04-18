package model.team;

import java.util.ArrayList;
import java.util.List;

import model.player.Player;
import model.player.Position;

public class Team {
  private Integer id;
  private String name;
  private String countryOrigin;

  private ArrayList<Player> players;

  public Team(Integer id_, String name_, String countryOrigin_, ArrayList<Player> players_){
    this.id = id_;
    this.name = name_;
    this.countryOrigin = countryOrigin_;
    this.players = players_;
  }

  public Team(Integer id_, String name_, String countryOrigin_){
    this(id_, name_, countryOrigin_, new ArrayList<Player>());
  }

  public void addPlayer(Player new_player){
    players.add(new_player);
  }

  public void removePlayer(Player target_player){
    players.remove(target_player);
  }

  public List<Player> getAllInPosition(Position position){
    return players.stream().filter(player -> player.getPosition() == position).toList();
  }

  public Integer getId() { return this.id; }
  public String getName() { return this.name; }
  public String getCountryOrigin() { return this.countryOrigin; }
  public List<Player> getPlayers() { return this.players; }
}
