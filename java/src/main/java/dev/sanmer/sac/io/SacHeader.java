package dev.sanmer.sac.io;

import java.io.File;

@SuppressWarnings("SpellCheckingInspection")
public class SacHeader {
    private float delta;
    private float depmin;
    private float depmax;
    private float scale;
    private float odelta;
    private float b;
    private float e;
    private float o;
    private float a;
    private float[] t;
    private float f;
    private float[] resp;
    private float stla;
    private float stlo;
    private float stel;
    private float stdp;
    private float evla;
    private float evlo;
    private float evel;
    private float evdp;
    private float mag;
    private float[] user;
    private float dist;
    private float az;
    private float baz;
    private float gcarc;
    private float depmen;
    private float cmpaz;
    private float cmpinc;
    private float xminimum;
    private float xmaximum;
    private float yminimum;
    private float ymaximum;
    private int nzyear;
    private int nzjday;
    private int nzhour;
    private int nzmin;
    private int nzsec;
    private int nzmsec;
    private int nvhdr;
    private int norid;
    private int nevid;
    private int npts;
    private int nwfid;
    private int nxsize;
    private int nysize;
    private int iftype;
    private int idep;
    private int iztype;
    private int iinst;
    private int istreg;
    private int ievreg;
    private int ievtyp;
    private int iqual;
    private int isynth;
    private int imagtyp;
    private int imagsrc;
    private boolean leven;
    private boolean lpspol;
    private boolean lovrok;
    private boolean lcalda;
    private String kstnm;
    private String kevnm;
    private String khole;
    private String ko;
    private String ka;
    private String[] kt;
    private String kf;
    private String kuser0;
    private String kuser1;
    private String kuser2;
    private String kcmpnm;
    private String knetwk;
    private String kdatrd;
    private String kinst;

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDepmin() {
        return depmin;
    }

    public void setDepmin(float depmin) {
        this.depmin = depmin;
    }

    public float getDepmax() {
        return depmax;
    }

