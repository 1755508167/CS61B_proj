import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    // 地图整个区域的边界（depth=0）
    static final double ROOT_ULLON = -122.2998046875; // 左上角经度
    static final double ROOT_ULLAT = 37.892195547244356; //左上角纬度
    static final double ROOT_LRLON = -122.2119140625; //右下角经度
    static final double ROOT_LRLAT = 37.82280243352756; //右下角纬度

    private final double PIXEL = 256;//分辨率，每张图片是256*256的

    //用一个数据结构在存储每张照片的左上、右下两个点的经纬度
    //采用三层Map,三个纬度分别是depth,x,y
    private Map<Integer,Map<Integer,Map<Integer,tileBounds>>> tiles=new HashMap<>();
    //这个列表只会存储8个值，分别对应depth为0-7的每个地图的LonDPP
    private final List<Double> LonDPP_list = new LinkedList<>();

    //辅助类，用来存储每张地图的左上和右下点的经纬度
    private class tileBounds{
        double ullon,ullat,lrlon,lrlat;
        private tileBounds(double ullon,double ullat,double lrlon,double lrlat){
            this.ullon = ullon;
            this.ullat = ullat;
            this.lrlon = lrlon;
            this.lrlat = lrlat;
        }
    }

    //辅助函数，用来计算每个depth的地图的经度跨度
    private double calcLonSpan(int depth){
        return (ROOT_LRLON-ROOT_ULLON)/Math.pow(2,depth);
    }
    //辅助函数，用来计算每个depth的地图的纬度跨度
    private double calcLatSpan(int depth){
        return (ROOT_ULLAT-ROOT_LRLAT)/Math.pow(2,depth);
    }
    //计算每个depth地图的边界
    private tileBounds calcBorder(int depth,int x,int y){
        double lonSpan=calcLonSpan(depth);
        double latSpan=calcLatSpan(depth);
        double ullon=ROOT_ULLON+x*lonSpan;
        double ullat=ROOT_ULLAT-y*latSpan;
        double lrlon=ullon+lonSpan;
        double lrlat=ullat-latSpan;

        return new tileBounds(ullon,ullat,lrlon,lrlat);
    }

    public Rasterer() {
        // YOUR CODE HERE
        //计算每个depth图片的LonDPP(每像素在实际中覆盖的距离)
        double d_longitude=ROOT_LRLON-ROOT_ULLON;//整个区域经度的跨度
        for (int depth=0;depth<8;depth++){
            double temp_longitude=(d_longitude/Math.pow(2,depth))/PIXEL;
            LonDPP_list.add(temp_longitude);
        }
        //计算每个depth地图的边界，并放入三层嵌套的Map中
        for (int depth=0;depth<8;depth++){
            int nums= (int) Math.pow(2,depth);
            Map<Integer,Map<Integer,tileBounds>> xMap=new HashMap<>();
            for (int x=0;x<nums;x++){
                Map<Integer,tileBounds> yMap=new HashMap<>();
                for (int y=0;y<nums;y++){
                    yMap.put(y,calcBorder(depth,x,y));
                }
                xMap.put(x,yMap);
            }
            tiles.put(depth,xMap);
        }

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    //返回一个包含七个字段的Map
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                + "your browser.");
        double ullon = params.get("ullon");//左上角经度
        double ullat = params.get("ullat");//左上角纬度
        double lrlon = params.get("lrlon");//右下角经度
        double lrlat = params.get("lrlat");//右下角纬度
        double w = params.get("w");//图片的宽度(像素数量)
        double h = params.get("h");//图片的高度(像素数量)

        double queryLonDPP = (lrlon - ullon) / w;//计算LonDPP
        //确定选用地图的depth，通过比较LonDPP
        //选择LonDPP最接近用户LonDPP的那一个depth
        int depth=7;
        for (int Dth=0;Dth<8;Dth++){
            if (LonDPP_list.get(Dth)<queryLonDPP){
                depth=Dth;
                break;
            }
        }
        //计算每个tile的经纬度的值
        int num= (int) Math.pow(2,depth);
        double tileLon = (ROOT_LRLON - ROOT_ULLON) / num;
        double tileLat = (ROOT_ULLAT - ROOT_LRLAT) / num;
        //计算tile的起始位置
        int x_start =(int) Math.max(0, Math.floor((ullon - ROOT_ULLON) / tileLon));
        int x_end  =(int) Math.min(num - 1, Math.floor((lrlon - ROOT_ULLON) / tileLon));

        int y_start =(int) Math.max(0, Math.floor((ROOT_ULLAT - ullat) / tileLat));
        int y_end   =(int) Math.min(num - 1, Math.floor((ROOT_ULLAT - lrlat) / tileLat));
        //存储文件名的字符串
        String[][] render_grid=new String[y_end-y_start+1][x_end-x_start+1];
        for (int y=y_start;y<=y_end;y++){
            for (int x=x_start;x<=x_end;x++){
                render_grid[y-y_start][x-x_start]=String.format("d%d_x%d_y%d.png",depth,x,y);
            }
        }

        double raster_ul_lon = ROOT_ULLON + x_start * tileLon;
        double raster_ul_lat = ROOT_ULLAT - y_start * tileLat;
        double raster_lr_lon = ROOT_ULLON + (x_end + 1) * tileLon;
        double raster_lr_lat = ROOT_ULLAT - (y_end + 1) * tileLat;

        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", true);

        return results;
    }
    public static void main(String[] args){

    }

}
