package com.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

/**
 * 身份证工具类
 * @author tomtop2149
 */
public class IdCardUtils {
    private static final ResourceBundle IdCard_Address = ResourceBundle.getBundle("config/idCardAddress");
    /** 中国公民身份证号码最小长度。 */
    public static final int CHINA_ID_MIN_LENGTH = 15;
    /** 中国公民身份证号码最大长度。 */
    public static final int CHINA_ID_MAX_LENGTH = 18;
    /** 省、直辖市代码表 */
    public static final String cityCode[] = {
            "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41",
            "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71",
            "81", "82", "91"
    };

    /** 每位加权因子 */
    public static final int power[] = {
            7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };

    /** 第18位校检码 */
    public static final String verifyCode[] = {
            "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"
    };
    /** 最低年限 */
    public static final int MIN = 1930;
    public static Map<String, String> provinceCodes = new HashMap<String, String>();
    /** 台湾身份首字母对应数字 */
    public static Map<String, Integer> twFirstCode = new HashMap<String, Integer>();
    /** 香港身份首字母对应数字 */
    public static Map<String, Integer> hkFirstCode = new HashMap<String, Integer>();
    static {
        provinceCodes.put("11", "北京市");
        provinceCodes.put("12", "天津市");
        provinceCodes.put("13", "河北省");
        provinceCodes.put("14", "山西省");
        provinceCodes.put("15", "内蒙古");
        provinceCodes.put("21", "辽宁省");
        provinceCodes.put("22", "吉林省");
        provinceCodes.put("23", "黑龙江省");
        provinceCodes.put("31", "上海市");
        provinceCodes.put("32", "江苏省");
        provinceCodes.put("33", "浙江省");
        provinceCodes.put("34", "安徽省");
        provinceCodes.put("35", "福建省");
        provinceCodes.put("36", "江西省");
        provinceCodes.put("37", "山东省");
        provinceCodes.put("41", "河南省");
        provinceCodes.put("42", "湖北省");
        provinceCodes.put("43", "湖南省");
        provinceCodes.put("44", "广东省");
        provinceCodes.put("45", "广西");
        provinceCodes.put("46", "海南省");
        provinceCodes.put("50", "重庆市");
        provinceCodes.put("51", "四川省");
        provinceCodes.put("52", "贵州省");
        provinceCodes.put("53", "云南省");
        provinceCodes.put("54", "西藏");
        provinceCodes.put("61", "陕西省");
        provinceCodes.put("62", "甘肃省");
        provinceCodes.put("63", "青海省");
        provinceCodes.put("64", "宁夏");
        provinceCodes.put("65", "新疆");
        provinceCodes.put("71", "台湾");
        provinceCodes.put("81", "香港");
        provinceCodes.put("82", "澳门");
        provinceCodes.put("91", "国外");
        twFirstCode.put("A", 10);
        twFirstCode.put("B", 11);
        twFirstCode.put("C", 12);
        twFirstCode.put("D", 13);
        twFirstCode.put("E", 14);
        twFirstCode.put("F", 15);
        twFirstCode.put("G", 16);
        twFirstCode.put("H", 17);
        twFirstCode.put("J", 18);
        twFirstCode.put("K", 19);
        twFirstCode.put("L", 20);
        twFirstCode.put("M", 21);
        twFirstCode.put("N", 22);
        twFirstCode.put("P", 23);
        twFirstCode.put("Q", 24);
        twFirstCode.put("R", 25);
        twFirstCode.put("S", 26);
        twFirstCode.put("T", 27);
        twFirstCode.put("U", 28);
        twFirstCode.put("V", 29);
        twFirstCode.put("X", 30);
        twFirstCode.put("Y", 31);
        twFirstCode.put("W", 32);
        twFirstCode.put("Z", 33);
        twFirstCode.put("I", 34);
        twFirstCode.put("O", 35);
        hkFirstCode.put("A", 1);
        hkFirstCode.put("B", 2);
        hkFirstCode.put("C", 3);
        hkFirstCode.put("R", 18);
        hkFirstCode.put("U", 21);
        hkFirstCode.put("Z", 26);
        hkFirstCode.put("X", 24);
        hkFirstCode.put("W", 23);
        hkFirstCode.put("O", 15);
        hkFirstCode.put("N", 14);
    }

