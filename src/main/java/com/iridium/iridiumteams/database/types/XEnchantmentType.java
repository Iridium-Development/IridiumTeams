package com.iridium.iridiumteams.database.types;

import com.cryptomorin.xseries.XEnchantment;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

import java.sql.SQLException;
import java.util.Optional;

public class XEnchantmentType extends StringType {

    private static final XEnchantmentType instance = new XEnchantmentType();

    public static XEnchantmentType getSingleton() {
        return instance;
    }

    protected XEnchantmentType() {
        super(SqlType.STRING, new Class<?>[] { XEnchantment.class });
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        String value = (String) super.sqlArgToJava(fieldType, sqlArg, columnPos);
        Optional<XEnchantment> enchantment = XEnchantment.of(value);
        return enchantment.orElse(null);
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) throws SQLException {
        XEnchantment enchant = (XEnchantment) object;
        return super.javaToSqlArg(fieldType, enchant.toString());
    }
    
}
