package net.sanjayts.educative.acejava.ds;

import lombok.extern.slf4j.Slf4j;

/*
https://leetcode.com/problems/container-with-most-water/

Proof -- https://leimao.github.io/blog/Proof-Container-With-Most-Water-Problem/
 */
@Slf4j
public class ContainerWithMostWater {

    public static void main(String[] args) {
        var containers = new int[] {1,8,6,2,5,4,8,3,7};
        var out = new ContainerWithMostWater().maxArea(containers);
        log.info("Maximum volume of water contained in containers {} is {}", containers, out);
    }

    public int maxArea(int[] height) {
        int max = -1, beg = 0, end = height.length - 1;
        while (beg < end) {
            int area = (end - beg) * Math.min(height[beg], height[end]);
            if (area > max) max = area;
            if (height[beg] < height[end]) {
                ++beg;
            } else {
                --end;
            }
        }
        return max;
    }

}
