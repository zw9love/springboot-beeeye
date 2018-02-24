package com.roncoo.education.dao.impl;

import com.roncoo.education.bean.User;
import com.roncoo.education.util.Base.Page;

public interface UserDaoImpl {

    int insert(User user);

    int deleteById(int id);

    int updateById(User roncooUser);

    User selectById(int id);

    Page<User> queryForPage(int i, int j, String string);
}
