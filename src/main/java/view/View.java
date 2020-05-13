package view;

import controller.*;

public class View {
//    public ArrayList<CommandProcess> allMenus = new ArrayList<CommandProcess>();
//
//    public View() {
//        ViewPersonalInfoMenu viewPersonalInfoMenu = new ViewPersonalInfoMenu();
//        viewPersonalInfoMenu.commandProcess1 = new CommandProcess() {
//            @Override
//            public String commandProcessor(String command) throws Exception {
//                try {
//                    ViewPersonalInfoMenu.filter();
//                } catch (Exception e) {
//                    System.out.println("filter nashod");
//                }
//            }
//        };
//        final ProductsMenu productsMenu = new ProductsMenu();
//        productsMenu.commandProcess = new CommandProcess() {
//            @Override
//            public String commandProcessor(String command) throws Exception {
//              if (command.equals("get products")){
//                  try {
//                      ArrayList t = productsMenu.getallProducts;
//                      t.for{
//                          System.out.println(t.name);
//                      }
//                  }catch (Exception e){
//                      System.out.println(" gerfte nashod");
//                  }
//              }
//            }
//        };
//
//        HandleMenu.getMenu().commandProcess.commandProssor;
//
//    }
    private final LoginRegisterMenu loginRegisterMenu = new LoginRegisterMenu();

    public View() {
        initializeLoginRegisterMenu();

    }

    private void initializeLoginRegisterMenu() {
        loginRegisterMenu.commandProcess = new CommandProcess() {
            @Override
            public void commandProcessor(String command) throws Exception {
                try {
                    if (command.matches("create account (?<type>customer|seller|admin) (?<username>\\S+)")) {

                    } else if (command.matches("login (?<username>\\S+) (?<password>\\S+)")) {

                    } else if (command.equalsIgnoreCase("back")) {
                        loginRegisterMenu.goToPreviousMenu();
                    } else {
                        System.out.println("Command not found!");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    public void run() {

    }
}
