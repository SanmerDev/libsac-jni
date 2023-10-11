use jni::errors::Result;
use jni::objects::JObject;
use jni::sys::jint;
use jni::JNIEnv;
use libsac::{Endian, Sac, SacHeader};

use crate::impl_ext::JNIEnvExt;

pub trait JNIEnvHelper<'local> {
    fn get_sac_endian(&self, value: jint) -> Endian {
        match value {
            0 => Endian::Little,
            1 => Endian::Big,
            _ => unreachable!(),
        }
    }

    fn new_sac_header(&mut self, sac: &Sac) -> Result<JObject<'local>>;

    fn get_sac_header(&mut self, obj: &JObject) -> Result<SacHeader>;
}

impl<'local> JNIEnvHelper<'local> for JNIEnv<'local> {
    fn new_sac_header(&mut self, sac: &Sac) -> Result<JObject<'local>> {
        let class = self.find_class("dev/sanmer/sac/io/SacHeader")?;
        let obj = self.alloc_object(class)?;

        self.set_float_field(&obj, "delta", sac.delta)?;
        self.set_float_field(&obj, "depmin", sac.depmin)?;
        self.set_float_field(&obj, "depmax", sac.depmax)?;
        self.set_float_field(&obj, "scale", sac.scale)?;
        self.set_float_field(&obj, "odelta", sac.odelta)?;
        self.set_float_field(&obj, "b", sac.b)?;
        self.set_float_field(&obj, "e", sac.e)?;
        self.set_float_field(&obj, "o", sac.o)?;
        self.set_float_field(&obj, "a", sac.a)?;
        self.set_float_array_field(&obj, "t", &sac.t)?;
        self.set_float_field(&obj, "f", sac.f)?;
        self.set_float_array_field(&obj, "resp", &sac.resp)?;
        self.set_float_field(&obj, "stla", sac.stla)?;
        self.set_float_field(&obj, "stlo", sac.stlo)?;
        self.set_float_field(&obj, "stel", sac.stel)?;
        self.set_float_field(&obj, "stdp", sac.stdp)?;
        self.set_float_field(&obj, "evla", sac.evla)?;
        self.set_float_field(&obj, "evlo", sac.evlo)?;
        self.set_float_field(&obj, "evel", sac.evel)?;
        self.set_float_field(&obj, "evdp", sac.evdp)?;
        self.set_float_field(&obj, "mag", sac.mag)?;
        self.set_float_array_field(&obj, "user", &sac.user)?;
        self.set_float_field(&obj, "dist", sac.dist)?;
        self.set_float_field(&obj, "az", sac.az)?;
        self.set_float_field(&obj, "baz", sac.baz)?;
        self.set_float_field(&obj, "gcarc", sac.gcarc)?;
        self.set_float_field(&obj, "depmen", sac.depmen)?;
        self.set_float_field(&obj, "cmpaz", sac.cmpaz)?;
        self.set_float_field(&obj, "cmpinc", sac.cmpinc)?;
        self.set_float_field(&obj, "xminimum", sac.xminimum)?;
        self.set_float_field(&obj, "xmaximum", sac.xmaximum)?;
        self.set_float_field(&obj, "yminimum", sac.yminimum)?;
        self.set_float_field(&obj, "ymaximum", sac.ymaximum)?;
        self.set_int_field(&obj, "nzyear", sac.nzyear)?;
        self.set_int_field(&obj, "nzjday", sac.nzjday)?;
        self.set_int_field(&obj, "nzhour", sac.nzhour)?;
        self.set_int_field(&obj, "nzmin", sac.nzmin)?;
        self.set_int_field(&obj, "nzsec", sac.nzsec)?;
        self.set_int_field(&obj, "nzmsec", sac.nzmsec)?;
        self.set_int_field(&obj, "nvhdr", sac.nvhdr)?;
        self.set_int_field(&obj, "norid", sac.norid)?;
        self.set_int_field(&obj, "nevid", sac.nevid)?;
        self.set_int_field(&obj, "npts", sac.npts)?;
        self.set_int_field(&obj, "nwfid", sac.nwfid)?;
        self.set_int_field(&obj, "nxsize", sac.nxsize)?;
        self.set_int_field(&obj, "nysize", sac.nysize)?;
        self.set_int_field(&obj, "iftype", sac.iftype.into())?;
        self.set_int_field(&obj, "idep", sac.idep)?;
        self.set_int_field(&obj, "iztype", sac.iztype)?;
        self.set_int_field(&obj, "iinst", sac.iinst)?;
        self.set_int_field(&obj, "istreg", sac.istreg)?;
        self.set_int_field(&obj, "ievreg", sac.ievreg)?;
        self.set_int_field(&obj, "ievtyp", sac.ievtyp)?;
        self.set_int_field(&obj, "iqual", sac.iqual)?;
        self.set_int_field(&obj, "isynth", sac.isynth)?;
        self.set_int_field(&obj, "imagtyp", sac.imagtyp)?;
        self.set_int_field(&obj, "imagsrc", sac.imagsrc)?;
        self.set_boolean_field(&obj, "leven", sac.leven)?;
        self.set_boolean_field(&obj, "lpspol", sac.lpspol)?;
        self.set_boolean_field(&obj, "lovrok", sac.lovrok)?;
        self.set_boolean_field(&obj, "lcalda", sac.lcalda)?;
        self.set_string_field(&obj, "kstnm", &sac.kstnm)?;
        self.set_string_field(&obj, "kevnm", &sac.kevnm)?;
        self.set_string_field(&obj, "khole", &sac.khole)?;
        self.set_string_field(&obj, "ko", &sac.ko)?;
        self.set_string_field(&obj, "ka", &sac.ka)?;
        self.set_string_array_field(&obj, "kt", &sac.kt)?;
        self.set_string_field(&obj, "kf", &sac.kf)?;
        self.set_string_field(&obj, "kuser0", &sac.kuser0)?;
        self.set_string_field(&obj, "kuser1", &sac.kuser1)?;
        self.set_string_field(&obj, "kuser2", &sac.kuser2)?;
        self.set_string_field(&obj, "kcmpnm", &sac.kcmpnm)?;
        self.set_string_field(&obj, "knetwk", &sac.knetwk)?;
        self.set_string_field(&obj, "kdatrd", &sac.kdatrd)?;
        self.set_string_field(&obj, "kinst", &sac.kinst)?;

        Ok(obj)
    }

    fn get_sac_header(&mut self, obj: &JObject) -> Result<SacHeader> {
        let h = SacHeader {
            delta: self.get_float_field(obj, "delta")?,
            depmin: self.get_float_field(obj, "depmin")?,
            depmax: self.get_float_field(obj, "depmax")?,
            scale: self.get_float_field(obj, "scale")?,
            odelta: self.get_float_field(obj, "odelta")?,
            b: self.get_float_field(obj, "b")?,
            e: self.get_float_field(obj, "e")?,
            o: self.get_float_field(obj, "o")?,
            a: self.get_float_field(obj, "a")?,
            t: self.get_float_array_field(obj, "t")?,
            f: self.get_float_field(obj, "f")?,
            resp: self.get_float_array_field(obj, "resp")?,
            stla: self.get_float_field(obj, "stla")?,
            stlo: self.get_float_field(obj, "stlo")?,
            stel: self.get_float_field(obj, "stel")?,
            stdp: self.get_float_field(obj, "stdp")?,
            evla: self.get_float_field(obj, "evla")?,
            evlo: self.get_float_field(obj, "evlo")?,
            evel: self.get_float_field(obj, "evel")?,
            evdp: self.get_float_field(obj, "evdp")?,
            mag: self.get_float_field(obj, "mag")?,
            user: self.get_float_array_field(obj, "user")?,
            dist: self.get_float_field(obj, "dist")?,
            az: self.get_float_field(obj, "az")?,
            baz: self.get_float_field(obj, "baz")?,
            gcarc: self.get_float_field(obj, "gcarc")?,
            depmen: self.get_float_field(obj, "depmen")?,
            cmpaz: self.get_float_field(obj, "cmpaz")?,
            cmpinc: self.get_float_field(obj, "cmpinc")?,
            xminimum: self.get_float_field(obj, "xminimum")?,
            xmaximum: self.get_float_field(obj, "xmaximum")?,
            yminimum: self.get_float_field(obj, "yminimum")?,
            ymaximum: self.get_float_field(obj, "ymaximum")?,
            nzyear: self.get_int_field(obj, "nzyear")?,
            nzjday: self.get_int_field(obj, "nzjday")?,
            nzhour: self.get_int_field(obj, "nzhour")?,
            nzmin: self.get_int_field(obj, "nzmin")?,
            nzsec: self.get_int_field(obj, "nzsec")?,
            nzmsec: self.get_int_field(obj, "nzmsec")?,
            nvhdr: self.get_int_field(obj, "nvhdr")?,
            norid: self.get_int_field(obj, "norid")?,
            nevid: self.get_int_field(obj, "nevid")?,
            npts: self.get_int_field(obj, "npts")?,
            nwfid: self.get_int_field(obj, "nwfid")?,
            nxsize: self.get_int_field(obj, "nxsize")?,
            nysize: self.get_int_field(obj, "nysize")?,
            iftype: self.get_int_field(obj, "iftype")?.into(),
            idep: self.get_int_field(obj, "idep")?,
            iztype: self.get_int_field(obj, "iztype")?,
            iinst: self.get_int_field(obj, "iinst")?,
            istreg: self.get_int_field(obj, "istreg")?,
            ievreg: self.get_int_field(obj, "ievreg")?,
            ievtyp: self.get_int_field(obj, "ievtyp")?,
            iqual: self.get_int_field(obj, "iqual")?,
            isynth: self.get_int_field(obj, "isynth")?,
            imagtyp: self.get_int_field(obj, "imagtyp")?,
            imagsrc: self.get_int_field(obj, "imagsrc")?,
            leven: self.get_boolean_field(obj, "leven")?,
            lpspol: self.get_boolean_field(obj, "lpspol")?,
            lovrok: self.get_boolean_field(obj, "lovrok")?,
            lcalda: self.get_boolean_field(obj, "lcalda")?,
            kstnm: self.get_string_field(obj, "kstnm")?,
            kevnm: self.get_string_field(obj, "kevnm")?,
            khole: self.get_string_field(obj, "khole")?,
            ko: self.get_string_field(obj, "ko")?,
            ka: self.get_string_field(obj, "ka")?,
            kt: self.get_string_array_field(obj, "kt")?,
            kf: self.get_string_field(obj, "kf")?,
            kuser0: self.get_string_field(obj, "kuser0")?,
            kuser1: self.get_string_field(obj, "kuser1")?,
            kuser2: self.get_string_field(obj, "kuser2")?,
            kcmpnm: self.get_string_field(obj, "kcmpnm")?,
            knetwk: self.get_string_field(obj, "knetwk")?,
            kdatrd: self.get_string_field(obj, "kdatrd")?,
            kinst: self.get_string_field(obj, "kinst")?,
        };

        Ok(h)
    }
}
