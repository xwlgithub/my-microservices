package com.itxwl.getoutserver.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;
@Data
@ApiModel(value = "User实体",discriminator = "用户对象")
public class User {
    @ApiModelProperty(value = "id",example = "123")
    private Long id;
    @ApiModelProperty(value = "name",example = "123")
    private String name;
    @ApiModelProperty(value = "password",example = "123")
    private String password;
    @ApiModelProperty(value = "email",example = "123")
    private String email;
    @ApiModelProperty(value = "phoneNumber",example = "123")
    private BigInteger phoneNumber;
}
