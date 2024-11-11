package view;

import mapper.PoetryMapper;
import model.Poetry;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class ManagerFrame  extends JFrame implements ActionListener {

    User user = null;
    // 顶部菜单
    private JMenuBar menuBar;
    private JMenu managerMenu = new JMenu("管理");
    private JMenuItem exitItem = new JMenuItem("退出");

    private JMenu poemsMenu = new JMenu("诗人管理");
    private JMenuItem addPoemsItem = new JMenuItem("添加诗人");
    private JMenuItem editPoemsItem = new JMenuItem("编辑诗人");

    private JMenu dynastyMenu = new JMenu("朝代管理");
    private JMenuItem addDynastyItem = new JMenuItem("添加朝代");
    private JMenuItem editDynastyItem = new JMenuItem("编辑朝代");

    private JMenu categoryMenu = new JMenu("诗词分类管理");
    private JMenuItem addCategoryItem = new JMenuItem("添加");
    private JMenuItem editCategoryItem = new JMenuItem("编辑");

    // 表格组件
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    private JLabel poetryTitleLabel = new JLabel("标题:");
    private JTextField poetryTextFiled = new JTextField();

    private JLabel poemsTipLabel = new JLabel("诗人:");
    private JTextField poemsTextFiled = new JTextField();

    private JLabel dynastyTipLabel = new JLabel("朝代:");
    private JTextField dynastyTextFiled = new JTextField();

    private JLabel categoryTipLabel = new JLabel("诗词类型:");
    private JTextField categoryTextFiled = new JTextField();

    private JLabel searchTipLabel = new JLabel("查询类型:");
    private JComboBox<String> searchTypeBox = new JComboBox<>();
    private JTextField searchTextField = new JFormattedTextField();
    private JButton searchButton = new JButton("查询");
    private JButton searchAllButton = new JButton("查看全部");

    private List<Poetry> curPoetrylists = null;
    private Poetry curPoetry = null;

    private JTextArea poetryContentArea = new JTextArea();

    private JButton addPoetryBtn = new JButton("添加");
    private JButton editPoetryBtn = new JButton("修改");
    private JButton deletePoetryBtn = new JButton("删除");

    public static void main(String[] args) {
        new ManagerFrame(new User("辅导员"));
    }

    public ManagerFrame(User user) {
        this.user = user;
        setupFrame();
        initMenu();
        initButtons();
        querytAllPoetry();
        initPoetryTable();
        initContentViews();
        initPoetryContentArea();
        initPoetryActionBtn();
        this.setVisible(true);
    }

    private void setupFrame() {
        // 大小
        this.setSize(1000,700);
        // 标题
        this.setTitle("欢迎 " + user.getName() + " - 管理员");
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

    private void initMenu() {
        menuBar = new JMenuBar();
        managerMenu.setFont(new Font("宋体", Font.PLAIN, 18));

        poemsMenu.setFont(new Font("宋体", Font.PLAIN, 16));
        addPoemsItem.setFont(new Font("宋体", Font.PLAIN, 16));
        addPoemsItem.addActionListener(this);
        editPoemsItem.setFont(new Font("宋体", Font.PLAIN, 16));
        addPoemsItem.addActionListener(this);
        poemsMenu.add(addPoemsItem);
        poemsMenu.add(editPoemsItem);
        managerMenu.add(poemsMenu);

        dynastyMenu.setFont(new Font("宋体", Font.PLAIN, 16));
        addDynastyItem.setFont(new Font("宋体", Font.PLAIN, 16));
        addDynastyItem.addActionListener(this);
        editDynastyItem.setFont(new Font("宋体", Font.PLAIN, 16));
        editDynastyItem.addActionListener(this);
        dynastyMenu.add(addDynastyItem);
        dynastyMenu.add(editDynastyItem);
        managerMenu.add(dynastyMenu);

        categoryMenu.setFont(new Font("宋体", Font.PLAIN, 16));
        addCategoryItem.setFont(new Font("宋体", Font.PLAIN, 16));
        addCategoryItem.addActionListener(this);
        editCategoryItem.setFont(new Font("宋体", Font.PLAIN, 16));
        editCategoryItem.addActionListener(this);
        categoryMenu.add(addCategoryItem);
        categoryMenu.add(editCategoryItem);
        managerMenu.add(categoryMenu);

        exitItem.setFont(new Font("宋体", Font.PLAIN, 16));
        exitItem.addActionListener(this);
        managerMenu.add(exitItem);
        menuBar.add(managerMenu);
        this.setJMenuBar(menuBar);
    }

    private void initButtons() {
        searchTipLabel.setBounds(50,50,80,40);
        searchTipLabel.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(searchTipLabel);
        // 下拉框设置
        searchTypeBox.addItem("诗人");
        searchTypeBox.addItem("标题");
        searchTypeBox.addItem("朝代");
        searchTypeBox.addItem("类型");
        searchTypeBox.addItem("内容");
        searchTypeBox.setFont(new Font("宋体",Font.BOLD,15));
        searchTypeBox.setBounds(130,52,80,40);
        this.getContentPane().add(searchTypeBox);

        searchTextField.setBounds(210,56,110,30);
        searchTextField.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(searchTextField);

        // 查找
        searchButton.setBounds(380,52,80,40);
        searchButton.setFont(new Font("宋体",Font.BOLD,18));
        searchButton.addActionListener(this);
        this.add(searchButton);
        // 查询全部按钮
        searchAllButton.setBounds(500,52,100,40);
        searchAllButton.setFont(new Font("宋体",Font.BOLD,18));
        searchAllButton.addActionListener(this);
        this.add(searchAllButton);
    }

    private void initContentViews() {
        poetryTitleLabel.setBounds(560, 100, 40,40);
        poetryTitleLabel.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(poetryTitleLabel);
        poetryTextFiled.setBounds(600, 104, 140, 30);
        poetryTextFiled.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(poetryTextFiled);

        poemsTipLabel.setBounds(760, 100, 40, 40);
        poemsTipLabel.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(poemsTipLabel);
        poemsTextFiled.setBounds(800, 104, 140, 30);
        poemsTextFiled.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(poemsTextFiled);

        dynastyTipLabel.setBounds(560, 150, 40, 40);
        dynastyTipLabel.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(dynastyTipLabel);
        dynastyTextFiled.setBounds(600, 154, 140, 30);
        dynastyTextFiled.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(dynastyTextFiled);

        categoryTipLabel.setBounds(760, 150, 80, 40);
        categoryTipLabel.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(categoryTipLabel);
        categoryTextFiled.setBounds(840, 154, 100, 30);
        categoryTextFiled.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(categoryTextFiled);
    }

    private void initPoetryContentArea() {
        poetryContentArea.setLineWrap(true);
        poetryContentArea.setEditable(true);
        poetryContentArea.setBounds(640,200,260,340);
        poetryContentArea.setBackground(Color.cyan);
        poetryContentArea.setFont(new Font("宋体", Font.BOLD, 20));
        poetryContentArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        poetryContentArea.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(poetryContentArea);
    }

    private void initPoetryActionBtn() {
        addPoetryBtn.setBounds(635, 560, 80, 40);
        addPoetryBtn.setFont(new Font("宋体",Font.BOLD,18));
        addPoetryBtn.addActionListener(this);
        this.add(addPoetryBtn);

        editPoetryBtn.setBounds(725,560,80,40);
        editPoetryBtn.setFont(new Font("宋体",Font.BOLD,18));
        editPoetryBtn.addActionListener(this);
        this.add(editPoetryBtn);

        deletePoetryBtn.setBounds(825,560,80,40);
        deletePoetryBtn.setFont(new Font("宋体",Font.BOLD,18));
        deletePoetryBtn.addActionListener(this);
        this.add(deletePoetryBtn);
    }

    private void querytAllPoetry() {
        // 获取诗词数据
        PoetryMapper mapper = new PoetryMapper();
        try {
            curPoetrylists = mapper.querytAllPoetry();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void initPoetryTable() {
        // 表格设置
        String[] tableHeaders = {"诗词标题","诗人","朝代","类型"};
        tableModel = new DefaultTableModel();
        for (String header : tableHeaders) {
            tableModel.addColumn(header);
        }

        for (Poetry poetry : curPoetrylists) {
            tableModel.addRow(new Object[]{poetry.getTitle(), poetry.getPoemsName(), poetry.getDynastyName(), poetry.getCategory()});
        }

        // 创建表格
        table = new JTable(tableModel);
        //表头和表格大小字体设置
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(),30));
        tableHeader.setFont(new Font("宋体",Font.PLAIN,16));

        table.setRowHeight(30);
        table.setFont(new Font("宋体",Font.PLAIN,16));
        //将表格放到容器中
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 120, 500, 500);
        this.add(scrollPane);

        // 给表格添加事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取被点到的行的数组
                int[] rows = table.getSelectedRows();
                curPoetry = curPoetrylists.get(rows[0]);
                poetryContentArea.setText(curPoetry.getContent());
                poetryTextFiled.setText(curPoetry.getTitle());
                poemsTextFiled.setText(curPoetry.getPoemsName());
                dynastyTextFiled.setText(curPoetry.getDynastyName());
                categoryTextFiled.setText(curPoetry.getCategory());
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(exitItem)) {
            // 退出
            this.dispose();
            new LoginFrame();
        } else if (e.getSource().equals(searchButton)) {
            // 获取诗词数据
            PoetryMapper mapper = new PoetryMapper();
            try {
                curPoetrylists = mapper.conditionalSearch(searchTypeBox.getSelectedIndex(), searchTextField.getText());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            remove(scrollPane);
            initPoetryTable();
        } else if (e.getSource().equals(searchAllButton)) {
            searchTextField.setText("");
            remove(scrollPane);
            querytAllPoetry();
            initPoetryTable();
        } else if (e.getSource().equals(addPoetryBtn)) {
            if (poetryTextFiled.getText().isEmpty() ||
                    poemsTextFiled.getText().isEmpty() ||
                    dynastyTextFiled.getText().isEmpty() ||
                    categoryTextFiled.getText().isEmpty() ||
                    poetryContentArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "部分内容为空，请输入对应内容!");
                return;
            }
            PoetryMapper mapper = new PoetryMapper();
            int result = 0;
            try {
                result = mapper.addPoetry(poetryTextFiled.getText(), poetryContentArea.getText(), poemsTextFiled.getText(), dynastyTextFiled.getText(), categoryTextFiled.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "添加诗词成功!");
                remove(scrollPane);
                querytAllPoetry();
                initPoetryTable();
            } else {
                JOptionPane.showMessageDialog(this, "添加诗词失败!");
            }
        } else if (e.getSource().equals(deletePoetryBtn)) {
            if (curPoetry == null) {
                JOptionPane.showMessageDialog(this, "当前未选中诗词!");
            } else {
                int result = 0;
                PoetryMapper mapper = new PoetryMapper();
                try {
                    result = mapper.delePoetry(curPoetry.getPoetryId());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "删除诗词成功!");
                    remove(scrollPane);
                    poetryTextFiled.setText("");
                    poemsTextFiled.setText("");
                    dynastyTextFiled.setText("");
                    categoryTextFiled.setText("");
                    querytAllPoetry();
                    initPoetryTable();
                } else {
                    JOptionPane.showMessageDialog(this, "删除诗词失败!");
                }
            }
        } else if (e.getSource().equals(editPoetryBtn)) {
            if (curPoetry == null) {
                JOptionPane.showMessageDialog(this, "当前未选中诗词!");
                return;
            }
            if (poetryTextFiled.getText().isEmpty() ||
                    poemsTextFiled.getText().isEmpty() ||
                    dynastyTextFiled.getText().isEmpty() ||
                    categoryTextFiled.getText().isEmpty() ||
                    poetryContentArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "部分内容为空，请输入对应内容!");
                return;
            }
            PoetryMapper mapper = new PoetryMapper();
            int result = 0;
            try {
                result = mapper.editPoetry(poetryTextFiled.getText(), poetryContentArea.getText(), poemsTextFiled.getText(), dynastyTextFiled.getText(), categoryTextFiled.getText(), curPoetry.getPoetryId());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "编辑诗词成功!");
                remove(scrollPane);
                poetryTextFiled.setText("");
                poemsTextFiled.setText("");
                dynastyTextFiled.setText("");
                categoryTextFiled.setText("");
                querytAllPoetry();
                initPoetryTable();
            } else {
                JOptionPane.showMessageDialog(this, "编辑诗词失败!");
            }
        }
    }
}
