package bdmap;

import static bdmap.ConvertUtil.indexOf;
import static bdmap.ConvertUtil.toByte;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import util.FileUtil2;
import util.ImageUtil2;

public class MakeMap
{
    public static final int TILE_SIZE = 78;
    public static final int WORLD = 0x2c;
    public static final int WIDTH = 0x40;
    public static final int HEIGHT = 0x42;
    public static final int[] LEVEL_DATA_HEADER = { 0x4C, 0x45, 0x44, 0x00 }; // "LED"
    public static final int[] LEVEL_PROPERTIES_HEADER = { 0x4D, 0x49, 0x47, 0x00 }; // "MIG"

    public static final int TITANIUM_WALL_1 = 0x1405;
    public static final int TITANIUM_WALL_2 = 0x1505;
    public static final int TITANIUM_WALL_3 = 0x1605;
    public static final int TITANIUM_WALL_4 = 0x1705;
    public static final int TITANIUM_WALL_5 = 0x1805;
    public static final int TITANIUM_WALL_6 = 0x1905;
    public static final int TITANIUM_WALL_7 = 0x1A05;
    public static final int TITANIUM_WALL_8 = 0x1B05;
    public static final int TITANIUM_WALL_9 = 0x1C05;
    public static final int TITANIUM_WALL_10 = 0x1D05;

    public static final int MONSTER_GATE_1 = 0x8205;
    public static final int MONSTER_GATE_2 = 0xbe05;

    public static final int EXIT = 0x7C05;

    public static final int WALL_1 = 0x4c04;
    public static final int WALL_2 = 0x4D04;
    public static final int WALL_3 = 0x4e04;
    public static final int WALL_4 = 0x4f04;
    public static final int WALL_5 = 0x5004;
    public static final int WALL_6 = 0x5104;
    public static final int WALL_7 = 0x5204;
    public static final int WALL_8 = 0x5304;
    public static final int WALL_9 = 0x5404;
    public static final int WALL_10 = 0x5504;
    public static final int WALL_11 = 0x5604;
    public static final int WALL_12 = 0x5704;
    public static final int WALL_13 = 0x5804;

    public static final int DIAMOND = 0x1002;
    public static final int DIAMOND_FALLING = 0x1202;

    public static final int DIAMOND_SUPER = 0x1402; // white, one per level

    public static final int DIAMOND_BOSS_TOP_LEFT = 0x4808;
    public static final int DIAMOND_BOSS_TOP_RIGHT = 0x4908;
    public static final int DIAMOND_BOSS_BOTTOM_LEFT = 0x4a08;
    public static final int DIAMOND_BOSS_BOTTOM_RIGHT = 0x4b08;

    public static final int DIRT = 0xE803;

    public static final int SPACE = 0x0000;
    public static final int SPACE_DD = 0x1D01; // space (appears only in Double Dash)

    public static final int ROCKFORD = 0x6400;

    public static final int BOULDER = 0x9001;
    public static final int BOULDER_HEAVY = 0x9a01;
    public static final int BOULDER_LIGHT = 0x3a02; // swims

    public static final int MONSTER_UP_DOWN = 0xcd00;

    public static final int MONSTER_LEFT_RIGHT_1 = 0xc800;
    public static final int MONSTER_LEFT_RIGHT_2 = 0xc900;

    public static final int MONSTER_BEES = 0xce00;

    public static final int MONSTER_SKULL = 0xa005; // poison in all directions

    public static final int MONSTER_SKULL_FLAME_UP = 0xa105;
    public static final int MONSTER_SKULL_FLAME_RIGHT = 0xa205;
    public static final int MONSTER_SKULL_FLAME_DOWN = 0xa305;
    public static final int MONSTER_SKULL_FLAME_LEFT = 0xa405;

    public static final int MONSTER_FLAME_1 = 0xd200;
    public static final int MONSTER_FLAME_2 = 0xd300;
    public static final int MONSTER_FLAME_3 = 0xd400;
    public static final int MONSTER_FLAME_4 = 0xd500;

    public static final int MONSTER_TAURUS_1 = 0xd700;
    public static final int MONSTER_TAURUS_2 = 0xd800;
    public static final int MONSTER_TAURUS_3 = 0xd900;
    public static final int MONSTER_TAURUS_4 = 0xda00;