    public void setDepmax(float depmax) {
        this.depmax = depmax;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getOdelta() {
        return odelta;
    }

    public void setOdelta(float odelta) {
        this.odelta = odelta;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getE() {
        return e;
    }

    public void setE(float e) {
        this.e = e;
    }

    public float getO() {
        return o;
    }

    public void setO(float o) {
        this.o = o;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float[] getT() {
        return t;
    }

    public void setT(float[] t) {
        this.t = t;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public float[] getResp() {
        return resp;
    }

    public void setResp(float[] resp) {
        this.resp = resp;
    }

    public float getStla() {
        return stla;
    }

    public void setStla(float stla) {
        this.stla = stla;
    }

    public float getStlo() {
        return stlo;
    }

    public void setStlo(float stlo) {
        this.stlo = stlo;
    }

    public float getStel() {
        return stel;
    }

    public void setStel(float stel) {
        this.stel = stel;
    }

    public float getStdp() {
        return stdp;
    }

    public void setStdp(float stdp) {
        this.stdp = stdp;
    }

    public float getEvla() {
        return evla;
    }

    public void setEvla(float evla) {
        this.evla = evla;
    }

    public float getEvlo() {
        return evlo;
    }

    public void setEvlo(float evlo) {
        this.evlo = evlo;
    }

    public float getEvel() {
        return evel;
    }

    public void setEvel(float evel) {
        this.evel = evel;
    }

    public float getEvdp() {
        return evdp;
    }

    public void setEvdp(float evdp) {
        this.evdp = evdp;
    }

    public float getMag() {
        return mag;
    }

    public void setMag(float mag) {
        this.mag = mag;
    }

    public float[] getUser() {
        return user;
    }

    public void setUser(float[] user) {
        this.user = user;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }

    public float getAz() {
        return az;
    }

    public void setAz(float az) {
        this.az = az;
    }

    public float getBaz() {
        return baz;
    }

    public void setBaz(float baz) {
        this.baz = baz;
    }

    public float getGcarc() {
        return gcarc;
    }

    public void setGcarc(float gcarc) {
        this.gcarc = gcarc;
    }

    public float getDepmen() {
        return depmen;
    }

    public void setDepmen(float depmen) {
        this.depmen = depmen;
    }

    public float getCmpaz() {
        return cmpaz;
    }

    public void setCmpaz(float cmpaz) {
        this.cmpaz = cmpaz;
    }

    public float getCmpinc() {
        return cmpinc;
    }

    public void setCmpinc(float cmpinc) {
        this.cmpinc = cmpinc;
    }

    public float getXminimum() {
        return xminimum;
    }

    public void setXminimum(float xminimum) {
        this.xminimum = xminimum;
    }

    public float getXmaximum() {
        return xmaximum;
    }

    public void setXmaximum(float xmaximum) {
        this.xmaximum = xmaximum;
    }

    public float getYminimum() {
        return yminimum;
    }

    public void setYminimum(float yminimum) {
        this.yminimum = yminimum;
    }

    public float getYmaximum() {
        return ymaximum;
    }

    public void setYmaximum(float ymaximum) {
        this.ymaximum = ymaximum;
    }

    public int getNzyear() {
        return nzyear;
    }

    public void setNzyear(int nzyear) {
        this.nzyear = nzyear;
    }

    public int getNzjday() {
        return nzjday;
    }

    public void setNzjday(int nzjday) {
        this.nzjday = nzjday;
    }

    public int getNzhour() {
        return nzhour;
    }

    public void setNzhour(int nzhour) {
        this.nzhour = nzhour;
    }

    public int getNzmin() {
        return nzmin;
    }

    public void setNzmin(int nzmin) {
        this.nzmin = nzmin;
    }

    public int getNzsec() {
        return nzsec;
    }

    public void setNzsec(int nzsec) {
        this.nzsec = nzsec;
    }

    public int getNzmsec() {
        return nzmsec;
    }

    public void setNzmsec(int nzmsec) {
        this.nzmsec = nzmsec;
    }

    public int getNvhdr() {
        return nvhdr;
    }

    public void setNvhdr(int nvhdr) {
        this.nvhdr = nvhdr;
    }

    public int getNorid() {
        return norid;
    }

    public void setNorid(int norid) {
        this.norid = norid;
    }

    public int getNevid() {
        return nevid;
    }

    public void setNevid(int nevid) {
        this.nevid = nevid;
    }

    public int getNpts() {
        return npts;
    }

    public void setNpts(int npts) {
        this.npts = npts;
    }

    public int getNwfid() {
        return nwfid;
    }

    public void setNwfid(int nwfid) {
        this.nwfid = nwfid;
    }

    public int getNxsize() {
        return nxsize;
    }

    public void setNxsize(int nxsize) {
        this.nxsize = nxsize;
    }

    public int getNysize() {
        return nysize;
    }

    public void setNysize(int nysize) {
        this.nysize = nysize;
    }

    public int getIftype() {
        return iftype;
    }

    public void setIftype(int iftype) {
        this.iftype = iftype;
    }

    public int getIdep() {
        return idep;
    }

    public void setIdep(int idep) {
        this.idep = idep;
    }

    public int getIztype() {
        return iztype;
    }

    public void setIztype(int iztype) {
        this.iztype = iztype;
    }

    public int getIinst() {
        return iinst;
    }

    public void setIinst(int iinst) {
        this.iinst = iinst;
    }

    public int getIstreg() {
        return istreg;
    }

    public void setIstreg(int istreg) {
        this.istreg = istreg;
    }

    public int getIevreg() {
        return ievreg;
    }

    public void setIevreg(int ievreg) {
        this.ievreg = ievreg;
    }

    public int getIevtyp() {
        return ievtyp;
    }

    public void setIevtyp(int ievtyp) {
        this.ievtyp = ievtyp;
    }

    public int getIqual() {
        return iqual;
    }

    public void setIqual(int iqual) {
        this.iqual = iqual;
    }

    public int getIsynth() {
        return isynth;
    }

    public void setIsynth(int isynth) {
        this.isynth = isynth;
    }

    public int getImagtyp() {
        return imagtyp;
    }

    public void setImagtyp(int imagtyp) {
        this.imagtyp = imagtyp;
    }

    public int getImagsrc() {
        return imagsrc;
    }

    public void setImagsrc(int imagsrc) {
        this.imagsrc = imagsrc;
    }

    public boolean isLeven() {
        return leven;
    }

    public void setLeven(boolean leven) {
        this.leven = leven;
    }

    public boolean isLpspol() {
        return lpspol;
    }

    public void setLpspol(boolean lpspol) {
        this.lpspol = lpspol;
    }

    public boolean isLovrok() {
        return lovrok;
    }

    public void setLovrok(boolean lovrok) {
        this.lovrok = lovrok;
    }

    public boolean isLcalda() {
        return lcalda;
    }

    public void setLcalda(boolean lcalda) {
        this.lcalda = lcalda;
    }

    public String getKstnm() {
        return kstnm;
    }

    public void setKstnm(String kstnm) {
        this.kstnm = kstnm;
    }

    public String getKevnm() {
        return kevnm;
    }

    public void setKevnm(String kevnm) {
        this.kevnm = kevnm;
    }

    public String getKhole() {
        return khole;
    }

    public void setKhole(String khole) {
        this.khole = khole;
    }

    public String getKo() {
        return ko;
    }

    public void setKo(String ko) {
        this.ko = ko;
    }

    public String getKa() {
        return ka;
    }

    public void setKa(String ka) {
        this.ka = ka;
    }

    public String[] getKt() {
        return kt;
    }

    public void setKt(String[] kt) {
        this.kt = kt;
    }

    public String getKf() {
        return kf;
    }

    public void setKf(String kf) {
        this.kf = kf;
    }

    public String getKuser0() {
        return kuser0;
    }

    public void setKuser0(String kuser0) {
        this.kuser0 = kuser0;
    }

    public String getKuser1() {
        return kuser1;
    }

    public void setKuser1(String kuser1) {
        this.kuser1 = kuser1;
    }

    public String getKuser2() {
        return kuser2;
    }

    public void setKuser2(String kuser2) {
        this.kuser2 = kuser2;
    }

    public String getKcmpnm() {
        return kcmpnm;
    }

    public void setKcmpnm(String kcmpnm) {
        this.kcmpnm = kcmpnm;
    }

    public String getKnetwk() {
        return knetwk;
    }

    public void setKnetwk(String knetwk) {
        this.knetwk = knetwk;
    }

    public String getKdatrd() {
        return kdatrd;
    }

    public void setKdatrd(String kdatrd) {
        this.kdatrd = kdatrd;
    }

    public String getKinst() {
        return kinst;
    }

    public void setKinst(String kinst) {
        this.kinst = kinst;
    }

    public static SacHeader read(File file, Endian endian) {
        Sac sac = Sac.readHeader(file, endian);
        SacHeader h = sac.getHeader();

        sac.close();
        return h;
    }
}
