package com.gabriel.gerenciadorestoque.managers;

import com.gabriel.gerenciadorestoque.dbconnection.Product;
import com.gabriel.gerenciadorestoque.dbconnection.User;
import com.gabriel.gerenciadorestoque.view.MenuServices;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class StockMenuManager {
    Scanner scanner = new Scanner(System.in);
    private User user;
    public void menuStock() {
        boolean exit = false;
        while(!exit)
        {

            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println("Nome: " + user.getName());
            System.out.println("E-mail: " + user.getEmail());
            System.out.println(user.getId());
            System.out.println("-----------------------------------------------------------------------------------------");
            MenuServices.stockMenu();
            System.out.print("Opcao >>>> ");
            int response = scanner.nextInt();
            switch (response) {
                case 1:
                    scanner.nextLine();
                    listProductHandler();
                    break;
                case 2:
                    scanner.nextLine();
                    removeProductHandler();
                    break;
                case 3:
                    scanner.nextLine();
                    insertProductHandler();
                    break;
                case 4:
                    scanner.nextLine();
                    userConfigurationHandler();
                    break;
                case 5:
                    exit = true;
                    return;
            }
        }
    }

    private void listProductHandler() {
        List<String> list;
        Product product;
        boolean exit = false;
        int page = 0;
        int index = 5;
        int offset = 5;
        double max_lines = Product.getMaxRows();
        int max_pages = (int) Math.ceil(max_lines / index);
        var map = new HashMap<Integer, List<String>>();
        MenuServices.menuListStock();
        while(!exit) {
            map = Product.listProducts(offset * page);
            printProductList(map);
            System.out.println("Pagina " + (page + 1) + "/" + max_pages);
            System.out.print("Digite uma opção >>>> ");
            String response = scanner.nextLine().toUpperCase();
            if (response.equalsIgnoreCase("N")) {
                if ((page + 1) == max_pages) {
                    System.out.println("Não é possivel avançar para proxima pagina.");
                } else {
                    page++;
                }
            } else if (response.equalsIgnoreCase("P")) {
                if ((page + 1) <= 1) {
                    System.out.println("Não é possivel avançar para proxima pagina.");
                } else {
                    page--;
                }
            } else if (response.equalsIgnoreCase("S")) {
                System.out.println("Saindo....");
                exit = true;
            } else if (response.matches("[1-5]")) {
                int selectedIndex = Integer.parseInt(response);
                if (selectedIndex > map.size()) {
                    System.out.println("Valor invalido.");
                } else {
                    list = map.get(selectedIndex);
                    product = new Product(list.get(3));
                    System.out.println(product.getProductName());
                    System.out.printf("%-20s%-20s%-20s%-20s%n", "Nome", "Quantidade", "Fornecedor", "Codigo de Barras");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    list.forEach(item -> {
                        System.out.printf("%-20s", item);
                    });
                    System.out.println();
                    System.out.println("-----------------------------------------------------------------------------------------");
                    changeProductValues(product);
                }
            } else {
                System.out.println("Opção invalida.");
            }
        }
    }

    private void changeProductValues(Product product) {
        MenuServices.changeProductStock();
        System.out.print("Escolha uma opcao para alterar >>>> ");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                System.out.print("Insira o novo nome do Produto >>>> ");
                changeProductName(product, scanner.nextLine());
                break;
            case "2":
                System.out.print("Insira nova quantidade do Produto >>>> ");
                changeProductQuantity(product, Integer.parseInt(scanner.nextLine()));
                break;
            case "3":
                System.out.print("Insira nova nome do Fabricante >>>> ");
                changeProductManufacturer(product, scanner.nextLine());
                break;
            case "4":
                System.out.print("Insira novo Codigo de Barras do Produto >>>> ");
                changeProductCodebar(product, scanner.nextLine());
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    private void printProductList(HashMap<Integer, List<String>> map) {
        System.out.printf("%-20s%-20s%-20s%-20s%n", "Nome", "Quantidade", "Fornecedor", "Codigo de Barras");
        System.out.println("-----------------------------------------------------------------------------------------");
        map.forEach((key, value) -> {
            value.forEach(item -> {
                System.out.printf("%-20s", item);
            });
            System.out.println();
        });
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    private void insertProductHandler() {
        Scanner scanner = new Scanner(System.in);
        String product_name, manufacturer, codebar;
        int quantity;
        do {
            System.out.print("Digite o nome do Produto >>>> ");
            product_name = scanner.nextLine();
            System.out.print("Digite Quantidade >>>> ");
            quantity = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Digite o nome do Fabricante >>>> ");
            manufacturer = scanner.nextLine();
            System.out.print("Digite o codigo de barras do Produto >>>> ");
            codebar = scanner.nextLine();
            Product product = new Product(product_name, quantity, manufacturer, codebar);
            product.addProduct();

            System.out.print("Deseja continuar? [S/N] >>>> ");
        } while (scanner.nextLine().equalsIgnoreCase("S"));
    }

    private void removeProductHandler() {
        Scanner scanner = new Scanner(System.in);
        String codebar;
        System.out.print("Insira codigo de barras do Produto >>>> ");
        codebar = scanner.nextLine();
        Product.removeProduct(codebar);
    }

    private void userConfigurationHandler() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        int option;

        while(!exit) {
            System.out.println("-----------------------------------------------------------------------------------------");
            System.out.println("Nome: " + user.getName());
            System.out.println("E-mail: " + user.getEmail());
            System.out.println("Senha: " + user.getPassword());
            System.out.println("-----------------------------------------------------------------------------------------");
            MenuServices.userConfiguration();
            System.out.print("Opção >>>> ");
            option = scanner.nextInt();
            System.out.println(option);
            switch (option) {
                case 1:
                    scanner.nextLine();
                    System.out.println("Nome: " + user.getName());
                    System.out.print("Insira novo nome >>>> ");
                    String new_name = scanner.nextLine();
                    user.setName(new_name);
                    user.saveChanges(user.getId());
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("E-mail: " + user.getEmail());
                    System.out.print("Insira novo email >>>> ");
                    user.setEmail(scanner.nextLine());
                    user.saveChanges(user.getId());
                    break;
                case 3:
                    String old_password;
                    String new_password;
                    scanner.nextLine();
                    while(true) {
                        System.out.print("Insira senha antiga >>>> ");
                        old_password = scanner.nextLine();
                        if (old_password.equals(user.getPassword())) {
                            System.out.print("Insira nova senha >>>> ");
                            new_password = scanner.nextLine();
                            user.setPassword(new_password);
                            user.saveChanges(user.getId());
                            break;
                        } else {
                            System.out.print("Senha incorreta, deseja continuar? [S/N] >>>> ");
                            String response = scanner.nextLine();
                            if (response.equalsIgnoreCase("N")) {
                                break;
                            }
                        }
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Valor invalido");
                    break;
            }
        }
    }

    private void changeProductName(Product product, String name) {
        product.setProductName(name);
        System.out.println("Nome alterado para >>>> " + name);
        product.saveChanges();
    }

    private void changeProductQuantity(Product product, Integer quantity) {
        product.setQuantity(quantity);
        System.out.println("Quantidade alterada para >>>> " + quantity);
        product.saveChanges();
    }

    private void changeProductManufacturer(Product product, String manufacturer_name) {
        product.setManufacturer(manufacturer_name);
        System.out.println("Fabricante alterada para >>>> " + manufacturer_name);
        product.saveChanges();
    }

    private void changeProductCodebar(Product product, String codebar) {
        product.setCodeBar(codebar);
        System.out.println("Codigo de Barras alterado para >>>> " + codebar);
        product.saveChanges();
    }

    public void receiveUser(User user) {
        this.user = user;
    }
}
