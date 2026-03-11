package com.skillup.skillup.util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilidad para exportar la base de datos de Railway a un archivo .sql local.
 * Útil cuando el mysqldump local es incompatible con MySQL 8+/9+.
 */
public class SqlExporter {

    public static void main(String[] args) {
        String host = "centerbeam.proxy.rlwy.net";
        String port = "11384";
        String user = "root";
        String pass = "wmHXIpVIEVOveSrVZORJoeQcuLkadHvr";
        String db = "railway";
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=false&allowPublicKeyRetrieval=true";
        String outputFile = "sql/skillup_railway_respaldo.sql";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Conexión exitosa a Railway. Iniciando exportación...");

            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                writer.println("-- SkillUp - Respaldo Automático de Base de Datos");
                writer.println("-- Generado el: " + new java.util.Date());
                writer.println("SET FOREIGN_KEY_CHECKS = 0;");
                writer.println();

                List<String> tables = new ArrayList<>();
                DatabaseMetaData metaData = conn.getMetaData();
                try (ResultSet rs = metaData.getTables(db, null, "%", new String[] { "TABLE" })) {
                    while (rs.next()) {
                        tables.add(rs.getString("TABLE_NAME"));
                    }
                }

                for (String table : tables) {
                    System.out.println("Exportando tabla: " + table);

                    // 1. Estructura (CREATE TABLE)
                    try (Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table)) {
                        if (rs.next()) {
                            writer.println("-- Estructura de la tabla " + table);
                            writer.println("DROP TABLE IF EXISTS `" + table + "`;");
                            writer.println(rs.getString(2) + ";");
                            writer.println();
                        }
                    }

                    // 2. Datos (INSERT)
                    try (Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table)) {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnCount = rsmd.getColumnCount();

                        while (rs.next()) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("INSERT INTO `").append(table).append("` VALUES (");
                            for (int i = 1; i <= columnCount; i++) {
                                Object value = rs.getObject(i);
                                if (value == null) {
                                    sb.append("NULL");
                                } else if (value instanceof Number) {
                                    sb.append(value);
                                } else {
                                    sb.append("'").append(value.toString().replace("'", "''")).append("'");
                                }
                                if (i < columnCount)
                                    sb.append(", ");
                            }
                            sb.append(");");
                            writer.println(sb.toString());
                        }
                    }
                    writer.println();
                }

                writer.println("SET FOREIGN_KEY_CHECKS = 1;");
                System.out.println("¡Exportación completada con éxito en: " + outputFile);
            }

        } catch (Exception e) {
            System.err.println("Error durante la exportación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
