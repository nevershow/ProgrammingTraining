/*************************************************************************
 *  Copyright [2015] <Huangjw>
 *  All rights reserved.
 *
 *  Author: huangjw
 *  File name: Storage.cpp
 *
 *  Created on July 8, 2015, 08:30 AM
 *************************************************************************/

#include "Storage.h"
#include <iostream>
#include <fstream>
#include <cstring>
#include <algorithm>
#include <string>
#include <list>
using std::list;
using std::function;
using std::string;
using std::ofstream;
using std::ifstream;
using std::getline;

// singleton
Storage* Storage::instance_ = NULL;
Storage* Storage::getInstance() {
    if (NULL == instance_)
        instance_ = new Storage;
    return instance_;
}

// DISALLOW_COPY_AND_ASSIGN
Storage::Storage(const Storage&) {}
void Storage::operator =(const Storage&) {}

// default constructor
Storage::Storage() {
    readFromFile("./");  // read the data from memory
}

// destructor
Storage::~Storage() {
    sync();  // write the data into memory
    instance_ = NULL;
}

// file read operation, read the data from memory
bool Storage::readFromFile(const char* fpath = "./") {
    string User_csv = "/User.csv";
    string Meeting_csv = "/Meeting.csv";
    User_csv = fpath + User_csv;
    Meeting_csv = fpath + Meeting_csv;
    
    string line;
    ifstream MeetingIn(Meeting_csv.c_str());
    if (!MeetingIn) return false;
    getline(MeetingIn, line);  // the first line is title
    while (getline(MeetingIn, line)) {
        int len = line.size(), ct = 0;
        if (len < 1 && line[0] != '"')             
        	continue;
        string str = "", meet[5];
        for (int i = 1; i + 1 < len; ++i) {
            if ('"' == line[i] && ',' == line[i + 1]) {
                meet[ct] = str;
                ++ct;
                str = "";
                i += 2;
                continue;
            }
            str.push_back(line[i]);
        }
        meet[ct] = str;
        Date sdate = Date::stringToDate(meet[2]);
        Date edate = Date::stringToDate(meet[3]);

        if (!Date::isValid(sdate) || !Date::isValid(edate) || sdate >= edate)
            continue;

		bool has = false;
        for (auto it = meetingList_.begin(); it != meetingList_.end(); ++it)
	  	  if (it->getTitle() == meet[4]) {
	  		  has = true;
	  		  break;
	   	  }
      	if (!has)
      		createMeeting(Meeting(meet[0], meet[1], sdate, edate, meet[4]));
    }
    MeetingIn.close();  // close the file
    
    ifstream UserIn(User_csv.c_str());  // open the file in the current folder
    if (!UserIn) return false;
    getline(UserIn, line);  // the first line is title
        while (getline(UserIn, line)) {
        int len = line.size(), ct = 0;
        if (len && line[0] != '"')
            continue;
        string str = "", user[4];
        for (int i = 1; i + 1 < len; ++i) {
            if ('"' == line[i] && ',' == line[i + 1]) {
                user[ct] = str;
                ++ct;
                str = "";
                i += 2;
                continue;
            }
            str.push_back(line[i]);
        }
        user[ct] = str;

        for (auto it = userList_.begin(); it != userList_.end(); ++it)
            if (it->getName() == user[1])
                continue;

        createUser(User(user[0], user[1], user[2], user[3]));
    }
    UserIn.close();

    return true;
}

// file write operation, write the data into memory
bool Storage::writeToFile(const char* fpath = "./") {
    string User_csv = "/User.csv";
    string Meeting_csv = "/Meeting.csv";
    User_csv = fpath + User_csv;
    Meeting_csv = fpath + Meeting_csv;
    ofstream UserOut(User_csv.c_str());  // open the file in the current folder
    ofstream MeetingOut(Meeting_csv.c_str());

    if (!UserOut || !MeetingOut)
        return false;

    UserOut << "\"name\",\"password\",\"email\",\"phone\"\n";  // title
    for (auto it = userList_.begin(); it != userList_.end(); ++it) {
        UserOut << '"' << it->getName()
            << "\",\"" << it->getPassword()
            << "\",\"" << it->getEmail()
            << "\",\"" << it->getPhone() << "\"\n";
    }

    MeetingOut << "\"sponsor\",\"participator\""
        << ",\"sdate\",\"edate\",\"title\"\n";  // title
    for (auto it = meetingList_.begin(); it != meetingList_.end(); ++it) {
        MeetingOut << '"' << it->getSponsor()
            << "\",\"" << it->getParticipator()
            << "\",\"" << Date::dateToString(it->getStartDate())
            << "\",\"" << Date::dateToString(it->getEndDate())
            << "\",\"" << it->getTitle() << "\"\n";
    }

    UserOut.close();
    MeetingOut.close();  // close the file
    return true;
}

bool Storage::sync() {
    return (writeToFile("./"));  // write the data into memory
}

// create a new user or meeting
void Storage::createUser(const User& u) {
    userList_.push_back(u);
}
void Storage::createMeeting(const Meeting& m) {
    meetingList_.push_back(m);
}

// delete the user, return the number of deleted users
int Storage::deleteUser(function<bool(const User&)> filter) {
    int ct = 0;
    for (auto it = userList_.begin(); it != userList_.end();) {
        if (true == filter(*it)) {
            it = userList_.erase(it);
            ++ct;
        } else {
            ++it;
        }
    }
    if (ct) sync();
    return ct;
}

// delete the meeting, return the number of deleted meetings
int Storage::deleteMeeting(function<bool(const Meeting&)> filter) {
    int ct = 0;
    for (auto it = meetingList_.begin(); it != meetingList_.end();) {
        if (true == filter(*it)) {
            it = meetingList_.erase(it);
            ++ct;
        } else {
            ++it;
        }
    }
    if (ct) sync();
    return ct;
}

// return found users
list<User> Storage::queryUser(function<bool(const User&)> filter) {
    list<User> result;
    for (auto it = userList_.begin(); it != userList_.end(); ++it) {
        if (true == filter(*it))
            result.push_back(*it);
    }
    return result;
}

// return found meetings
list<Meeting> Storage::queryMeeting(function<bool(const Meeting&)> filter) {
    list<Meeting> result;
    for (auto it = meetingList_.begin(); it != meetingList_.end(); ++it) {
        if (true == filter(*it))
            result.push_back(*it);
    }
    return result;
}

// return the number of updated users
int Storage::updateUser(function<bool(const User&)> filter, \
                        function<void(User&) > switcher) {
    int ct = 0;
    for (auto it = userList_.begin(); it != userList_.end(); ++it)
        if (true == filter(*it)) {
            switcher(*it);
            ++ct;
        }
    sync();
    return ct;
}

// return the number of updated meetings
int Storage::updateMeeting(function<bool(const Meeting&)> filter, \
                          function<void(Meeting&) > switcher) {
    int ct = 0;
    for (auto it = meetingList_.begin(); it != meetingList_.end(); ++it)
        if (true == filter(*it)) {
            switcher(*it);
            ++ct;
        }
    sync();
    return ct;
}
