package com.iridium.iridiumteams.database.types;

import com.cryptomorin.xseries.XBiome;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

import java.sql.SQLException;
import java.util.Optional;

public class XBiomeType extends StringType {

    private static final XBiomeType instance = new XBiomeType();

    public static XBiomeType getSingleton() {
        return instance;
    }

    protected XBiomeType() {
        super(SqlType.STRING, new Class<?>[] { XBiome.class });
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        String value = (String) super.sqlArgToJava(fieldType, sqlArg, columnPos);
        Optional<XBiome> biome = XBiome.of(value);
        return biome.orElse(null);
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object object) throws SQLException {
        XBiome biome = (XBiome) object;
        return super.javaToSqlArg(fieldType, biome.toString());
    }
    
}
