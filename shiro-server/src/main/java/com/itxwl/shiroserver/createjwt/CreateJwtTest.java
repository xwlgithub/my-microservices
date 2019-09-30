package com.itxwl.shiroserver.createjwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * 创建tocken字符串
 */
public class CreateJwtTest {
    public static void main(String[] args) {
//        JwtBuilder builder= Jwts.builder().setId("88")
//                .setSubject("小白")
//                .setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256,"ihrm")
//                .claim("username","root")
//                .claim("password","123456");
        JwtBuilder builder = Jwts.builder().setId("mark")
                .setSubject("123")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"xwl");
        //创建tocken
        String compact = builder.compact();
        System.out.println(compact);
    }
}
