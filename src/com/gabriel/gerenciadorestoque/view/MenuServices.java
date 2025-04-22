package com.gabriel.gerenciadorestoque.view;

import com.gabriel.gerenciadorestoque.dbconnection.User;
import com.gabriel.gerenciadorestoque.managers.StockMenuManager;

public class MenuServices {

    public static void loginMenu() {
        System.out.println("*****************************************************");
        System.out.println("*                                                   *");
        System.out.println("*               GERENCIADOR DE ESTOQUE              *");
        System.out.println("*                                                   *");
        System.out.println("*     [1] Entrar                                    *");
        System.out.println("*     [2] Cadastrar                                 *");
        System.out.println("*     [3] Sair                                      *");
        System.out.println("*                                                   *");
        System.out.println("*****************************************************");
    }

    public static void stockMenu() {
        System.out.println("*****************************************************");
        System.out.println("*                                                   *");
        System.out.println("*               GERENCIADOR DE ESTOQUE              *");
        System.out.println("*                                                   *");
        System.out.println("*     [1] Listar Estoque                            *");
        System.out.println("*     [2] Remover Item                              *");
        System.out.println("*     [3] Adicionar Item                            *");
        System.out.println("*     [4] Configuracoes de Usuario                  *");
        System.out.println("*     [5] Sair                                      *");
        System.out.println("*                                                   *");
        System.out.println("*****************************************************");
    }

    public static void menuListStock() {
        System.out.println("*****************************************************");
        System.out.println("*                                                   *");
        System.out.println("*     [N] Proxima Pagina                            *");
        System.out.println("*     [P] Pagina Anterior                           *");
        System.out.println("*     [1-5] Selecionar uma Linha                    *");
        System.out.println("*     [S] Sair                                      *");
        System.out.println("*                                                   *");
        System.out.println("*****************************************************");
    }

    public static void changeProductStock() {
        System.out.println("*****************************************************");
        System.out.println("*                                                   *");
        System.out.println("*     [1] Nome                                      *");
        System.out.println("*     [2] Quantidade                                *");
        System.out.println("*     [3] Fabricante                                *");
        System.out.println("*     [4] Codigo de Barras                          *");
        System.out.println("*                                                   *");
        System.out.println("*     [5] Sair                                      *");
        System.out.println("*                                                   *");
        System.out.println("*****************************************************");
    }

    public static void userConfiguration() {
        System.out.println("*****************************************************");
        System.out.println("*                                                   *");
        System.out.println("*     [1] Alterar nome                              *");
        System.out.println("*     [2] Alterar e-mail                            *");
        System.out.println("*     [3] Alterar senha                             *");
        System.out.println("*     [4] Voltar                                    *");
        System.out.println("*                                                   *");
        System.out.println("*****************************************************");
    }
}
