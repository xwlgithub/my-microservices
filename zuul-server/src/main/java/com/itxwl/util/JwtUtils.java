package com.itxwl.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
@Data
@SuppressWarnings("all")
public class JwtUtils {
    //签名私钥
    private String key=ParamEveryUtil.SIGN_NAME;
    //过期时间
    //private Long ttl;
    public JwtUtils(){

    }
    public JwtUtils(String key){
        this.key=key;
       // this.ttl=ttl;
    }
    /**
     * 认证tocken
     * id:登录用户id
     * subject:登录用户名
     */
    public  String createJwt(String name, String password,Map<String,Object> map) {
        //设置失效时间
        //long now = System.currentTimeMillis();//当前毫秒
        //签名 xuewenliang
       // long exp = now + ttl;
        JwtBuilder builder = Jwts.builder().setId(name)
                .setSubject(password)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,key);
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            builder.claim(stringObjectEntry.getKey(),stringObjectEntry.getValue());
        }
        //设置失效时间
        //builder.setExpiration(new Date(exp));
        String tocken = builder.compact();
        return tocken;
    }
    /**
     * 解析tocken
     *
     */
    public Claims parseJwt(String tocken){

        Claims ihrm = Jwts.parser().setSigningKey(key).parseClaimsJws(tocken).getBody();
        return ihrm;
    }

    public static void main(String[] args) {
        Claims claims = new JwtUtils().parseJwt("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTc4NTU2MzU5ODYwNDUzMzc2Iiwic3ViIjoiVHVlIE1hciAxNyAxNTozOTo0OCBDU1QgMjAyMCIsImlhdCI6MTU4NDQzMDc4OCwi6Jab5paH6ImvIjoiMTIzNDU2In0.3NS92N1Ckn4yc5B5M5kG3PHbE7KgmpSDnbrLeHnRP2o");
        System.out.println(claims.getId());
        System.out.println(claims.getIssuer());
        System.out.println(claims.toString());
    }

}
