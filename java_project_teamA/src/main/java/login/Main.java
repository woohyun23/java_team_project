package login;

import services.LoginService;
import models.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoginService loginService = new LoginService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 로그인 시스템 ===");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.print("선택: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        switch (choice) {
            case 1:
                // 로그인 기능
                System.out.print("아이디: ");
                String username = scanner.nextLine();

                System.out.print("비밀번호: ");
                String password = scanner.nextLine();

                boolean isAuthenticated = loginService.authenticate(username, password);

                if (isAuthenticated) {
                    System.out.println("로그인 성공! 환영합니다, " + username + "님.");
                } else {
                    System.out.println("로그인 실패! 아이디 또는 비밀번호를 확인하세요.");
                }
                break;

            case 2:
                // 회원가입 기능
                System.out.print("새 아이디: ");
                String newUsername = scanner.nextLine();

                System.out.print("새 비밀번호: ");
                String newPassword = scanner.nextLine();

                User newUser = new User(newUsername, newPassword);
                boolean isRegistered = loginService.register(newUser);

                if (isRegistered) {
                    System.out.println("회원가입 성공! 이제 로그인할 수 있습니다.");
                } else {
                    System.out.println("회원가입 실패! 이미 존재하는 아이디입니다.");
                }
                break;

            default:
                System.out.println("잘못된 선택입니다. 프로그램을 종료합니다.");
        }

        scanner.close();
    }
}

