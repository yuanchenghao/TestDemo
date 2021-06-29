package ch.study.leetcode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 文 件 名: _20有效括号_easy
 * 创 建 人: 原成昊
 * 创建日期: 2021/6/29 19:02
 * 邮   箱: 188897876@qq.com
 * 修改备注：
 */
//        给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
//        有效字符串需满足：
//        左括号必须用相同类型的右括号闭合。
//        左括号必须以正确的顺序闭合。
//
//        示例 1：
//        输入：s = "()"
//        输出：true
//
//        示例 2：
//        输入：s = "()[]{}"
//        输出：true
//
//        示例 3：
//        输入：s = "(]"
//        输出：false
//
//        示例 4：
//        输入：s = "([)]"
//        输出：false
//
//        示例 5：
//        输入：s = "{[]}"
//        输出：true
//
//        提示：
//        1 <= s.length <= 104
//        s 仅由括号 '()[]{}' 组成

class _20有效括号_easy {
    public boolean isValid(String s) {
        int n = s.length();
        if (n % 2 == 1) {
            return false;
        }
        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Deque<Character> stack = new LinkedList<Character>();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (pairs.containsKey(ch)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }
}
