import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.*;

/**
 *
 */

/**
 * @author Huangjw
 *
 */
public class Calculator {
  private static JButton add = new JButton("+");
  private static JButton sub = new JButton("-");
  private static JButton mul = new JButton("*");
  private static JButton div = new JButton("/");
  private static JButton ok = new JButton("OK");
  private static JTextField oprand1 = new JTextField(); // 第一个操作数
  private static JTextField oprand2 = new JTextField(); // 第二个操作数
  private static JLabel operator = new JLabel(); // 操作符
  private static JTextField result = new JTextField(); // 结果
  private static final JLabel equal = new JLabel("="); // 等号

  // 去除结果多余的零
  private static String deleteZero(double num) {
    DecimalFormat df = new DecimalFormat("0.000");
    return BigDecimal.valueOf(Double.parseDouble(df.format(num)))
           .stripTrailingZeros().toPlainString();
  }

  // 只能输入数字和小数点
  private static KeyListener textListener = new KeyAdapter() {
    public void keyTyped(KeyEvent e) {
      int keyChar = e.getKeyChar();
      if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9
          || e.getKeyChar() == '.' || e.getKeyChar() == '-') {

      } else {
        e.consume(); // 屏蔽掉非法输入
      }
    }
  };

  // 按钮监听
  private static ActionListener actionListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if (e.getSource().equals(ok)) {
        if (!oprand1.getText().isEmpty() && !oprand2.getText().isEmpty()
            && !operator.getText().isEmpty()) {
          double a = Double.parseDouble(oprand1.getText());
          double b = Double.parseDouble(oprand2.getText());
          double res = 0;
          switch (operator.getText()) {
          case "+":
            res = a + b;
            break;
          case "-":
            res = a - b;
            break;
          case "*":
            res = a * b;
            break;
          case "/":
            res = a / b;
            break;
          }
          String s = Double.toString(res);
          result.setText(s == "NaN" || s == "Infinity" ? s
                         : deleteZero(res));
        }
      } else {
        operator.setText(((JButton) e.getSource()).getText());
      }
    }
  };

  private static void init() {
    JFrame frame = new JFrame("Easy calculator"); // 创建一个窗口
    Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize(); // 获得显示器大小对象
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 点击关闭按钮退出程序
    frame.setLocation((displaySize.width - 400) / 2,
                      (displaySize.height - 200) / 2); // 设置居中
    frame.setResizable(false); // 窗口大小不可改变
    frame.setSize(380, 150); // 设置窗口大小
    frame.setLayout(new GridLayout(2, 5, 10, 10)); // 网格布局 2行5列 间距10

    Font font = new Font("Microsoft YaiHei", Font.BOLD, 15); // 字体格式

    oprand1.setBorder(BorderFactory.createLineBorder(Color.black)); // 设置边框颜色
    oprand1.setHorizontalAlignment(0); // 文本居中
    oprand1.addKeyListener(textListener); // 键盘输入监听
    oprand1.setFont(font);

    oprand2.setBorder(BorderFactory.createLineBorder(Color.black));
    oprand2.setHorizontalAlignment(0);
    oprand2.addKeyListener(textListener);
    oprand2.setFont(font);

    operator.setBorder(BorderFactory.createLineBorder(Color.gray));
    operator.setHorizontalAlignment(0);
    operator.setFont(font);
    result.setBorder(BorderFactory.createLineBorder(Color.gray));
    result.setHorizontalAlignment(0);
    result.setFont(font);
    result.setEditable(false);
    equal.setBorder(BorderFactory.createLineBorder(Color.gray));
    equal.setHorizontalAlignment(0);
    equal.setFont(font);

    add.setFont(font);
    sub.setFont(font);
    mul.setFont(font);
    div.setFont(font);
    ok.setFont(font);
    add.setFocusable(false);
    sub.setFocusable(false);
    mul.setFocusable(false);
    div.setFocusable(false);
    ok.setFocusable(false);
    add.addActionListener(actionListener);
    sub.addActionListener(actionListener);
    mul.addActionListener(actionListener);
    div.addActionListener(actionListener);
    ok.addActionListener(actionListener);

    // 添加所有控件
    frame.add(oprand1);
    frame.add(operator);
    frame.add(oprand2);
    frame.add(equal);
    frame.add(result);
    frame.add(add);
    frame.add(sub);
    frame.add(mul);
    frame.add(div);
    frame.add(ok);

    frame.setVisible(true); // 设置窗口为可见的，默认为不可见
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    init();
  }
}
