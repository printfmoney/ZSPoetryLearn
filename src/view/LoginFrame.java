package view;

import mapper.UserMapper;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoginFrame extends JFrame implements ActionListener {

    // 标题logo
    JLabel logoLabel = new JLabel();
    JLabel titleLabel = new JLabel("古诗词学习交流平台");

    JLabel nameLabel = new JLabel("用户名:");
    JLabel passwordLabel = new JLabel("密码:");
    JLabel identityLabel = new JLabel("身份:");

    JTextField nameTextFiled = new JFormattedTextField();
    JPasswordField passwordField = new JPasswordField();

    // 身份下拉框
    JComboBox<String> identityBox = new JComboBox<>();

    JButton loginBtn = new JButton("登录");
    JButton registerBtn = new JButton("注册");

    public static void main(String[] args) {
        new LoginFrame();
    }

    public LoginFrame(){
        setupLoginFrame();
        initLoginFrame();
        this.setVisible(true);
    }

    private void setupLoginFrame() {
        // 大小
        this.setSize(700,600);
        // 标题
        this.setTitle("登录界面");
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
        identityLabel.setBounds(120,342,120,40);
        nameLabel.setFont(new Font("宋体",Font.BOLD,20));
        passwordLabel.setFont(new Font("宋体",Font.BOLD,20));
        identityLabel.setFont(new Font("宋体",Font.BOLD,20));
        this.getContentPane().add(nameLabel);
        this.getContentPane().add(passwordLabel);
        this.getContentPane().add(identityLabel);

        nameTextFiled.setBounds(220,225,300,35);
        nameTextFiled.setFont(new Font("宋体",Font.BOLD,20));
        passwordField.setBounds(220,285,300,35);
        passwordField.setFont(new Font("宋体",Font.BOLD,20));
        this.getContentPane().add(nameTextFiled);
        this.getContentPane().add(passwordField);
        // 身份下拉框设置
        identityBox.addItem("普通用户");
        identityBox.addItem("管理员");
        identityBox.setFont(new Font("宋体",Font.BOLD,15));
        identityBox.setBounds(220,345,300,40);
        this.getContentPane().add(identityBox);
        // 按钮设置
        loginBtn.setBounds(120,440,150,40);
        loginBtn.setFont(new Font("宋体",Font.BOLD,25));
        registerBtn.setBounds(380,440,150,40);
        registerBtn.setFont(new Font("宋体",Font.BOLD,25));

        // 按钮点击事件
        loginBtn.addActionListener(this);
        registerBtn.addActionListener(this);

        // 添加按钮
        this.getContentPane().add(loginBtn);
        this.getContentPane().add(registerBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(loginBtn)){
            System.out.println("登录按钮被点击");
            // 登录事件
            // 获取输入的账号和密码
            if (nameLabel.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()){
                JOptionPane.showMessageDialog(this,"请输入用户名和密码！");
                return;
            }
            String nameString = nameTextFiled.getText();
            String password = new String(passwordField.getPassword());
            // 根据身份登录
            try {
                login(nameString, password, identityBox.getSelectedIndex());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource().equals(registerBtn)){
            // 注册事件
            // 先退出登录页面，再显示注册页面
            this.dispose();

            new RegisterFrame();
        }
    }

    // 根据身份登录功能
    private void login(String userName, String password, Integer identifyIndex) throws Exception {
        UserMapper mapper = new UserMapper();
        User user = mapper.loginAction(userName, password, identityString(identifyIndex));
        if (user != null) {
            JOptionPane.showMessageDialog(this,"登录成功");
            this.dispose();
            if (identifyIndex == 0) {
                // 跳转到普通用户页面
                this.dispose();
                new OrdinaryUserFrame(user);
            } else {
                // 跳转到管理员页面
            }
        } else {
            JOptionPane.showMessageDialog(this, "用户名或密码错误");
        }
    }

    private String identityString(Integer index) {
        if (index == 0) {
            return "普通用户";
        } else  {
            return "管理员";
        }
    }
}
