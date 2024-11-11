## 系统设计
### 总体设计
本系统主要包括管理员端和客户端，主要包括故事词管理、读者管理、查询管理、朝代管理和诗人类别管理等功能。

#### 管理员端
- 登录：管理员需登录系统后，才可操作系统
- 诗词管理：管理员能够对诗词进行添加、修改和删除等操作，管员员可以查询诗词，包括根据据朝代查询诗词，根据诗人查询诗词，也可以按照诗名、诗人及诗词类型等信息实现精确查询
- 读者管理：在读者注册后，管理员对读者进行管理。管理员可以查询所有读者信息，根据读者姓名查询读者
- 诗词类别管理：管理员添加诗词类别、修改诗词类别、查看诗词类型、删除诗词类别
管理员端功能模块图
![manager_function](http://aqara.cloverkim.com/manager_function.png)

#### 读者端
- 注册：读者输入个信信息进行注册
- 登录：读者需登录系统后，才可操作系统。如果读者忘记密码了，可以进行修改密码
- 诗词管理：读者可以根据诗名搜索诗词，根据诗人搜索诗词，也可以按照诗名、诗人及朝代等信息实现诗词精确搜索
- 个人信息管理：读者可以查看个人信息，对个人信息进行修改，对密码进行修改
读者端功能模块图如图所示![reader_function](http://aqara.cloverkim.com/reader_function.png)

## 数据库设计
#### 诗词表

| 字段          | 类型                       | 是否为空 | 主键        | 中文描述 |
|---------------|----------------------------|----------|:------------|:---------|
| category_name | VARCHAR(50)                | NOT NULL | PRIMARY KEY | 类别名字 |
| category_id   | INT(11)                    | NOT NULL | PRIMARY KEY | 类别编号 |
| user_name     | VARCHAR(50)                | NOT NULL | PRIMARY KEY | 用户姓名 |
| poetry_id     | INT(11)                    | NOT NULL | PRIMARY KEY | 诗词id   |
| dynasty_name  | VARCHAR(50)                | NOT NULL | PRIMARY KEY | 朝代名   |
| dynasty_id    | INT(11)                    | NOT NULL | PRIMARY KEY | 朝代id   |
| title         | VARCHAR(50)                | NOT NULL | 否          | 诗词名称 |
| content       | TEXT                       | NOT NULL | 否          | 诗词内容 |
| user_type     | ENUM('普通用户', '管理员') | NOT NULL | 否          | 用户类型 |
| password      | VARCHAR(255)               | NOT NULL | 否          | 密码     |

