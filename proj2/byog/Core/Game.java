package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import byog.lab5.Position;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;


    //设置房间数量和房间尺寸范围
    public static final int RoomWidthRange = 10;
    public static final int RoomHeightRange = 10;
    public static final int RoomNumRange = 40;

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
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        //ter.initialize(WIDTH, HEIGHT);
        //创建一个世界
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        //把world填充为NOTHING
        initWorld(finalWorldFrame);

        //处理输入
        String numbers = input.replaceAll("\\D", ""); // \\D 表示非数字字符，替换为空
        Long seed=Long.parseLong(numbers);

        //生成一个random对象
        Random random = new Random(seed);
        //使用列表来存储生成的房间，可以用来检测是否存在房间重叠
        List<Room> roomList = new ArrayList<>();
        //随机生成一个room
        Room room1 = generateRoom(random);
        roomList.add(room1);
        //房间的数量
        int roomNums = 7+random.nextInt(RoomNumRange-7);
        //System.out.println("roomNums:" + (roomNums + 1));
        //检测重叠
        for (int i = 0; i < roomNums; i++) {
            Room room = generateRoom(random);
            boolean isOverlap = false;
            //判断是否与已有的房间重叠
            for (Room r : roomList) {
                //如果重叠
                if (judgeOverlap(room, r)) {
                    isOverlap = true;
                    break;
                }
            }
            //如果没有重叠，则添加进列表中
            if (!isOverlap) {
                roomList.add(room);
            }
        }
        //对所有房间进行排序，按照房间左上角的点的x坐标
        roomSort(roomList);
        /*
        for (Room room : roomList) {
            System.out.println(room.position.x + " " + room.position.y + " " + room.width + " " + room.height);
        }

         */

        for (Room room : roomList) {
            drawRoom(finalWorldFrame, room);
        }

        //System.out.println("生成的房间数量：" + roomList.size());

        //绘制走廊
        int one = 0;
        int two = 1;

        for (int i = 0; i < roomList.size() - 1; i++) {
            connectRooms(finalWorldFrame, roomList.get(one), roomList.get(two));
            one += 1;
            two += 1;
        }
        //添加墙壁
        generateWall(finalWorldFrame);
        //添加门
        addDoor(finalWorldFrame,seed);
        //添加玩家
        addPlayer(finalWorldFrame,seed);


        System.out.println(Arrays.deepToString(finalWorldFrame));
        //渲染世界
        //ter.renderFrame(finalWorldFrame);

        return finalWorldFrame;

    }

    //添加玩家
    public void addPlayer(TETile[][] world,long seed) {
        //收集所有有效生成点
        List<Position> validPlayer=new ArrayList<>();
        for (int x =1;x<WIDTH;x++){
            for (int y =1;y<HEIGHT;y++){
                if (world[x][y]==Tileset.FLOOR){
                    validPlayer.add(new Position(x,y));
                }
            }
        }
        Random random=new Random(seed);
        int randomIndex=random.nextInt(validPlayer.size());
        int x =validPlayer.get(randomIndex).x;
        int y=validPlayer.get(randomIndex).y;
        world[x][y]=Tileset.PLAYER;
    }

    //添加门,door的前后左右四个tiel中，必须有FLOOR和NOTHING
    public void addDoor(TETile[][] world,long seed) {
        List<Position> valisdDoors = new ArrayList<>();
        //遍历某一行
        for (int x = 1; x < WIDTH - 1; x++) {
            for (int y = 1; y < HEIGHT - 1; y++) {
                if (world[x][y]==Tileset.WALL &&
                        (checkHorizontalPattern(world, x, y) || checkVerticalPattern(world, x, y))
                ) {
                    valisdDoors.add(new Position(x, y));
                }
            }
        }
        Random random=new Random(seed);
        int randomIndex=random.nextInt(valisdDoors.size());
        int x = valisdDoors.get(randomIndex).x;
        int y = valisdDoors.get(randomIndex).y;
        world[x][y] = Tileset.LOCKED_DOOR;

    }

    //检查门的两种模式
    //门的左右两侧都是WALL，上下分别为NOTHING和FLOOR
    public boolean checkHorizontalPattern(TETile[][] world, int x, int y) {
        boolean result = true;
        if (world[x - 1][y] == Tileset.WALL && world[x + 1][y] == Tileset.WALL) {
            if ((world[x][y + 1] == Tileset.FLOOR && world[x][y - 1] == Tileset.NOTHING) || (world[x][y + 1] == Tileset.NOTHING && world[x][y - 1] == Tileset.FLOOR)) {
                result = true;
            }
        } else {
            result = false;
        }
        return result;
    }

    //门的上下两侧都是WALL，左右分别为NOTHING和FLOOR
    public boolean checkVerticalPattern(TETile[][] world, int x, int y) {
        boolean result = true;
        if ((world[x][y + 1] == Tileset.FLOOR && world[x][y - 1] == Tileset.NOTHING) || (world[x][y + 1] == Tileset.NOTHING && world[x][y - 1] == Tileset.FLOOR)) {
            if (world[x - 1][y] == Tileset.WALL && world[x + 1][y] == Tileset.WALL) {
                result = true;
            }
        } else {
            result = false;
        }
        return result;
    }

    //用走廊连接两个房间
    public void connectRooms(TETile[][] world, Room room1, Room room2) {
        Position center1;
        Position center2;

        //计算两个房间的中心点
        center1 = new Position(room1.position.x + room1.width / 2, room1.position.y - room1.height / 2);
        center2 = new Position(room2.position.x + room2.width / 2, room2.position.y - room2.height / 2);
        int x1 = center1.x;
        int y1 = center1.y;

        int x2 = center2.x;
        int y2 = center2.y;
        // 先水平连接，再垂直连接
        createHorizontalCorridor(world, x1, x2, y1);
        createVerticalCorridor(world, y1, y2, x2);

    }

    // 生成水平走廊
    private void createHorizontalCorridor(TETile[][] world, int x1, int x2, int y) {
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            world[x][y] = Tileset.FLOOR; // 标记走廊
        }
    }

    // 生成垂直走廊
    private void createVerticalCorridor(TETile[][] world, int y1, int y2, int x) {
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            world[x][y] = Tileset.FLOOR; // 标记走廊
        }
    }

    //绘制行,position为起始点位置，s为长度
    public void drawline(TETile[][] world, Position position, int s) {
        int x = position.x;
        int y = position.y;
        for (int i = 0; i < s; i++) {
            world[x + i][y] = Tileset.FLOOR;
        }
    }

    //绘制房间
    public void drawRoom(TETile[][] world, Room room) {
        for (int i = 0; i < room.height; i++) {
            drawline(world, new Position(room.position.x, room.position.y - i), room.width);
        }
    }

    //一个roomlist里的room按照x坐标进行排序,使用选择排序算法
    public void roomSort(List<Room> roomList) {
        int size = roomList.size();
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (roomList.get(j).position.x < roomList.get(minIndex).position.x) {
                    //更新最大项的索引
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                //交换元素
                Room temp = roomList.get(i);
                roomList.set(i, roomList.get(minIndex));
                roomList.set(minIndex, temp);
            }
        }
    }

    //判断两个房间是否存在重叠
    public boolean judgeOverlap(Room room1, Room room2) {
        /*
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

         */
        // 计算room1的边界（坐标系原点在左下角，position是左上角）
        int r1Left = room1.position.x;
        int r1Right = room1.position.x + room1.width - 1;
        int r1Bottom = room1.position.y - room1.height + 1; // 下边界
        int r1Top = room1.position.y;                       // 上边界

        // 计算room2的边界
        int r2Left = room2.position.x;
        int r2Right = room2.position.x + room2.width - 1;
        int r2Bottom = room2.position.y - room2.height + 1;
        int r2Top = room2.position.y;

        // 检测x轴和y轴投影是否重叠
        boolean xOverlap = (r1Left <= r2Right) && (r1Right >= r2Left);
        boolean yOverlap = (r1Bottom <= r2Top) && (r1Top >= r2Bottom);
        return xOverlap && yOverlap;
    }

    //判断一个点是不是在一个房间内,judgeOverlap调用这个函数
    public boolean pointIsInRoom(Room room, Position point) {
        //room在x轴的范围
        int xRangeMin = room.position.x;
        int xRangeMax = room.position.x + room.width - 1;

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

    //用来生成墙壁
    public void generateWall(TETile[][] world) {
        // 创建临时副本避免修改影响判断
        //使用tempworld检查地板，在world上修改
        TETile[][] tempWorld = deepCopy(world);
        //因为房间不会在边界那一圈
        int width = WIDTH - 1;
        int height = HEIGHT - 1;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //检查是否是FLOOR，是才需要在周围生成WALL
                if (isFloor((tempWorld[x][y]))) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if (dx == 0 && dy == 0) {
                                continue;
                            }
                            int nx = x + dx;
                            int ny = y + dy;

                            //仅在空位生成墙壁
                            if (world[nx][ny] == Tileset.NOTHING) {
                                world[nx][ny] = Tileset.WALL;
                            }
                        }
                    }
                }
            }
        }


    }

    // 辅助方法：深拷贝二维数组
    private TETile[][] deepCopy(TETile[][] original) {
        TETile[][] copy = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            System.arraycopy(original[x], 0, copy[x], 0, HEIGHT);
        }
        return copy;
    }

    //用来判断一个方块是否是地板类型
    public boolean isFloor(TETile tile) {
        return tile == Tileset.FLOOR || tile == Tileset.FLOOR;
    }

    //随机生成一个房间
    public Room generateRoom(Random random) {
        //nextInt是左闭右开
        //因为房间的最小长宽为2，所以是width-2,height-2
        Position position = new Position(1+random.nextInt( WIDTH - 3), 2+random.nextInt( HEIGHT - 3));
        int width;
        int height;

        // 计算最大可用宽度和高度
        int maxWidth = WIDTH - position.x - 1; // 房间右侧最大可用空间
        int maxHeight = position.y;   // 房间下方最大可用空间

        if (maxWidth > RoomWidthRange) {
            width = 2+random.nextInt(RoomWidthRange-2);//宽的范围为10
        } else if (maxWidth == 2) {
            width = 2;
        } else {
            width = 2+random.nextInt(maxWidth-2);
        }

        if (maxHeight > RoomHeightRange) {
            height = 2+random.nextInt( RoomHeightRange-2);
        } else if (maxHeight == 2) {
            height = 2;
        } else {
            height = 2+random.nextInt(maxHeight-2);
        }
        Room room = new Room(position, width, height);

        return room;
    }

    //初始化世界，所有格子设置为Nothing
    public void initWorld(TETile[][] world) {
        //先填充Nothing
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    //生成一个在(min.max)范围内的随机数，用于地图数量，房间大小等
    public int randomNum(int min, int max, int seed) {
        Random random = new Random(seed);
        int randomnum = random.nextInt(max - min) + min;
        //System.out.println("生成的随机数为："+randomnum);
        return randomnum;
    }

}
