package services;

import db.DatabaseConnection;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService {

    // 사용자를 인증하는 메서드
    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // 결과가 있으면 인증 성공

        } catch (Exception e) {
            System.out.println("로그인 오류: " + e.getMessage());
            return false;
        }
    }

    // 새 사용자 추가
    public boolean register(User user) {
        // 중복 아이디 검사
        if (isUsernameTaken(user.getUsername())) {
            System.out.println("중복된 아이디가 있습니다.");
            return false;
        }

        // 새 사용자 등록
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            int result = preparedStatement.executeUpdate();
            return result > 0; // 성공적으로 추가되면 true 반환

        } catch (Exception e) {
            System.out.println("사용자 등록 오류: " + e.getMessage());
            return false;
        }
    }

    // 아이디 중복 확인
    private boolean isUsernameTaken(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // 결과가 있으면 중복된 아이디

        } catch (Exception e) {
            System.out.println("중복 검사 오류: " + e.getMessage());
            return true; // 오류 시 중복된 것으로 간주
        }
    }
}
