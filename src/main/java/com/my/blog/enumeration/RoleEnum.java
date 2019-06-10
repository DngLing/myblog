package com.my.blog.enumeration;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 20:27 2019/5/16
 * @Modified By:
 */
public enum  RoleEnum {
    USER(0,"普通用户");

    private int roleCode;
    private String roleName;

    RoleEnum(int roleCode, String roleName){
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public static RoleEnum getRoleEnumByCode(int code){
        for (RoleEnum roleEnum : values()){
            if(code == roleEnum.getRoleCode()){
                return roleEnum;
            }
        }
        return null;
    }

    public int getRoleCode() {
        return roleCode;
    }


    public String getRoleName() {
        return roleName;
    }}
