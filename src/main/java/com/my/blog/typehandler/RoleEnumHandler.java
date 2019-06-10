package com.my.blog.typehandler;

import com.my.blog.enumeration.RoleEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author： Dong
 * @Description:
 * @Date: Created in 20:35 2019/5/16
 * @Modified By:
 */
public class RoleEnumHandler extends BaseTypeHandler<RoleEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, RoleEnum roleEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,roleEnum.getRoleCode());
    }

    @Override
    public RoleEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int code = resultSet.getInt(s);
        if (code >= 0 && code <= 1) {
            return RoleEnum.getRoleEnumByCode(code);
        }
        return null;
    }

    @Override
    public RoleEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        if (code >= 0 && code <= 1) {
            return RoleEnum.getRoleEnumByCode(code);
        }
        return null;
    }

    @Override
    public RoleEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        if (code >= 0 && code <= 1) {
            return RoleEnum.getRoleEnumByCode(code);
        }
        return null;
    }
}