    public static final int MONSTER_DIAMOND_EATER_1 = 0xdc00;
    public static final int MONSTER_DIAMOND_EATER_2 = 0xdd00;

    public static final int MONSTER_CRAB_1 = 0xf000;
    public static final int MONSTER_CRAB_2 = 0xf100;

    public static final int MONSTER_SQUID_1 = 0xeb00;
    public static final int MONSTER_SQUID_2 = 0xec00;
    public static final int MONSTER_SQUID_3 = 0xed00;
    public static final int MONSTER_SQUID_4 = 0xee00;

    public static final int MONSTER_MANTA_RAY_1 = 0xe600;
    public static final int MONSTER_MANTA_RAY_2 = 0xe700;

    public static final int MONSTER_SKELETON = 0xfa00;

    public static final int MONSTER_OWL_1 = 0xf500;
    public static final int MONSTER_OWL_2 = 0xf600;
    public static final int MONSTER_OWL_3 = 0xf700;
    public static final int MONSTER_OWL_4 = 0xf800;

    public static final int MONSTER_SPIDER = 0xff00;

    public static final int BOSS = 0x2201;

    public static final int POWERUP_AMMO = 0x4108;
    public static final int POWERUP_AMMO_ONCE = 0x2c01;

    public static final int POWERUP_MAGNET = 0x4208;
    public static final int POWERUP_MAGNET_ONCE = 0x2d01;

    public static final int POWERUP_WATER = 0x4308;
    public static final int POWERUP_WATER_ONCE = 0x2e01;

    public static final int POWERUP_BOMB = 0x4408;
    public static final int POWERUP_BOMB_ONCE = 0x2f01;

    public static final int POWERUP_FREEZE = 0x4508;
    public static final int POWERUP_FREEZE_ONCE = 0x3001; // unused

    public static final int POWERUP_EMPTY = 0x4608; // only in world4 boss

    public static final int POWERUP_SPEED = 0x3401;

    public static final int POWERUP_TIME = 0x3501;

    public static final int KEY_RED = 0x8d05;
    public static final int KEY_YELLOW = 0x8c05;
    public static final int KEY_WHITE = 0x8e05;

    public static final int LOCK_RED = 0x9705;
    public static final int LOCK_YELLOW = 0x9605;
    public static final int LOCK_WHITE = 0x9805;

    public static final int HEALTH_ONCE = 0x3201;
    public static final int HEALTH = 0x3301; // full health

    public static final int DYNAMITE = 0x7805;

    public static final int TELEPORTER_BLUE = 0xb405;
    public static final int TELEPORTER_WHITE = 0xb505;
    public static final int TELEPORTER_GREEN = 0xb705;
    public static final int TELEPORTER_ORANGE = 0xb605;

    public static final int ONEWAY_RIGHT = 0x7e05;
    public static final int ONEWAY_UP = 0x7f05;
    public static final int ONEWAY_LEFT = 0x7d05;
    public static final int ONEWAY_DOWN = 0x8005;

    public static final int LAVA_DOWN_RIGHT = 0x7607;
    public static final int LAVA_UP_LEFT = 0x7407;
    public static final int LAVA_DOWN_LEFT = 0x7307;
    public static final int LAVA_UP_RIGHT = 0x7507;

    public static final int LAVA_HORIZONTAL_1 = 0x7707;
    public static final int LAVA_HORIZONTAL_2 = 0x6c07;
    public static final int LAVA_LEFT = 0x7007;
    public static final int LAVA_RIGHT = 0x6e07;
    public static final int LAVA_ISOLATED = 0x7207;

    public static final int LAVA_UP = 0x7107;
    public static final int LAVA_DOWN = 0x6f07;
    public static final int LAVA_VERTICAL_1 = 0x7807;
    public static final int LAVA_VERTICAL_2 = 0x6d07;

    public static final int INFLAMMABLE = 0xa505;

    public static final int OIL_DROP = 0xa705;

    public static final int AMOEBA = 0xa805;

    public static final int WATER = 0xed07;

    public static final int HYDRANT_LEFT = 0x7a05;
    public static final int HYDRANT_RIGHT = 0x7b05;

    public static final int POWERUP_DIVE = 0x3601;

    public static final int SPIDER_WEB = 0x3a08;

    public static final int POISON_BOTTLE = 0x3d08;
    public static final int MAGIC_WALL = 0x3908;

