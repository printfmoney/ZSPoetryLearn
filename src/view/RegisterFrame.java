package view;

import mapper.UserMapper;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame implements ActionListener {

    // 标题logo
    JLabel logoLabel = new JLabel();
    JLabel titleLabel = new JLabel("古诗词学习交流平台");

    JLabel nameLabel = new JLabel("用户名:");
    JLabel passwordLabel = new JLabel("密码:");
    JTextField nameTextFiled = new JFormattedTextField();
    JPasswordField passwordField = new JPasswordField();

    JButton registerBtn = new JButton("注册");
    JButton loginBtn = new JButton("返回登录页面");

    public static void main(String[] args) {
        new RegisterFrame();
    }

    public RegisterFrame() {
        setupLoginFrame();
        initLoginFrame();
        this.setVisible(true);
    }

    private void setupLoginFrame() {
        // 大小
        this.setSize(700,500);
        // 标题
        this.setTitle("注册界面");
        // 居中
        this.setLocationRelativeTo(null);
        // 关闭模式
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 默认组件设置
        this.setLayout(null);
        // 不能最大化
        this.setResizable(false);
        // 设置背景为白色
        this.getContentPane().setBackground(Color.WHITE);
    }

    private void initLoginFrame() {
        logoLabel.setBounds(60,25,140,140);
        logoLabel.setIcon(new ImageIcon(new ImageIcon("image/bg_image.jpeg").getImage().getScaledInstance(logoLabel.getWidth(), logoLabel.getHeight(), Image.SCALE_DEFAULT)));
        this.getContentPane().add(logoLabel);

        titleLabel.setFont(new Font("微软雅黑",Font.BOLD, 40));
        titleLabel.setBounds(250,15,500,200);
        this.add(titleLabel);

        nameLabel.setBounds(120,220,120,40);
        passwordLabel.setBounds(120,280,120,40);
        nameLabel.setFont(new Font("宋体",Font.BOLD,20));
        passwordLabel.setFont(new Font("宋体",Font.BOLD,20));
        this.getContentPane().add(nameLabel);
        this.getContentPane().add(passwordLabel);

        nameTextFiled.setBounds(220,225,300,35);
        nameTextFiled.setFont(new Font("宋体",Font.BOLD,20));
        passwordField.setBounds(220,285,300,35);
        passwordField.setFont(new Font("宋体",Font.BOLD,20));
        this.getContentPane().add(nameTextFiled);
        this.getContentPane().add(passwordField);
        // 按钮设置
        registerBtn.setBounds(280,360,150,40);
        registerBtn.setFont(new Font("宋体",Font.BOLD,25));

        loginBtn.setBounds(300, 420, 100, 20);
        loginBtn.setFont(new Font("宋体",Font.BOLD,10 ));

        // 按钮点击事件
        registerBtn.addActionListener(this);
        loginBtn.addActionListener(this);

        // 添加按钮
        this.getContentPane().add(registerBtn);
        this.getContentPane().add(loginBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(registerBtn)) {
            System.out.println("注册按钮被点击");
            // 获取输入的账号和密码
            if (nameLabel.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()){
                JOptionPane.showMessageDialog(this,"请输入用户名和密码！");
                return;
            }
            try {
                register(nameTextFiled.getText(), new String(passwordField.getPassword()));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            this.dispose();
            new LoginFrame();
        }
    }

    // 注册功能
    private void register(String userName, String password) throws Exception {
        UserMapper mapper = new UserMapper();
        User user = mapper.queryUserByName(userName);
        if (user != null) {
            JOptionPane.showMessageDialog(this,"已存在该用户名，请重新输入!");
        } else {
            int result = mapper.registerAction(userName, password);
            if (result > 0) {
                JOptionPane.showMessageDialog(this,"用户注册成功，欢迎使用!");
                // 注册成功，销毁注册页面，跳转登录页面
                this.dispose();
                new LoginFrame();
            }
        }
    }
}
