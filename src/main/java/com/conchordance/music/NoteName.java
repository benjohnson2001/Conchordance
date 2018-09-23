package com.conchordance.music;

public enum NoteName {
    C("C", 0),
    D("D", 2),
    E("E", 4),
    F("F", 5),
    G("G", 7),
    A("A", 9),
    B("B", 11);

    static NoteName fromChar(char c) {
        switch(c) {
            case 'A': return A;
            case 'B': return B;
            case 'C': return C;
            case 'D': return D;
            case 'E': return E;
            case 'F': return F;
            case 'G': return G;
            default: return null;
        }
    }

    public final int halfStepsFromC;
    public final String toStringName;

    public NoteName offset(int offset) {
        return values()[(ordinal() + offset) % values().length];
    }

    NoteName(String toStringNameArg, int stepsFromC) {
       toStringName = toStringNameArg;
        halfStepsFromC = stepsFromC;
    }

   @Override
   public String toString() {
      return toStringName;
   }
}
