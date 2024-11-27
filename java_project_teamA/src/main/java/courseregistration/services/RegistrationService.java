package courseregistration.services;

import courseregistration.db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationService {

    public boolean registerCourse(String nickname, String courseName) {
        try (Connection connection = DatabaseManager.getConnection()) {
            // 사용자 ID 조회
            int userId = getUserId(connection, nickname);
            if (userId == -1) return false;

            // course_name을 이용해 course_id 조회
            int courseId = getCourseId(connection, courseName);
            if (courseId == -1) return false;

            // 강의 신청
            String query = "INSERT INTO registrations (user_id, course_id) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, userId);
                ps.setInt(2, courseId);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getRegisteredCourses(String nickname) {
        List<String> courses = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            // 사용자 ID 조회
            int userId = getUserId(connection, nickname);
            if (userId == -1) return courses;

            // 신청된 강의 조회 (course_id)
            String query = "SELECT course_id FROM registrations WHERE user_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int courseId = rs.getInt("course_id");
                        String courseName = getCourseName(connection, courseId);  // course_id로 course_name 조회
                        courses.add(courseName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private int getUserId(Connection connection, String nickname) throws SQLException {
        String query = "SELECT id FROM users WHERE nickname = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nickname);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1; // 사용자 없음
    }

    // course_name을 사용해 course_id 조회
    private int getCourseId(Connection connection, String courseName) throws SQLException {
        String query = "SELECT id FROM courses WHERE course_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, courseName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1; // 강의 없음
    }

    // course_id를 사용해 course_name 조회
    private String getCourseName(Connection connection, int courseId) throws SQLException {
        String query = "SELECT course_name FROM courses WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("course_name");
                }
            }
        }
        return null; // 강의명 없음
    }

    public List<String> getAvailableCourses() {
        List<String> courses = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT course_name FROM courses";
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    courses.add(rs.getString("course_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
