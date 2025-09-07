package com.cyx.layer.starter;

import com.cyx.layer.controller.UserController;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {
        UserController userController = new UserController();
        Scanner scanner = new Scanner(System.in);
        /*System.out.println("请输入注册账号：");
        String username = scanner.next();
        System.out.println("请输入注册密码：");
        String password = scanner.next();
        String result = userController.register(username, password);
        System.out.println(result);*/

        System.out.println("请输入登录账号：");
        String loginUsername = scanner.next();
        System.out.println("请输入登录密码：");
        String loginPassword = scanner.next();
        String loginResult = userController.login(loginUsername, loginPassword);
        System.out.println(loginResult);
    }
}