    public static final int EXPANDING_WALL_HORIZONTAL = 0x3708;
    public static final int EXPANDING_WALL_VERTICAL = 0x3508;

    public static final int BACKGROUND_1 = 0x0001;
    public static final int BACKGROUND_2 = 0x0002;
    public static final int BACKGROUND_3 = 0x0100;
    public static final int BACKGROUND_4 = 0x0101;
    public static final int BACKGROUND_5 = 0x0102;
    public static final int BACKGROUND_6 = 0x0103;
    public static final int BACKGROUND_7 = 0x0200;
    public static final int BACKGROUND_8 = 0x0201;
    public static final int BACKGROUND_9 = 0x0202;
    public static final int BACKGROUND_10 = 0x0301;
    public static final int BACKGROUND_11 = 0x0302;

    private static String levelsIniFile = null;
    private static String[] levelsIniLines = null;
    private static String toc = null;
    private static Map<String, BufferedImage> name2image = new HashMap<>();
    private static final Font font = new Font(Font.SERIF, Font.BOLD, 20);

    /**
     * Convert Boulder Dash Rocks level file to a map.
     */
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        
        List<String> argList = new ArrayList<String>(Arrays.asList(args));

        if (argList.contains("-h") || argList.contains("--help") || argList.size() == 0)
        {
            System.out.println();
            System.out.println("Usage: MakeMap [level.bdl] ...");
            System.out.println();
            System.out.println("Convert Boulder Dash Rocks level into a PNG map.");
            System.out.println();
            System.out.println("Options:");            
            System.out.println(" -levelsIniFile <filename>  Levels.ini filename");
            System.out.println(" -toc <filename>            Create HTML file which links all PNG maps");
            System.out.println();
            System.exit(0);
        }
        
        levelsIniFile = getArgumentString(argList, "levelsIniFile");
        toc = getArgumentString(argList, "toc");
        SortedSet<String> sortedFiles = new TreeSet<>();
        for (String file : argList)
        {
            String pngFilename = convertLevelToMap(file);
            sortedFiles.add(pngFilename);
        }
        
        createHtmlToc(sortedFiles);
        
