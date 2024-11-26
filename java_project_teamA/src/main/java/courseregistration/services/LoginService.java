package courseregistration.services;

import java.util.HashMap;
import java.util.Map;

public class LoginService {
    // 메모리 기반 임시 저장소
    private final Map<String, String> activeUsers = new HashMap<>();

    /**
     * 로그인 메서드: 닉네임으로 로그인
     * @param nickname 사용자 닉네임
     * @return 성공 여부 메시지
     */
    public String login(String nickname) {
        if (activeUsers.containsKey(nickname)) {
            return "이미 사용 중인 닉네임입니다. 다른 닉네임을 선택하세요.";
        }
        activeUsers.put(nickname, "active"); // 상태를 active로 설정
        return "로그인 성공! 닉네임: " + nickname;
    }

    /**
     * 로그아웃 메서드: 닉네임으로 로그아웃
     * @param nickname 사용자 닉네임
     * @return 성공 여부 메시지
     */
    public String logout(String nickname) {
        if (!activeUsers.containsKey(nickname)) {
            return "로그아웃 실패: 닉네임이 존재하지 않습니다.";
        }
        activeUsers.remove(nickname); // 닉네임 삭제
        return "로그아웃 성공! 닉네임: " + nickname;
    }

    /**
     * 현재 활성 사용자 목록 확인 (테스트용)
     * @return 활성 사용자 목록
     */
    public Map<String, String> getActiveUsers() {
        return new HashMap<>(activeUsers);
    }
}
