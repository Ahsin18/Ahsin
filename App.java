import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth= 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
       // frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();//for the navigation bar and title bar size to not be included in the size
        flappyBird.requestFocus();  
        frame.setVisible(true);
        
    }
}
