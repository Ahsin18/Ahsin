import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;


public class FlappyBird extends JPanel implements ActionListener, KeyListener
{
    int boardWidth = 360;
    int boardHeight = 640;

    // initializing the images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;


    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird
    {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;
        Bird(Image img){
            this.img = img;
        }
    }

    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64; //Scalled 1/6
    int pipeHeight = 512;

    class Pipe
    {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; // for scoring
        Pipe(Image img){
            this.img = img;
        }
    }

    // Game logic
    Bird bird;
    int velocityX =-4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;

    double score = 0;
    boolean gameOver = false;
    

    FlappyBird()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue); 
        setFocusable(true);
        addKeyListener(this);


        // loading the images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        
        
        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        //pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesTimer.start();

        //game loop
        gameLoop = new Timer (1000/60, this);
        gameLoop.start();


    
    }

    public void placePipes()
    {
        //math.random() returns a value between 0 and 1
        //pipeHeight/4 =  128 
        // 0 - 128- (0 -256)

        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4; // space between the top and bottom pipe
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
       
    }   

    public void draw(Graphics g)
    {
        
        //background
       g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

       //bird
       g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

         //pipes
         for(int i = 0; i < pipes.size(); i++)
         {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
         }

         //score
         g.setColor(Color.white);
         g.setFont(new Font("Arial", Font.BOLD, 32));
         if(gameOver)
         {
            g.drawString("Game Over: " + (int)score, 10, 35);

         }
         else
         {
             g.drawString( String.valueOf((int)score), 10, 35);
         }

    }

    public void move()
    {
        //BIRD 
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max( bird.y,0); // preventing bird from going above the top of the screen


        //PIPES
        for(int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width) // if the bird has passed the pipe
                {
                    score += 0.5; // increase score by 0.5 for each pipe passed (top and bottom)
                    pipe.passed = true;
                }
                //collision
                if (collision(bird, pipe))
                {
                    gameOver = true;
                }
            //removing pipes that are out of the screen
            if(pipe.x + pipe.width < 0)
            {
                pipes.remove(pipe);
                i--;
            }
        } 

        if (bird.y > boardHeight)
        {
            gameOver = true;
            
        }
    }

    public boolean collision(Bird a, Pipe b)
    {
        return a.x < b.x + b.width &&  //a's top left corner doesnot reach b's top right corner
               a.x + a.width > b.x &&  //a's top right corner passes b's top left corner
               a.y < b.y + b.height && //a's top left corner doesnot reach b's botton left corner
               a.y + a.height > b.y; //a's bottom left corner passes b's top left corner
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver)
        {
            placePipesTimer.stop();
            gameLoop.stop();
        }
       }

   

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            velocityY = -9;
            if (gameOver)
            {
                //restart the game
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();

            }
        }
    }
     @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    
}
