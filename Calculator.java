import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Calculator extends JFrame {
    private JTextField inputSpace;
    private ArrayList<String > equation = new ArrayList<String >();
    private String  num = "";
    private String prev_operation = "";
    public Calculator(){
        setLayout(null);
        inputSpace = new JTextField();
        inputSpace.setEditable(false);
        inputSpace.setBackground(Color.WHITE);
        inputSpace.setHorizontalAlignment(JTextField.RIGHT);
        inputSpace.setFont(new Font("Arial", Font.BOLD,50));
        inputSpace.setBounds(15, 10, 270, 70);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBounds(15, 90, 270, 235);

        String button_names[] = {"C", "/", "x", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0"};
        JButton buttons[] = new JButton[button_names.length];

        for(int i = 0; i < button_names.length; i++){
            buttons[i] = new JButton (button_names[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            if (button_names[i] == "C"){
                buttons[i].setBackground(Color.RED);
            }else if((4 <= i || i <=6)&&(8 <= i || i <= 10)&&(12 <= i || i <= 14)){
                buttons[i].setBackground(Color.BLACK);
            }else{
                buttons[i].setBackground(Color.GRAY);
            }
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBorderPainted(false);
            buttons[i].setOpaque(true);
            buttons[i].addActionListener(new PadActionListner());
            buttonPanel.add(buttons[i]);
        }
        add(inputSpace);
        add(buttonPanel);


        setTitle("계산가");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(300, 370);
        setLocationRelativeTo(null);
        setResizable(false);

    }
    class PadActionListner implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String operation = e.getActionCommand();
            if (operation.equals("C")){
                inputSpace.setText("");
            }else if(operation.equals("=")) {
                String result = Double.toString(calculate(inputSpace.getText()));
                inputSpace.setText("" + result);
                num = "";
            } else if(operation.equals("+") || operation.equals("-") || operation.equals("x") || operation.equals("/")) {
                if(inputSpace.getText().equals("") && operation.equals("-")){
                    inputSpace.setText(inputSpace.getText() + e.getActionCommand());
                } else if(!inputSpace.getText().equals("") && !prev_operation.equals("+") && !prev_operation.equals("-") && !prev_operation.equals("x") && !prev_operation.equals("/")) {
                    inputSpace.setText(inputSpace.getText() + e.getActionCommand());
                }
            }else {
                inputSpace.setText(inputSpace.getText() + e.getActionCommand());
            }
            prev_operation = e.getActionCommand();
        }
    }

    private void fullTextParsing(String inputText){
        equation.clear();

        for(int i = 0; i < inputText.length(); i++){
            char ch = inputText.charAt(i);

            if(ch == '+' || ch == '-' || ch == 'x' || ch == '/'){
                equation.add(num); //ArrayList에 num 추가
                num = ""; // num String 초기화
                equation.add("" + ch); // ArrayList에 기호 추가
            }else{
                num += ch;  //num이 숫자일때는 그냥 num String에 ch 추가
            }
        }
        equation.add(num); // 최종적으로 num을 ArrayList에 추가
        equation.remove(""); // 맨처음 연산자가 -로 나올때 ""가 추가되므로 ""를 지워주는 코드
    }

    public double calculate(String inputText){
        fullTextParsing(inputText);

        double prev = 0;
        double current = 0;
        String mode ="";

        for(int i = 0; i < equation.size(); i++){
            String s = equation.get(i);
            if(s.equals("+")){
                mode = "add";
            }else if(s.equals("-")){
                mode = "sub";
            }else if(s.equals("x")){
                mode = "mul";
            }else if(s.equals("/")){
                mode = "div";
            }else {
                if((mode.equals("mul") || mode.equals("div")) && !s.equals("+") && !s.equals("-") && !s.equals("x") && !s.equals("/")){
                    Double one = Double.parseDouble(equation.get(i - 2));
                    Double two = Double.parseDouble(equation.get(i));
                    Double result = 0.0;
                    if (mode.equals("mul")){
                        result = one * two;
                    }else if(mode.equals("div")){
                        result = one / two;
                    }
                    equation.add(i + 1, Double.toString(result));
                    for(int j = 0; j < 3; j++){
                        equation.remove(i - 2);
                    }
                    i -= 2;
                }
            }
        }
        for (String s : equation){
            if(s.equals("+")){
                mode = "add";
            }else if(s.equals("-")){
                mode = "sub";
            }else {
                current = Double.parseDouble(s);
                if (mode == "add"){
                    prev += current;
                }else if(mode == "sub"){
                    prev -= current;
                }else {
                    prev = current;
                }
            }
            prev = Math.round(prev * 100000) / 100000.0;
        }
        return prev;
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
