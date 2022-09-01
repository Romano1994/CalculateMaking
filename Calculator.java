import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator extends JFrame {
    //계산기의 출력부분을 생성
    private JTextField inputSpace;
    private String num = "";
    private ArrayList<String> equation = new ArrayList<String>();

    public Calculator() {
        setLayout(null);

        //계산기의 출력부분을 꾸미는 코드
        inputSpace = new JTextField();
        inputSpace.setEditable(false);
        inputSpace.setBackground(Color.WHITE);
        inputSpace.setFont(new Font("Arial", Font.BOLD, 50));
        inputSpace.setBounds(15, 10, 270, 70);
        add(inputSpace);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBounds(15, 90, 270, 235);

        String button_names[] = {"C", "/", "X", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0"};
        JButton buttons[] = new JButton[button_names.length];

        for (int i = 0; i < button_names.length; i++) {
            buttons[i] = new JButton(button_names[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            if (button_names[i] == "C") {
                buttons[i].setBackground(Color.RED);
            } else if ((4 <= i && i <= 6) || (8 <= i && i <= 10) || (12 <= i && i < 14)) {
                buttons[i].setBackground(Color.BLACK);
            } else {
                buttons[i].setBackground(Color.GRAY);
            }
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBorderPainted(false);
            buttonPanel.add(buttons[i]);
            buttons[i].setOpaque(true);
        }

        add(buttonPanel);

        //전체적인 창을 생성하고 꾸미는 코드
        setTitle("계산기");
        setVisible(true);
        setSize(300, 370);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    class PadActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String operation = e.getActionCommand();
            if (operation.equals("C")) {
                inputSpace.setText("");
            } else if (operation.equals("=")) {
                String result = Double.toString(calculate(inputSpace.getText()));
                inputSpace.setText("" + result);
                num ="";
            } else {
                inputSpace.setText(inputSpace.getText() + e.getActionCommand());
            }
        }
    }

    private void fullTextParsing(String inputText) {
        equation.clear(); // 저장된 숫자 삭제

        for (int i = 0; i < inputText.length(); i++) {
            char ch = inputText.charAt(i);
            if (ch == '-' || ch == '+' || ch == 'X' || ch == '/') {
                equation.add(num);
                num = "";
                equation.add(ch + "");
                //num="" / num="3"/ num="32"가 되고 연산기호가 나올 시 num은 ArrayList에 추가되고 초기화된다
            } else {
                num = num + ch;
                //연산기호가 아닐 경우 그냥 num 문자열에 추가된다
            }
        }
        equation.add(num); // 반복문이 끝나고 최종의 num도 ArrayList에 추가
    }

    public double calculate(String inputText) {
        fullTextParsing(inputText);

        double prev = 0;
        double current = 0;
        String mode = "";

        for (String s : equation) {   //for(A:B)는 B에서 객체를 꺼내서 A에 넣는다는 얘기
            if (s.equals("+")) {
                mode = "add";
            } else if (s.equals("-")) {
                mode = "sub";
            } else if (s.equals("*")) {
                mode = "mul";
            } else if (s.equals("/")) {
                mode = "div";
            } else {
                current = Double.parseDouble(s);
                if (mode == "add") {
                    prev += current;
                }else if (mode == "sub"){
                    prev -= current;
                }else if (mode == "mul"){
                    prev *= current;
                }else if (mode == "div"){
                    prev /= current;
                }else{
                    prev = current;
                }
            }
        }
        return prev;
    }


    public static void main(String[] args) {
        new Calculator();

    }
}
