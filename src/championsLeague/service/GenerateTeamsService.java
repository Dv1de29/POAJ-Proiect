package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.group.Group;
import model.group.GroupType;
import model.player.Attacker;
import model.player.Defender;
import model.player.Goalkeeper;
import model.player.Midfielder;
import model.player.Player;
import model.team.Team;
import model.tournament.Tournament;

public class GenerateTeamsService {
  private int nextTeamId = 1;
  private int nextPlayerId = 1;

  public List<Team> loadTeams(String jsonPath) throws IOException {
    String json = Files.readString(Path.of(jsonPath));
    @SuppressWarnings("unchecked")
    List<Object> arr = (List<Object>) new JsonParser(json).parse();

    List<Team> teams = new ArrayList<>();
    for (Object entry : arr) {
      @SuppressWarnings("unchecked")
      Map<String, Object> teamObj = (Map<String, Object>) entry;
      String name = (String) teamObj.get("team");
      Team team = new Team(nextTeamId++, name, "");

      @SuppressWarnings("unchecked")
      List<Object> playersArr = (List<Object>) teamObj.get("players");
      for (Object pEntry : playersArr) {
        @SuppressWarnings("unchecked")
        Map<String, Object> pObj = (Map<String, Object>) pEntry;
        team.addPlayer(createPlayer(pObj));
      }
      teams.add(team);
    }
    return teams;
  }

  public Tournament buildTournament(String edition, List<Team> teams) {
    EnumMap<GroupType, Group> groups = new EnumMap<>(GroupType.class);
    GroupType[] types = GroupType.values();
    if (teams.isEmpty()) return new Tournament(edition, groups);

    int groupCount = Math.min(types.length, (int) Math.ceil(teams.size() / 4.0));
    int perGroup = (int) Math.ceil(teams.size() / (double) groupCount);

    for (int i = 0; i < groupCount; i++) {
      int from = i * perGroup;
      if (from >= teams.size()) break;
      int to = Math.min(from + perGroup, teams.size());
      List<Team> groupTeams = new ArrayList<>(teams.subList(from, to));
      groups.put(types[i], new Group(types[i], groupTeams));
    }
    return new Tournament(edition, groups);
  }

  public Tournament buildTournamentFromJson(String edition, String jsonPath) throws IOException {
    return buildTournament(edition, loadTeams(jsonPath));
  }

  private Player createPlayer(Map<String, Object> pObj) {
    String name = (String) pObj.get("name");
    String pos = (String) pObj.get("position");
    int id = nextPlayerId++;

    if ("GK".equals(pos)) {
      return new Goalkeeper(id, name, "",
          intOf(pObj, "diving"),
          intOf(pObj, "handling"),
          intOf(pObj, "kicking"),
          intOf(pObj, "reflexes"),
          intOf(pObj, "speed"),
          intOf(pObj, "positioning"));
    }

    int pace = intOf(pObj, "pace");
    int shooting = intOf(pObj, "shooting");
    int passing = intOf(pObj, "passing");
    int dribbling = intOf(pObj, "dribbling");
    int defending = intOf(pObj, "defending");
    int physical = intOf(pObj, "physical");

    return switch (categorize(pos)) {
      case "DEFENDER" -> new Defender(id, name, "", pace, shooting, passing, dribbling, defending, physical);
      case "MIDFIELDER" -> new Midfielder(id, name, "", pace, shooting, passing, dribbling, defending, physical);
      case "ATTACKER" -> new Attacker(id, name, "", pace, shooting, passing, dribbling, defending, physical);
      default -> throw new IllegalArgumentException("Unknown position: " + pos);
    };
  }

  private static int intOf(Map<String, Object> m, String key) {
    return ((Number) m.get(key)).intValue();
  }

  private static String categorize(String pos) {
    return switch (pos) {
      case "CB", "LB", "RB", "LWB", "RWB", "LCB", "RCB" -> "DEFENDER";
      case "CM", "CAM", "CDM", "LM", "RM", "LCM", "RCM", "LDM", "RDM" -> "MIDFIELDER";
      case "ST", "CF", "LW", "RW", "LF", "RF", "LS", "RS" -> "ATTACKER";
      default -> throw new IllegalArgumentException("Unknown position: " + pos);
    };
  }

  private static class JsonParser {
    private final String src;
    private int pos;

    JsonParser(String src) { this.src = src; this.pos = 0; }

    Object parse() { skipWs(); return value(); }

    private Object value() {
      skipWs();
      char c = src.charAt(pos);
      if (c == '{') return object();
      if (c == '[') return array();
      if (c == '"') return string();
      if (c == '-' || Character.isDigit(c)) return number();
      if (src.startsWith("true", pos)) { pos += 4; return Boolean.TRUE; }
      if (src.startsWith("false", pos)) { pos += 5; return Boolean.FALSE; }
      if (src.startsWith("null", pos)) { pos += 4; return null; }
      throw new RuntimeException("Unexpected char at " + pos + ": " + c);
    }

    private Map<String, Object> object() {
      Map<String, Object> m = new LinkedHashMap<>();
      pos++;
      skipWs();
      if (src.charAt(pos) == '}') { pos++; return m; }
      while (true) {
        skipWs();
        String key = string();
        skipWs();
        pos++;
        m.put(key, value());
        skipWs();
        if (src.charAt(pos) == ',') { pos++; continue; }
        if (src.charAt(pos) == '}') { pos++; return m; }
      }
    }

    private List<Object> array() {
      List<Object> l = new ArrayList<>();
      pos++;
      skipWs();
      if (src.charAt(pos) == ']') { pos++; return l; }
      while (true) {
        l.add(value());
        skipWs();
        if (src.charAt(pos) == ',') { pos++; continue; }
        if (src.charAt(pos) == ']') { pos++; return l; }
      }
    }

    private String string() {
      pos++;
      StringBuilder sb = new StringBuilder();
      while (src.charAt(pos) != '"') {
        char c = src.charAt(pos);
        if (c == '\\') {
          pos++;
          char esc = src.charAt(pos);
          switch (esc) {
            case 'n' -> sb.append('\n');
            case 't' -> sb.append('\t');
            case 'r' -> sb.append('\r');
            case '"' -> sb.append('"');
            case '\\' -> sb.append('\\');
            case '/' -> sb.append('/');
            case 'u' -> {
              sb.append((char) Integer.parseInt(src.substring(pos + 1, pos + 5), 16));
              pos += 4;
            }
            default -> sb.append(esc);
          }
        } else {
          sb.append(c);
        }
        pos++;
      }
      pos++;
      return sb.toString();
    }

    private Number number() {
      int start = pos;
      if (src.charAt(pos) == '-') pos++;
      while (pos < src.length()) {
        char c = src.charAt(pos);
        if (Character.isDigit(c) || c == '.' || c == 'e' || c == 'E' || c == '+' || c == '-') pos++;
        else break;
      }
      String s = src.substring(start, pos);
      if (s.contains(".") || s.contains("e") || s.contains("E")) return Double.parseDouble(s);
      return Long.parseLong(s);
    }

    private void skipWs() {
      while (pos < src.length() && Character.isWhitespace(src.charAt(pos))) pos++;
    }
  }
}
