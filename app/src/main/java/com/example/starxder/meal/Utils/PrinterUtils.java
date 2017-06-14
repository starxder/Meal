package com.example.starxder.meal.Utils;

import com.example.starxder.meal.Bean.MealEZ;
import com.example.starxder.meal.Bean.Wxorder;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class PrinterUtils {
    String ip_address;
    int mPort;
    List<MealEZ> mealez;
    Wxorder wxorder;
    private Pos pos;

    public PrinterUtils(String ip_address, int mPort, List<MealEZ> mealez, Wxorder wxorder) {
        this.ip_address = ip_address;
        this.mPort = mPort;
        this.mealez = mealez;
        this.wxorder =wxorder;
    }

    public boolean print() {
        if(!ip_address.equals("")){
            new Thread() {
                @Override
                public void run() {
                    try {
                        pos = new Pos(ip_address, mPort, "GBK");
                        //初始化打印机
                        pos.initPos();


                        pos.bold(true);
                        pos.printTabSpace(2);
                        pos.printWordSpace(1);

                        pos.printText(wxorder.getBody());

                        pos.printLocation(0);
                        pos.printTextNewLine("----------------------------------------------");
                        pos.bold(false);
                        pos.printTextNewLine("订 单 号："+wxorder.getOutTradeNo());
                        pos.printTextNewLine("桌  号："+wxorder.getTablecode()+"号桌");
                        if (wxorder.getIfpay().equals("true")){
                            pos.printTextNewLine("订单状态：已支付");
                        }else{
                            pos.printTextNewLine("订单状态：未支付");
                        }
                        pos.printLine(2);

                        pos.printText("菜品");
                        pos.printLocation(20, 1);
                        pos.printText("     ");
                        pos.printLocation(99, 1);
                        pos.printWordSpace(1);
                        pos.printText("数量");
                        pos.printWordSpace(3);
                        pos.printText("小计");
                        pos.printTextNewLine("----------------------------------------------");


                        for (MealEZ meals : mealez) {
                            pos.printTextNewLine(meals.getMealName());
                            pos.printLocation(20, 1);
                            pos.printText("         ");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText(meals.getMealNum()+"");
                            pos.printWordSpace(3);
                            pos.printText(meals.getMealprice()+"元");
                        }

                        pos.printTextNewLine("----------------------------------------------");
                        pos.printTextNewLine("                                总计"+wxorder.getTotalFee()+"元");
                        pos.printLocation(1);
                        pos.printLine(2);

//                    long divider = 2000;
//                    try {
//                        Thread.sleep(divider);
//                        //打印二维码
//                        pos.qrCode("http://blog.csdn.net/haovip123");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        Thread.sleep(divider);
//                        //切纸
//                        pos.feedAndCut();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }


                        pos.closeIOAndSocket();
                        pos = null;


                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();


            new Thread() {
                @Override
                public void run() {
                    try {
                        pos = new Pos(ip_address, mPort, "GBK");
                        //初始化打印机
                        pos.initPos();

//                    //打印二维码
//                    pos.qrCode("http://blog.csdn.net/haovip123");


                        //切纸
                        pos.feedAndCut();

                        pos.closeIOAndSocket();
                        pos = null;
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
        return  true;
    }

}