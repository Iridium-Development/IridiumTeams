package com.iridium.iridiumteams.database.types;

import com.cryptomorin.xseries.XEntityType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

import java.sql.SQLException;
import java.util.Optional;

public class XEntityTypeType extends StringType {

    private static final XEntityTypeType instance = new XEntityTypeType();

    public static XEntityTypeType getSingleton() {
        return instance;
    }

    protected XEntityTypeType() {
        super(SqlType.STRING, new Class<?>[] { XEntityType.class });
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        String value = (String) super.sqlArgToJava(fieldType, sqlArg, columnPos);
        Optional<XEntityType> entity =  XEntityType.of(value);
        return entity.orElse(null);
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) throws SQLException {
        XEntityType entityType = (XEntityType) object;
        return super.javaToSqlArg(fieldType, entityType.toString());
    }
    
}
