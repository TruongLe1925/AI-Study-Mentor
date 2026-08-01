package com._anhtai.aistudymentor.dao;

import com._anhtai.aistudymentor.entity.User;

public interface UserDAO {
    User findByEmail(String email);
}
