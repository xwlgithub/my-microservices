package com.itxwl.shiroserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import java.util.Date;

@Data
@Configuration
public class JwtUtil {
    private static final String signName = "xuewenliang";

    /**
     * 生成认证tocken
     *
     * @param
     * @param
     * @return
     */
    public  String createTocken(String id, String time) {
        JwtBuilder builder = Jwts.builder().setId(id).setSubject(time).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, signName);
        String compact = builder.compact();
        return compact;
    }

    /**
     * 解析tocken
     *
     * @param tocken
     * @return
     */
    public  Claims analysis(String tocken) {
        Claims claims = Jwts.parser().setSigningKey(signName).parseClaimsJws(tocken).getBody();
        return claims;
    }

    public static void main(String[] args) {
        JwtBuilder builder = Jwts.builder().setId("1178556359860453376").setSubject(new Date().toString()).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "xuewenliang");
        String compact = builder.compact();
        System.out.println(compact);
        Claims xuewenliang = Jwts.parser().setSigningKey("xuewenliang").parseClaimsJws(compact).getBody();
        System.out.println(xuewenliang.getId());
        System.out.println(xuewenliang.getSubject());
        System.out.println(xuewenliang.getIssuedAt());
    }
}
