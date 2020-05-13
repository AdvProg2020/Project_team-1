package view;

import old.CommandProcess;

import java.util.ArrayList;

import old.*;

public class View {
    public ArrayList<CommandProcess> allMenus = new ArrayList<CommandProcess>();

    public View() {
        ViewPersonalInfoMenu viewPersonalInfoMenu = new ViewPersonalInfoMenu();
        viewPersonalInfoMenu.commandProcess1 = new CommandProcess() {
            @Override
            public String commandProcessor(String command) throws Exception {
                try {
                    ViewPersonalInfoMenu.filter();
                } catch (Exception e) {
                    System.out.println("filter nashod");
                }
            }
        };
        final ProductsMenu productsMenu = new ProductsMenu();
        productsMenu.commandProcess = new CommandProcess() {
            @Override
            public String commandProcessor(String command) throws Exception {
              if (command.equals("get products")){
                  try {
                      ArrayList t = productsMenu.getallProducts;
                      t.for{
                          System.out.println(t.name);
                      }
                  }catch (Exception e){
                      System.out.println(" gerfte nashod");
                  }
              }
            }
        };

        HandleMenu.getMenu().commandProcess.commandProssor;

    }

    public void run() {

    }
}
