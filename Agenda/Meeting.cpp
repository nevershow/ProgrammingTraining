/*************************************************************************
 *  Copyright [2015] <Huangjw>
 *  All rights reserved.
 *
 *  Author: huangjw
 *  File name: Meeting.cpp
 *
 *  Created on July 8, 2015, 08:30 AM
 *************************************************************************/

#include "Meeting.h"
#include <string>
using std::string;

// default constructor
Meeting::Meeting() {sponsor_ = participator_ = title_ = "";}

Meeting::Meeting(string sponsor, string participator, Date startTime, \
    Date endTime, string title) {
    sponsor_ = sponsor;
    participator_ = participator;
    startDate_ = startTime;
    endDate_ = endTime;
    title_ = title;
}

// get Meeting data member
string Meeting::getSponsor() const {return sponsor_;}
string Meeting::getParticipator() const {return participator_;}
Date Meeting::getStartDate() const {return startDate_;}
Date Meeting::getEndDate() const {return endDate_;}
string Meeting::getTitle() const {return title_;}

// set Meeting data member
void Meeting::setSponsor(string sponsor) {sponsor_ = sponsor;}
void Meeting::setParticipator(string participat) {participator_ = participat;}
void Meeting::setStartDate(Date startTime) {startDate_ = startTime;}
void Meeting::setEndDate(Date endTime) {endDate_ = endTime;}
void Meeting::setTitle(string title) {title_ = title;}
