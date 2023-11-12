use std::path::Path;

use jni::objects::{JClass, JFloatArray, JObject, JString};
use jni::sys::{jfloat, jint, jlong, jsize};
use jni::JNIEnv;
use libsac::{Sac, SacError};

use crate::impl_helper::JNIEnvHelper;

mod impl_ext;
mod impl_helper;

fn get_path(env: &mut JNIEnv, path: &JString) -> String {
    match env.get_string(&path) {
        Ok(path) => path.into(),
        Err(err) => {
            env.throw_new("java/lang/IllegalArgumentException", err.to_string())
                .unwrap();

            String::default()
        }
    }
}

fn read<F>(env: &mut JNIEnv, read: F) -> jlong
where
    F: Fn() -> Result<Sac, SacError>,
{
    match read() {
        Ok(v) => Box::into_raw(Box::new(v)) as jlong,
        Err(err) => {
            env.throw_new("java/io/IOException", err.to_string())
                .unwrap();

            jlong::default()
        }
    }
}

fn write<F>(env: &mut JNIEnv, write: F)
where
    F: Fn() -> Result<(), SacError>,
{
    match write() {
        Ok(_) => {}
        Err(err) => {
            env.throw_new("java/io/IOException", err.to_string())
                .unwrap();
        }
    }
}

fn new_float_array<'a>(env: &mut JNIEnv<'a>, length: jsize) -> JFloatArray<'a> {
    match env.new_float_array(length) {
        Ok(array) => array,
        Err(err) => {
            env.throw_new("java/lang/RuntimeException", err.to_string())
                .unwrap();

            JFloatArray::default()
        }
    }
}

fn set_float_array(env: &mut JNIEnv, array: &JFloatArray, buf: &[jfloat]) {
    match env.set_float_array_region(array, 0, buf) {
        Ok(_) => {}
        Err(err) => {
            env.throw_new("java/lang/RuntimeException", err.to_string())
                .unwrap();
        }
    }
}

fn get_float_array(env: &mut JNIEnv, array: &JFloatArray, buf: &mut [jfloat]) {
    match env.get_float_array_region(array, 0, buf) {
        Ok(_) => {}
        Err(err) => {
            env.throw_new("java/lang/RuntimeException", err.to_string())
                .unwrap();
        }
    }
}

#[no_mangle]
pub extern "system" fn Java_dev_sanmer_sac_io_Sac_readHeader(
    mut env: JNIEnv,
    _class: JClass,
    path: JString,
    endian: jint,
) -> jlong {
    let path = get_path(&mut env, &path);
    let path = Path::new(&path);
    let endian = env.get_sac_endian(endian);

    read(&mut env, || Sac::read_header(path, endian))
}

#[no_mangle]
pub extern "system" fn Java_dev_sanmer_sac_io_Sac_read(
    mut env: JNIEnv,
    _class: JClass,
    path: JString,
    endian: jint,
) -> jlong {
    let path = get_path(&mut env, &path);
    let path = Path::new(&path);
    let endian = env.get_sac_endian(endian);

    read(&mut env, || Sac::read(path, endian))
}

#[no_mangle]
pub extern "system" fn Java_dev_sanmer_sac_io_Sac_empty(
    mut env: JNIEnv,
    _class: JClass,
    path: JString,
    endian: jint,
) -> jlong {
    let path = get_path(&mut env, &path);
    let path = Path::new(&path);
    let endian = env.get_sac_endian(endian);

    let sac = Sac::new(path, endian);
    Box::into_raw(Box::new(sac)) as jlong
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_writeHeader(
    mut env: JNIEnv,
    _class: JClass,
    ptr: jlong,
) {
    let sac = &*(ptr as *mut Sac);
    write(&mut env, || sac.write_header());
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_write(
    mut env: JNIEnv,
    _class: JClass,
    ptr: jlong,
) {
    let sac = &*(ptr as *mut Sac);
    write(&mut env, || sac.write());
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_writeTo(
    mut env: JNIEnv,
    _class: JClass,
    ptr: jlong,
    path: JString,
) {
    let path = get_path(&mut env, &path);
    let path = Path::new(&path);

    let sac = &*(ptr as *mut Sac);
    write(&mut env, || sac.write_to(path));
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_getHeader<'local>(
    mut env: JNIEnv<'local>,
    _class: JClass,
    ptr: jlong,
) -> JObject<'local> {
    let sac = &*(ptr as *mut Sac);
    match env.new_sac_header(sac) {
        Ok(obj) => obj,
        Err(err) => {
            env.throw_new("java/lang/IllegalArgumentException", err.to_string())
                .unwrap();

            JObject::null()
        }
    }
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_setHeader(
    mut env: JNIEnv,
    _class: JClass,
    ptr: jlong,
    header: JObject,
) {
    let sac = &mut *(ptr as *mut Sac);
    match env.get_sac_header(&header) {
        Ok(h) => sac.set_header(h),
        Err(err) => {
            env.throw_new("java/lang/IllegalArgumentException", err.to_string())
                .unwrap();
        }
    }
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_setEndian(
    env: JNIEnv,
    _class: JClass,
    ptr: jlong,
    endian: jint,
) {
    let endian = env.get_sac_endian(endian);

    let sac = &mut *(ptr as *mut Sac);
    sac.set_endian(endian);
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_drop(
    _env: JNIEnv,
    _class: JClass,
    ptr: jlong,
) {
    let sac = Box::from_raw(ptr as *mut Sac);
    drop(sac);
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_getFirst<'local>(
    mut env: JNIEnv<'local>,
    _obj: JObject,
    ptr: jlong,
) -> JFloatArray<'local> {
    let sac = &*(ptr as *mut Sac);

    let array = new_float_array(&mut env, sac.first.len() as jsize);
    set_float_array(&mut env, &array, &sac.first);

    array
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_setFirst(
    mut env: JNIEnv,
    _obj: JObject,
    ptr: jlong,
    array: JFloatArray,
) {
    let sac = &mut *(ptr as *mut Sac);
    get_float_array(&mut env, &array, &mut sac.first);
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_getSecond<'local>(
    mut env: JNIEnv<'local>,
    _obj: JObject,
    ptr: jlong,
) -> JFloatArray<'local> {
    let sac = &*(ptr as *mut Sac);

    let array = new_float_array(&mut env, sac.second.len() as jsize);
    set_float_array(&mut env, &array, &sac.second);

    array
}

#[no_mangle]
pub unsafe extern "system" fn Java_dev_sanmer_sac_io_Sac_setSecond(
    mut env: JNIEnv,
    _obj: JObject,
    ptr: jlong,
    array: JFloatArray,
) {
    let sac = &mut *(ptr as *mut Sac);
    get_float_array(&mut env, &array, &mut sac.second);
}
