// Name:Sude Naz Aslan
// ID: 2024400336


import java.awt.*;
import java.awt.event.KeyEvent;

public class Main {

    //setting canvas sizes
    static int canvas_width = 400;
    static int canvas_height = 600;

    //duration between each show().
    static int pauseDuration=60;

    //player variables and setting.
    static double playerx=canvas_width/2;
    static double playery=100;
    static int playerScore=0;
    static int playerlives=3;
    static int player_width=canvas_width/8;
    static int player_height=canvas_height/8;


    static String gameState;

    //enemy ship variables
    static double aircrafspeed=5.0;
    static double max_speed=15.0;
    static double min_speed=1.0;
    static double speed_increase=0.5;

    //player's bullets
    static double[] player_bullets_x=new double[100];
    static double[] player_bullets_y=new double[100];
    static boolean[] player_bullets_isactive=new boolean[100];
    static int activebullets=0;

    static double bullet_speed=15.0;



    //enemy's variables

    static double[] enemyX = new double[8];
    static double[] enemyY = new double[8];
    static boolean[] enemyActive = new boolean[8];
    static  int enemy_number = 8;
    static  double enemy_speed = 5.0;
    static int enemy_width=canvas_width/6;
    static int enemy_height=canvas_height/9;

    //enemy bullets
    static double[] enemyBulletX = new double[100];
    static double[] enemyBulletY = new double[100];
    static boolean[] enemyBulletActive = new boolean[100];
    static double[] enemy_speedd = new double[8];
    static int enemy_move=0;

    static int enemyBulletCount = 0;
    static int bullet_width=enemy_width/6;
    static int bullet_height=enemy_height/2;

    //flying hearts
    static double[] heartX = new double[20];
    static double[] heartY = new double[20];
    static boolean[] heartActive = new boolean[20];
    static int heart_number = 0;
    static int heart_width=player_width/2;
    static int heart_height=player_width/2;
    static double heart_speed=bullet_speed;
    // explosions
    static double[] explosionX = new double[50];
    static double[] explosionY = new double[50];
    static int[] eplosion_timer = new int[50];
    static boolean[] explosionActive = new boolean[50];
    static int explosionNumber = 0;
    static int explosion_duration = 20;

    // enemy shooting timer
    static int[] enemyShootTimer = new int[8];
    static int enemy_shoot_interval = 60;

    static int timer=0;



    public static void main(String[] args) {
        //drawing the canvas
        StdDraw.setCanvasSize(canvas_width, canvas_height);
        StdDraw.setXscale(0.0, canvas_width);
        StdDraw.setYscale(0.0, canvas_height);
        StdDraw.setTitle("2042: Interceptor");
        StdDraw.enableDoubleBuffering();

        //starting with menu screen.
        gameState = "menu";

        while (true) {
            //listing possible screen options
            if (gameState.equals("menu")) {
                gameState = mainScreen();

            } else if (gameState.equals("game")) {
                gameState = game();

            } else if (gameState.equals("pause")) {
                gameState = pause();

            } else if (gameState.equals("victory")) {
                gameState = endGame("victory");

            } else if (gameState.equals("game_over")) {
                gameState = endGame("defeat");

            }
        }
    }

     static String mainScreen() {

        //placing the player in the middle of the screen.
        playerx = canvas_height /8*3-15;
        playery = canvas_height/6+20;
        activebullets=0;


        while (true) {

            //drawing the main screen


            StdDraw.clear(StdDraw.BLACK);
            StdDraw.picture(canvas_width / 2, canvas_height / 2, "assets/background.png", canvas_width, canvas_height);
            StdDraw.picture(playerx, playery, "assets/interceptor.png",player_width, player_height);
            StdDraw.picture(canvas_width / 2, canvas_height * 3 / 5, "assets/title.png", canvas_width * 3 / 4, canvas_height / 7);
            updateplayerbullet();
            drawplayerbullets();
            double fps = 1000.0 / pauseDuration;

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));

