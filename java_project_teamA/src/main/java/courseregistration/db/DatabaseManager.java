package courseregistration.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // JDBC URL, 사용자명, 비밀번호 설정
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = ""; // 비밀번호 없음

    // 데이터베이스 연결 반환
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // 데이터베이스 초기화 (테이블 생성)
    public static void initialize() {
        try (Connection conn = getConnection()) {
            // 수강 과목 테이블 생성
            conn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS courses (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(255), " +
                            "capacity INT)"
            );

            // 수강 신청 테이블 생성
            conn.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS registrations (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "volatile_id VARCHAR(255), " +
                            "course_id INT, " +
                            "FOREIGN KEY (course_id) REFERENCES courses(id))"
            );

            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
