/*************************************************************************
*  Copyright [2015] <Huangjw>
*  All rights reserved.
*
*  Author: huangjw
*  File name: AgendaService.cpp
*
*  Created on July 8, 2015, 08:30 AM
*************************************************************************/

#include "AgendaService.h"
#include <iostream>
using std::string;
using std::list;
using std::cout;

// constructor
AgendaService::AgendaService() {startAgenda();}

// destructor
AgendaService::~AgendaService() {}

void AgendaService::startAgenda() {storage_ = Storage::getInstance();}
void AgendaService::quitAgenda() {
    delete storage_;
    storage_ = NULL;
}

bool AgendaService::userRegister(string userName, string password, \
    string email, string phone) {
    bool flag = storage_->queryUser([&] (const User& u) -> bool {
        return (u.getName() == userName);
    }).empty();

    if (false == flag) {  // the username had been registered
        return false;
    } else {
        storage_->createUser(User(userName, password, email, phone));
        storage_->sync();
        return true;
    }
}

bool AgendaService::userLogIn(string userName, string password) {
    return !(storage_->queryUser([&] (const User & u) -> bool {
        return (userName == u.getName() && password == u.getPassword());
    }).empty());
}

// a user can only delete itself
bool AgendaService::deleteUser(string userName, string password) {
    deleteAllMeetings(userName);
    bool flag = storage_->deleteUser([&] (const User & u) -> bool {
        return (userName == u.getName() && password == u.getPassword());
    });
    storage_->sync();
    return flag;
}

list<User> AgendaService::listAllUsers() {
    return storage_->queryUser([] (const User & u) -> bool {
        return true;
    });
}

list<Meeting> AgendaService::meetingQuery(string userName, string title) {
    return storage_->queryMeeting([&] (const Meeting & m) -> bool {
        return (userName == m.getSponsor() || userName == m.getParticipator())
        && title == m.getTitle();
    });
}

list<Meeting> AgendaService::meetingQuery(string userName, string startDate, \
    string endDate) {
    return storage_->queryMeeting([&] (const Meeting & m) -> bool {
        return (userName == m.getSponsor() || userName == m.getParticipator())
                && m.getStartDate() <= Date::stringToDate(endDate) &&
            m.getEndDate() >= Date::stringToDate(startDate);
    });
}

inline bool check_str(string & s) {
    if (s.length() < 16)
        return false;
    if (s[4] != '-' || s[7] != '-' || s[10] != '/' || s[13] != ':')
        return false;
    for (int i = 0; i < 16; ++i)
        if (4 != i && 7 != i && 10 != i && 13 != i && false == isdigit(s[i]))
            return false;
    return true;
}
bool AgendaService::createMeeting(string userName, string title, string \
    participator, string startDate, string endDate) {
    if (userName == participator)
        return false;  // the same person

    if (false == check_str(startDate) || false == check_str(endDate))
        return false;  // invalid string

    Date sdate = Date::stringToDate(startDate);
    Date edate = Date::stringToDate(endDate);
    if (!Date::isValid(sdate) || !Date::isValid(edate) || sdate >= edate)
        return false;  // invalid date

    bool user = storage_->queryUser([&] (const User & u) -> bool {
        return userName == u.getName();
    }).empty();
    if (true == user) return false;  // not exist such user

    bool part = storage_->queryUser([&] (const User & u) -> bool {
        return participator == u.getName();
    }).empty();
    if (true == part) return false;  // not exist such participator

    bool onlytitle = storage_->queryMeeting([&] (const Meeting & m) -> bool {
        return (m.getTitle() == title);
    }).empty();
    if (false == onlytitle) return false;

    if (false == meetingQuery(userName, startDate, endDate).empty() ||
        false == meetingQuery(participator, startDate, endDate).empty())
        return false;  // user or participator already has meeting

    storage_->createMeeting(Meeting(userName, participator, \
            sdate, edate, title));  // create a meeting successfully
    storage_->sync();
    return true;
}

// delete a specific meeting
bool AgendaService::deleteMeeting(string userName, string title) {
    bool flag = storage_->deleteMeeting([&] (const Meeting & m) -> bool {
       return userName == m.getSponsor() && title == m.getTitle();
    });
    storage_->sync();
    return flag;
}

// delete all meetings about a user, both sponsor and participator
bool AgendaService::deleteAllMeetings(string userName) {
    bool flag = storage_->deleteMeeting([&] (const Meeting & m) -> bool {
        return userName == m.getSponsor() || userName == m.getParticipator();
    });
    storage_->sync();
    return flag;
}

list<Meeting> AgendaService::listAllMeetings(string userName) {
    return storage_->queryMeeting([&] (const Meeting & m) -> bool {
        return userName == m.getSponsor() || userName == m.getParticipator();
    });
}

list<Meeting> AgendaService::listAllParticipateMeetings(string userName) {
    return storage_->queryMeeting([&] (const Meeting & m) -> bool {
        return userName == m.getParticipator();
    });
}

list<Meeting> AgendaService::listAllSponsorMeetings(string userName) {
    return storage_->queryMeeting([&] (const Meeting & m) -> bool {
        return userName == m.getSponsor();
    });
}
