package io.renren.common.utils;

/**
 * @version 1.0
 * @auther guanhongli
 * @date 2023/1/18 9:50 PM
 */
public class goodsUntils {
    /**
     * 判断进出口类型
     * @param fileName
     * @return
     */
    public static String getTradeType(String fileName){
        if (fileName.contains("进出口")){
            return "进出口";
        }
        if (fileName.contains("进口")){
            return "进口";
        }
        if (fileName.contains("出口")){
            return "出口";
        }
        return "无进出口类型";
    }
}
