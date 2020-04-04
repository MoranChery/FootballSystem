package Model;

public class System_Controller {

    private static System_Controller ourInstance = new System_Controller();

    public static System_Controller getInstance() {
        return ourInstance;
    }

    private System_Controller() {

    }

    public static void main(String[] args){
        System.out.println("try");

    }

}
