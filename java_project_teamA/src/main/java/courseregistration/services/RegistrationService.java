package courseregistration.services;

import courseregistration.db.DatabaseManager;
import courseregistration.models.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RegistrationService {
    // 등록 가능한 모든 과목 가져오기
    public List<Course> getAvailableCourses() {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM courses")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("capacity")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    // 특정 휘발성 ID를 가진 사용자가 수강 신청
    public boolean registerCourse(String userId, int courseId) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO registrations (volatile_id, course_id) VALUES (?, ?)")) {

            stmt.setString(1, userId);
            stmt.setInt(2, courseId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
