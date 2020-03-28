package com.itxwl.shiroserver.entiry;

import com.itxwl.shiroserver.dto.PermisssCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
public class PermissionDto {
    private Set<PermisssCode> permisssCodes;
    private Set<String> points;
    private Set<String> apis;

    public PermissionDto() {

    }

    public PermissionDto(Set<PermisssCode> permisssCodes, Set<String> points, Set<String> apis) {
        this.permisssCodes = permisssCodes;
        this.points = points;
        this.apis = apis;
    }

}