            StdDraw.textLeft(canvas_width / 2 - 100, canvas_height / 2 - 40, ">Start Game<");

            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
            StdDraw.textLeft(canvas_width / 2 - 100, canvas_height / 2 - 100, String.format("FPS: %.1f|Speed: %.1f", fps, aircrafspeed));

            // Display control instructions
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 9));
            StdDraw.textLeft(canvas_width / 2 - 50, canvas_height / 30, "FPS: A/D Speed:Q/E");
            StdDraw.textLeft(canvas_width / 2 - 50, canvas_height / 20 , "Shoot: [Space]");
            StdDraw.textLeft(canvas_width / 2 - 50, canvas_height / 15 , "Press [ENTER] to start");
            StdDraw.textLeft(canvas_width / 2 - 50, canvas_height / 12 , "Move[<-][↑][<-][↓]");

            StdDraw.show();
            StdDraw.pause(pauseDuration);

            //keyboard button adjustments

            if (StdDraw.isKeyPressed('Q')) {
                aircrafspeed = Math.min(aircrafspeed + speed_increase, max_speed);
                StdDraw.pause(100);

            }

            if (StdDraw.isKeyPressed('E')) {
                aircrafspeed = Math.max(aircrafspeed - speed_increase, min_speed);
                StdDraw.pause(100);
            }

            if (StdDraw.isKeyPressed('A')) {
                pauseDuration = Math.min(pauseDuration + 2, 100);
                StdDraw.pause(100);
            }
            if (StdDraw.isKeyPressed('D')) {
                pauseDuration = Math.max(pauseDuration - 2, 5);
                StdDraw.pause(100);
            }

            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_LEFT)) {
                playerx = Math.max(playerx - aircrafspeed, 20);

            }
            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_RIGHT)) {
                playerx = Math.min(playerx + aircrafspeed, canvas_width - 20);
            }
            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_UP)) {
                playery = Math.min(playery + aircrafspeed, canvas_height - player_height / 2);
            }
            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_DOWN)) {
                playery = Math.max(playery - aircrafspeed, player_height / 2);
            }

            //preventing to fire multiple bullets at the same time
            boolean spacePressed=false;
            if (StdDraw.isKeyPressed(' ')) {
                if (!spacePressed) {
                    addPlayerBullet(playerx, playery);
                    spacePressed = true;
                    StdDraw.pause(70);

                }
            } else {
                spacePressed = false;
            }

            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_ENTER)) {
                //starting the game
                startGame();
                return "game";
            }
        }

    }

    static String game(){

        //game screen is opened
        playerlives=3;
        boolean ingame=true;
        boolean prevSpaceDown = false;

        while (ingame) {

            //this is for enemy speed adjustment. i am decreasing the pause duration to increase their speed.
            timer++;
            if(timer%100==0){
                pauseDuration=Math.max(25,pauseDuration-5);
            }
            boolean spaceDown = StdDraw.isKeyPressed(' ');
            if (spaceDown && !prevSpaceDown) {
                addPlayerBullet(playerx, playery); // senin bullet ekleme fonksiyonun
            }
            prevSpaceDown = spaceDown;
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.picture(canvas_width / 2, canvas_height / 2, "assets/background.png", canvas_width, canvas_height);


            updateplayerbullet();
            updateenemies();

            updateEnemyBullets();
            updateHearts();
            updateExplosions();

            checkCollisions();

            // Draw everything
            drawplayer();
            drawplayerbullets();
            drawenemy();
            drawenemybullets();
            drawHearts();
            drawExplosions();

            // Draw UI
            drawthegame();

            StdDraw.show();
            StdDraw.pause(pauseDuration);

            if(StdDraw.isKeyPressed('P')){
                //game status switched to pause.
                StdDraw.pause(100);
                return "pause";
            }


            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_LEFT)) {
                playerx = Math.max(playerx - aircrafspeed, player_width / 2);
            }
            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_RIGHT)) {
                playerx = Math.min(playerx + aircrafspeed, canvas_width - player_width / 2);
            }
            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_UP)) {
                playery = Math.min(playery + aircrafspeed, canvas_height - player_height / 2);
            }
            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_DOWN)) {
                playery = Math.max(playery - aircrafspeed, player_height / 2);
            }

            // checking win/loss conditions
            if (allEnemiesDefeated()&&playerlives>0) {
                return "victory";
            }
            if(playerlives<=0){
                return "game_over";
            }
        }
        return "game";
    }

    static String pause(){
        boolean paused=true;
        while(paused){
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.picture(canvas_width/2,canvas_height/2,"assets/background.png",canvas_width,canvas_height);

            drawplayerbullets();
            drawthegame();
            drawplayerbullets();
            drawplayer();
            drawHearts();
            drawenemybullets();

            StdDraw.picture(canvas_width / 2, canvas_height / 2, "assets/paused.png", canvas_width/2, canvas_height/8);

            StdDraw.show();
            StdDraw.pause(pauseDuration);

            if(StdDraw.isKeyPressed('P')){
                StdDraw.pause(100);
                paused=false;
                return "game";
            }

        }
        return "pause";

    }
    static String endGame(String status){
        //it is whether victory or defeat
        String menu="restart";
        while(true){
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.picture(canvas_width / 2, canvas_height / 2, "assets/background.png", canvas_width, canvas_height);

            // Draw end game message
            if (status.equals("victory")) {
                StdDraw.picture(canvas_width / 2, canvas_height / 4*3-50, "assets/victory.png", canvas_width / 2, canvas_height / 5);
            } else {
                StdDraw.picture(canvas_width / 2, canvas_height / 4*3-50 , "assets/gameOver.png", canvas_width / 2, canvas_height / 5);
            }

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
            StdDraw.text(canvas_width / 2, canvas_height / 2 , String.format("Score: %d", playerScore));

            if(menu.equals("restart")){
                StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(canvas_width / 2, canvas_height / 2 - 50, "> Restart <");
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setFont(new java.awt.Font("Arial", Font.PLAIN, 15));

                StdDraw.text(canvas_width / 2, canvas_height / 2 - 75, "End Game");

            }else{
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setFont(new java.awt.Font("Arial", Font.PLAIN, 15));
                StdDraw.text(canvas_width / 2, canvas_height / 2 - 50, "Restart");
                StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
                StdDraw.text(canvas_width / 2, canvas_height / 2 - 75, "> End Game <");

            }

            StdDraw.show();
            StdDraw.pause(pauseDuration);
 //moving between two options
            if(StdDraw.isKeyPressed(KeyEvent.VK_UP)){
                menu="restart";
            }
            else if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN)){
                menu="endgame";
            }
            else if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER)){
                if(menu.equals("restart")){
                    startGame();
                    return "game";
                }else{
                    System.exit(0);

                }
            }
        }
    }

    //game functs
    static void startGame(){
        timer=0;
        pauseDuration=50;
        enemy_move=0;
        playerx=canvas_width/2;
        playery=100;
        playerlives=3;
        playerScore=0;

        //initializing the enemies.

        for(int i=0; i<enemy_number;i++){
            enemyActive[i]=true;
            enemy_speedd[i]=enemy_speed;
            enemyShootTimer[i]=0;
            if (i<4){
                //placing them into 2 rows
                enemyX[i]=canvas_width/9+i*canvas_width/9*2;
                enemyY[i]=canvas_height-enemy_height/2;
            }else{
                enemyX[i]=canvas_width/9+(i-4)*canvas_width/9*2;
                enemyY[i]=canvas_height-enemy_height*3/2;
            }
        }
        activebullets=0;
        enemyBulletCount=0;
        heart_number=0;
        explosionNumber=0;
    }
    //moving bullets and enemies
    static void updateplayerbullet(){
        for(int i=0;i<activebullets;i++){
            if(player_bullets_isactive[i]){
                player_bullets_y[i]+=bullet_speed;

                if (player_bullets_y[i]>canvas_height) {
                    player_bullets_isactive[i] = false;
                }

            }
        }
    }

    static void updateenemies(){
        enemy_move+=enemy_speed;
        //allocating space to each enemy to move between.
        if (enemy_move>canvas_width/7) {
            //if they reach to the edge of their boxes
            enemy_move = 0;
            for(int i=0;i<enemy_number;i++) {
                if (enemyActive[i]) {
                    enemy_speedd[i] *= -1;

                }
            }
        }
        else{
            enemy_move+=enemy_speed;
            for(int i=0;i<enemy_number;i++) {
                if (enemyActive[i]) {
                    enemyX[i] += enemy_speedd[i];
                    enemyShootTimer[i]++;
                    //each enemy has their own timer variables which is limited with 60 seconds. if it is longer than that time period, with %50 probability, enemies are shooting.
                    
                    if (enemyShootTimer[i] > enemy_shoot_interval) {
                        if (Math.random() < 0.4) { // 40% chance to shoot
                            addEnemyBullet(enemyX[i], enemyY[i]);
                        }
                        enemyShootTimer[i] = 0;
                    }
                }

            }
        }
    }

