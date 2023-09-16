package com.xiaoye.StonePuzzle;

import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class MainFrame extends JFrame implements KeyListener {
    //此二维数组用于确定图片坐标以方便进行移动
    int[][] arr = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    //此二维数组用于跟坐标数组进行判断，最后比较胜利
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    int row;//0号元素行坐标位置
    int column;//0号元素列坐标位置
    int count;//统计步数的变量

    //用父类的方法，不需要super，因为已经继承到了父类方法
    public MainFrame() {
        //窗体对象.addkeyListener(KeyListener实现类对象)
        //此方法用于监听上下左右
        this.addKeyListener(this);//继承了窗体，又获得了接口，this可以同时调用两者
        //this:当前类对象
        //1）窗体对象
        //2）KeyListtener实现类对象
        //初始化随机打乱
        initFrame();
        //初始窗体化
        initData();
        //绘制游戏界面
        paintView();
        //
        //设置窗体可见
        setVisible(true);
    }

    /**
     * 初始化随机打乱的方法
     */
    public void initData() {
        Random r = new Random();
        //随机交换图片
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                int x = r.nextInt(4);
                int y = r.nextInt(4);
                int temp = arr[i][j];
                arr[i][j] = arr[x][y];
                arr[x][y] = temp;
            }
        }
        //获取随机交换图片之后的0号图片
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == 0) {
                    row = i;
                    column = j;
                }
            }
        }
    }

    /**
     * 初始窗体化的方法
     */
    public void initFrame() {
        //设置窗体大小
        setSize(514, 595);
        //设置窗体标题
        setTitle("石头迷阵游戏");
        //设置窗体界面置顶
        setAlwaysOnTop(true);
        //设置窗体居中
        setLocationRelativeTo(null);
        //设置组件默认值
        setLayout(null);
        //设置窗体关闭
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    /**
     * 绘制游戏界面的方法
     */
    public void paintView() {
        //每一次绘制界面，都清空所有主键(图片),加载新的界面
        getContentPane().removeAll();
        //此判断用于胜利之后加入胜利图标
        if (victory() == true) {
            JLabel Jl = new JLabel(new ImageIcon("G:\\编程精华\\JavaSE进阶\\image\\win.png"));
            Jl.setBounds(124, 230, 288, 88);
            getContentPane().add(Jl);
        }
        //统计步数
        JLabel JLTest = new JLabel("步数统计为:"+count+"步");
        JLTest.setBounds(50,20,100,20);
        getContentPane().add(JLTest);
        //重写游戏按钮
        JButton btn = new JButton("重新游戏");
        btn.setBounds(350,20,100,20);
        getContentPane().add(btn);
        //添加点击监听,函数式接口，可以直接匿名内部类转换为lambda表达式
        btn.addActionListener(e -> {
            count = 0;
            paintView();
            initData();
        });
        //取消按钮焦点
        btn.setFocusable(false);
        //加载石头方块图片
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JLabel image = new JLabel(new ImageIcon("G:\\编程精华\\JavaSE进阶\\image\\" + arr[i][j] + ".png"));
                image.setBounds(50 + 100 * j, 90 + 100 * i, 100, 100);
                getContentPane().add(image);
            }
        }
        //设置背景图片
        JLabel background = new JLabel(new ImageIcon("G:\\编程精华\\JavaSE进阶\\image\\background.png"));
        background.setBounds(26, 30, 450, 484);
        getContentPane().add(background);
        //加载完之后，面板刷新
        getContentPane().repaint();
    }

    /**
     * 移动业务方法
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //判断达成胜利之后，将不在调用移动方法
        if (victory() != true) {
            move(e);
            //每一次移动之后，重写绘制游戏界面
            paintView();
        }
    }

    /**
     * 此方法用于处理移动业务
     */
    private void move(KeyEvent e) {
        if (e.getKeyCode() == 37) {
            //此判断防止索引越界，直接返回结束方法，重新载入
            if (column == 3) {
                return;
            }
            //空白块与右侧数据交换
            //arr[row][column] arr[row][column++]
            int temp = arr[row][column];
            arr[row][column] = arr[row][column + 1];
            arr[row][column + 1] = temp;
            column++;
            count++;
        } else if (e.getKeyCode() == 38) {
            //此判断防止索引越界，直接返回结束方法，重新载入
            if (row == 3) {
                return;
            }
            //空白块与上侧数据交换
            //arr[row][column] arr[row++][column]
            int temp = arr[row][column];
            arr[row][column] = arr[row + 1][column];
            arr[row + 1][column] = temp;
            row++;
            count++;
        } else if (e.getKeyCode() == 39) {
            //此判断防止索引越界，直接返回结束方法，重新载入
            if (column == 0) {
                return;
            }
            //空白块与左侧数据交换
            //arr[row][column] arr[row][column--]
            int temp = arr[row][column];
            arr[row][column] = arr[row][column - 1];
            arr[row][column - 1] = temp;
            column--;
            count++;
        } else if (e.getKeyCode() == 40) {
            //此判断防止索引越界，直接返回结束方法，重新载入
            if (row == 0) {
                return;
            }
            //空白块与下侧数据交换
            //arr[row][column] arr[row][column--]
            int temp = arr[row][column];
            arr[row][column] = arr[row - 1][column];
            arr[row - 1][column] = temp;
            row--;
            count++;
        } else if (e.getKeyCode() == 90) {
            //按Z一键位通关,数组覆盖写法 数组名 = new int[][]{};
            arr = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
        }
    }

    /**
     * 此方法判断游戏是否胜利
     */
    public boolean victory() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (win[i][j] != arr[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    //-------------------下面代码没用---------------------//
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
