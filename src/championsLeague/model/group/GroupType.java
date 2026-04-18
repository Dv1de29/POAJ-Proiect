package model.group;

public enum GroupType {
  GR_A, GR_B, GR_C, GR_D, GR_E, GR_F, GR_G, GR_H;

  @Override
  public String toString() {
      return switch (this) {
        case GR_A -> "Group A";
        case GR_B -> "Group B";
        case GR_C -> "Group C";
        case GR_D -> "Group D";
        case GR_E -> "Group E";
        case GR_F -> "Group F";
        case GR_G -> "Group G";
        case GR_H -> "Group H";
      };
  }
}
