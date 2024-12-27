package com.iridium.iridiumteams.database.types;

import com.cryptomorin.xseries.XPotion;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

import java.sql.SQLException;
import java.util.Optional;

public class XPotionType extends StringType {

    private static final XPotionType instance = new XPotionType();

    public static XPotionType getSingleton() {
        return instance;
    }

    protected XPotionType() {
        super(SqlType.STRING, new Class<?>[] { XPotion.class });
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        String value = (String) super.sqlArgToJava(fieldType, sqlArg, columnPos);
        Optional<XPotion> potion = XPotion.of(value);
        return potion.orElse(null);
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) throws SQLException {
        XPotion potion = (XPotion) object;
        return super.javaToSqlArg(fieldType, potion.toString());
    }
    
}
