package us.plxhack.MEH.Globals;

public class Version {
    public static int MajorVersion = 1;
    public static int MinorVersion = 1;
    public static int Build = 15;
    public static String Release = "a";
    public final static String RequestApplicationBuild() {
        return MajorVersion + "." + MinorVersion + "." + Build + Release;
    }
    public static String[] Contributors = {"Shiny Quagsire", "interdpth", "Bela", "DeltaSalamence", "trevor403", "diegoisawesome"};
}
