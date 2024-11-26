package courseregistration;

import courseregistration.services.LoginService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoginService loginService = new LoginService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. 로그인");
            System.out.println("2. 로그아웃");
            System.out.println("3. 활성 사용자 보기");
            System.out.println("4. 종료");
            System.out.print("메뉴 선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1: // 로그인
                    System.out.print("닉네임 입력: ");
                    String loginNickname = scanner.nextLine();
                    String loginResult = loginService.login(loginNickname);
                    System.out.println(loginResult);
                    break;

                case 2: // 로그아웃
                    System.out.print("닉네임 입력: ");
                    String logoutNickname = scanner.nextLine();
                    String logoutResult = loginService.logout(logoutNickname);
                    System.out.println(logoutResult);
                    break;

                case 3: // 활성 사용자 보기 (테스트용)
                    System.out.println("현재 활성 사용자: " + loginService.getActiveUsers().keySet());
                    break;

                case 4: // 종료
                    System.out.println("프로그램 종료");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("올바른 메뉴를 선택하세요.");
            }
        }
    }
}
