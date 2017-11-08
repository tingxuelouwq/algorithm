package com.kevin.algorithm.greedy.binpacking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/10/25 20:19
 */
public class Main {
    /**
     输入：
     7
     2 5 4 7 1 3 8
     */
    public static void main(String[] args) {
        int n;
        Item[] items;
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()) {
            n = in.nextInt();
            items = new Item[n];
            for(int i = 1; i <= n; i++)
                items[i - 1] = new Item(i, in.nextInt());

            List<Bin> bins = firstFitNonIncreasing(items);
            print(bins);
        }
    }

    private static void print(List<Bin> bins) {
        System.out.println("所需箱子数为: " + bins.size());
        for(Bin bin : bins)
            System.out.println(bin);
    }

    /**
     * next fit算法，联机算法，时间复杂度O(n)
     * 算法思路：当处理任何一项物品时，首先检查看它是否还能装进刚刚装进物品的同一个箱子中去，如果能够装进去，那么就把它放入该箱中；否则，就开辟
     * 一个新的箱子。
     * @param items
     * @return
     */
    public static List<Bin> nextFit(Item[] items) {
        List<Bin> bins = new ArrayList<>();

        Bin oneBin = new Bin();
        oneBin.binPackage(items[0]); // 将第一个物品装箱
        bins.add(oneBin);

        for(int i = 1; i < items.length; i++) {
            Bin box = bins.get(bins.size() - 1);
            if(items[i].weight <= box.avail)    // 如果前一个箱子能够装入物品，则装箱
                box.binPackage(items[i]);
            else {                             // 否则，构建一个新的箱子，并装箱
                Bin anotherBin = new Bin();
                anotherBin.binPackage(items[i]);
                bins.add(anotherBin);
            }
        }

        return bins;
    }

    /**
     * first fit算法，联机算法，时间复杂度O(n^2)，可以做到O(nlogn)
     * 算法思路：依次扫描这些箱子，并把新的一项物品放入足以盛下它的第一个箱子中。如果前面那些放置物品的箱子已经容不下当前物品时，才开辟一个新箱子。
     * @param items
     * @return
     */
    public static List<Bin> firstFit(Item[] items) {
        List<Bin> bins = new ArrayList<>();

        Bin oneBin = new Bin();
        oneBin.binPackage(items[0]);
        bins.add(oneBin);

        boolean flag;
        for(int i = 1; i < items.length; i++) {
            flag = false;
            for(Bin bin : bins) {
                if (items[i].weight <= bin.avail) {
                    bin.binPackage(items[i]);
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                Bin anotherBin = new Bin();
                anotherBin.binPackage(items[i]);
                bins.add(anotherBin);
            }
        }

        return bins;
    }

    /**
     * best bit算法，联机算法，时间复杂度O(nlogn)
     * 算法思路：把一项新物品放入所有箱子中能够容纳它的最满的箱子中，如果都不能放入，才开辟一个新箱子。
     * @param items
     * @return
     */
    public static List<Bin> bestFit(Item[] items) {
        List<Bin> bins = new ArrayList<>();

        Bin oneBin = new Bin();
        oneBin.binPackage(items[0]);
        bins.add(oneBin);

        int minAvail;
        int minIndex;
        boolean flag;
        for(int i = 1; i < items.length; i++) {
            minAvail = Integer.MAX_VALUE;
            minIndex = 0;
            flag = false;
            for(int j = 0; j < bins.size(); j++) {
                if(items[i].weight <= bins.get(j).avail) {
                    if(bins.get(j).avail - items[i].weight < minAvail) {
                        minAvail = bins.get(j).avail - items[i].weight;
                        minIndex = j;
                    }
                    flag = true;
                }
            }
            if(flag)
                bins.get(minIndex).binPackage(items[i]);
            else {
                Bin anotherBin = new Bin();
                anotherBin.binPackage(items[i]);
                bins.add(anotherBin);
            }
        }

        return bins;
    }

    /**
     * first fit non-increasing算法，脱机算法
     * @param items
     * @return
     */
    public static List<Bin> firstFitNonIncreasing(Item[] items) {
        quickSort(items, 0, items.length - 1);
        return firstFit(items);
    }

    private static void quickSort(Item[] items, int left, int right) {
        if(left < right) {
            int i = left, j = right;
            Item pivot = items[i];

            while(i < j) {
                while (i < j && items[j].weight < pivot.weight)
                    j--;
                if (i < j)
                    items[i++] = items[j];
                while (i < j && items[i].weight > pivot.weight)
                    i++;
                if (i < j)
                    items[j--] = items[i];
            }

            items[i] = pivot;
            quickSort(items, left, i - 1);
            quickSort(items, i + 1, right);
        }
    }
}
