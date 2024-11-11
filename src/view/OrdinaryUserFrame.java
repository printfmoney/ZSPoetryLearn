package view;

import mapper.CollectionMapper;
import mapper.PoetryMapper;
import model.Collection;
import model.Poetry;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdinaryUserFrame extends JFrame implements ActionListener {
    User user = null;
    // 顶部菜单
    private JMenuBar menuBar;
    private JMenuItem exitMenu;
    private JMenuItem collectionMenu;
    private JMenu managerMenu;

    // 表格组件
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;

    private JLabel searchTipLabel = new JLabel("查询类型:");
    private JComboBox<String> searchTypeBox = new JComboBox<>();
    private JTextField searchTextField = new JFormattedTextField();
    private JButton searchButton = new JButton("查询");
    private JButton searchAllButton = new JButton("查看全部");

    private JTextArea poetryContentArea = new JTextArea();

    private JLabel collectionTipText = new JLabel();
    private JButton collectionBtn = new JButton();

    private List<Poetry> curPoetrylists = null;
    private Poetry curPoetry = null;

    public static void main(String[] args) {
        new OrdinaryUserFrame(new User("张爽"));
    }

    public OrdinaryUserFrame(User user) {
        this.user = user;
        setupFrame();
        initMenu();
        initButtons();
        querytAllPoetry();
        initPoetryTable();
        initPoetryContentArea();
        initCollectionBtn();
        this.setVisible(true);
    }

    private void setupFrame() {
        // 大小
        this.setSize(1000,700);
        // 标题
        this.setTitle("欢迎 " + user.getName() + " - 普通用户");
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
        managerMenu = new JMenu("管理");
        managerMenu.setFont(new Font("宋体", Font.PLAIN, 18));
        collectionMenu = new JMenuItem("查看收藏诗词");
        collectionMenu.setFont(new Font("宋体", Font.PLAIN, 16));
        collectionMenu.addActionListener(this);
        managerMenu.add(collectionMenu);
        exitMenu = new JMenuItem("退出");
        exitMenu.setFont(new Font("宋体", Font.PLAIN, 16));
        exitMenu.addActionListener(this);
        managerMenu.add(exitMenu);
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
        scrollPane.setBounds(50, 120, 600, 500);
        this.add(scrollPane);

        // 给表格添加事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 获取被点到的行的数组
                int[] rows = table.getSelectedRows();
                curPoetry = curPoetrylists.get(rows[0]);
                poetryContentArea.setText("《" + curPoetry.getTitle() + "》" + "\n" + curPoetry.getDynastyName() + " · " + curPoetry.getPoemsName() + "\n\n" + curPoetry.getContent());
                try {
                    queryCollectionStatus();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void initPoetryContentArea() {
        poetryContentArea.setLineWrap(true);
        poetryContentArea.setEditable(false);
        poetryContentArea.setBounds(700,150,200,400);
        poetryContentArea.setBackground(Color.cyan);
        poetryContentArea.setFont(new Font("宋体", Font.BOLD, 20));
        poetryContentArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        poetryContentArea.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.add(poetryContentArea);
    }

    private void initCollectionBtn() {
        collectionTipText.setBounds(700,580,80,40);
        collectionTipText.setFont(new Font("宋体",Font.PLAIN,16));
        this.add(collectionTipText);
        collectionBtn.setBounds(780,580,100,40);
        collectionBtn.setFont(new Font("宋体",Font.BOLD,18));
        collectionBtn.addActionListener(this);
        collectionBtn.setVisible(false);
        this.add(collectionBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(collectionMenu)) {
            // 收藏夹
            poetryContentArea.setText("");
            searchTextField.setText("");
            collectionTipText.setText("");
            collectionBtn.setVisible(false);
            queryCollectionList();
            remove(scrollPane);
            initPoetryTable();
        } else if (e.getSource().equals(exitMenu)) {
            // 退出
            this.dispose();
            new LoginFrame();
        } else if (e.getSource().equals(searchButton)) {
            poetryContentArea.setText("");
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
            poetryContentArea.setText("");
            searchTextField.setText("");
            collectionTipText.setText("");
            collectionBtn.setVisible(false);
            remove(scrollPane);
            querytAllPoetry();
            initPoetryTable();
        } else if (e.getSource().equals(collectionBtn)) {
            try {
                collectionBtnAction();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void collectionBtnAction() throws SQLException {
        CollectionMapper mapper = new CollectionMapper();
        if (collectionBtn.isSelected()) {
            int result = mapper.removeCollectionAction(user.getName(), curPoetry.getPoetryId());
            if (result > 0) {
                JOptionPane.showMessageDialog(this,"移除收藏成功");
                collectionTipText.setText("未收藏");
                collectionBtn.setText("点击收藏");
            } else {
                JOptionPane.showMessageDialog(this,"移除收藏失败，请重试!");
            }
        } else {
            int result = mapper.addCollectionAction(user.getName(), curPoetry.getPoetryId());
            if (result > 0) {
                JOptionPane.showMessageDialog(this,"添加收藏成功");
                collectionTipText.setText("已收藏");
                collectionBtn.setText("取消收藏");
            } else {
                JOptionPane.showMessageDialog(this,"添加收藏失败，请重试!");
            }
        }
    }

    private void queryCollectionStatus() throws SQLException {
        CollectionMapper mapper = new CollectionMapper();
        Collection collection = null;
        try {
            collection = mapper.queryStatus(user.getName(), curPoetry.getPoetryId());
        }catch (SQLException e) {
            e.printStackTrace();
        }
        if (collection == null) {
            collectionTipText.setText("未收藏");
            collectionBtn.setText("点击收藏");
            collectionBtn.setSelected(false);
        } else {
            collectionTipText.setText("已收藏");
            collectionBtn.setText("取消收藏");
            collectionBtn.setSelected(true);
        }
        collectionBtn.setVisible(true);
    }

    private void queryCollectionList() {
        // 获取诗词数据
        PoetryMapper mapper = new PoetryMapper();
        List<Poetry> poetrylists = null;
        try {
            poetrylists = mapper.querytAllPoetry();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        CollectionMapper mapper1 = new CollectionMapper();
        List<Collection> collectionlists = null;
        try {
            collectionlists = mapper1.queryAllCollection(user.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Poetry> newLists = new ArrayList<>();
        for (Poetry poetry : poetrylists) {
            for (Collection collection : collectionlists) {
                if (poetry.getPoetryId().equals(collection.getPoetryId())) {
                    newLists.add(poetry);
                    break;
                }
            }
        }
        curPoetrylists = newLists;
    }
}
