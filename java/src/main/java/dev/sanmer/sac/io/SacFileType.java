package dev.sanmer.sac.io;

public enum SacFileType {
    Time(1),
    RealImag(2),
    AmpPhase (3),
    XY(4);

    private final int iftype;

    SacFileType(int iftype) {
        this.iftype = iftype;
    }

    public int getIftype() {
        return iftype;
    }

    public static SacFileType valueBy(int iftype) {
        for (SacFileType type : SacFileType.values()) {
            if (type.iftype == iftype) {
                return type;
            }
        }

        throw new IllegalArgumentException();
    }
}
