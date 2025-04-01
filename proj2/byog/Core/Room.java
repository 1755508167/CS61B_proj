package byog.Core;

import byog.lab5.Position;

public class Room {
    public Position position;
    public int width;
    public int height;
    //左上角坐标，长度，和宽度
    public Room(Position position,int width,int height){
        this.position=position;
        this.width=width;
        this.height=height;
    }
}
