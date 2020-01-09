package com.example.demo.rowmapper;

import com.example.demo.utils.Tool;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
 * @author YM
 * @date 2019/11/25 14:49
 */
public class BaseRowMapper<T> implements RowMapper<T> {
    private Class<?> targetClazz;

    private HashMap<String, Field> fieldMap;


    /**
     * 构造函数.
     *
     * @param targetClazz .
     */
    public BaseRowMapper(Class<?> targetClazz) {
        this.targetClazz = targetClazz;
        fieldMap = new HashMap<>();
        Field[] fields = targetClazz.getDeclaredFields();
        for (Field field : fields) {
            // 同时存入大小写，如果表中列名区分大小写且有列ID和列iD，则会出现异常。
            // 阿里开发公约，建议表名、字段名必须使用小写字母或数字；禁止出现数字开头，禁止两个下划线中间只出现数字。
            fieldMap.put(field.getName(), field);
            // fieldMap.put(getFieldNameUpper(field.getName()), field);
        }
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T obj = null;
        String columnName = null;
        String fieldName = null;

        try {
            obj = (T) targetClazz.newInstance();

            ResultSetMetaData metaData = rs.getMetaData();

            int columnLength = metaData.getColumnCount();

            for (int i = 1; i <= columnLength; i++) {
                columnName = metaData.getColumnLabel(i);
                fieldName = Tool.lineToHump(columnName.toLowerCase());
                Class fieldClazz = fieldMap.get(fieldName).getType();
                Field field = fieldMap.get(fieldName);
                field.setAccessible(true);
//                if (ThreadLoaclUtil.databaseTypeTheardLocal.get().equals(DatabaseType.mysql.label())) {
//                    mapToMysql(field, fieldClazz, obj, rs, columnName);
//                }
//                if (ThreadLoaclUtil.databaseTypeTheardLocal.get().equals(DatabaseType.oracle.label())) {
//                    mapToOracle(field, fieldClazz, obj, rs, columnName);
//                }
                mapToMysql(field, fieldClazz, obj, rs, columnName);
                field.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(columnName);
            System.out.println(fieldName);
        }
        return obj;
    }


    private void mapToMysql(Field field, Class fieldClazz, Object obj, ResultSet rs, String columnName) throws SQLException, IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (fieldClazz == int.class || fieldClazz == Integer.class) {
            field.set(obj, rs.getInt(columnName));
        } else if (fieldClazz == boolean.class || fieldClazz == Boolean.class) {
            field.set(obj, rs.getBoolean(columnName));
        } else if (fieldClazz == String.class) {
            field.set(obj, rs.getString(columnName));
        } else if (fieldClazz == float.class) {
            field.set(obj, rs.getFloat(columnName));
        } else if (fieldClazz == double.class || fieldClazz == Double.class) {
            field.set(obj, rs.getDouble(columnName));
        } else if (fieldClazz == BigDecimal.class) {
            field.set(obj, rs.getBigDecimal(columnName));
        } else if (fieldClazz == short.class || fieldClazz == Short.class) {
            field.set(obj, rs.getShort(columnName));
        } else if (fieldClazz == Date.class) {
            field.set(obj, new Date(rs.getTimestamp(columnName).getTime()));
        } else if (fieldClazz == Timestamp.class) {
            field.set(obj, new Date(rs.getTimestamp(columnName).getTime()));
        } else if (fieldClazz == Long.class || fieldClazz == long.class) {
            field.set(obj, rs.getLong(columnName));
        } else if (fieldClazz.isEnum()) {
            enumField(field, fieldClazz, obj, rs, columnName);
        }
    }


    private void enumField(Field field, Class fieldClazz, Object obj, ResultSet rs, String columnName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {

        Enum[] enumConstant = (Enum[]) fieldClazz.getEnumConstants();
        //根据方法名获取方法
        Method origin = fieldClazz.getMethod("origin");
        for (Enum en : enumConstant) {
            //执行方法
            Integer a = (Integer) origin.invoke(en);
            if (a == rs.getInt(columnName)) {
                field.set(obj, en);
                break;
            }
        }
    }
}
