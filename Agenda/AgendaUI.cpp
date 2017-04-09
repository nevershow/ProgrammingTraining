/*************************************************************************
*  Copyright [2015] <Huangjw>
*  All rights reserved.
*
*  Author: huangjw
*  File name: AgendaUI.cpp
*
*  Created on July 8, 2015, 08:30 AM
*************************************************************************/
#include "AgendaUI.h"
#include <iomanip>
#include <algorithm>

using std::string;
using std::getline;
using std::list;
using std::left;
using std::setw;

AgendaUI::AgendaUI() {startAgenda();}

void AgendaUI::startAgenda(void) {
    userName_ = userPassword_ = "";
    agendaService_.startAgenda();
}

void AgendaUI::quitAgenda(void) {
    agendaService_.quitAgenda();
}

void printfHelp() {    
   cout << "----------------------------Agenda------------------------------\n"
    	<< "Action :\n"
    	<< "o   - log out Agenda\n"
        << "dc  - delete Agenda account\n"
        << "lu  - list all Agenda user\n"
        << "cm  - create a meeting\n"
        << "la  - list all meetings\n"
        << "las - list all sponsor meetings\n"
        << "lap - list all participate meetings\n"
        << "qm  - query meeting by title\n"
        << "qt  - query meeting by time interval\n"
    	<< "dm  - delete meeting by title\n"
    	<< "da  - delete all meetings\n"
    	<< "---------------------------------------------------------------\n";
}

void AgendaUI::OperationLoop() {
    if (userName_ == "")
    cout << "----------------------------Agenda-----------------------------\n"
         << "Action :\n"
         << "l   - log in Agenda by user name and password\n"
         << "r   - register an Agenda account\n"
         << "q   - quit Agenda\n"
         << "h   - for help\n"
         << "---------------------------------------------------------------\n\n";
    executeOperation(getOperation());
}

string AgendaUI::getOperation() {
    string in = "";
	while (in.length() < 1) {
		if ("" == userName_) cout << "Agenda : ~$ ";
        else cout << "Agenda@" << userName_ << " : # ";
		getline(cin, in, '\n');
	}
	return in;
}

bool AgendaUI::executeOperation(std::string op) {
    bool flag = true;
    if (userName_ == "") {
        if (op == "l") userLogIn();
        else if (op == "r") userRegister();
        else if (op == "q") quitAgenda();
        else if (op == "h") {
        	printfHelp();
        	OperationLoop();
    	}
        else flag = 0;
    } else if (userName_ != "") {
        if (op == "o") userLogOut();
        else if (op == "dc") deleteUser();
        else if (op == "lu") listAllUsers();
        else if (op == "cm") createMeeting();
        else if (op == "la") listAllMeetings();
        else if (op == "las") listAllSponsorMeetings();
        else if (op == "lap") listAllParticipateMeetings();
        else if (op == "qm") queryMeetingByTitle();
        else if (op == "qt") queryMeetingByTimeInterval();
        else if (op == "dm") deleteMeetingByTitle();
        else if (op == "da") deleteAllMeetings();
        else flag = 0;
    }
    if (flag) {
        return flag;
    } else {
        cout << "Invalid input! please try again\n\n";
        OperationLoop();
    return 0;
  }
}

void AgendaUI::userLogIn(void) {
    cout << "[log in] [user name] [password]\n[log in] ";
    string name, pw;
    cin >> name >> pw;
    cin.ignore();
    bool flag = agendaService_.userLogIn(name, pw);
    if (flag) {
        userName_ = name;
        userPassword_ = pw;
        cout << "[log in] succeed!\n\n";
    	printfHelp();
    } else {
        cout << "[error] log in fail!\n\n";
    }
    OperationLoop();
}

void AgendaUI::userRegister(void) {
    cout << "[register] [user name] [password] [email] [phone]\n";
    cout << "[register] ";
    string name, pw, email, phone;
    cin >> name >> pw >> email >> phone;
    cin.ignore();
    bool flag = agendaService_.userRegister(name, pw, email, phone);
    if (flag) cout << "[register] succeed!\n\n";
    else cout << "[error] register fail!\n\n";
    OperationLoop();
}

void AgendaUI::userLogOut(void) {
    userName_ = "", userPassword_ = "";
    cout << "\n\n";
    OperationLoop();
}

