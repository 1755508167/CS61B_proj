package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;


    }

    //判断两个房间是否存在重叠
    public boolean judgeOverlap(Room room1, Room room2) {
        //room1在x轴的范围
        int xRangeMin = room1.position.x;
        int xRangeMax = room1.position.x - room1.width + 1;

        //room1在y轴的范围
        int yRangeMax = room1.position.y;
        int yRangeMin = room1.position.y - room1.height + 1;

        //然后判断room2的四个点的x,y是不是都在这个范围内
        //左上角的点
        Position point1 = room2.position;
        //左下角的点
        Position point2 = new Position(point1.x, point1.y - room2.height + 1);
        //右上角的点
        Position point3 = new Position(point1.x + room2.width - 1, point1.y);
        //右下角的点
        Position point4 = new Position(point1.x + room2.width - 1, point1.y - room2.height + 1);

        if (!pointIsInRoom(room1, point1) && !pointIsInRoom(room1, point2)) {
            if (!pointIsInRoom(room1, point3) && !pointIsInRoom(room1, point4)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    //判断一个点是不是在一个房间内
    public boolean pointIsInRoom(Room room, Position point) {
        //room在x轴的范围
        int xRangeMin = room.position.x;
        int xRangeMax = room.position.x + room.width -1;

        //room在y轴的范围
        int yRangeMax = room.position.y;
        int yRangeMin = room.position.y - room.height + 1;

        int x = point.x;
        int y = point.y;
        if ((x > xRangeMax || x < xRangeMin) || (y < yRangeMin || y > yRangeMax)) {
            return false;
        } else {
            return true;
        }
    }

    //随机生成一个房间
    public Room generateRoom(int seed) {
        Position position = new Position(randomNum(0,WIDTH, seed), randomNum(0,HEIGHT, seed));
        int width = randomNum(0,RoomWidthRange, seed);
        int height = randomNum(0,RoomHeightRange, seed);

        Room room = new Room(position, width, height);

        return room;
    }
}
