//package com.javaclimb.music.utils;
//
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTCreator;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.javaclimb.music.dto.TokeDto;
//import com.xiaoxi.internalcommon.dto.TokenResult;
//
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
//public class JwtUtils {
//
//    // 盐
//    private static final String SIGN = "CPFmsb!@#$$";
//
//    private static final String JWT_KEY_PHONE = "phone";
//
//    // 乘客是1，司机是2
//    private static final String JWT_KEY_IDENTITY = "identity";
//
//    // token类型
//    private static final String JWT_TOKEN_TYPE = "tokenType";
//
//    private static final String JWT_TOKEN_TIME = "tokenTime";
//
//    // 生成token
//    public static String generatorToken(String phone , String identity , String tokenType) {
//        Map<String,String> map = new HashMap<>();
//        map.put(JWT_KEY_PHONE,phone);
//        map.put(JWT_KEY_IDENTITY, identity);
//        map.put(JWT_TOKEN_TYPE, tokenType);
//        // 防止每次生成的token一样。
//        map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());
//
//        JWTCreator.Builder builder = JWT.create();
//        // 整合map
//        map.forEach(
//            (k,v) -> {
//                builder.withClaim(k,v);
//            }
//        );
//        // 整合过期时间
////        builder.withExpiresAt(date);
//
//        // 生成 token
//        String sign = builder.sign(Algorithm.HMAC256(SIGN));
//
//        return sign;
//    }
//
//
//    // 解析token
//    public static TokeDto parseToken(String token){
//        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
//        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
//        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
//
//        TokeDto tokenDto = new TokeDto();
//        tokenDto.setPhone(phone);
//        tokenDto.setIdentity(identity);
//        return tokenDto;
//
//    }
//
//    /**
//     * 校验token，主要判断token是否异常
//     * @param token
//     * @return
//     */
//    public static TokeDto checkToken(String token){
//        TokeDto tokenDto = null;
//        try {
//            tokenDto = JwtUtils.parseToken(token);
//        }catch (Exception e){
//
//        }
//        return tokenDto;
//    }
//
//    public static void main(String[] args) {
//
//        String s = generatorToken("13910733521" , "1" , "accessToken");
//        System.out.println("生成的token："+s);
//        System.out.println("解析-----------------");
//        TokeDto tokenDto = parseToken(s);
//        System.out.println("手机号："+tokenDto.getPhone());
//        System.out.println("身份："+tokenDto.getIdentity());
//    }
//}
