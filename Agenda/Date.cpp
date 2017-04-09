/*************************************************************************
 *  Copyright [2015] <Huangjw>
 *  All rights reserved.
 *
 *  Author: huangjw
 *  File name: Date.cpp
 *
 *  Created on July 8, 2015, 08:30 AM
 *************************************************************************/

#include "Date.h"
#include <iostream>
#include <sstream>
#include <iomanip>
#include <string>
using std::stringstream;
using std::string;
using std::setfill;
using std::setw;

const int permonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

// default constructor
Date::Date() {  // default invalid date
    year_ = month_ = day_ = hour_ = minute_ = 0;
}

Date::Date(int y, int m, int d, int h, int mi) {
    year_ = y;
    month_ = m;
    day_ = d;
    hour_ = h;
    minute_ = mi;
}

// get Date data member
int Date::getYear() const {return year_;}
int Date::getMonth() const {return month_;}
int Date::getDay() const {return day_;}
int Date::getHour() const {return hour_;}
int Date::getMinute() const {return minute_;}

// set Data data member
void Date::setYear(int year) {year_ = year;}
void Date::setMonth(int month) {month_ = month;}
void Date::setDay(int day) {day_ = day;}
void Date::setHour(int hour) {hour_ = hour;}
void Date::setMinute(int minute) {minute_ = minute;}


inline bool isLeap(int year) {
    return (0 == year % 400 || (year % 100 != 0 && 0 == year % 4));
}
bool Date::isValid(Date date) {
    int y = date.year_, m = date.month_, d = date.day_, \
        h = date.hour_, mi = date.minute_;

    if (y < 1000 || y > 9999 || m < 1 || m > 12 || d < 1 || d > 31)
        return false;
    if (h < 0 || h > 23 || mi < 0 || mi > 59)
        return false;

    return (d <= (permonth[m - 1] + (isLeap(y) && 2 == m)));
}

// Date --> string
string Date::dateToString(Date date) {
    string str;
    stringstream ss;
    ss << setw(4) << setfill('0') << date.year_ << '-';
    ss << setw(2) << setfill('0') << date.month_ << '-';
    ss << setw(2) << setfill('0') << date.day_ << '/';
    ss << setw(2) << setfill('0') << date.hour_ << ':';
    ss << setw(2) << setfill('0') << date.minute_;
    ss >> str;
    return str;
}

// string --> Date
Date Date::stringToDate(string dateString) {
    string s = dateString;
    int len = s.length();
    int y, m, d, h, mi;
    for (int i = 0; i < len; ++i)
        if ('-' == s[i] || ':' == s[i] || '/' == s[i])
            s[i] = ' ';
    stringstream ss(s);
    ss >> y >> m >> d >> h >> mi;
    Date tmp(y, m, d, h, mi);
    return tmp;
}

Date& Date::operator =(const Date &date) {
    year_ = date.year_;
    month_ = date.month_;
    day_ = date.day_;
    hour_ = date.hour_;
    minute_ = date.minute_;
    return *this;
}

// operator overload : compare two date
bool Date::operator <(const Date& date) const {return !(*this >= date);}
bool Date::operator <=(const Date& date) const {return !(*this > date);}

bool Date::operator ==(const Date &date) const {
    return (year_ == date.year_ && month_ == date.month_ && day_ == date.day_
            && hour_ == date.hour_ && minute_ == date.minute_);
}

bool Date::operator >(const Date& date) const {
    return (dateToString(*this) > date.dateToString(date));
}

bool Date::operator >=(const Date& date) const {
    return (*this == date || *this > date);
}
