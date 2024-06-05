package MODELS;

   public class Main {

       public static void main(String[] args) {
           App app = new App();
           LoginMenu loginMenu = new LoginMenu(app);
           loginMenu.displayLoginMenu();
       }
   }