//moving bullets,hearts
    static void updateEnemyBullets() {
        for (int i = 0; i < enemyBulletCount; i++) {
            if (enemyBulletActive[i]) {
                enemyBulletY[i] -= bullet_speed;

                if (enemyBulletY[i] < 0) {
                    enemyBulletActive[i] = false;
                }
            }
        }
    }

    static void updateHearts() {
        for (int i = 0; i < heart_number; i++) {
            if (heartActive[i]) {
                heartY[i] -= heart_speed;

                if (heartY[i] < 0) {
                    heartActive[i] = false;
                }
            }
        }
    }
//incrementing explosion timer
    static void updateExplosions() {
        for (int i = 0; i < explosionNumber; i++) {
            if (explosionActive[i]) {
                eplosion_timer[i]++;

                //checking timer for each explosion.
                if (eplosion_timer[i] > explosion_duration) {
                    explosionActive[i] = false;
                }
            }
        }
    }
//method to check all kinds of collisions that may occur int he game between objects.
    static void checkCollisions() {
        // Player bullets vs enemies
        for (int i = 0; i < activebullets; i++) {
            if (!player_bullets_isactive[i]){//passing inactive bullets
                continue;
            };

            for (int j = 0; j < enemy_number; j++) {
                if (!enemyActive[j]) continue;
//this method checks rectangle collision
                if (rectCollide(player_bullets_x[i], player_bullets_y[i], bullet_width, bullet_height,
                        enemyX[j], enemyY[j], enemy_width, enemy_height)) {

                    player_bullets_isactive[i] = false;
                    enemyActive[j] = false;
                    playerScore += 30;

                    addExplosion(enemyX[j], enemyY[j]);

                    // 40% chance to drop a heart
                    if (Math.random() < 0.4) {
                        addHeart(enemyX[j], enemyY[j]);
                    }
                }
            }
        }

        // Enemy bullets vs player
        for (int i = 0; i < enemyBulletCount; i++) {
            if (!enemyBulletActive[i]) continue;

            if (rectCollide(enemyBulletX[i], enemyBulletY[i], bullet_width, bullet_height,
                    playerx, playery, player_width, player_height)) {

                enemyBulletActive[i] = false;
                playerlives--;
            }
        }

        // Enemy ships vs player
        for (int i = 0; i < enemy_number; i++) {
            if (!enemyActive[i]) {
                continue;
            };

            if (rectCollide(playerx, playery, player_width, player_height,
                    enemyX[i], enemyY[i], enemy_width, enemy_height)) {

                enemyActive[i] = false;
                playerlives--;
                playerScore += 30;
                addExplosion(enemyX[i], enemyY[i]);
            }
        }

        // Hearts vs player
        for (int i = 0; i < heart_number; i++) {
            if (!heartActive[i]) continue;

            if (rectCollide(playerx, playery, player_width, player_height,
                    heartX[i], heartY[i], heart_width, heart_height)) {

                heartActive[i] = false;
                playerlives++;
            }
        }



    }

    static boolean rectCollide(double x1, double y1, double w1, double h1,
                               double x2, double y2, double w2, double h2) {
        
        //checking the collision conditions.
        return x1 - w1 / 2 < x2 + w2 / 2 &&
                x1 + w1 / 2 > x2 - w2 / 2 &&
                y1 - h1 / 2 < y2 + h2 / 2 &&
                y1 + h1 / 2 > y2 - h2 / 2;
    }

    static boolean allEnemiesDefeated() { //checking whether there is an enemy
        for (int i = 0; i < enemy_number; i++) {
            if (enemyActive[i]) {
                return false;
            }
        }
        return true;
    }
    //------------------drawing everything to the screen
    static void drawplayer(){

        StdDraw.picture(playerx,playery,"assets/interceptor.png",player_width,player_height);
    }

    static void drawplayerbullets(){
        for (int i = 0; i < activebullets; i++) {
            if(player_bullets_isactive[i]){
                StdDraw.picture(player_bullets_x[i],player_bullets_y[i],"assets/bullet.png",bullet_width,bullet_height);

            }
        }

    }

    static void drawenemy(){
        for(int i=0;i<enemy_number;i++){
            if(enemyActive[i]){
                StdDraw.picture(enemyX[i],enemyY[i],"assets/enemyFighter.png",enemy_width,enemy_height);

            }
        }
    }
    static void drawenemybullets(){
        for (int i = 0; i < enemyBulletCount; i++) {
            if(enemyBulletActive[i]){
                StdDraw.picture(enemyBulletX[i],enemyBulletY[i],"assets/enemyBullet.png",bullet_width,bullet_height);

            }
        }
    }
    static void drawHearts() {
        for (int i = 0; i < heart_number; i++) {
            if (heartActive[i]) {
                StdDraw.picture(heartX[i], heartY[i], "assets/heart.png", heart_width, heart_height);
            }
        }
    }
    static void drawExplosions() {
        for (int i = 0; i < explosionNumber; i++) {
            if (explosionActive[i]) {
                if (eplosion_timer[i] < 10) {
                    StdDraw.picture(explosionX[i], explosionY[i], "assets/explosionSmall.png", 60, 60);
                } else {
                    StdDraw.picture(explosionX[i], explosionY[i], "assets/explosionBig.png", 80, 80);
                }
            }
        }
    }


    static void drawthegame(){
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
        StdDraw.textLeft(20, canvas_height - 40, "Score: " + playerScore);


        for(int i=0; i<playerlives;i++){
            StdDraw.picture(canvas_width-heart_width*(i+1),canvas_height-heart_height,"assets/heart.png",heart_width,heart_height);
        }
    }
    
    //helper functions
    static void addPlayerBullet(double x, double y) {
        //adding coordinates of bullets to bullet's x and y list.

        player_bullets_x[activebullets] = x;
        player_bullets_y[activebullets] = y;
        player_bullets_isactive[activebullets] = true;
        activebullets++;

    }
//saving coordinates of newly added items
    static void addEnemyBullet(double x, double y) {

        enemyBulletX[enemyBulletCount] = x;
        enemyBulletY[enemyBulletCount] = y;
        enemyBulletActive[enemyBulletCount] = true;
        enemyBulletCount++;

    }

    static void addHeart(double x, double y) {

        heartX[heart_number] = x;
        heartY[heart_number] = y;
        heartActive[heart_number] = true;
        heart_number++;

    }

    static void addExplosion(double x, double y) {

        explosionX[explosionNumber] = x;
        explosionY[explosionNumber] = y;
        eplosion_timer[explosionNumber] = 0;
        explosionActive[explosionNumber] = true;
        explosionNumber++;

    }
}
    





        



