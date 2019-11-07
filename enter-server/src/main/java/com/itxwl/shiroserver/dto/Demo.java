package com.itxwl.shiroserver.dto;


public class Demo {
    public static void main(String[] args) {
        PermissDto permissDto=new PermissDto();
        permissDto.setId("sss");
        if (permissDto.getId().equals("sss")){
            permissDto.setId("zzz");
        }
        System.out.println(permissDto.getId());
    }
}
