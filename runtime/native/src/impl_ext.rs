use std::array;

use jni::errors::Result;
use jni::objects::{JFloatArray, JIntArray, JObject, JObjectArray, JString, JValue};
use jni::sys::{jfloat, jint, jlong, jsize};
use jni::JNIEnv;

pub trait JNIEnvExt {
    fn get_float_field(&mut self, obj: &JObject, name: &str) -> Result<jfloat>;

    fn set_float_field(&mut self, obj: &JObject, name: &str, val: jfloat) -> Result<()>;

    fn get_long_field(&mut self, obj: &JObject, name: &str) -> Result<jlong>;

    fn set_long_field(&mut self, obj: &JObject, name: &str, val: jlong) -> Result<()>;

    fn get_int_field(&mut self, obj: &JObject, name: &str) -> Result<jint>;

    fn set_int_field(&mut self, obj: &JObject, name: &str, val: jint) -> Result<()>;

    fn get_boolean_field(&mut self, obj: &JObject, name: &str) -> Result<bool>;

    fn set_boolean_field(&mut self, obj: &JObject, name: &str, val: bool) -> Result<()>;

    fn get_string_field(&mut self, obj: &JObject, name: &str) -> Result<String>;

    fn set_string_field(&mut self, obj: &JObject, name: &str, val: &String) -> Result<()>;

    fn get_float_array_field<const N: usize>(
        &mut self,
        obj: &JObject,
        name: &str,
    ) -> Result<[jfloat; N]>;

    fn set_float_array_field(&mut self, obj: &JObject, name: &str, vals: &[jfloat]) -> Result<()>;

    fn get_int_array_field<const N: usize>(
        &mut self,
        obj: &JObject,
        name: &str,
    ) -> Result<[jint; N]>;

    fn set_int_array_field(&mut self, obj: &JObject, name: &str, vals: &[jint]) -> Result<()>;

    fn get_string_array_field<const N: usize>(
        &mut self,
        obj: &JObject,
        name: &str,
    ) -> Result<[String; N]>;

    fn set_string_array_field(&mut self, obj: &JObject, name: &str, vals: &[String]) -> Result<()>;
}

impl<'local> JNIEnvExt for JNIEnv<'local> {
    fn get_float_field(&mut self, obj: &JObject, name: &str) -> Result<jfloat> {
        self.get_field(obj, name, "F")?.f()
    }

    fn set_float_field(&mut self, obj: &JObject, name: &str, val: jfloat) -> Result<()> {
        self.set_field(obj, name, "F", JValue::from(val))
    }

    fn get_long_field(&mut self, obj: &JObject, name: &str) -> Result<jlong> {
        self.get_field(obj, name, "J")?.j()
    }

    fn set_long_field(&mut self, obj: &JObject, name: &str, val: jlong) -> Result<()> {
        self.set_field(obj, name, "J", JValue::from(val))
    }

    fn get_int_field(&mut self, obj: &JObject, name: &str) -> Result<jint> {
        self.get_field(obj, name, "I")?.i()
    }

    fn set_int_field(&mut self, obj: &JObject, name: &str, val: jint) -> Result<()> {
        self.set_field(obj, name, "I", JValue::from(val))
    }

    fn get_boolean_field(&mut self, obj: &JObject, name: &str) -> Result<bool> {
        self.get_field(obj, name, "Z")?.z()
    }

    fn set_boolean_field(&mut self, obj: &JObject, name: &str, val: bool) -> Result<()> {
        self.set_field(obj, name, "Z", JValue::from(val))
    }

    fn get_string_field(&mut self, obj: &JObject, name: &str) -> Result<String> {
        let owned = self.get_field(obj, name, "Ljava/lang/String;")?;
        let obj = JString::from(owned.l()?);

        let value: String = self.get_string(&obj)?.into();

        Ok(value)
    }

    fn set_string_field(&mut self, obj: &JObject, name: &str, val: &String) -> Result<()> {
        let value = self.new_string(val)?;

        let value_obj = JObject::from(value);
        self.set_field(obj, name, "Ljava/lang/String;", JValue::from(&value_obj))
    }

    fn get_float_array_field<const N: usize>(
        &mut self,
        obj: &JObject,
        name: &str,
    ) -> Result<[jfloat; N]> {
        let owned = self.get_field(obj, name, "[F")?;
        let obj = JFloatArray::from(owned.l()?);

        let mut array = [0 as jfloat; N];
        self.get_float_array_region(obj, 0, &mut array)?;

        Ok(array)
    }

    fn set_float_array_field(&mut self, obj: &JObject, name: &str, vals: &[jfloat]) -> Result<()> {
        let float_array = self.new_float_array(vals.len() as jsize)?;
        self.set_float_array_region(&float_array, 0, vals)?;

        let array_obj = JObject::from(float_array);
        self.set_field(obj, name, "[F", JValue::from(&array_obj))
    }

    fn get_int_array_field<const N: usize>(
        &mut self,
        obj: &JObject,
        name: &str,
    ) -> Result<[jint; N]> {
        let owned = self.get_field(obj, name, "[I")?;
        let obj = JIntArray::from(owned.l()?);

        let mut array = [0 as jint; N];
        self.get_int_array_region(obj, 0, &mut array)?;

        Ok(array)
    }

    fn set_int_array_field(&mut self, obj: &JObject, name: &str, vals: &[jint]) -> Result<()> {
        let int_array = self.new_int_array(vals.len() as jsize)?;
        self.set_int_array_region(&int_array, 0, vals)?;

        let array_obj = JObject::from(int_array);
        self.set_field(obj, name, "[I", JValue::from(&array_obj))
    }

    fn get_string_array_field<const N: usize>(
        &mut self,
        obj: &JObject,
        name: &str,
    ) -> Result<[String; N]> {
        let owned = self.get_field(obj, name, "[Ljava/lang/String;")?;
        let obj = JObjectArray::from(owned.l()?);

        let mut array: [String; N] = array::from_fn(|_| String::new());
        let length = self.get_array_length(&obj)?.min(N as jsize);

        for index in 0..length {
            let element_obj = self.get_object_array_element(&obj, index)?;
            let element = JString::from(element_obj);
            let value: String = self.get_string(&element)?.into();
            array[index as usize] = value
        }

        Ok(array)
    }

    fn set_string_array_field(&mut self, obj: &JObject, name: &str, vals: &[String]) -> Result<()> {
        let element_class = self.find_class("java/lang/String")?;
        let string_array =
            self.new_object_array(vals.len() as jsize, element_class, JString::default())?;

        for (index, value) in vals.iter().enumerate() {
            let value = self.new_string(value)?;
            self.set_object_array_element(&string_array, index as jsize, value)?;
        }

        let array_obj = JObject::from(string_array);
        self.set_field(obj, name, "[Ljava/lang/String;", JValue::from(&array_obj))
    }
}
