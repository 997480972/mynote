package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
 
public class MD5 {
 
    protected static final Logger logger = Logger.getLogger(MD5.class);
    private static final int DEFAULT_BUFFER_SIZE = 16 * 1024;
 
    public static String hashToBase64String(String data) {
        try {
            return hashToBase64String(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
 
    public static String hashToBase64String(byte[] data) {
        return Base64.encodeBase64String(hash(data));
    }
 
    public static String hashToHexString(String data) {
        try {
            return hashToHexString(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
 
    public static String hashToHexString(byte[] data) {
        return HexString.bytesToHexString(hash(data));
    }
 
    public static byte[] hash(String data) {
        try {
            return hash(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
 
    public static byte[] hash(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(data);
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
 
    public static String hashToBase64String(InputStream in) {
        return Base64.encodeBase64String(hash(in));
    }
 
    public static String hashToHexString(InputStream in) {
        return HexString.bytesToHexString(hash(in));
    }
 
    public static byte[] hash(InputStream in) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] data = new byte[DEFAULT_BUFFER_SIZE];
            while (in.read(data) != -1) {
                md.update(data);
            }
            return md.digest();
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
 
    public static String hashToBase64String(String data, byte[] salt) {
        try {
            return hashToBase64String(data.getBytes("UTF-8"), salt);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
 
    public static String hashToBase64String(byte[] data, byte[] salt) {
        return Base64.encodeBase64String(hash(data, salt));
    }
 
    public static String hashToHexString(String data, byte[] salt) {
        try {
            return hashToHexString(data.getBytes("UTF-8"), salt);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
 
    public static String hashToHexString(byte[] data, byte[] salt) {
        return HexString.bytesToHexString(hash(data, salt));
    }
 
    public static byte[] hash(String data, byte[] salt) {
        try {
            return hash(data.getBytes("UTF-8"), salt);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
 
    public static byte[] hash(byte[] data, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            md.update(salt);
            byte[] digest = md.digest();
            byte[] hash = new byte[digest.length + salt.length];
            System.arraycopy(digest, 0, hash, 0, digest.length);
            System.arraycopy(salt, 0, hash, digest.length, salt.length);
            return hash;
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }
 
    public static String hashToBase64String(InputStream in, byte[] salt) {
        return Base64.encodeBase64String(hash(in, salt));
    }
 
    public static String hashToHexString(InputStream in, byte[] salt) {
        return HexString.bytesToHexString(hash(in, salt));
    }
 
    public static byte[] hash(InputStream in, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] data = new byte[DEFAULT_BUFFER_SIZE];
            while (in.read(data) != -1) {
                md.update(data);
            }
            byte[] digest = md.digest();
            byte[] hash = new byte[digest.length + salt.length];
            System.arraycopy(digest, 0, hash, 0, digest.length);
            System.arraycopy(salt, 0, hash, digest.length, salt.length);
            return hash;
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return null;
    }
}
