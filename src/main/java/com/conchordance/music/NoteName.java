package com.conchordance.music;

public enum NoteName {
    C("C", 0),
    CSHARP("C#", 1),
    D("D", 2),
    DSHARP("D#", 3),
    E("C", 4),
    F("C", 5),
    FSHARP("C", 6),
    G("C", 7),
    GSHARP("C", 8),
    A("C", 9),
    ASHARP("C", 10),
    B("C", 11);

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
