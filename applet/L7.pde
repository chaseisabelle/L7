static int screenWidth = 800;
static int screenHeight = 600;

Level level = new White();

int score = 0;
int lives = 5;

void setup() {
  size(screenWidth, screenHeight);
  frameRate(25);
  textFont(createFont("Courier New", 12, true));
  textAlign(CENTER, CENTER);
  noCursor();
}

void draw() {
  if (lives <= 0) {
    if (mousePressed) {
      score = 0;
      lives = 5;
      level = new White();
    }
    
    stroke(0, 0, 0);
    fill(0, 0, 0);
    rect(0, screenHeight / 2 - 25, screenWidth, 50);
    
    stroke(255, 255, 255);
    fill(255, 255, 255);
    text("YOU LOST!  WHATTA LOSER!  LoLz!", screenWidth / 2, screenHeight / 2);
    
    return;
  }
  
  level.draw();
}
