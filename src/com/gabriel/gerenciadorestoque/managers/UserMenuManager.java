package com.gabriel.gerenciadorestoque.managers;

import com.gabriel.gerenciadorestoque.dbconnection.User;
import com.gabriel.gerenciadorestoque.view.MenuServices;

import java.util.Scanner;

public class UserMenuManager {
    private final Scanner scanner = new Scanner(System.in);
    private User user;
    public void startProgram() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        int option;
        MenuServices.loginMenu();
        while(!exit) {
            System.out.print("Selecione uma opcao >>>> ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    loginHandler();
                    scanner.nextLine();
                    exit = true;
                    break;
                case 2:
                    registerHandler();
                    scanner.nextLine();
                    break;
                case 3:
                    scanner.close();
                    return;
                default:
                    System.out.println("Valor invalido");
                    System.out.print("Selecione uma opcao >>>> ");
                    option = scanner.nextInt();
                    break;
            }
        }
    }

    private void loginHandler() {
        String pw, email, response;
        while(true) {
            System.out.print("E-mail >>> ");
            email = scanner.nextLine();

            System.out.print("Senha >>> ");
            pw = scanner.nextLine();

            user = new User(email, pw);
            if(user.loginUser()) {
                user = new User(user.getId(), user.getName(), pw, email);
                System.out.println(user.getId());
                StockMenuManager stock = new StockMenuManager();
                stock.receiveUser(user);
                stock.menuStock();
                break;
            } else {
                System.out.print("Email ou senha invalidos deseja continuar? [S/N] >>>> ");
                response = scanner.nextLine().trim();
            }
            if(!response.equalsIgnoreCase("S")) break;
        }
    }

    private void registerHandler() {
        String name, pw, email, response;
        boolean exit = false;

        while(true) {
            System.out.print("Nome de usuario >>> ");
            name = scanner.nextLine();

            System.out.print("E-mail >>> ");
            email = scanner.nextLine();

            System.out.print("Senha >>> ");
            pw = scanner.nextLine();

            if (isValidEmail(email)) {
                user = new User(name, pw, email);
                if (user.addUser()) {
                    System.out.println("Conta criada com sucesso.");
                    break;
                } else {
                    System.out.print("E-mail ja existente deseja tentar novamente? [S/N] >>>> ");
                    response = scanner.nextLine();
                }
            } else {
                System.out.print("E-mail invalido deseja continuar? [S/N] >>>> ");
                response = scanner.nextLine().trim();
            }
            if (!response.equalsIgnoreCase("S")) break;
        }
    }

    private boolean isValidEmail(String email) {
        return (email.matches("^[^@]+@[^@]+\\.com$"));
    }

}
