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
