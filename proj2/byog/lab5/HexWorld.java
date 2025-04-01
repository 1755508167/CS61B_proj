package byog.lab5;

import org.junit.Test;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    //定义长和宽
    private static final int WIDTH = 60;
    private static final int HEIGHT = 50;

    //绘制行的方法
    //Position表示起始点的位置，统一往右开始绘制，num表示要绘制的tile的数量
    public void addLine(TETile[][] world,Position position,int num,TETile t){
        for (int i =0;i<num;i++) {
            world[position.x+i][position.y]=t;
        }
    }

    public void adddNothing(TETile[][] world) {
        //先填充Nothing
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y]=Tileset.NOTHING;
            }
        }
    }


    //绘制六边形的方法
    //size为六边形的顶边的长度
    //position为六边形的左下角的坐标
    public void addHexagon(TETile[][] world, Position position,int size,TETile t) {
        //绘制六边形
        world[position.x][position.y]=Tileset.WALL;
        addLine(world,position,size,t);

        //一个六边形分成上半部分和下半部分，两部分分开来绘制
        //先绘制下半部分
        for (int i =0;i<size;i++){
            addLine(world,new Position(position.x-i,position.y+i),size+2*i,t);
        }

        //绘制下半部分
        //先计算这个六边形的左上角的坐标
        //有一个问题需要考虑：position-i可能为负，这样会报错
        Position position1=new Position(position.x, position.y+2*size-1);
        for (int i =0;i<size;i++) {
            addLine(world,new Position(position1.x-i, position1.y-i),size+2*i,t);
        }

    }

    public static void main(String[] args) {
        //初始化渲染引擎
        TERenderer render = new TERenderer();
        render.initialize(WIDTH, HEIGHT);

        //初始化world
        TETile[][] world=new TETile[WIDTH][HEIGHT];
        Position position=new Position(10,20);


        HexWorld w=new HexWorld();
        //先往world里填充Nothing
        w.adddNothing(world);
        w.addHexagon(world,position,5,Tileset.WALL);
        w.addHexagon(world,new Position(30,35),4,Tileset.FLOWER);
        w.addHexagon(world,new Position(15,20),7,Tileset.WATER);
        //绘制世界
        render.renderFrame(world);
    }


}
