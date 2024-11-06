### Curdoog--施工中 ~~~

1. **注解驱动的条件生成**：
    - **自动解析注解**： `@Query` 和 `@OrderBy` 注解，自动解析对象中的查询条件和排序条件，减少了手动编写 SQL 的工作量。
    - **灵活的条件类型**：支持多种条件类型（如 `IN`, `LESSEQUAL`, `GREATERTHAN` 等），可以根据业务需求灵活选择。

2. **工厂模式的转换器**：
    - **可扩展性**：使用工厂模式创建不同的条件转换器（`IWrapperTransefer`），便于扩展新的条件类型。
    - **代码复用**：每个转换器负责一种特定的条件类型，提高了代码的复用性和可维护性。

3. **通用的基础控制器**：
    - **简化开发**：提供了一个通用的基础控制器 `DomainBaseController`，封装了常见的 CRUD 操作，减少了重复代码。
    - **易于集成**：业务模块只需要继承 `DomainBaseController` 并实现具体的业务逻辑即可。

4. **分页查询的支持**：
    - **便捷的分页功能**：集成了 MyBatis-Plus 的分页功能，提供了便捷的分页查询方法。
    - **统一的响应格式**：使用 `RespInfo` 封装返回结果，提供统一的响应格式，方便前端处理。


### 如何使用这个工具

1. **引入依赖**：
    - 确保项目中引入了 MyBatis-Plus 和相关依赖。

2. **配置注解**：
    - 在需要查询的对象上使用 `@Query` 和 `@OrderBy` 注解，例如：
      ```java
      public class UserParam {
          @Query(conditionType = ConditionType.EQUAL)
          private String username;
 
          @OrderBy(orderByType = OrderByType.ORDERBYDESC)
          private String createTime;
      }
      ```

3. **继承控制器**：
    - 在业务模块中继承 `DomainBaseController`，实现具体的业务逻辑，例如：
      ```java
      @RestController
      @RequestMapping("/users")
      public class UserController extends DomainBaseController<UserMapper, User, UserParam, UserResponse> {
          // 可以在这里添加自定义的业务方法
      }
      ```

4. **调用方法**：
    - 通过控制器提供的方法进行 CRUD 操作，例如：
      ```java
      @GetMapping("/list")
      public RespInfo<PageInfo<UserResponse>> getUserList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody UserParam param) {
          return baseSelectPage(pageNum, pageSize, param);
      }
 
      @PostMapping("/saveOrUpdate")
      public RespInfo<UserResponse> saveOrUpdateUser(@RequestBody UserParam userParam) {
          return baseSaveOrUpDate(userParam);
      }
 
      @GetMapping("/getById")
      public RespInfo<UserResponse> getUserById(@RequestParam Integer id) {
          return baseGetById(id);
      }
 
      @PostMapping("/deleteById")
      public RespInfo deleteUser(@RequestParam Integer id) {
          return baseDeleteById(id);
      }
      ```

### 一个简单的示例

假设我们有一个 `User` 实体类，包含以下字段：
- `id`：用户ID
- `username`：用户名
- `email`：电子邮件
- `createTime`：创建时间

### 1. MyBatis-Plus 的实现

#### 1.1 实体类

```java
import lombok.Data;

@Data
public class User {
   private Long id;
   private String username;
   private String email;
   private Date createTime;

}
```

#### 1.2 Mapper 接口

```java
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User> {
}
```

#### 1.3 Service 接口

```java
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
}
```

#### 1.4 Service 实现

```java
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
```

#### 1.5 Controller

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> getUserList(@RequestParam String username, @RequestParam String email, @RequestParam String orderBy) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (username != null) {
            queryWrapper.eq("username", username);
        }
        if (email != null) {
            queryWrapper.eq("email", email);
        }
        if ("desc".equals(orderBy)) {
            queryWrapper.orderByDesc("create_time");
        } else {
            queryWrapper.orderByAsc("create_time");
        }
        return userService.list(queryWrapper);
    }

    @PostMapping("/saveOrUpdate")
    public User saveOrUpdateUser(@RequestBody User user) {
        userService.saveOrUpdate(user);
        return user;
    }

    @GetMapping("/getById")
    public User getUserById(@RequestParam Long id) {
        return userService.getById(id);
    }

    @PostMapping("/deleteById")
    public String deleteUser(@RequestParam Long id) {
        userService.removeById(id);
        return "操作成功";
    }
}
```

### 2. 使用Curdoog

#### 2.1 实体类

```java
import lombok.Data;

@Data
public class User {
   private Long id;
   private String username;
   private String email;
   private Date createTime;
}
```

#### 2.2 参数类

```java
import com.haverson.common.mybatis.annotations.OrderBy;
import com.haverson.common.mybatis.annotations.Query;
import com.haverson.common.mybatis.enums.ConditionType;
import com.haverson.common.mybatis.enums.OrderByType;
import lombok.Data;

@Data
public class UserParam {
   @Query(conditionType = ConditionType.EQUAL)
   private String username;

   @Query(conditionType = ConditionType.EQUAL)
   private String email;

   @OrderBy(orderByType = OrderByType.ORDERBYDESC)
   private String createTime;

}
```

#### 2.3 Mapper 接口

```java
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User> {
}
```

#### 2.4 Service 接口

```java
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
}
```

#### 2.5 Service 实现

```java
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haverson.common.mybatis.service.XchdServiceImpl;

@Service
public class UserServiceImpl extends XchdServiceImpl<UserMapper, User> implements UserService {
}
```

#### 2.6 Controller

```java
import com.haverson.common.core.util.RespInfo;
import com.haverson.common.core.util.PageInfo;
import com.haverson.common.service.controller.DomainBaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController extends DomainBaseController<UserMapper, User, UserParam, UserResponse> {

    @GetMapping("/list")
    public RespInfo<PageInfo<UserResponse>> getUserList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestBody UserParam param) {
        return baseSelectPage(pageNum, pageSize, param);
    }

    @PostMapping("/saveOrUpdate")
    public RespInfo<UserResponse> saveOrUpdateUser(@RequestBody UserParam userParam) {
        return baseSaveOrUpDate(userParam);
    }

    @GetMapping("/getById")
    public RespInfo<UserResponse> getUserById(@RequestParam Long id) {
        return baseGetById(id);
    }

    @PostMapping("/deleteById")
    public RespInfo deleteUser(@RequestParam Long id) {
        return baseDeleteById(id);
    }
}
```


