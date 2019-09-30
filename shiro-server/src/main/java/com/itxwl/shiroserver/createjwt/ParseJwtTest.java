package com.itxwl.shiroserver.createjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.HashMap;
import java.util.Map;

public class ParseJwtTest {
    public static void main(String[] args) {
        String tocken="eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1Njc0OTU2NDh9.tAnzWB506vBpbB0cJO9UUAR3ZiIC7fNcf8d_OiwmdrU";
        Map<String, Object> map =new HashMap<>();
        Claims ihrm = Jwts.parser().setSigningKey("xuewenliang").parseClaimsJws(tocken).getBody();
        System.out.println(ihrm.getId());
        System.out.println(ihrm.getSubject());
        System.out.println(ihrm.getIssuedAt());



    }
}