        long endTime = System.currentTimeMillis();
        long millis = endTime - startTime;
        int seconds = (int) (millis / 1000) % 60 ;
        int minutes = (int) ((millis / (1000*60)) % 60);
        System.out.println(String.format("Time consumed: %02d:%02d", minutes, seconds));
    }
    
    private static String getArgumentString(List<String> argList, String arg)
    {
        int index = argList.indexOf("-" + arg);
        if (index != -1 && argList.size() > index + 1)
        {
            String value = argList.get(index + 1);
            System.out.println(arg + " = " + value);
            argList.remove(index + 1);
            argList.remove(index);
            return value;
        }

        return null;
    }
    
    enum World
    {
        WORLD1, WORLD2, WORLD3, WORLD4
    }

    private static World getWorld(int world)
    {
        switch (world)
        {
        case 1:
            return World.WORLD1;
        case 2:
            return World.WORLD2;
        case 3:
            return World.WORLD3;
        case 4:
            return World.WORLD4;
        case 5:
            return World.WORLD1; // boss stage
        case 6:
            return World.WORLD2; // boss stage
        case 7:
            return World.WORLD3; // boss stage
        case 8:
            return World.WORLD4; // boss stage
        default:
            throw new IllegalArgumentException("Illegal world found: " + world);
        }
    }

    /**
     * Convert level to PNG map file.
     * @param filename level.bdl
     * @return PNG filename
     */
    private static String convertLevelToMap(String filename)
    {
        byte[] level = FileUtil2.readFile(filename);
        World world = getWorld(level[WORLD]);
        int width = level[WIDTH];
        int height = level[HEIGHT];
        int level_start = indexOf(level, toByte(LEVEL_DATA_HEADER)) + LEVEL_DATA_HEADER.length;
        int level_properties = indexOf(level, toByte(LEVEL_PROPERTIES_HEADER)) + LEVEL_PROPERTIES_HEADER.length;
        System.out.println("Level: " + filename);
        System.out.println("Dimension: " + width + " x " + height);
        int diamonds = Byte.toUnsignedInt(level[level_properties + 2]);
        int time = Byte.toUnsignedInt(level[level_properties + 10]);
        if (filename.contains("BossLevel") && time == 0)
        {
            time = diamonds;
            diamonds = 0;
        }
        System.out.println("Diamonds needed: " + diamonds);
        System.out.println("Maximum time: " + time);

        BufferedImage img = new BufferedImage(width * TILE_SIZE, height * TILE_SIZE, BufferedImage.TYPE_INT_RGB);

        int pos = level_start;
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int e = Byte.toUnsignedInt(level[pos]) * 256 + Byte.toUnsignedInt(level[pos + 1]);
                if (e == 0)
                {
                    pos += 2; // skip space
                    System.out.print(" "); // space
                    continue;
                }
                System.out.print(convert(e));
                addTile(img, x, y, e, world);
                pos += 2; // element is 2 bytes wide
                
                int highByte = Byte.toUnsignedInt(level[pos]);
                int lowByte = Byte.toUnsignedInt(level[pos + 1]);
                e = highByte * 256 + lowByte; // separator (2 zero bytes) or filler
                if (e == 0)
                {
                    pos += 2; // separated with 2 zero bytes
                }
                else if (lowByte == 0)
                {

                    pos += 2; // skip filler
                }
            }
            System.out.println();
        }

        // bytes beyond this position are backgrounds only, can be nulled until footer: 4D 49 47 00 ("MIG")
        // useful to make screenshots without background decoration
        System.out.println("Level end position: 0x" + Integer.toHexString(pos));

        // crop levels
        Color c = new Color(img.getRGB(0, 0));
        if (c.equals(Color.BLACK))
        {
            System.out.println("Map cropped");
            img = ImageUtil2.crop(img);
        }

        // add description footer
        String renamedFile = renameLevelAccordingToIniFile(filename, world);
        String footer = "Level: " + renamedFile + ", Diamonds needed: " + diamonds + ", Max. time: " + time;
        BufferedImage imageWithFooter = new BufferedImage(img.getWidth(), img.getHeight() + font.getSize() * 2, BufferedImage.TYPE_INT_RGB);
        imageWithFooter.getGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        Graphics g = imageWithFooter.getGraphics();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(footer, 5, imageWithFooter.getHeight() - fm.getHeight() / 2);
        g.dispose();
        
        System.out.println("Writing map file: " + renamedFile  + ".png");
        ImageUtil2.writeImage(imageWithFooter, renamedFile + ".png");
        System.out.println();
        
        return renamedFile;
    }

    private static void addTile(BufferedImage img, int x, int y, int element, World world)
    {
        String f = getFilename(element, world);
        BufferedImage tile = name2image.get(f); 
        if (tile == null)
        {
            URL url = MakeMap.class.getResource("/gfx/" + f);
            tile = ImageUtil2.readImage(url);
            name2image.put(f, tile);
        }

        Graphics g = img.getGraphics();
        g.drawImage(tile, x * TILE_SIZE, y * TILE_SIZE, null);
        g.dispose();
    }

    public static String convert(int element)
    {
        switch (element)
        {
        case TITANIUM_WALL_1:
        case TITANIUM_WALL_2:
        case TITANIUM_WALL_3:
        case TITANIUM_WALL_4:
        case TITANIUM_WALL_5:
        case TITANIUM_WALL_6:
        case TITANIUM_WALL_7:
        case TITANIUM_WALL_8:
        case TITANIUM_WALL_9:
        case TITANIUM_WALL_10:
            return "W";
        case MONSTER_GATE_1:
        case MONSTER_GATE_2:
            return "=";
        case EXIT:
            return "E";
        case WALL_1:
        case WALL_2:
        case WALL_3:
        case WALL_4:
        case WALL_5:
        case WALL_6:
        case WALL_7:
        case WALL_8:
        case WALL_9:
        case WALL_10:
        case WALL_11:
        case WALL_12:
        case WALL_13:
            return "w";
        case DIAMOND:
        case DIAMOND_FALLING:
            return "d";
        case DIAMOND_SUPER:
            return "D";
        case DIAMOND_BOSS_TOP_LEFT:
            return "(";
        case DIAMOND_BOSS_TOP_RIGHT:
            return ")";
        case DIAMOND_BOSS_BOTTOM_LEFT:
            return "[";
        case DIAMOND_BOSS_BOTTOM_RIGHT:
            return "]";
        case DIRT:
            return ".";
        case SPACE:
        case SPACE_DD:
            return " ";
        case ROCKFORD:
            return "R";
        case BOULDER:
            return "S";
        case BOULDER_HEAVY:
            return "B";
        case BOULDER_LIGHT:
            return "b";
        case MONSTER_UP_DOWN:
            return "1";
        case MONSTER_LEFT_RIGHT_1:
        case MONSTER_LEFT_RIGHT_2:
            return "2";
        case MONSTER_BEES:
            return "3";
        case MONSTER_SKULL:
            return "4";
        case MONSTER_SKULL_FLAME_UP:
            return "5";
        case MONSTER_SKULL_FLAME_RIGHT:
            return "5";
        case MONSTER_SKULL_FLAME_DOWN:
            return "5";
        case MONSTER_SKULL_FLAME_LEFT:
            return "5";
        case MONSTER_FLAME_1:
        case MONSTER_FLAME_2:
        case MONSTER_FLAME_3:
        case MONSTER_FLAME_4:
            return "6";
        case MONSTER_TAURUS_1:
        case MONSTER_TAURUS_2:
        case MONSTER_TAURUS_3:
        case MONSTER_TAURUS_4:
            return "7";
        case MONSTER_DIAMOND_EATER_1:
        case MONSTER_DIAMOND_EATER_2:
            return "8";
        case MONSTER_CRAB_1:
        case MONSTER_CRAB_2:
            return "9";
        case MONSTER_SQUID_1:
        case MONSTER_SQUID_2:
        case MONSTER_SQUID_3:
        case MONSTER_SQUID_4:
            return "q";
        case MONSTER_MANTA_RAY_1:
        case MONSTER_MANTA_RAY_2:
            return "r";
        case MONSTER_SKELETON:
            return "n";
        case MONSTER_OWL_1:
        case MONSTER_OWL_2:
        case MONSTER_OWL_3:
        case MONSTER_OWL_4:
            return "O";
        case MONSTER_SPIDER:
            return "%";
        case BOSS:
            return "X";
        case POWERUP_AMMO:
            return "A";
        case POWERUP_AMMO_ONCE:
            return "a";
        case POWERUP_MAGNET:
            return "C";
        case POWERUP_MAGNET_ONCE:
            return "c";
        case POWERUP_WATER:
            return "J";
        case POWERUP_WATER_ONCE:
            return "j";
        case POWERUP_BOMB:
            return "G";
        case POWERUP_BOMB_ONCE:
            return "g";
        case POWERUP_FREEZE:
        case POWERUP_FREEZE_ONCE: // unused
            return "s";
        case POWERUP_EMPTY:
            return "0";
        case POWERUP_SPEED:
            return "F";
        case POWERUP_TIME:
            return "t";
        case KEY_RED:
            return "k";
        case KEY_YELLOW:
            return "l";
        case KEY_WHITE:
            return "m";
        case LOCK_RED:
            return "K";
        case LOCK_YELLOW:
            return "L";
        case LOCK_WHITE:
            return "M";
        case HEALTH_ONCE:
            return "h";
        case HEALTH:
            return "H";
        case DYNAMITE:
            return "§";
        case TELEPORTER_BLUE:
            return "T";
        case TELEPORTER_WHITE:
            return "U";
        case TELEPORTER_GREEN:
            return "V";
        case TELEPORTER_ORANGE:
            return "W";
        case ONEWAY_RIGHT:
            return ">";
        case ONEWAY_UP:
            return "^";
        case ONEWAY_LEFT:
            return "<";
        case ONEWAY_DOWN:
            return ",";
        case LAVA_DOWN_RIGHT:
        case LAVA_UP_LEFT:
            return "/";
        case LAVA_DOWN_LEFT:
            return "X";
        case LAVA_UP_RIGHT:
            return "\\";
        case LAVA_HORIZONTAL_1:
        case LAVA_HORIZONTAL_2:
        case LAVA_LEFT:
        case LAVA_RIGHT:
        case LAVA_ISOLATED:
            return "-";
        case LAVA_UP:
        case LAVA_DOWN:
        case LAVA_VERTICAL_1:
        case LAVA_VERTICAL_2:
            return "|";
        case INFLAMMABLE:
            return "i";
        case OIL_DROP:
            return "o";
        case AMOEBA:
            return "$";
        case WATER:
            return "~";
        case HYDRANT_LEFT:
            return "{";
        case HYDRANT_RIGHT:
            return "}";
        case POWERUP_DIVE:
            return "&";
        case SPIDER_WEB:
            return "#";
        case POISON_BOTTLE:
            return "p";
        case MAGIC_WALL:
            return "y";
        case EXPANDING_WALL_HORIZONTAL:
            return "Z";
        case EXPANDING_WALL_VERTICAL:
            return "N";
        case BACKGROUND_1:
        case BACKGROUND_2:
        case BACKGROUND_3:
        case BACKGROUND_4:
        case BACKGROUND_5:
        case BACKGROUND_6:
        case BACKGROUND_7:
        case BACKGROUND_8:
        case BACKGROUND_9:
        case BACKGROUND_10:
        case BACKGROUND_11:
            return ""; // background (unfortunately hit, because the level size for some levels include outer walls) 
        default:
            System.out.println("Unknown: 0x" + Integer.toHexString(element));
            return "?";
        }
    }

    public static String getFilename(int element, World world)
    {
        switch (element)
        {
        case TITANIUM_WALL_1:
        case TITANIUM_WALL_2:
        case TITANIUM_WALL_3:
        case TITANIUM_WALL_4:
        case TITANIUM_WALL_5:
        case TITANIUM_WALL_6:
        case TITANIUM_WALL_7:
        case TITANIUM_WALL_8:
        case TITANIUM_WALL_9:
        case TITANIUM_WALL_10:
            switch (world)
            {
            case WORLD1:
                return "titanium_wall_world1.png";
            case WORLD2:
                return "titanium_wall_world2.png";
            case WORLD3:
                return "titanium_wall_world3.png";
            case WORLD4:
                return "titanium_wall_world4.png";
            }

        case MONSTER_GATE_1:
        case MONSTER_GATE_2:
            return "monster_gate.png";
        case EXIT:
            return "exit.png";
        case WALL_1:
        case WALL_2:
        case WALL_3:
        case WALL_4:
        case WALL_5:
        case WALL_6:
        case WALL_7:
        case WALL_8:
        case WALL_9:
        case WALL_10:
        case WALL_11:
        case WALL_12:
        case WALL_13:
            switch (world)
            {
            case WORLD1:
                return "wall_world1.png";
            case WORLD2:
                return "wall_world2.png";
            case WORLD3:
                return "wall_world3.png";
            case WORLD4:
                return "wall_world4.png";
            }
        case DIAMOND:
        case DIAMOND_FALLING:
            switch (world)
            {
            case WORLD1:
                return "diamond_world1.png";
            case WORLD2:
                return "diamond_world2.png";
            case WORLD3:
                return "diamond_world3.png";
            case WORLD4:
                return "diamond_world4.png";
            }
        case DIAMOND_SUPER:
            return "diamond_super.png";
        case DIAMOND_BOSS_TOP_LEFT:
            return "diamond_big1.png";
        case DIAMOND_BOSS_TOP_RIGHT:
            return "diamond_big2.png";
        case DIAMOND_BOSS_BOTTOM_LEFT:
            return "diamond_big3.png";
        case DIAMOND_BOSS_BOTTOM_RIGHT:
            return "diamond_big4.png";
        case DIRT:
            switch (world)
            {
            case WORLD1:
                return "dirt_world1.png";
            case WORLD2:
                return "dirt_world2.png";
            case WORLD3:
                return "dirt_world3.png";
            case WORLD4:
                return "dirt_world4.png";
            }
        case SPACE:
            return "space.png";
        case SPACE_DD:
            return "space.png"; // TODO maybe player2?
        case ROCKFORD:
            return "rockford.png";
        case BOULDER:
            switch (world)
            {
            case WORLD1:
                return "boulder_world1.png";
            case WORLD2:
                return "boulder_world2.png";
            case WORLD3:
                return "boulder_world3.png";
            case WORLD4:
                return "boulder_world4.png";
            }
        case BOULDER_HEAVY:
            return "boulder_heavy.png";
        case BOULDER_LIGHT:
            return "boulder_light.png";
        case MONSTER_UP_DOWN:
            return "monster_up_down.png";
        case MONSTER_LEFT_RIGHT_1:
        case MONSTER_LEFT_RIGHT_2:
            return "monster_left_right.png";
        case MONSTER_BEES:
            return "monster_bees.png";
        case MONSTER_SKULL:
            return "monster_skull.png";
        case MONSTER_SKULL_FLAME_UP:
            return "monster_skull_flame_up.png";
        case MONSTER_SKULL_FLAME_RIGHT:
            return "monster_skull_flame_right.png";
        case MONSTER_SKULL_FLAME_DOWN:
            return "monster_skull_flame_down.png";
        case MONSTER_SKULL_FLAME_LEFT:
            return "monster_skull_flame_left.png";
        case MONSTER_FLAME_1:
        case MONSTER_FLAME_2:
        case MONSTER_FLAME_3:
        case MONSTER_FLAME_4:
            return "monster_flame.png";
        case MONSTER_TAURUS_1:
        case MONSTER_TAURUS_2:
        case MONSTER_TAURUS_3:
        case MONSTER_TAURUS_4:
            return "monster_taurus.png";
        case MONSTER_DIAMOND_EATER_1:
        case MONSTER_DIAMOND_EATER_2:
            return "monster_diamond_eater.png";
        case MONSTER_CRAB_1:
        case MONSTER_CRAB_2:
            return "monster_crab.png";
        case MONSTER_SQUID_1:
        case MONSTER_SQUID_2:
        case MONSTER_SQUID_3:
        case MONSTER_SQUID_4:
            return "monster_squid.png";
        case MONSTER_MANTA_RAY_1:
        case MONSTER_MANTA_RAY_2:
            return "monster_manta_ray.png";
        case MONSTER_SKELETON:
            return "monster_skeleton.png";
        case MONSTER_OWL_1:
        case MONSTER_OWL_2:
        case MONSTER_OWL_3:
        case MONSTER_OWL_4:
            return "monster_owl.png";
        case MONSTER_SPIDER:
            return "monster_spider.png";
        case BOSS:
            return "boss.png";
        case POWERUP_AMMO:
            return "powerup_ammo.png";
        case POWERUP_AMMO_ONCE:
            return "powerup_ammo_once.png";
        case POWERUP_MAGNET:
            return "powerup_magnet.png";
        case POWERUP_MAGNET_ONCE:
            return "powerup_magnet_once.png";
        case POWERUP_WATER:
            return "powerup_water.png";
        case POWERUP_WATER_ONCE:
            return "powerup_water_once.png";
        case POWERUP_BOMB:
            return "powerup_bomb.png";
        case POWERUP_BOMB_ONCE:
            return "powerup_bomb_once.png";
        case POWERUP_FREEZE:
            return "powerup_freeze.png";
        case POWERUP_FREEZE_ONCE:
            return "powerup_freeze_once.png";
        case POWERUP_EMPTY:
            return "powerup_empty.png";
        case POWERUP_SPEED:
            return "powerup_speed.png";
        case POWERUP_TIME:
            return "powerup_time.png";
        case KEY_RED:
            return "key_red.png";
        case KEY_YELLOW:
            return "key_yellow.png";
        case KEY_WHITE:
            return "key_white.png";
        case LOCK_RED:
            return "lock_red.png";
        case LOCK_YELLOW:
            return "lock_yellow.png";
        case LOCK_WHITE:
            return "lock_white.png";
        case HEALTH_ONCE:
            return "health_once.png";
        case HEALTH:
            return "health.png";
        case DYNAMITE:
            return "dynamite.png";
        case TELEPORTER_BLUE:
            return "teleporter_blue.png";
        case TELEPORTER_WHITE:
            return "teleporter_white.png";
        case TELEPORTER_GREEN:
            return "teleporter_green.png";
        case TELEPORTER_ORANGE:
            return "teleporter_orange.png";
        case ONEWAY_RIGHT:
            return "oneway_right.png";
        case ONEWAY_UP:
            return "oneway_up.png";
        case ONEWAY_LEFT:
            return "oneway_left.png";
        case ONEWAY_DOWN:
            return "oneway_down.png";
        case LAVA_DOWN_RIGHT:
            return "lava_down_right.png";
        case LAVA_UP_LEFT:
            return "lava_up_left.png";
        case LAVA_DOWN_LEFT:
            return "lava_down_left.png";
        case LAVA_UP_RIGHT:
            return "lava_up_right.png";
        case LAVA_HORIZONTAL_1:
        case LAVA_HORIZONTAL_2:
            return "lava_horizontal.png";
        case LAVA_LEFT:
            return "lava_left.png";
        case LAVA_RIGHT:
            return "lava_right.png";
        case LAVA_ISOLATED:
            return "lava_isolated.png";
        case LAVA_UP:
            return "lava_up.png";
        case LAVA_DOWN:
            return "lava_down.png";
        case LAVA_VERTICAL_1:
        case LAVA_VERTICAL_2:
            return "lava_vertical.png";
        case INFLAMMABLE:
            return "inflammable.png";
        case OIL_DROP:
            return "oil_drop.png";
        case AMOEBA:
            return "amoeba.png";
        case WATER:
            return "water.png";
        case HYDRANT_LEFT:
            return "hydrant_left.png";
        case HYDRANT_RIGHT:
            return "hydrant_right.png";
        case POWERUP_DIVE:
            return "powerup_dive.png";
        case SPIDER_WEB:
            return "spiderweb.png";
        case POISON_BOTTLE:
            return "poison_bottle.png";
        case MAGIC_WALL:
            return "magic_wall.png";
        case EXPANDING_WALL_HORIZONTAL:
            return "expanding_wall_horizontal.png";
        case EXPANDING_WALL_VERTICAL:
            return "expanding_wall_vertical.png";
        case BACKGROUND_1:
        case BACKGROUND_2:
        case BACKGROUND_3:
        case BACKGROUND_4:
        case BACKGROUND_5:
        case BACKGROUND_6:
        case BACKGROUND_7:
        case BACKGROUND_8:
        case BACKGROUND_9:
        case BACKGROUND_10:
        case BACKGROUND_11:
            return "space.png"; // background (unfortunately hit, because the level size for some levels include outer walls) 
        default:
            System.out.println("Unknown: 0x" + Integer.toHexString(element));
            return "?";
        }
    }

    private static String renameLevelAccordingToIniFile(String levelFilename, World world)
    {
        String type = ""; // to add prefix RouteRace, DoubleDash or TimeTrial
                          // to get a natural file order  
        String f = FileUtil2.getBasename(levelFilename); // examples: "PlanetTour_JungleWorld_01", "BossLevel_Jungle.bdl", "RouteRace_000a.bdl"
        if (!f.startsWith("PlanetTour") && !f.startsWith("BossLevel"))
        {
            int index = f.indexOf("_");
            if (index != -1)
            {
                type = f.substring(0, index);
            }
        }
        
        if (levelsIniFile == null)
        {
            return world.toString().toLowerCase() + "-" + f;
        }

        if (levelsIniLines == null)
        {
            String levelsIni = FileUtil2.readFileUTF8(levelsIniFile);
            levelsIniLines = levelsIni.split("\n");
        }
        for (int i = 0; i < levelsIniLines.length; i++)
        {
            String line = levelsIniLines[i]; // example: "1=PlanetTour_JungleWorld_01.bdl"
            if (line.contains(f))
            {
                String[] number_file = line.split("=");
                String number = number_file[0];
                if (number.length() == 1)
                {
                    number = "0" + number;
                }
                String w = world.toString().toLowerCase();
                if (type.equals(""))
                {
                    return w + "-" + number + "-" + f;
                }
                else
                {
                    return type + "-" + number + "-" + f;
                }
                
            }
        }

        System.err.println("\nERROR: " + levelsIniFile + " does not contain: " + levelFilename);
        System.exit(1);

        return null;
    }
    
    private static void createHtmlToc(SortedSet<String> sortedFiles)
    {
        if (toc == null)
        {
            return;
        }
        
        StringBuffer buf = new StringBuffer();
        String header = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                    <meta name="viewport" content="width=320, initial-scale=1, minimum-scale=0.75"/>
                    <meta name="ROBOTS" content="NOARCHIVE">
                    <title>Boulder Dash Rocks! (DS) Maps</title>
                </head>
                <body style="margin:0">
                <h1>Boulder Dash Rocks! (DS) Maps</h1>
                <div style="padding:5px">
                """;
        buf.append(header);
        
        for (String file : sortedFiles)
        {
            buf.append("<a href=\""+ file + ".png\">" + file + "</a><br>\n");
        }

        String footer = """
                </div>
                </body>
                </html>
                """;
        buf.append(footer);

        System.out.println("Writing toc file: " + toc);
        FileUtil2.writeFileUTF8(toc, buf.toString());
        System.out.println();
    }
}
