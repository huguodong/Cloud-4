package com.ssit.util;

import com.ssit.entity.BookCategoryEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShowDataUtils {
    // 借书增加量
    public static List<Integer> bookBorrow = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(20);//00:00
            add(15);//01:00
            add(10);//02:00
            add(5);//03:00
            add(5);//04:00
            add(5);//05:00

            // 每分钟增加一次
            add(2);//06:00
            add(2);//07:00
            add(5);//08:00
            add(10);//09:00
            add(8);//10:00
            add(10);//11:00
            add(8);//12:00
            add(5);//13:00
            add(6);//14:00
            add(10);//15:00
            add(15);//16:00
            add(13);//17:00
            add(12);//18:00
            add(12);//19:00
            add(8);//20:00
            add(6);//21:00

            // 每30分钟增加一次
            add(5);//22:00
            add(5);//23:00
        }
    };

    // 借书增加量基数
    public static List<Integer> bookBorrowBase = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(0);//00:00
            add(0);//01:00
            add(0);//02:00
            add(0);//03:00
            add(0);//04:00
            add(0);//05:00

            // 每分钟增加一次
            add(0);//06:00
            add(1);//07:00
            add(5);//08:00
            add(12);//09:00
            add(8);//10:00
            add(10);//11:00
            add(8);//12:00
            add(5);//13:00
            add(8);//14:00
            add(15);//15:00
            add(15);//16:00
            add(13);//17:00
            add(10);//18:00
            add(9);//19:00
            add(5);//20:00
            add(3);//21:00

            // 每30分钟增加一次
            add(100);//22:00
            add(5);//23:00
        }
    };

    // 还书增加量
    public static List<Integer> bookReturn = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(20);//00:00
            add(15);//01:00
            add(10);//02:00
            add(5);//03:00
            add(5);//04:00
            add(5);//05:00

            // 每分钟增加一次
            add(3);//06:00
            add(3);//07:00
            add(6);//08:00
            add(11);//09:00
            add(9);//10:00
            add(11);//11:00
            add(9);//12:00
            add(6);//13:00
            add(7);//14:00
            add(16);//15:00
            add(14);//16:00
            add(12);//17:00
            add(11);//18:00
            add(11);//19:00
            add(9);//20:00
            add(7);//21:00

            // 每30分钟增加一次
            add(5);//22:00
            add(5);//23:00
        }
    };

    // 还书增加量基数
    public static List<Integer> bookReturnBase = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(0);//00:00
            add(0);//01:00
            add(0);//02:00
            add(0);//03:00
            add(0);//04:00
            add(0);//05:00

            // 每分钟增加一次
            add(1);//06:00
            add(2);//07:00
            add(6);//08:00
            add(14);//09:00
            add(8);//10:00
            add(13);//11:00
            add(9);//12:00
            add(6);//13:00
            add(9);//14:00
            add(15);//15:00
            add(15);//16:00
            add(12);//17:00
            add(9);//18:00
            add(8);//19:00
            add(6);//20:00
            add(4);//21:00

            // 每30分钟增加一次
            add(100);//22:00
            add(5);//23:00
        }
    };

    // 续借增加量
    public static List<Integer> bookContinue = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(1);//00:00
            add(1);//01:00
            add(1);//02:00
            add(1);//03:00
            add(1);//04:00
            add(1);//05:00

            // 每分钟增加一次
            add(1);//06:00
            add(1);//07:00
            add(2);//08:00
            add(3);//09:00
            add(3);//10:00
            add(3);//11:00
            add(3);//12:00
            add(3);//13:00
            add(3);//14:00
            add(3);//15:00
            add(3);//16:00
            add(3);//17:00
            add(3);//18:00
            add(3);//19:00
            add(6);//20:00
            add(6);//21:00

            // 每30分钟增加一次
            add(1);//22:00
            add(1);//23:00
        }
    };

    // 续借增加量基数
    public static List<Integer> bookContinueBase = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(0);//00:00
            add(0);//01:00
            add(0);//02:00
            add(0);//03:00
            add(0);//04:00
            add(0);//05:00

            // 每分钟增加一次
            add(0);//06:00
            add(1);//07:00
            add(1);//08:00
            add(1);//09:00
            add(1);//10:00
            add(1);//11:00
            add(1);//12:00
            add(1);//13:00
            add(1);//14:00
            add(1);//15:00
            add(1);//16:00
            add(1);//17:00
            add(1);//18:00
            add(1);//19:00
            add(1);//20:00
            add(1);//21:00

            // 每30分钟增加一次
            add(0);//22:00
            add(0);//23:00
        }
    };

    // 进馆增加量
    public static List<Integer> visitIn = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(10);//00:00
            add(10);//01:00
            add(10);//02:00
            add(10);//03:00
            add(10);//04:00
            add(10);//05:00

            // 每分钟增加一次
            add(2);//06:00
            add(2);//07:00
            add(30);//08:00
            add(60);//09:00
            add(50);//10:00
            add(40);//11:00
            add(30);//12:00
            add(20);//13:00
            add(45);//14:00
            add(50);//15:00
            add(30);//16:00
            add(30);//17:00
            add(30);//18:00
            add(50);//19:00
            add(30);//20:00
            add(20);//21:00

            // 每30分钟增加一次
            add(10);//22:00
            add(5);//23:00
        }
    };

    // 进馆增加量基数
    public static List<Integer> visitInBase = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(0);//00:00
            add(0);//01:00
            add(0);//02:00
            add(0);//03:00
            add(0);//04:00
            add(0);//05:00

            // 每分钟增加一次
            add(0);//06:00
            add(0);//07:00
            add(27);//08:00
            add(52);//09:00
            add(42);//10:00
            add(32);//11:00
            add(27);//12:00
            add(15);//13:00
            add(47);//14:00
            add(40);//15:00
            add(42);//16:00
            add(25);//17:00
            add(32);//18:00
            add(45);//19:00
            add(45);//20:00
            add(20);//21:00

            // 每30分钟增加一次
            add(80);//22:00
            add(40);//23:00
        }
    };

    // 出馆馆增加量
    public static List<Integer> visitOut = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(10);//00:00
            add(10);//01:00
            add(10);//02:00
            add(10);//03:00
            add(10);//04:00
            add(10);//05:00

            // 每分钟增加一次
            add(2);//06:00
            add(2);//07:00
            add(15);//08:00
            add(30);//09:00
            add(30);//10:00
            add(50);//11:00
            add(60);//12:00
            add(30);//13:00
            add(30);//14:00
            add(30);//15:00
            add(35);//16:00
            add(50);//17:00
            add(25);//18:00
            add(35);//19:00
            add(30);//20:00
            add(30);//21:00

            // 每30分钟增加一次
            add(1);//22:00
            add(120);//23:00
        }
    };

    // 出馆增加量基数
    public static List<Integer> visitOutBase = new ArrayList<Integer>() {
        {
            // 每30分钟增加一次
            add(0);//00:00
            add(0);//01:00
            add(0);//02:00
            add(0);//03:00
            add(0);//04:00
            add(0);//05:00

            // 每分钟增加一次
            add(0);//06:00
            add(0);//07:00
            add(13);//08:00
            add(20);//09:00
            add(30);//10:00
            add(40);//11:00
            add(50);//12:00
            add(28);//13:00
            add(28);//14:00
            add(22);//15:00
            add(40);//16:00
            add(40);//17:00
            add(50);//18:00
            add(50);//19:00
            add(30);//20:00
            add(20);//21:00

            // 每30分钟增加一次
            add(100);//22:00
            add(40);//23:00
        }
    };

    public static BookCategoryEntity randomBookCategory(BookCategoryEntity bookCategory, int total, boolean isAll){
        if(isAll){
            bookCategory.setCirculateTotal(total);
            return bookCategory;
        }
        Random random = new Random();
        int category = random.nextInt(total);
        bookCategory.setCirculateTotal(category);
        return bookCategory;
    }

    public static int card(){
        Random random = new Random();
        return random.nextInt(2);
    }

    public static void main(String[] args) {
//        Random random = new Random();
//        for (int i = 0; i < 24; i++) {
//            int num = random.nextInt(ShowDataUtils.visitIn.get(i));
//            System.out.println(i+":00\t"+(num+ShowDataUtils.visitInBase.get(i)));
//        }

        BigDecimal b1 = new BigDecimal(2);
        BigDecimal b2 = new BigDecimal(3);
        System.out.println(b1.divide(b2, 1,BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