    /**
     * 验证身份证是否合法
     */
    public static boolean validateCard(String idCard) {
        String card = idCard.trim();
        if (validateIdCard18(card)) {
            return true;
        }
        if (validateIdCard15(card)) {
            return true;
        }
        String[] cardval = validateIdCard10(card);
        if (cardval != null) {
            if (cardval[2].equals("true")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证18位身份编码是否合法
     *
     * @param idCard 身份编码
     * @return 是否合法
     */
    public static boolean validateIdCard18(String idCard) {
        boolean bTrue = false;
        if (idCard.length() == CHINA_ID_MAX_LENGTH) {
            // 前17位
            String code17 = idCard.substring(0, 17);
            // 第18位
            String code18 = idCard.substring(17, CHINA_ID_MAX_LENGTH);
            if (isNum(code17)) {
                char[] cArr = code17.toCharArray();
                if (cArr != null) {
                    int[] iCard = converCharToInt(cArr);
                    int iSum17 = getPowerSum(iCard);
                    // 获取校验位
                    String val = getCheckCode18(iSum17);
                    if (val.length() > 0) {
                        if (val.equalsIgnoreCase(code18)) {
                            bTrue = true;
                        }
                    }
                }
            }
        }
        return bTrue;
    }

    /**
     * 验证15位身份编码是否合法
     *
     * @param idCard
     *            身份编码
     * @return 是否合法
     */
    public static boolean validateIdCard15(String idCard) {
        if (idCard.length() != CHINA_ID_MIN_LENGTH) {
            return false;
        }
        if (isNum(idCard)) {
            String proCode = idCard.substring(0, 2);
            if (provinceCodes.get(proCode) == null) {
                return false;
            }
            String birthCode = idCard.substring(6, 12);
            Date birthDate = null;
            try {
                birthDate = new SimpleDateFormat("yy").parse(birthCode.substring(0, 2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            if (birthDate != null)
                cal.setTime(birthDate);
            if (!valiDate(cal.get(Calendar.YEAR), Integer.valueOf(birthCode.substring(2, 4)),
                    Integer.valueOf(birthCode.substring(4, 6)))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
    /**
     * 验证10位身份编码是否合法
     *
     * @param idCard 身份编码
     * @return 身份证信息数组
     *         <p>
     *         [0] - 台湾、澳门、香港 [1] - 性别(男M,女F,未知N) [2] - 是否合法(合法true,不合法false)
     *         若不是身份证件号码则返回null
     *         </p>
     */
    public static String[] validateIdCard10(String idCard) {
        String[] info = new String[3];
        String card = idCard.replaceAll("[\\(|\\)]", "");
        if (card.length() != 8 && card.length() != 9 && idCard.length() != 10) {
            return null;
        }
        if (idCard.matches("^[a-zA-Z][0-9]{9}{1}quot;")) {
            info[0] = "台湾";
            System.out.println("11111");
            String char2 = idCard.substring(1, 2);
            if (char2.equals("1")) {
                info[1] = "M";
                System.out.println("MMMMMMM");
            } else if (char2.equals("2")) {
                info[1] = "F";
                System.out.println("FFFFFFF");
            } else {
                info[1] = "N";
                info[2] = "false";
                System.out.println("NNNN");
                return info;
            }
            info[2] = validateTWCard(idCard) ? "true" : "false";
        } else if (idCard.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?{1}quot;")) { // 澳门
            info[0] = "澳门";
            info[1] = "N";
        } else if (idCard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?{1}quot;")) { // 香港
            info[0] = "香港";
            info[1] = "N";
            info[2] = validateHKCard(idCard) ? "true" : "false";
        } else {
            return null;
        }
        return info;
    }

    /**
     * 验证台湾身份证号码
     *
     * @param idCard
     *            身份证号码
     * @return 验证码是否符合
     */
    public static boolean validateTWCard(String idCard) {
        String start = idCard.substring(0, 1);
        String mid = idCard.substring(1, 9);
        String end = idCard.substring(9, 10);
        Integer iStart = twFirstCode.get(start);
        Integer sum = iStart / 10 + (iStart % 10) * 9;
        char[] chars = mid.toCharArray();
        Integer iflag = 8;
        for (char c : chars) {
            sum = sum + Integer.valueOf(c + "") * iflag;
            iflag--;
        }
        return (sum % 10 == 0 ? 0 : (10 - sum % 10)) == Integer.valueOf(end) ? true : false;
    }

    /**
     * 验证香港身份证号码(存在Bug，部份特殊身份证无法检查)
     * <p>
     * 身份证前2位为英文字符，如果只出现一个英文字符则表示第一位是空格，对应数字58 前2位英文字符A-Z分别对应数字10-35
     * 最后一位校验码为0-9的数字加上字符"A"，"A"代表10
     * </p>
     * <p>
     * 将身份证号码全部转换为数字，分别对应乘9-1相加的总和，整除11则证件号码有效
     * </p>
     *
     * @param idCard 身份证号码
     * @return 验证码是否符合
     */
    public static boolean validateHKCard(String idCard) {
        String card = idCard.replaceAll("[\\(|\\)]", "");
        Integer sum = 0;
        if (card.length() == 9) {
            sum = (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 9
                    + (Integer.valueOf(card.substring(1, 2).toUpperCase().toCharArray()[0]) - 55) * 8;
            card = card.substring(1, 9);
        } else {
            sum = 522 + (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 8;
        }
        String mid = card.substring(1, 7);
        String end = card.substring(7, 8);
        char[] chars = mid.toCharArray();
        Integer iflag = 7;
        for (char c : chars) {
            sum = sum + Integer.valueOf(c + "") * iflag;
            iflag--;
        }
        if (end.toUpperCase().equals("A")) {
            sum = sum + 10;
        } else {
            sum = sum + Integer.valueOf(end);
        }
        return (sum % 11 == 0) ? true : false;
    }

    /**
     * 将字符数组转换成数字数组
     *
     * @param ca
     *            字符数组
     * @return 数字数组
     */
    public static int[] converCharToInt(char[] ca) {
        int len = ca.length;
        int[] iArr = new int[len];
        try {
            for (int i = 0; i < len; i++) {
                iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return iArr;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param iArr
     * @return 身份证编码。
     */
    public static int getPowerSum(int[] iArr) {
        int iSum = 0;
        if (power.length == iArr.length) {
            for (int i = 0; i < iArr.length; i++) {
                for (int j = 0; j < power.length; j++) {
                    if (i == j) {
                        iSum = iSum + iArr[i] * power[j];
                    }
                }
            }
        }
        return iSum;
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     *
     * @param iSum
     * @return 校验位
     */
    public static String getCheckCode18(int iSum) {
        String sCode = "";
        switch (iSum % 11) {
            case 10:
                sCode = "2";
                break;
            case 9:
                sCode = "3";
                break;
            case 8:
                sCode = "4";
                break;
            case 7:
                sCode = "5";
                break;
            case 6:
                sCode = "6";
                break;
            case 5:
                sCode = "7";
                break;
            case 4:
                sCode = "8";
                break;
            case 3:
                sCode = "9";
                break;
            case 2:
                sCode = "x";
                break;
            case 1:
                sCode = "0";
                break;
            case 0:
                sCode = "1";
                break;
        }
        return sCode;
    }

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard  身份编号
     * @return 年龄
     */
    public static int getAgeByIdCard(String idCard) {
        int iAge = 0;
        if (idCard.length() == CHINA_ID_MIN_LENGTH) {
            idCard = conver15CardTo18(idCard);
        }
        String year = idCard.substring(6, 10);
        Calendar cal = Calendar.getInstance();
        int iCurrYear = cal.get(Calendar.YEAR);
        iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthByIdCard(String idCard) {
        Integer len = idCard.length();
        if (len < CHINA_ID_MIN_LENGTH) {
            return null;
        } else if (len == CHINA_ID_MIN_LENGTH) {
            idCard = conver15CardTo18(idCard);
        }
        return idCard.substring(6, 14);
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    public static Short getYearByIdCard(String idCard) {
        Integer len = idCard.length();
        if (len < CHINA_ID_MIN_LENGTH) {
            return null;
        } else if (len == CHINA_ID_MIN_LENGTH) {
            idCard = conver15CardTo18(idCard);
        }
        return Short.valueOf(idCard.substring(6, 10));
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard
     *            身份编号
     * @return 生日(MM)
     */
    public static Short getMonthByIdCard(String idCard) {
        Integer len = idCard.length();
        if (len < CHINA_ID_MIN_LENGTH) {
            return null;
        } else if (len == CHINA_ID_MIN_LENGTH) {
            idCard = conver15CardTo18(idCard);
        }
        return Short.valueOf(idCard.substring(10, 12));
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard
     *            身份编号
     * @return 生日(dd)
     */
    public static Short getDayByIdCard(String idCard) {
        Integer len = idCard.length();
        if (len < CHINA_ID_MIN_LENGTH) {
            return null;
        } else if (len == CHINA_ID_MIN_LENGTH) {
            idCard = conver15CardTo18(idCard);
        }
        return Short.valueOf(idCard.substring(12, 14));
    }
    /**
     * 根据身份编号获取性别
     * @param idCard 身份编号
     * @return 性别(男，女)
     */
    public static String getGenderByIdCard(String idCard) {
        String sGender = "男";
        if (idCard.length() == CHINA_ID_MIN_LENGTH) {
            idCard = conver15CardTo18(idCard);
        }
        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "男";
        } else {
            sGender = "女";
        }
        return sGender;
    }

    /**
     * 根据身份编号获取户籍省份
     *
     * @param idCard 身份编码
     * @return 省级编码。
     */
    public static String getProvinceByIdCard(String idCard) {
        int len = idCard.length();
        String sProvince = null;
        String sProvinNum = "";
        if (len == CHINA_ID_MIN_LENGTH || len == CHINA_ID_MAX_LENGTH) {
            sProvinNum = idCard.substring(0, 2);
        }
        sProvince = provinceCodes.get(sProvinNum);
        return sProvince;
    }

    /**
     * 数字验证
     *
     * @param val
     * @return 提取的数字。
     */
    public static boolean isNum(String val) {
        return val == null || "".equals(val) ? false : val.matches("^[0-9]*{1}quot;");
    }

    /**
     * 验证小于当前日期 是否有效
     *
     * @param iYear
     *            待验证日期(年)
     * @param iMonth
     *            待验证日期(月 1-12)
     * @param iDate
     *            待验证日期(日)
     * @return 是否有效
     */
    public static boolean valiDate(int iYear, int iMonth, int iDate) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int datePerMonth;
        if (iYear < MIN || iYear >= year) {
            return false;
        }
        if (iMonth < 1 || iMonth > 12) {
            return false;
        }
        switch (iMonth) {
            case 4:
            case 6:
            case 9:
            case 11:
                datePerMonth = 30;
                break;
            case 2:
                boolean dm = ((iYear % 4 == 0 && iYear % 100 != 0) || (iYear % 400 == 0))
                        && (iYear > MIN && iYear < year);
                datePerMonth = dm ? 29 : 28;
                break;
            default:
                datePerMonth = 31;
        }
        return (iDate >= 1) && (iDate <= datePerMonth);
    }

    /**
     * 根据身份编号获取户籍地址（省市区）
     * @param idCard 身份编码
     * @param idCard
     * @return 户籍地址（省市区）
     */
    public static String getAddressByIdCard(String idCard) {
        String key = idCard;
        try {
            int len = idCard.length();
            if (len == CHINA_ID_MIN_LENGTH || len == CHINA_ID_MAX_LENGTH) {
                key = idCard.substring(0, 6);
            }
            return IdCard_Address.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    /**
     * 将15位身份证号码转换为18位
     *
     * @param idCard 15位身份编码
     * @return 18位身份编码
     */
    public static String conver15CardTo18(String idCard) {
        String idCard18 = "";
        if (idCard.length() != CHINA_ID_MIN_LENGTH) {
            return null;
        }
        if (isNum(idCard)) {
            // 获取出生年月日
            String birthday = idCard.substring(6, 12);
            Date birthDate = null;
            try {
                birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            if (birthDate != null)
                cal.setTime(birthDate);
            // 获取出生年(完全表现形式,如：2010)
            String sYear = String.valueOf(cal.get(Calendar.YEAR));
            idCard18 = idCard.substring(0, 6) + sYear + idCard.substring(8);
            // 转换字符数组
            char[] cArr = idCard18.toCharArray();
            if (cArr != null) {
                int[] iCard = converCharToInt(cArr);
                int iSum17 = getPowerSum(iCard);
                // 获取校验位
                String sVal = getCheckCode18(iSum17);
                if (sVal.length() > 0) {
                    idCard18 += sVal;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
        return idCard18;
    }
    /**
     * TODO 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param imgFilePath
     * @return
     */
    public static String getImageStr(String imgFilePath) {
        byte[] data = null;
        InputStream in = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 对字节数组Base64编码
        return Base64.encodeBase64String(data);
    }

    /**
     * 将Base64编码字符串转化为图片文件
     *
     * @param imgFilePath
     *            图片文件path
     * @param data
     *            Base64编码字符串
     */
    public static void strToImg(String imgFilePath, String data) {
        try {
            System.out.println("..........imgFilePath : " + imgFilePath);
            byte[] b = Base64.decodeBase64(data.getBytes());
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            File file = new File(imgFilePath);
            File dir = file.getParentFile();
            if(!dir.exists()){ //目录不存就创建目录
                dir.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(b);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 马赛克化，打两行码
     * @param size  马赛克尺寸，即每个矩形的长宽
     * @param x
     * @param y
     * @param count 打码总数量
     * @param imgType
     * @param imgPath
     * @param saveImgPath
     * @return
     * @throws Exception
     */
    public static String mosaic(int size, int x, int y, int count, String imgType, String imgPath, String saveImgPath) throws Exception {
        File file = new File(imgPath);
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " 不是一个图片文件!");
        }
        BufferedImage spinImage = ImageIO.read(file); // 读取该图片
        int width = spinImage.getWidth();
        int height = spinImage.getHeight();
        System.out.println("img width:" + width + ",height:" + height);
        if (width < size || height < size || size <= 0) { // 马赛克格尺寸太大或太小
            return "马赛克格尺寸太大或太小";
        }
        // 绘制马赛克(绘制矩形并填充颜色)
        Graphics2D gs = spinImage.createGraphics();
        //马赛克矩形格大小
        int mwidth = size;
        int mheight = size;
        int tmpX = x;
        for (int j = 0; j < 2; j++) { //打两行码
            for (int i = 0; i < count; i++) {
                // 矩形颜色取中心像素点RGB值
                int centerX = x;
                int centerY = y;
                Color color = new Color(spinImage.getRGB(centerX, centerY));
                gs.setColor(color);
//        		gs.setColor(Color.gray);
                gs.fillRect(x, y, mwidth, mheight);
                x = x + mwidth;// 计算下一个矩形的y坐标
            }
            y = y + mwidth;
            x = tmpX;
        }
        gs.dispose();
        File sf = new File(saveImgPath);
        ImageIO.write(spinImage, imgType, sf); // 保存图片
        //BufferImage转Base64码
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(spinImage, imgType, outputStream);
        return Base64.encodeBase64String(outputStream.toByteArray());
    }
    /**
     * 添加文字
     * @param font 字体
     * @param x
     * @param y
     * @param text 文字
     * @param imgType 图片类型（如：jpg）
     * @param color 文字的颜色
     * @param srcImgPath 源图片路径
     * @param destImgPath 目标图片路径（最终保存的图片路径）
     * @return Base64编码字符串
     */
    public static String markImgText(Font font, int x, int y, String text,String imgType, Color color, String srcImgPath, String destImgPath){
        try {
            File file = new File(srcImgPath);
            if (!file.isFile()) {
                throw new Exception("srcImgPath>>>" + file + " 不是一个图片文件!");
            }
            BufferedImage spinImage = ImageIO.read(file); // 读取该图片
            Graphics2D gs = spinImage.createGraphics();
            //颜色
            gs.setColor(color);
            gs.setFont(font);//字体
            //印位置
            gs.drawString(text, x, y);
            gs.dispose();
            File sf = new File(destImgPath);
            ImageIO.write(spinImage, imgType, sf); // 保存图片
            //BufferImage转Base64码
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(spinImage, imgType, outputStream);
            return Base64.encodeBase64String(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 给图片添加单个图片水印、可设置水印图片旋转角度
     * @param iconData 水印图片Base64字符串
     * @param srcImgPath 源图片路径
     * @param destImgPath 目标图片路径（最后保存的路径）
     * @param targetWidth 缩放的目标宽度
     * @param x
     * @param y
     * @param imageType 图片类型（如：jpg）
     * @param degree 水印图片旋转角度，为null表示不旋转
     */
    public static Boolean markImgBySingleIcon(String iconData,int targetWidth,int x,int y,String srcImgPath,String destImgPath,String imageType,Integer degree) {
        try {
            File file = new File(srcImgPath);
            if (!file.isFile()) {
                return false;
            }
            byte[] b = Base64.decodeBase64(iconData.getBytes());
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            InputStream inputStream = new ByteArrayInputStream(b);
            //将icon加载到内存中
            BufferedImage ic = ImageIO.read(inputStream);
            /* 原始icon图像的宽度和高度 */
            int icWidth = ic.getWidth();
            int icHeight = ic.getHeight();
            //计算压缩比例 缩放图片
            float resizeTimes = (float)targetWidth / (float)icWidth;
            BigDecimal bd  = new BigDecimal((double)resizeTimes);
            // (1:小数点位数, 4:表示四舍五入，可以选择其他舍值方式，例如去尾等等.
            bd = bd.setScale(1 ,4);
            resizeTimes = bd.floatValue();
            /* 调整后的图片的宽度和高度 - 按照压缩比例计算出新的宽度和高度 */
            int toWidth = (int) (icWidth * resizeTimes);
            int toHeight = (int) (icHeight * resizeTimes);
            /* 新生成结果图片 */
            BufferedImage icBufferedImage = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
            icBufferedImage.getGraphics().drawImage( ic.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
            ic = icBufferedImage;
            //将源图片读到内存中
            Image img = ImageIO.read(file);
            //图片宽
            int width = img.getWidth(null);
            //图片高
            int height = img.getHeight(null);
            BufferedImage bi = ImageIO.read(file);
            //创建一个指定 BufferedImage 的 Graphics2D 对象
            Graphics2D g = bi.createGraphics();
            //设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            //呈现一个图像，在绘制前进行从图像空间到用户空间的转换
            g.drawImage(img.getScaledInstance(width,height,Image.SCALE_SMOOTH),0,0,null);
            if (null != degree) {
                //设置水印旋转
                g.rotate(Math.toRadians(degree),(double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
            }
            //透明度，最小值为0，最大值为1
            float clarity = 0.5f;//0.6f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,clarity));
            //表示水印图片的坐标位置(x,y)
            g.drawImage(ic, x, y, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            File sf = new File(destImgPath);
            ImageIO.write(bi, imageType, sf); // 保存图片
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public static void main(String[] args) {
        String idCard = "430923199311085211";
        String name = "王保成";//"王保成";//"韩黎光";//"刘勇";//"彭佳伟";
//		idCard = "650203199103092115"; //韩黎光
//		idCard = "430523199305078216";//彭佳伟
        idCard = "411522199206132134";//王保成
//		System.out.println(getAddressByIdCard(idCard));
//		System.out.println(getYearByIdCard(idCard));
//		System.out.println(getMonthByIdCard(idCard));
//		System.out.println(getDayByIdCard(idCard));
//		System.out.println(getGenderByIdCard(idCard));
        try {
            System.out.println("身份证");
            System.out.println("系统默认编码：" + System.getProperty("file.encoding")); //查询结果GBK
            System.out.println("系统默认字符编码：" + Charset.defaultCharset()); //查询结果GBK
            System.out.println("系统默认语言：" + System.getProperty("user.language")); //查询结果z
            System.out.println(getAddressByIdCard(idCard));
            System.out.println(new String(IdCardUtils.getAddressByIdCard(idCard).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            System.out.println(getYearByIdCard(idCard));
            System.out.println(getMonthByIdCard(idCard));
            System.out.println(getDayByIdCard(idCard));
            System.out.println(getGenderByIdCard(idCard));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

