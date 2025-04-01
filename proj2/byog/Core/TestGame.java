package byog.Core;
import byog.lab5.Position;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGame {
    @Test
    public void TsetpointIsInRoom() {
        Room room=new Room(new Position(15,15),5,5);
        Position point=new Position(15,10);
        Game game=new Game();
        assertEquals(game.pointIsInRoom(room,point),false);
        assertEquals(game.pointIsInRoom(room,new Position(20,10)),false);
        assertEquals(game.pointIsInRoom(room,new Position(19,11)),true);
        assertEquals(game.pointIsInRoom(room,new Position(17,12)),true);
    }

    @Test
    public void TestjudgeOverlap(){
        Game game=new Game();
        Room room1=new Room(new Position(15,15),5,5);
        Room room2=new Room(new Position(10,15),5,5);
        assertEquals(game.judgeOverlap(room1,room2),false);
        assertEquals(game.judgeOverlap(new Room(new Position(12,12),5,5),room1),true);
        Room room3=new Room(new Position(19,11),5,5);
        assertEquals(game.judgeOverlap(room1,room3),true);
    }

    @Test
    public void TestrandomNum(){
        Game game=new Game();
        int x=game.randomNum(0,30,123);
        System.out.println(x);
    }
}