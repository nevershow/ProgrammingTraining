/*************************************************************************
 *  Copyright [2015] <Huangjw>
 *  All rights reserved.
 *
 *  Author: huangjw
 *  File name: User.cpp
 *
 *  Created on July 8, 2015, 08:30 AM
 *************************************************************************/

#include "User.h"
#include <string>
using std::string;

// default constructor
User::User() {
    name_ = password_ = email_ = phone_ = "";
}

User::User(string Name, string Password, string Email, string Phone) {
    name_ = Name;
    password_ = Password;
    email_ = Email;
    phone_ = Phone;
}

// get User data member
string User::getName() const {return name_;}
string User::getPassword() const {return password_;}
string User::getEmail() const {return email_;}
string User::getPhone() const {return phone_;}

// set User data member
void User::setName(string name) {name_ = name;}
void User::setPassword(string password) {password_ = password;}
void User::setEmail(string email) {email_ = email;}
void User::setPhone(string phone) {phone_ = phone;}
