package io.renren.modules.app.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @auther guanhongli
 * @date 2023/2/28 9:11 PM
 */
public class commonUtils {

    public static List<String> mergeList(List<String> names, List<Integer> ages) {

        List<String> result = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            result.add(names.get(i) + ages.get(i));
        }

        System.out.println(result);
        return result;
    }

    public static double calculateAverage(List<Double> array) {
        double sum = 0;
        int count = 0;

        // 计算总和，并统计非零元素的数量
        for (Double num : array) {
            if (num != 0) {
                sum += num;
                count++;
            }
        }

        // 避免除以零的情况
        if (count == 0) {
            return 0.0;
        }

        // 计算平均值

        return sum / count;
    }


    public static double roundToTwoDecimalPlaces(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String roundedValue = decimalFormat.format(value);
        return Double.parseDouble(roundedValue);
    }

    public static double calculateSum( List<Double> list) {
        double sum = 0.0;
        // 遍历列表并求和
        for (double score : list) {
            sum += score;
        }
        return sum;
    }

}
