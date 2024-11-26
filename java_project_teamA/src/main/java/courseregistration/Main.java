package courseregistration;

import courseregistration.services.LoginService;
import courseregistration.services.RegistrationService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoginService loginService = new LoginService();
        RegistrationService registrationService = new RegistrationService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== 수강신청 서비스 ======");
            System.out.println("1. 로그인");
            System.out.println("2. 로그아웃");
            System.out.println("3. 활성 사용자 보기");
            System.out.println("4. 수강신청");
            System.out.println("5. 신청한 강의 목록 보기");
            System.out.println("6. 종료");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1: // 로그인
                    System.out.print("닉네임 입력: ");
                    String loginNickname = scanner.nextLine();
                    boolean loginResult = loginService.login(loginNickname);
                    System.out.println(loginResult ? "로그인 성공!" : "로그인 실패");
                    break;

                case 2: // 로그아웃
                    System.out.print("닉네임 입력: ");
                    String logoutNickname = scanner.nextLine();
                    boolean logoutResult = loginService.logout(logoutNickname);
                    System.out.println(logoutResult ? "로그아웃 성공!" : "로그아웃 실패");
                    break;

                case 3: // 활성 사용자 보기
                    System.out.println("현재 활성 사용자: " + loginService.getActiveUsers());
                    break;

                case 4: // 수강 신청
                    System.out.print("로그인 중인 닉네임 입력: ");
                    String courseNickname = scanner.nextLine();
                    System.out.print("신청할 강의 이름 입력: ");
                    String courseName = scanner.nextLine();
                    boolean registrationResult = registrationService.registerCourse(courseNickname, courseName);
                    if (registrationResult) {
                        System.out.println(courseName + " 강의 신청이 완료되었습니다.");
                    } else {
                        System.out.println("강의 신청 실패. 닉네임을 확인하세요.");
                    }
                    break;

                case 5: // 신청한 강의 목록 보기
                    System.out.print("로그인 중인 닉네임 입력: ");
                    String registeredNickname = scanner.nextLine();
                    System.out.println("신청한 강의 목록: " + registrationService.getRegisteredCourses(registeredNickname));
                    break;

                case 6: // 종료
                    System.out.println("프로그램 종료");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
            }
        }
    }
}
