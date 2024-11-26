package courseregistration.services;

import courseregistration.db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class LoginService {

    private Set<String> activeUsers = new HashSet<>(); // 활성 사용자 목록

    public boolean login(String nickname) {
        try (Connection connection = DatabaseManager.getConnection()) {
            // 사용자 삽입 시도
            String query = "INSERT INTO users (nickname) VALUES (?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, nickname);
                ps.executeUpdate();
            }
            activeUsers.add(nickname); // 로그인 성공 시 활성 사용자 목록에 추가
            return true;
        } catch (SQLException e) {
            // 닉네임 중복으로 실패 시
            System.out.println("이미 로그인 중인 닉네임입니다.");
            return false;
        }
    }

    public boolean logout(String nickname) {
        try (Connection connection = DatabaseManager.getConnection()) {
            // 사용자 삭제
            String query = "DELETE FROM users WHERE nickname = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, nickname);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    activeUsers.remove(nickname); // 로그아웃 시 활성 사용자 목록에서 제거
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Set<String> getActiveUsers() {
        return activeUsers; // 활성 사용자 목록 반환
    }
}
