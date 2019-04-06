import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class L7 extends PApplet {

static int screenWidth = 800;
static int screenHeight = 600;

Level level = new White();

int score = 0;
int lives = 5;

public void setup() {
  size(screenWidth, screenHeight);
  frameRate(25);
  textFont(createFont("Courier New", 12, true));
  textAlign(CENTER, CENTER);
  noCursor();
}

public void draw() {
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
class Ball
extends Rectangle {
  
  int livesR = 0;
  int livesG = 0;
  int livesB = 0;
  
  Ball() {
    super(screenWidth / 2 - 10, screenHeight / 2 - 10, 20, 20, 255, 255, 255);
    dy = 3;
  }
  
  public void move() {
    Brick brick;
    
    for (brick = level.bricks; brick != null;  brick = brick.next) {
      if (brick.explosion == null) break;
    }
    
    if (brick == null) return;
    
    if (isTouching(level.paddle)) {
      dx = (x + w / 2) - (level.paddle.x + level.paddle.w / 2);
      dy = (y + h / 2) - (level.paddle.y + level.paddle.h / 2);
      
      dx /= 2;
      dy /= 2;
      
      if (dy == 0) dy = 1;
    }
    
    if (y + h >= screenHeight) {
      lives--;
    }
    
    super.move();
  }
  
  public void draw() {
    super.draw();
    
    stroke(livesR, livesG, livesB);
    fill(livesR, livesG, livesB);
    text(lives, x + w / 2, y + h / 2);
  }
  
}
class Brick
extends Rectangle {
  
  public Explosion explosion = null;
  
  public Brick next = null;
  
  public int worth = 0;
  public int worthR = 0;
  public int worthG = 0;
  public int worthB = 0;
  
  Brick(int worth, int r, int g, int b) {
    super((int)random(screenWidth - 25), (int)random(screenHeight / 3), 25, 15, r, g, b);
    
    this.worth = worth;
    
    dy = 5;
    dx = (int)random(5) + 1;
  }
  
  public void draw() {
    if (explosion != null) {
      explosion.draw();
      return;
    }
    
    super.draw();
  }
  
  public void move() {
    for (Brick brick = level.bricks; brick != null; brick = brick.next) {
      if (brick != this && brick.explosion == null && isTouching(brick)) {
        dx = (x - brick.x) / 10;
        dy = (y - brick.y) / 2;
      }
    }
    
    if (y >= screenHeight / 3) {
      dy -= (y - screenHeight / 3) / 5;
    }
    
    if (isTouching(level.ball)) {
      explosion = new Explosion(this);
      score += worth;
      return;
    }
    
    super.move();
  }
  
  public void render() {
    super.render();
    
    stroke(worthR, worthG, worthB);
    fill(worthR, worthG, worthB);
    text(worth, x + w / 2, y + h / 2);
  }
  
}

class Explosion {
  
  public Brick brick;
  
  public int[] x;
  public int[] y;
  public int[] dx;
  public int[] dy;
  
  Explosion(Brick brick) {
    x = new int[brick.w * brick.h];
    y = new int[brick.w * brick.h];
    dx = new int[brick.w * brick.h];
    dy = new int[brick.w * brick.h];
    
    int i = 0;
    
    for (int xi = brick.x; xi < brick.x + brick.w; xi++) {
      for (int yi = brick.y; yi < brick.y + brick.h; yi++) {
        x[i] = xi;
        y[i] = yi;
        dx[i] = xi - level.ball.x + level.ball.w / 2 + (int)random(level.ball.dx);
        dy[i] = yi - level.ball.y + level.ball.h / 2 + (int)random(level.ball.dy);
        
        i++;
      }
    }
    
    this.brick = brick;
  }
  
  public void draw() {
    move();
    render();
  }
  
  public void move() {
    for (int i = 0; i < brick.w * brick.h; i++) {
      if (!(dy[i] == 0 && y[i] == 0)) {
        if (x[i] <= 0) dx[i] = (int)abs(dx[i]);
        if (x[i] >= screenWidth) dx[i] = -(int)abs(dx[i] / ((int)random(2) + 1));
        
        if (y[i] <= 0) dy[i] = (int)abs(dy[i]);
        if (y[i] >= screenHeight) dy[i] = -(int)abs(dy[i] / 2);
        
        if (x[i] >= level.paddle.x && x[i] <= level.paddle.x + level.paddle.w &&
            y[i] >= level.paddle.y && y[i] <= level.paddle.y + level.paddle.h) {
              dx[i] = x[i] - level.paddle.x + level.paddle.w / 2;
              dy[i] = y[i] - level.paddle.y + level.paddle.h / 2;
        }
        
        if (x[i] >= level.ball.x && x[i] <= level.ball.x + level.ball.w &&
            y[i] >= level.ball.y && y[i] <= level.ball.y + level.ball.h) {
              dx[i] = (x[i] - level.ball.x + level.ball.w / 2) / ((int)random(2) + 1);
              dy[i] = (y[i] - level.ball.y + level.ball.h / 2) / ((int)random(2) + 1);    
        }
        
        x[i] += dx[i];
        y[i] += dy[i];
        
        dy[i]++;  //< gravity
      }
    }
  }
  
  public void render() {
    stroke(brick.r, brick.g, brick.b);
    
    for (int i = 0; i < brick.w * brick.h; i++) {
      point(x[i], y[i]);
    }
  }
  
}

class RainbowBrick
extends Brick {
  
  public int dr = 1;
  public int dg = 1;
  public int db = 1;
  
  RainbowBrick(int worth, int r, int g, int b) {
    super(worth, r, g, b);
  }
  
  public void render() {
    if (r >= 255) dr = -(int)abs(dr);
    if (r <= 0) dr = (int)abs(dr);
    
    if (g >= 255) dg = -(int)abs(dg);
    if (g <= 0) dg = (int)abs(dg);
    
    if (b >= 255) db = -(int)abs(db);
    if (b <= 0) db = (int)abs(db);
    
    r += dr;
    g += dg;
    b += db;
    
    worthR = (int)random(255);
    worthG = (int)random(255);
    worthB = (int)random(255);
    
    super.render();
  }
  
}
abstract class Level {
  
  public int r = 0;
  public int g = 0;
  public int b = 0;
  
  public Paddle paddle = new Paddle();
  public Ball ball = new Ball();
  
  public Brick bricks = null;
  
  Level(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }
  
  public void draw() {
    background(r, g, b);
    
    ball.draw();
    paddle.draw();
    
    for (Brick brick = bricks; brick != null; brick = brick.next) {
      brick.draw();
    }
    
    if (mousePressed) {
      nextLevel();
    }
  }
  
  public void nextLevel() {
    for (Brick brick = bricks; brick != null; brick = brick.next) {
      if (brick.explosion == null) return;
    }
    
    level = getNextLevel();
  }
  
  public abstract Level getNextLevel();
  
}

class White
extends Level { 
  
  White() {
    super(255, 255, 255);
    
    paddle.r = paddle.g = paddle.b = 0;
    paddle.scoreR = paddle.scoreG = paddle.scoreB = 255;
    ball.r = ball.g = ball.b = 0;
    ball.livesR = ball.livesG = ball.livesB = 255;
    
    for (int i = 0; i < 5; i++) {
      Brick brick = new Brick(1 * (i + 1), 0, 0, 0);
      
      brick.worthR = brick.worthG = brick.worthB = 255;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Blue();
  }
  
}

class Blue
extends Level { 
  
  Blue() {
    super(0, 0, 255);
    
    paddle.r = 255;
    paddle.g = 0;
    paddle.b = 0;
    
    paddle.scoreR = 0;
    paddle.scoreG = 0;
    paddle.scoreB = 255;
    
    ball.r = 255;
    ball.g = 0;
    ball.b = 0;
    
    ball.livesR = 0;
    ball.livesG = 0;
    ball.livesB = 255;
    
    for (int i = 0; i < 7; i++) {
      Brick brick = new Brick(2 * (i + 1), 255, 0, 0);
      
      brick.worthR = 0;
      brick.worthG = 0;
      brick.worthB = 255;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Green();
  }
  
}

class Green
extends Level {
  
  Green() {
    super(0, 255, 0);
    
    paddle.r = 255;
    paddle.g = 0;
    paddle.b = 255;
    
    paddle.scoreR = 0;
    paddle.scoreG = 255;
    paddle.scoreB = 0;
    
    ball.r = 255;
    ball.g = 0;
    ball.b = 255;
    
    ball.livesR = 0;
    ball.livesG = 255;
    ball.livesB = 0;
    
    for (int i = 0; i < 10; i++) {
      Brick brick = new Brick(3 * (i + 1), 255, 0, 255);
      
      brick.worthR = 0;
      brick.worthG = 255;
      brick.worthB = 0;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Red();
  }
  
}

class Red
extends Level {
  
  Red() {
    super(255, 0, 0);
    
    paddle.r = 0;
    paddle.g = 255;
    paddle.b = 255;
    
    paddle.scoreR = 255;
    paddle.scoreG = 0;
    paddle.scoreB = 0;
    
    ball.r = 0;
    ball.g = 255;
    ball.b = 255;
    
    ball.livesR = 255;
    ball.livesG = 255;
    ball.livesB = 0;
    
    for (int i = 0; i < 13; i++) {
      Brick brick = new Brick(4 * (i + 1), 0, 255, 255);
      
      brick.worthR = 255;
      brick.worthG = 0;
      brick.worthB = 0;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Black();
  }
  
}

class Black
extends Level {
  
  Black() {
    super(0, 0, 0);
    
    paddle.r = 255;
    paddle.g = 255;
    paddle.b = 255;
    
    paddle.scoreR = 0;
    paddle.scoreG = 0;
    paddle.scoreB = 0;
    
    ball.r = 255;
    ball.g = 255;
    ball.b = 255;
    
    ball.livesR = 0;
    ball.livesG = 0;
    ball.livesB = 0;
    
    for (int i = 0; i < 15; i++) {
      Brick brick = new Brick(5 * (i + 1), 255, 255, 255);
      
      brick.worthR = 0;
      brick.worthG = 0;
      brick.worthB = 0;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Purple();
  }
  
}

class Purple
extends Level {
  
  Purple() {
    super(255, 0, 255);
    
    paddle.r = 0;
    paddle.g = 255;
    paddle.b = 0;
    
    paddle.scoreR = 255;
    paddle.scoreG = 0;
    paddle.scoreB = 255;
    
    ball.r = 0;
    ball.g = 255;
    ball.b = 0;
    
    ball.livesR = 255;
    ball.livesG = 0;
    ball.livesB = 255;
    
    for (int i = 0; i < 17; i++) {
      Brick brick = new Brick(5 * (i + 1), 0, 255, 0);
      
      brick.worthR = 0;
      brick.worthG = 0;
      brick.worthB = 0;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public Level getNextLevel() {
    return new Crazy();
  }
  
}

class Crazy
extends Level {
  
  int dr = 1;
  int dg = 1;
  int db = 1;
  
  Crazy() {
    super((int)random(255), (int)random(255), (int)random(255));
    
    for (int i = 0; i < 20; i++) {
      Brick brick = new RainbowBrick(10 * (i + 1), (int)random(255), (int)random(255), (int)random(255));
      
      brick.worthR = (brick.r + 255) % 255;
      brick.worthG = (brick.g + 255) % 255;
      brick.worthB = (brick.b + 255) % 255;
      
      brick.next = bricks;
      bricks = brick;
    }
  }
  
  public void draw() {
    if (r <= 0) dr = (int)abs(dr);
    if (r >= 255) dr = -(int)abs(dr);
    if (g <= 0) dg = (int)abs(dg);
    if (g >= 255) dg = -(int)abs(dg);
    if (b <= 0) db = (int)abs(db);
    if (b >= 255) db = -(int)abs(db);
    
    r += dr;
    b += db;
    g += dg;
    
    ball.r = paddle.r = (int)random(255);
    ball.g = paddle.g = (int)random(255);
    ball.b = paddle.b = (int)random(255);
    
    paddle.scoreR = (int)random(255);
    paddle.scoreG = (int)random(255);
    paddle.scoreB = (int)random(255);
    
    ball.livesR = (int)random(255);
    ball.livesG = (int)random(255);
    ball.livesB = (int)random(255);
    
    super.draw();
  }
  
  public Level getNextLevel() {
    score = 0;
    return new White();
  }
  
}
class Paddle
extends Rectangle {
  
  public int scoreR = 255;
  public int scoreG = 255;
  public int scoreB = 255;
  
  Paddle() {
    super(0, screenHeight - 60, 100, 20, 255, 255, 255);
  }
  
  public void move() {
    Brick brick;
    
    for (brick = level.bricks; brick != null;  brick = brick.next) {
      if (brick.explosion == null) break;
    }
    
    if (brick == null) return;
    
    x = mouseX - w / 2;
  }
  
  public void render() {
    super.render();
    
    stroke(scoreR, scoreG, scoreB);
    fill(scoreR, scoreG, scoreB);
    text(score, x + w / 2, y + h / 2);
  }
  
}
class Rectangle {
 
  public int x = 0;
  public int y = 0;
  
  public int w = 0;
  public int h = 0;
  
  public int r = 0;
  public int g = 0;
  public int b = 0;
  
  public int dx = 0;
  public int dy = 0;
  
  public int dr = 0;
  public int dg = 0;
  public int db = 0;
  
  Rectangle(int x, int y, 
            int w, int h,
            int r, int g, int b) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.r = r;
    this.g = g;
    this.b = b;    
  }
  
  public void draw() {
    move();
    rgb();
    render();
  }
  
  public void move() {
    if (x + w >= screenWidth)  dx = -(int)abs(dx);
    if (x <= 0)                dx =  (int)abs(dx);
    
    if (y + h >= screenHeight)  dy = -(int)abs(dy);
    if (y <= 0)                 dy =  (int)abs(dy);
    
    x += dx;
    y += dy;
  }
  
  public void rgb() {
    if (r >= 255)   dr = -(int)abs(dr);
    if (r <= 0)     dr =  (int)abs(dr);
    
    if (g >= 255)   dg = -(int)abs(dg);
    if (g <= 0)     dg =  (int)abs(dg);
    
    if (b >= 255)   db = -(int)abs(db);
    if (b <= 0)     db =  (int)abs(db);
    
    r += dr;
    g += dg;
    b += db;
  }
  
  public void render() {
    stroke(r, g, b);
    fill(r, g, b);
    rect(x, y, w, h);
  }
  
  public boolean isTouching(Rectangle rectangle) {
    return (((rectangle.x >= x && rectangle.x <= x + w || rectangle.x + rectangle.w >= x && rectangle.x + rectangle.w <= x + w) &&
              (rectangle.y >= y && rectangle.y <= y + h || rectangle.y + rectangle.h >= y && rectangle.y + rectangle.h <= y + h)) ||
            ((x >= rectangle.x && x <= rectangle.x + rectangle.w || x + w >= rectangle.x && x + w <= rectangle.x + rectangle.w) &&
              (y >= rectangle.y && y <= rectangle.y + rectangle.h || y + h >= rectangle.y && y + h <= rectangle.y + rectangle.h)));
  }
  
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "L7" });
  }
}
