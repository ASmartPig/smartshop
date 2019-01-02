package com.smart.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5Util {
     public static String md5Create(String password){
         String saltSource = "smartshop";
         String hashAlgorithmName = "MD5";
         int hashIterations = 1024;
         Object salt = new Md5Hash(saltSource);
         Object result = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
         return result.toString();
     }
}
