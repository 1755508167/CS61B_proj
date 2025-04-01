package byog.Core;

import byog.TileEngine.TETile;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    /*
    if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game game = new Game();
            TETile[][] worldState = game.playWithInputString(args[0]);
            System.out.println(TETile.toString(worldState));
        } else {
            Game game = new Game();
            game.playWithKeyboard();
        }
     */
    public static void main(String[] args) {
        Game game=new Game();
        String input = "n455857754086099036s";
        String numbers = input.replaceAll("\\D", ""); // \\D 表示非数字字符，替换为空
        Long number=Long.parseLong(numbers);
        System.out.println(numbers);
        TETile[][] world=game.playWithInputString("37636");
    }
}