void AgendaUI::deleteUser(void) {
    bool flag = agendaService_.deleteUser(userName_, userPassword_);
    if (flag)
        cout << "\n[delete agenda account] succeed！\n";
    else 
        cout << "\n[error] delete agenda account fail!\n";
    userLogOut();
}

void AgendaUI::listAllUsers(void) {
    cout << "\n[list all users]\n\n";
    cout << "name                  email                phone\n"; 
    list<User> lau = agendaService_.listAllUsers();
    for_each(lau.begin(), lau.end(), [](const User &u) {
    cout << setiosflags(ios::left) << setw(21) << u.getName() << ' ';
    cout << setiosflags(ios::left) << setw(20) << u.getEmail() << ' ';
    cout << setiosflags(ios::left) << u.getPhone() << '\n';
    });
    cout << '\n';
    OperationLoop();
}

void AgendaUI::listAllMeetings(void) {
    cout << "\n[list all meetings]\n\n";
    printMeetings(agendaService_.listAllMeetings(userName_));
    OperationLoop();
}

void AgendaUI::listAllSponsorMeetings(void) {
    cout << "\n[list all sponsor meetings]\n\n";
    printMeetings(agendaService_.listAllSponsorMeetings(userName_));
    OperationLoop();
}

void AgendaUI::listAllParticipateMeetings(void) {
    cout << "\n[list all participate meetings]\n\n";
    printMeetings(agendaService_.listAllParticipateMeetings(userName_));
    OperationLoop();
}

void AgendaUI::createMeeting(void) {
    cout << "\n[create meeting] [title] [participator] ";
    cout << "[start time<yyyy-mm-dd/hh:mm>] ";
    cout << "[end time<yyyy-mm-dd/hh:mm>]\n";
    cout << "[create meeting] ";
    string title, part, sDate, eDate;
    cin >> title >> part >> sDate >> eDate;
    cin.ignore();
    bool flag = agendaService_.createMeeting(userName_, title, part, sDate, eDate);
    if (flag) cout << "\n[create meeting] succeed!\n\n";
    else cout << "\n[error] create meeting fail!\n\n";
    OperationLoop();
}

void AgendaUI::queryMeetingByTitle(void) {
    cout << "\n[query meeting] [title]:\n" << "[query meeting] ";
    string title;
    cin >> title;
    cin.ignore();
    printMeetings(agendaService_.meetingQuery(userName_, title));
    OperationLoop();
}

void AgendaUI::queryMeetingByTimeInterval(void) {
    cout << "\n[query meetings] [start time<yyyy-mm-dd/hh:mm>] "
    << "[end time<yyyy-mm-dd/hh:mm>]\n" << "[query meetings] ";
    string sDate, eDate;
    cin >> sDate >> eDate;
    cin.ignore();
    cout << "\n[query meetings]\n";
    printMeetings(agendaService_.meetingQuery(userName_, sDate, eDate));
    OperationLoop();
}

void AgendaUI::deleteMeetingByTitle(void) {
    cout << "[delete meeting] [title]\n";
    cout << "[delete meeting] ";
    string title;
    cin >> title;
    cin.ignore();
    bool flag = agendaService_.deleteMeeting(userName_, title);
    if (flag) cout << "\n[delete meeting by title] succeed!\n";
    else cout << "\n[error] delete meeting fail!\n";
    OperationLoop();
}

void AgendaUI::deleteAllMeetings(void) {
    bool flag = agendaService_.deleteAllMeetings(userName_);
	if (flag) cout << "\n[delete all meetings] succeed!\n";
    else cout << "\n[delete all meetings] fail!\n";
    OperationLoop();
}

void AgendaUI::printMeetings(list<Meeting> meetings) {
    if (meetings.empty()) {
        cout << "[meetings not found]\n\n";
        return;
    }
    cout << "title      sponsor    participator  start time       end time\n";
    for_each(meetings.begin(), meetings.end(), [](const Meeting & m) {
    cout << setiosflags(ios::left) << setw(10) << m.getTitle() << ' ';
    cout << setiosflags(ios::left) << setw(10) << m.getSponsor() << ' ';
    cout << setiosflags(ios::left) << setw(13) << m.getParticipator() << ' ';
    cout << setiosflags(ios::left) << Date::dateToString(m.getStartDate()) << ' ';
    cout << setiosflags(ios::left) << Date::dateToString(m.getEndDate()) << endl;
    });
}

