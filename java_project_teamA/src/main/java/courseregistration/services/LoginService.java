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

    public void logoutAllActiveUsers() {
        try (Connection connection = DatabaseManager.getConnection()) {
            // 활성 사용자 목록에 있는 모든 닉네임 삭제
            String query = "DELETE FROM users WHERE nickname = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                for (String nickname : activeUsers) {
                    ps.setString(1, nickname);
                    ps.addBatch(); // Batch에 추가
                }
                ps.executeBatch(); // Batch 실행
            }
            // 활성 사용자 목록 초기화
            activeUsers.clear();
            System.out.println("모든 활성 사용자가 로그아웃되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("활성 사용자 로그아웃 처리 중 오류가 발생했습니다.");
        }
    }
}
