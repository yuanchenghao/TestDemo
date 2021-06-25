package ch.study.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 文 件 名: 两数之后
 * 创 建 人: 原成昊
 * 创建日期: 2021/6/24 16:45
 * 邮   箱: 188897876@qq.com
 * 修改备注：
 */
//        给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，
//        并返回它们的数组下标。你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
//        你可以按任意顺序返回答案。
//
//        示例 1：
//        输入：nums=[2,7,11,15],target=9
//        输出：[0,1]
//        解释：因为 nums[0]+nums[1]==9 ，返回[0,1] 。
//
//        示例 2：
//        输入：nums=[3,2,4],target=6
//        输出：[1,2]
//
//        示例 3：
//        输入：nums=[3,3],target=6
//        输出：[0,1]


class _01两数之后_easy {
    //暴力遍历 时间复杂度O(N^2)
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    //哈希表
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(target - nums[i])) {
                return new int[]{hashMap.get(target - nums[i]), i};
            } else {
                hashMap.put(nums[i], i);
            }
        }
        return null;
    }
}
